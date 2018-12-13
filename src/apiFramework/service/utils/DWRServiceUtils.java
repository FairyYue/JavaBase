package apiFramework.service.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.openqa.selenium.JavascriptExecutor;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.successfactors.saf.framework.PageFactory;
import com.successfactors.saf.framework.utils.StringUtils;
import com.successfactors.saf.library.cdpRevolution.dwr.MyStringObjectProcessor;
import com.successfactors.saf.library.cdpRevolution.utils.MentoringConstants;
import com.successfactors.saf.library.common.utils.EnvUtils;

import apiFramework.action.IDWRAction;
import apiFramework.testBean.dependency_ignore.APIStreamReader;
import apiFramework.testBean.dependency_ignore.HttpResponse;
import net.sf.json.JSONArray;
import net.sf.json.JSONNull;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

/**
 *  Utils for service:
 *  1. Generate URL
 *  2. Generate body by getPayLoadString
 *  3. (Header has been setting and store in service)
 */
@SuppressWarnings("deprecation")
public class DWRServiceUtils {
	
	private static final String COMMON_URL = "/xi/ajax/remoting/call/plaincall/";
	private static final String PACKAGE_SEPARATOR = ".";
	private static final String SERVICE_EXTENSION = "dwr";
	private static final MyStringObjectProcessor processor = new MyStringObjectProcessor();
	private static JsonConfig config = new JsonConfig();
	
	private static Logger logger = LogManager.getLogger(DWRServiceUtils.class);
	
	static 
	{
		config.registerDefaultValueProcessor(String.class, processor);
	}
	
	public static String generateServiceUrl(IDWRAction action)
	{
		return new StringBuilder().append(new EnvUtils().getCurrentEnvUrl()).append(COMMON_URL)
        		.append(action.getControllerProxy()).append(PACKAGE_SEPARATOR)
        		.append(action.getMethodName()).append(PACKAGE_SEPARATOR).append(SERVICE_EXTENSION).toString();
	}
	
	private static Object parseResultFromResponseBody(HttpResponse response) throws Exception
	{
		StringBuffer logContent = new StringBuffer();
		logContent.append("parseResultFromResponseBody:HTTP_RAW_DATA:BeforeParsingBody:StatusCode:");
		logContent.append(response.getStatus().getStatusCode());
		logContent.append(":StatusMessage:");
		logContent.append(response.getStatus().getStatusMessage());
		logger.info(logContent.toString());
		
		JavascriptExecutor executor = (JavascriptExecutor)PageFactory.getWebDriver();
		
		if (executor == null)
		{
			throw new Exception("Javascript executor is null. Have you start up your web browser?");
		}
		
		String body = APIStreamReader.convertStreamToString(response.getBody());
		
		
		logContent = new StringBuffer();
		logContent.append("parseResultFromResponseBody:HTTP_RAW_DATA:StatusCode:");
		logContent.append(response.getStatus().getStatusCode());
		logContent.append(":StatusMessage:");
		logContent.append(response.getStatus().getStatusMessage());
		logContent.append(":Body:");
		logContent.append(body);
		logger.info(logContent.toString());
		
		if (response.getStatus().getStatusCode() > 300)
		{
			throw new Exception("Your DWR call failed." + "HTTP CODE: " + response.getStatus().getStatusCode() 
					+ ". Response body: " + body);
		}
		
		String script = "var func = function(a,b,c){return c;};";
		for (String line : body.split("\n"))
		{
			if (StringUtils.isNullOrEmpty(line))
			{
				continue;
			}
			
			if (line.startsWith("//"))
			{
				continue;
			}
			
			if (line.trim().startsWith("throw "))
			{
				continue;
			}
			
			if (line.trim().startsWith("dwr.engine._remoteHandleCallback"))
			{
				script += line.replace("dwr.engine._remoteHandleCallback", "return func");
			}
			else if (line.trim().startsWith("dwr.engine._remoteHandleException"))
			{
				script += line.replace("dwr.engine._remoteHandleException", "return func");
			}
			else
			{
				script += line;
			}
		}
		
		return executor.executeScript(script);
	}
	
