package sg.edu.nus.iss.AD_Locum_Doctors.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum JobStatus {
	OPEN("Open"),
	PENDING_ACCEPTANCE("Pending Acceptance"),
	ACCEPTED("Accepted"),
	COMPLETED_PENDING_PAYMENT("Completed (Pending Payment)"),
	COMPLETED_PAYMENT_PROCESSED("Completed (Payment Processed)"),
	CANCELLED("Cancelled"),
	DELETED("Deleted");

	@Getter
	public String value;
}
