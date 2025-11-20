/**
 * Attributes of a {@link CompanyRep} that can be used for sorting or filtering.
 * These values are typically passed to helper classes such as
 * {@code CompanyRepFilter} and {@code CompanyRepSorter}.
 */
public enum CompanyRepAttributes {
	/** Company representative ID (usually email). */
	ID,
	/** Company name associated with the representative. */
	NAME,
	/** Department of the representative. */
	DEPARTMENT,
	/** Position or title of the representative. */
	POSITION,
	/** Approval status of the representative (e.g. APPROVED, PENDING, REJECT). */
	REPSTATUS,
	/** Number of internships added by the representative. */
	INTERNSHIPADDED
}
