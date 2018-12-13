package apiFramework.controller;

import java.util.logging.LogManager;
import java.util.logging.Logger;

import apiFramework.action.mentoringAction.SaveDraftProgramAction;
import apiFramework.response.mentoringResponse.SaveDraftProgramResponse;
import apiFramework.service.DWRBaseService;
import apiFramework.testBean.Program;

public class MentoringAdminController {

	private static Logger logger = null;
	
	// ------------------------- Create Draft Program -----------------------//
	public static String createDraftProgram(Program program, boolean doValidation) throws Exception {
		if (doValidation) {
			program.validate();
		}

		SaveDraftProgramAction action = new SaveDraftProgramAction(program);
		
		DWRBaseService service = DWRBaseService.getInstance("user_cgrant");
		service.performAction(action);
		
		SaveDraftProgramResponse response = action.getResponse();
		response.throwExceptionIfNeeded(response, "Error");

		return response.getProgramId();
	}
}
