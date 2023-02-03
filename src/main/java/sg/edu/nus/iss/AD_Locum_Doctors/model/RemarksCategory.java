package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RemarksCategory {
	
	GENERAL("General"), 
	DELETION("Deletion"), 
	ACCEPTION("Accepted Locum"), 
	CANCELLATION("Cancellation"), 
	REMOVED("Removed By Admin"), 
	COMPLETED_JOB("Completed_Job"), 
	PROCESSED_PAYMENT("Processed_Payment");
	
	@Getter
	public String value;
}