	public static <T> T convertResponseToObject(HttpResponse response, Class<T> clazz) throws Exception
	{
		Object obj = parseResultFromResponseBody(response);
		
		if(obj == null)
		{
			return null;
		}
		
		Gson gson = new Gson();
		
		// If response is a string and the expected entity is not,
		// Try to take the response as json string and convert it to the expected entity directly
		if (obj.getClass().isAssignableFrom(String.class) && ! clazz.isAssignableFrom(String.class))
		{
			return gson.fromJson((String)obj, clazz);
		}
		else
		{
			return gson.fromJson(gson.toJson(obj), clazz);
		}
	}
	
	public static String getJsonObject(HttpResponse response) throws Exception
	{
		Gson gson = new Gson();
		return gson.toJson(parseResultFromResponseBody(response));
	}
	
	public static String generatePayloadString(IDWRAction action, Object...parameters) throws Exception 
	{
		StringBuilder builder = new StringBuilder()
				.append("callCount=1").append(MentoringConstants.LINE_SEPARATOR)
				.append("scriptSessionId=1").append(MentoringConstants.LINE_SEPARATOR)
				.append("c0-scriptName=").append(action.getControllerProxy()).append(MentoringConstants.LINE_SEPARATOR)
				.append("c0-methodName=").append(action.getMethodName()).append(MentoringConstants.LINE_SEPARATOR)
				.append("c0-id=0").append(MentoringConstants.LINE_SEPARATOR)
				.append("batchId=1").append(MentoringConstants.LINE_SEPARATOR);
		
		int index = 0;
		int start = 1;
		if (parameters != null)
		{
			for(Object param : parameters)
			{
				if (param == null)
				{
					builder.append("c0-param").append(index++).append("=null:null")
						.append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (param instanceof String)
				{
					builder.append("c0-param").append(index++).append("=string:")
						.append(encodeStringAsUTF8(param.toString())).append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (param instanceof Boolean)
				{
					builder.append("c0-param").append(index++).append("=boolean:")
						.append(param).append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (param instanceof Long
						|| param instanceof Integer
						|| param instanceof Double
						|| param instanceof Float
						|| param.getClass().isEnum())
				{
					builder.append("c0-param").append(index++).append("=number:")
						.append(param).append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (param.getClass().isArray() || param instanceof List)
				{
					start = appendJsonArray(builder, JSONArray.fromObject(param, config), start);
					String payload = builder.toString();
					payload = payload.replace("c0-e" + (start - 1), "c0-param" + (index++));
					builder = new StringBuilder();
					builder.append(payload);
					start--;
				}
				else 
				{
					start = appendJsonObject(builder, JSONObject.fromObject(param, config), start);
					String payload = builder.toString();
					payload = payload.replace("c0-e" + (start - 1), "c0-param" + (index++));
					builder = new StringBuilder();
					builder.append(payload);
					start--;
				}
			}
		}
		
		return builder.toString();
	}
	
	private static String encodeStringAsUTF8(String source)
	{
		try {
			return URLEncoder.encode(source, "UTF-8").replaceAll("\\+", "%20");
		} catch (UnsupportedEncodingException e) {
			return source;
		}
	}
	
	public static String decodeStringAsUTF8(String source)
	{
		try {
			return URLDecoder.decode(source, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			return source;
		}
	}
	
	public static <T> T convertToObjectFromJson(String json, Class<T> clazz) throws Exception
	{
		Gson gson = new Gson();
		return gson.fromJson(json, clazz);
	}
	
	public static String convertToJsonString(Object obj) 
	{
		GsonBuilder gsonBuilder = new GsonBuilder();  
		gsonBuilder.serializeNulls();  
		Gson gson = gsonBuilder.create();
		return gson.toJson(obj);
	}
	
	public static String convertToEncodedJsonString(Object obj)
	{
		return encodeStringAsUTF8(convertToJsonString(obj));
	}
	
	private static int appendJsonArray(StringBuilder builder, JSONArray array, int start) throws Exception
	{
		if (array == null)
		{
			builder.append("c0-e").append(start).append("=null:null").append(MentoringConstants.LINE_SEPARATOR);
			return start + 1;
		}
		
		String summary = "Array:[";
		for (int index = 0; index < array.size(); index++)
		{
			Object child = array.get(index);
			
			if (child instanceof String)
			{
				builder.append("c0-e").append(start).append("=String:").append(encodeStringAsUTF8(child.toString()))
					.append(MentoringConstants.LINE_SEPARATOR);
				start++;
			}
			else if (child instanceof Long
					|| child instanceof Integer
					|| child instanceof Double
					|| child instanceof Float
					|| child.getClass().isEnum())
			{
				builder.append("c0-e").append(start).append("=number:").append(child.toString())
					.append(MentoringConstants.LINE_SEPARATOR);
				start++;
			}
			else if (child instanceof Boolean)
			{
				builder.append("c0-e").append(start).append("=boolean:").append(child)
					.append(MentoringConstants.LINE_SEPARATOR);
				start++;
			}
			else if (child instanceof JSONObject) 
			{
				start = appendJsonObject(builder, JSONObject.fromObject(child, config), start);
			}
			else if (child instanceof JSONArray) 
			{
				start = appendJsonArray(builder, JSONArray.fromObject(child, config), start);
			}
			else if (child instanceof JSONNull) 
			{
				builder.append("c0-e").append(start).append("=null:null").append(MentoringConstants.LINE_SEPARATOR);
				start++;
			}
			else 
			{
				throw new Exception("Not supported element type of array: " + child.getClass().getName());
			}
			
			summary += "reference:c0-e" + (start - 1) + ",";
		}
		
		if (org.apache.commons.lang3.StringUtils.endsWith(summary, ","))
		{
			summary = summary.substring(0, summary.length() - 1);
		}
		
		summary = "c0-e" + start + "=" + summary + "]" + MentoringConstants.LINE_SEPARATOR;
		builder.append(summary);
		
		return start + 1;
	}

	private static int appendJsonObject(StringBuilder builder, JSONObject object, int start) throws Exception
	{
		if (object.isNullObject())
		{
			builder.append("c0-e").append(start).append("=null:null").append(MentoringConstants.LINE_SEPARATOR);
			return start + 1;
		}
		
		if (object.isArray())
		{
			JSONArray arr = JSONArray.fromObject(object, config);
			start = appendJsonArray(builder, arr, start);
		}
		else
		{
			String summary = "Object_Object:{";
			for (Object key : object.keySet()) {
				
				if (!key.getClass().equals(String.class))
				{
					throw new Exception("Key of non-string type is not supported");
				}
				
				Object value = object.get(key.toString());
				
				if (value == null)
				{
					builder.append("c0-e").append(start++).append("=null:null")
						.append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (value instanceof String)
				{
					builder.append("c0-e").append(start++).append("=string:")
						.append(encodeStringAsUTF8(value.toString()))
						.append(MentoringConstants.LINE_SEPARATOR);
				}
				else if (value instanceof Boolean)
				{
					builder.append("c0-e").append(start++).append("=boolean:")
						.append(value).append(MentoringConstants.LINE_SEPARATOR);	
				}
				else if (value instanceof Integer
						|| value instanceof Double
						|| value instanceof Float
						|| value.getClass().isEnum()
						|| value instanceof Long)
				{
					builder.append("c0-e").append(start++).append("=number:")
						.append(value).append(MentoringConstants.LINE_SEPARATOR);	
				}
				else if (value instanceof JSONObject)
				{
				
					start = appendJsonObject(builder, JSONObject.fromObject(value, config), start);
				}
				else if (value instanceof JSONArray)
				{
					start = appendJsonArray(builder, JSONArray.fromObject(value, config), start);
				}
				else if (value instanceof JSONNull)
				{
					builder.append("c0-e").append(start++).append("=null:null")
						.append(MentoringConstants.LINE_SEPARATOR);	
				}
				else
				{
					throw new Exception("Not support for type " + value.getClass().getName());
				}
				
				summary += key.toString() + ":reference:c0-e" + (start - 1) + ",";
			}
			
			if (org.apache.commons.lang3.StringUtils.endsWith(summary, ","))
			{
				summary = summary.substring(0, summary.length() - 1);
			}
			
			summary = "c0-e" + start + "=" + summary + "}" + MentoringConstants.LINE_SEPARATOR;
			builder.append(summary);
			start++;
		}
		
		return start;
	}

}