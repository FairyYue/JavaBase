package apiFramework.action;

import java.io.InputStream;
import apiFramework.service.utils.DWRServiceUtils;
import apiFramework.testBean.dependency_ignore.APIStreamReader;
import apiFramework.testBean.dependency_ignore.HttpResponse;

/**
 * Base method of DWR call and super method
 */
public class DWRBaseAction {
	
	private HttpResponse rawResponse;
	private Object[] requestParameter;
	private IDWRAction action;
	
	protected DWRBaseAction(IDWRAction action, Object...parameters)
	{
		this.action = action;
		this.requestParameter = parameters;
	}
	
	public HttpResponse getRawResponse() {
		return rawResponse;
	}

	/**
	 * Internal use only. Don't call it in TC level
	 * @param rawResponse
	 */
	public void setRawResponse(HttpResponse rawResponse) {
		this.rawResponse = rawResponse;
	}
	
	public IDWRAction getAction()
	{
		return this.action;
	}
	
	public String getServiceUrl() {
		return DWRServiceUtils.generateServiceUrl(action);
	}
	
	public InputStream generatePayload() throws Exception
	{
		return APIStreamReader.convertStringToStream(
				DWRServiceUtils.generatePayloadString(getAction(), requestParameter));
	}
	
	protected <T> T getResponse(Class<T> clazz) throws Exception
	{
		if (this.getRawResponse() == null) 
		{
			throw new Exception("Current DWR call has no response.");
		}
		
		if (this.getRawResponse().getStatus() == null)
		{
			throw new Exception("Can't determine the status of current DWR call.");
		}
		
		if (((Integer) this.getRawResponse().getStatus()) >= 300) //.getStatusCode()
		{
			throw new Exception("Current DWR call failed with status code " + this.getRawResponse().getStatus());
		}
		
		return DWRServiceUtils.convertResponseToObject(getRawResponse(),
				clazz);
	}
}
