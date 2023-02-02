package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JobStatus {
	OPEN("Open"),
	PENDING_CONFIRMATION_BY_CLINIC("Pending Confirmation by Clinic"),
	ACCEPTED("Accepted"),
	COMPLETED_PENDING_PAYMENT("Completed (Pending Payment)"),
	COMPLETED_PAYMENT_PROCESSED("Completed (Payment Processed)"),
	CANCELLED("Cancelled"),
	DELETED("Deleted"),
	REMOVED("Removed (By Admin)");
	@Getter
	public String value;
	
	
}
