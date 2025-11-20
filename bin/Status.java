/**
 * Status values used to represent the lifecycle state of internships and related
 * application records.
 */
public enum Status {
    /** Internship has been approved and is available for applicants. */
    APPROVED,
    /** Record is awaiting review or decision. */
    PENDING,
    /** Application or request was rejected. */
    REJECT,
    /** Internship has no available slots remaining. */
    FILLED
}
