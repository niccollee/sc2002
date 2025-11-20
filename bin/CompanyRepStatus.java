/**
 * Represents the approval status of a company representative.
 * A representative can be approved, pending approval or rejected.
 */
public enum CompanyRepStatus {
    /** The representative has been approved and can perform full actions. */
    APPROVED,
    /** The representative's application is pending review. */
    PENDING,
    /** The representative's application has been rejected. */
    REJECT
}
