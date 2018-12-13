package apiFramework.response.mentoringResponse;

import apiFramework.response.MentoringBaseResponse;

/**
 *  For get specific info for different dwr call
 */
public class SaveDraftProgramResponse extends MentoringBaseResponse {
	
	private String programId;

	public String getProgramId() {
		return programId;
	}

	public void setProgramId(String programId) {
		this.programId = programId;
	}
}
