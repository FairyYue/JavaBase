package apiFramework.action.mentoringAction;

import apiFramework.action.DWRBaseAction;
import apiFramework.action.MentoringDWRAction;
import apiFramework.response.mentoringResponse.SaveDraftProgramResponse;
import apiFramework.testBean.Program;

/**
 * Specific dwr call and parameter
 */
public class SaveDraftProgramAction extends DWRBaseAction {
	
	public SaveDraftProgramAction(Program program)
	{
		super(MentoringDWRAction.SAVE_DRAFT_PROGRAM, program);
	}
	
	public SaveDraftProgramResponse getResponse() throws Exception
	{
		return getResponse(SaveDraftProgramResponse.class);
	}
}
