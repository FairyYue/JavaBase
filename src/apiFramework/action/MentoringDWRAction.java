package apiFramework.action;

public enum MentoringDWRAction implements IDWRAction {
	SAVE_DRAFT_PROGRAM("POST", "mentoringAdminController", "saveProgramDraft");
	
	private String httpRequestMethod;
	private String controllerProxy;
	private String name;
	
	MentoringDWRAction(String httpRequestMethod, String controller, String name)
	{
		this.httpRequestMethod = httpRequestMethod;
		this.controllerProxy = controller;
		this.name = name;
	}

	public String getHttpRequestMethod()
	{
		return this.httpRequestMethod;
	}
	
	public String getControllerProxy()
	{
		return this.controllerProxy;
	}
	
	public String getMethodName()
	{
		return this.name;
	}
}
