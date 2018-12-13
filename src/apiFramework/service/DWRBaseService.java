package apiFramework.service;

import java.net.HttpCookie;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import apiFramework.action.DWRBaseAction;
import apiFramework.testBean.dependency_ignore.APIStreamReader;
import apiFramework.testBean.dependency_ignore.HttpResponse;

/**
 * Base service method:
 * 1. Get HTTP info(Header - tokens) by loginWithPost, and set HTTP info within this service for future dwr sending.
 * 2. Use ServiceCache for performance and do sync handle
 */

@SuppressWarnings("deprecation")
public class DWRBaseService {

	// Default the ajax token valid period to 30 minutes
	// private static final long VALID_PERIOD = 30 * 60 * 1000;
	private static final String AJAX_TOKEN_PATTERN = ".*var\\s+ajaxSecKey\\s*=\\s*\\\"(?<token>[^\\\"]+)\\\"\\s*;.*";
	private static final String NON_SECURE_HINT = "You are using an insecure login method because your login URL might contain your username and password";
	private static boolean useSecureLogin = true;
	
	private Proxy proxy = null;
	private String loginUrl = null;
	private String ajaxToken = null;
	private long lastLoginTime;
	private String cookie;
	
	private static List<String> COOKIE_KEY = new ArrayList<>(); 
	
	static
	{
		COOKIE_KEY.add("loginMethodCookieKey");
		COOKIE_KEY.add("bizxCompanyId");
		COOKIE_KEY.add("JSESSIONID");
		COOKIE_KEY.add("route");
		COOKIE_KEY.add("zsessionid");
	}

	public DWRBaseService(SFUser user) {
		String companyId;
		try {
			companyId = PageFactory.getCompanyInfo().getCompanyId();
		} catch (Exception e) {
			companyId = "dummyCompany";
		}

		loginUrl = new StringBuilder().append(new EnvUtils().getCurrentEnvUrl()).append("/login?company=")
				.append(companyId).append("&username=").append(user.getUsername()).append("&password=")
				.append(user.getPassword()).toString();

		this.setProxy(SFProxyUtils.getAAFProxy());
	}
	
	protected DWRBaseService(SFUser user, String companyId) {
		loginUrl = new StringBuilder().append(new EnvUtils().getCurrentEnvUrl()).append("/login?company=")
				.append(companyId).append("&username=").append(user.getUsername()).append("&password=")
				.append(user.getPassword()).toString();

		this.setProxy(SFProxyUtils.getAAFProxy());
	}
	
	public static DWRBaseService getInstance(String user) throws Exception {
		String companyId;
		try {
			companyId = PageFactory.getCompanyInfo().getCompanyId();
		} catch (Exception e) {
			companyId = "dummyCompany";
		}

		String identifier = user + "_" + companyId;

		if (DWRServiceCache.get(identifier) == null) {
			DWRBaseService service = new DWRBaseService(user, companyId);
			service.login();
			DWRServiceCache.put(identifier, service);
		}

		return DWRServiceCache.get(identifier);
	}

	public static DWRBaseService getInstance(SFUser user, String company) throws Exception {
		String identifier = user.getUsername() + "_" + company;

		if (DWRServiceCache.get(identifier) == null) {
			DWRBaseService service = new DWRBaseService(user, company);
			service.login();
			DWRServiceCache.put(identifier, service);
		}

		return DWRServiceCache.get(identifier);
	}
	
	private void login() throws Exception
	{
		if (useSecureLogin) {
			loginWithPost(); 
		}
		else {
			loginWithGet();
		}
	}

	private void loginWithGet() throws Exception {
		
		HttpRequestInfo info = new HttpRequestInfo(loginUrl, proxy, getSslEnabled());
		HttpRequest request = new HttpRequest(info);
		HttpSendRequest client = new HttpSendRequest();
		HttpResponse response = client.httpGet(request);

		if (response.getStatus().getStatusCode() > 300) {
			throw new Exception("Login failed.");
		}
		
		this.setAjaxToken(parseAjaxTokenFromLoginResponse(response));
		this.setCookie(parseCookieFromLoginResponse(response));
	}
	
