package apiFramework.testBean;

import java.util.HashMap;
import java.util.Map;

/**
 * Map mentoringProgram in dev code
 *
 */
public class Program {
	private String startDate;
	private String endDate;
	private String description = "";
	private String name;
	private String externalCode = "";
	private String photoID; // Not used.
	private String mentorSignupDate;
	private String menteeSignupDate;
	private String matchingDate;
	private String maximumOfMentee = "1";
	private String maximumOfMentor = "1";
	private String photoId;
	private String[] owners = {};
	private String[] participantTargetGroups = {};
	private String matchingType;
	private boolean mentorshipClosureEnabled = false;
	private boolean jamEnabled = false;
	private String jamGroupName;
	private String jamGroupDescription = "";
	private String mentorApprovalType = null;
	private String mentorApprovalNotice;
	private String programMessageTypeId;
	private Map<String, String> nameI18n;


	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
		
		// Set default nameI18n
		Map<String, String> nameI18n = new HashMap<>();
		nameI18n.put("en_US", name);
		this.setNameI18n(nameI18n);
	}

	public String getExternalCode() {
		return externalCode;
	}

	public void setExternalCode(String externalCode) {
		this.externalCode = externalCode;
	}

	public String getPhotoID() {
		return photoID;
	}

	public void setPhotoID(String photoID) {
		this.photoID = photoID;
	}

	public String getMentorSignupDate() {
		return mentorSignupDate;
	}

	public void setMentorSignupDate(String mentorSignupDate) {
		this.mentorSignupDate = mentorSignupDate;
	}

	public String getMenteeSignupDate() {
		return menteeSignupDate;
	}

	public void setMenteeSignupDate(String menteeSignupDate) {
		this.menteeSignupDate = menteeSignupDate;
	}

	public String getMatchingDate() {
		return matchingDate;
	}

	public void setMatchingDate(String matchingDate) {
		this.matchingDate = matchingDate;
	}

	public String getMaximumOfMentee() {
		return maximumOfMentee;
	}

	public void setMaximumOfMentee(String maximumOfMentee) {
		this.maximumOfMentee = maximumOfMentee;
	}

	public String getMaximumOfMentor() {
		return maximumOfMentor;
	}

	public void setMaximumOfMentor(String maximumOfMentor) {
		this.maximumOfMentor = maximumOfMentor;
	}

	public String getPhotoId() {
		return photoId;
	}

	public void setPhotoId(String photoId) {
		this.photoId = photoId;
	}

	public String[] getOwners() {
		return owners;
	}

	public void setOwners(String[] owners) {
		this.owners = owners;
	}

	public String getMatchingType() {
		return matchingType;
	}

	public void setMatchingType(String matchingType) {
		this.matchingType = matchingType;
	}

	public boolean isMentorshipClosureEnabled() {
		return mentorshipClosureEnabled;
	}

	public void setMentorshipClosureEnabled(boolean mentorshipClosureEnabled) {
		this.mentorshipClosureEnabled = mentorshipClosureEnabled;
	}

	public boolean isJamEnabled() {
		return jamEnabled;
	}

	public void setJamEnabled(boolean jamEnabled) {
		this.jamEnabled = jamEnabled;
	}

	public String getJamGroupName() {
		return jamGroupName;
	}

	public void setJamGroupName(String jamGroupName) {
		this.jamGroupName = jamGroupName;
	}

	public String getJamGroupDescription() {
		return jamGroupDescription;
	}

	public void setJamGroupDescription(String jamGroupDescription) {
		this.jamGroupDescription = jamGroupDescription;
	}

	public String[] getParticipantTargetGroups() {
		return participantTargetGroups;
	}

	public void setParticipantTargetGroups(String[] participantTargetGroups) {
		this.participantTargetGroups = participantTargetGroups;
	}

	public String getMentorApprovalType() {
		return mentorApprovalType;
	}

	public void setMentorApprovalType(String mentorApprovalType) {
		this.mentorApprovalType = mentorApprovalType;
	}

	public String getMentorApprovalNotice() {
		return mentorApprovalNotice;
	}

	public void setMentorApprovalNotice(String mentorApprovalNotice) {
		this.mentorApprovalNotice = mentorApprovalNotice;
	}

	public void validate() throws Exception {
		if (name == null || name.equalsIgnoreCase("")) {
			throw new Exception("Program name can't be null or empty.");
		}
	}

	public static Program getSupervisedProgramReadyForLaunch() {
		Program program = null;

		HashMap<String, String> mentorQuestionI18n = new HashMap<>();
		mentorQuestionI18n.put("defaultValue", "Questions to mentors");
		HashMap<String, String> menteeQuestionI18n = new HashMap<>();
		menteeQuestionI18n.put("defaultValue", "Questions to mentees");

		return program;
	}

	public String getProgramMessageTypeId() {
		return programMessageTypeId;
	}

	public void setProgramMessageTypeId(String programMessageTypeId) {
		this.programMessageTypeId = programMessageTypeId;
	}

	public Map<String, String> getNameI18n() {
		return nameI18n;
	}

	public void setNameI18n(Map<String, String> nameI18n) {
		this.nameI18n = nameI18n;
	}
}
