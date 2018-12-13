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

		/*
		 * Define dwr call method,parameter
		 */
		SaveDraftProgramAction action = new SaveDraftProgramAction(program);
		
		/*
		 * Include login();
		 * Use: loginUrl,header,body to login
		 * Get: get/Set cookie,token.
		 */
		DWRBaseService service = DWRBaseService.getInstance("user_cgrant");
		
		/*
		 * generateUrl, SetHeader, generateBody
		 * SendRequest, (return response to action)
		 */
		service.performAction(action);
		
		SaveDraftProgramResponse response = action.getResponse();
		response.throwExceptionIfNeeded(response, "Error");

		return response.getProgramId();
	}
}
