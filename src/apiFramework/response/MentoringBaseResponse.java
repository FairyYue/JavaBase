package apiFramework.response;

import bsh.StringUtil;

/**
 * For get basic info from response
 */
public class MentoringBaseResponse {

	private boolean success;
	private String errorMessage;
	
	private String javaClassName;
	private String errorId;
	private String fingerprint;
	private String timestamp;
	private String message;
	
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
	}
	public String getErrorMessage() {
		return errorMessage;
	}
	public void setErrorMessage(String errorMessage) {
		this.errorMessage = errorMessage;
	}
	public String getJavaClassName() {
		return javaClassName;
	}
	public void setJavaClassName(String javaClassName) {
		this.javaClassName = javaClassName;
	}
	public String getErrorId() {
		return errorId;
	}
	public void setErrorId(String errorId) {
		this.errorId = errorId;
	}
	public String getFingerprint() {
		return fingerprint;
	}
	public void setFingerprint(String fingerprint) {
		this.fingerprint = fingerprint;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	public static void throwExceptionIfNeeded(MentoringBaseResponse response, String msg) throws Exception {
		if (response == null) {
			throw new Exception("No response is returned.");
		}
		if (!response.isSuccess()) {
			String errorMsg = response.getErrorMessage();
			String error = msg + " Reason is: " + errorMsg;
			throw new Exception(error);
		}
	}
}