	private void loginWithPost() throws Exception {
		
		HttpRequestInfo info = new HttpRequestInfo(loginUrl, proxy, getSslEnabled());
		HeaderCollection hs = new HeaderCollection();
		hs.addHeader(new Header("Content-Type", "application/x-www-form-urlencoded"));
		HttpRequest request = new HttpRequest(info, hs);
		HttpSendRequest client = new HttpSendRequest();
		HttpResponse response = client.httpPost(request);

		if (response.getStatus().getStatusCode() > 300 && response.getStatus().getStatusCode() != 302) {
			throw new Exception("Login failed.");
		}
		
		if (response.getStatus().getStatusCode() == 302) {
			
			this.setCookie(parseCookieFromLoginResponse(response));
			
			List<Header> headers = response.getAllHeader().getHeader("location");
			if (headers == null || headers.size() == 0) {
				throw new Exception("Can't get redirect URL.");
			}
			
			info = new HttpRequestInfo(new EnvUtils().getCurrentEnvUrl() + headers.get(0).getHeaderValue(), proxy, getSslEnabled());
			HeaderCollection hsCookie = new HeaderCollection();
			hsCookie.addHeader(new Header("Cookie", this.getCookie()));
			request = new HttpRequest(info, hsCookie);
			response = client.httpGet(request);
			
			if (response.getStatus().getStatusCode() > 300) {
				throw new Exception("Login failed. Status code is: " + response.getStatus().getStatusCode());
			}
		}
		
		this.setAjaxToken(parseAjaxTokenFromLoginResponse(response));
	}
	
    private boolean getSslEnabled()
    {
    	boolean enabled;
        String sslEnabled = ConfigProperties.getProperty("sslEnabled");
        if (sslEnabled != null)
        {
        	enabled = Boolean.valueOf(sslEnabled);
        }
        else
        {
        	enabled = true;
        }
        
        return enabled;
    }

	public HttpResponse performAction(DWRBaseAction action) throws Exception {
		String url = action.getServiceUrl();
		HttpRequestInfo info = new HttpRequestInfo(url, proxy, getSslEnabled());

		HeaderCollection headers = new HeaderCollection();
		headers.addHeader(new Header("X-Ajax-Token", getAjaxToken()));
		headers.addHeader(new Header("Cookie", getCookie()));
		headers.addHeader(new Header("Content-Type", "text/plain"));

		HttpRequest request = new HttpRequest(info, headers, action.generatePayload());

		HttpSendRequest client = new HttpSendRequest();

		if (action.getAction().getHttpRequestMethod() == HttpRequestMethod.POST) {
			HttpResponse response = client.httpPost(request);
			action.setRawResponse(response);
			return response;
		} else {
			throw new Exception("Not supported yet.");
		}
	}

	public Proxy getProxy() {
		return proxy;
	}

	public void setProxy(Proxy proxy) {
		this.proxy = proxy;
	}

	public String getAjaxToken() throws Exception {
		/*
		 * String identifier = getIdentifier(); DWRServiceCredentials credential =
		 * DWRServiceCache.get(identifier);
		 * 
		 * if (credential == null) { login(); credential = new
		 * DWRServiceCredentials(ajaxToken, cookie); DWRServiceCache.put(identifier,
		 * credential);
		 * 
		 * return ajaxToken; } else { return credential.getAjaxToken(); }
		 */
		return this.ajaxToken;
	}

	public void setAjaxToken(String ajaxToken) {
		this.ajaxToken = ajaxToken;
		// setLastLoginTime(System.currentTimeMillis());
	}

	public String getCookie() throws Exception {
		/*
		 * String identifier = getIdentifier(); DWRServiceCredentials credential =
		 * DWRServiceCache.get(identifier);
		 * 
		 * if (credential == null) { login(); credential = new
		 * DWRServiceCredentials(ajaxToken, cookie); DWRServiceCache.put(identifier,
		 * credential);
		 * 
		 * return cookie; } else { return credential.getCookies(); }
		 */
		return this.cookie;
	}

	public void setCookie(String cookie) {
		this.cookie = cookie;
	}

	/*
	 * private boolean needsLogin() { return this.getAjaxToken() == null ||
	 * this.getCookie() == null || System.currentTimeMillis() - getLastLoginTime() >
	 * VALID_PERIOD; }
	 */

	private String parseCookieFromLoginResponse(HttpResponse response) throws Exception {
		String cookieStr = "";
		for (HttpCookie cookie : response.getCookies()) {
			if (COOKIE_KEY.contains(cookie.getName()))
			{
				cookieStr += cookie.getName() + "=" + cookie.getValue() + ";";
			}
		}
		
		if (!cookieStr.contains("loginMethodCookieKey=PWD"))
		{
			cookieStr += "loginMethodCookieKey=PWD;";
		}

		return cookieStr;
	}

	private String parseAjaxTokenFromLoginResponse(HttpResponse response) throws Exception {
		String responseBody = APIStreamReader.convertStreamToString(response.getBody());
		Pattern p = Pattern.compile(AJAX_TOKEN_PATTERN);

		for (String line : responseBody.split(MentoringConstants.LINE_SEPARATOR)) {
			Matcher m = p.matcher(line);
			if (m.matches()) {
				return m.group("token");
			}
		}

		throw new Exception("Failed to extract ajaxSecKey from login response.");
	}
	
	protected boolean isNonSecureHintContainsInResponseBody(HttpResponse response) throws Exception {
		String responseBody = APIStreamReader.convertStreamToString(response.getBody());
		return responseBody.contains(NON_SECURE_HINT);
	}
}
