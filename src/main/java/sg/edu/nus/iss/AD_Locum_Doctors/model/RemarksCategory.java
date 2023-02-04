package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum RemarksCategory {

	GENERAL("General"),
	CREATED("Created"),
	DELETION("Deleted"),
	PENDING("Pending Confirmation"),
	ACCEPTION("Accepted Locum"),
	CANCELLATION("Cancelled"),
	REMOVED("Removed By Admin"),
	COMPLETED_JOB("Completed Job"),
	PROCESSED_PAYMENT("Processed Payment");

	@Getter
	public String value;
}
