/**
 * Attributes of an {@link Internship} used as keys for sorting and filtering.
 *
 * Each constant identifies a field or derived value that can be supplied to
 * {@link InternshipFilter} and {@link InternshipSorter}.
 */
public enum InternshipAttributes {
    /** Numeric identifier of the internship. */
    ID,
    /** Title of the internship position. */
    TITLE,
    /** Complexity level (see {@link InternshipLevel}). */
    LEVEL,
    /** Preferred major for applicants. */
    PREFERREDMAJOR,
    /** Application open date. */
    APPOPENDATE,
    /** Application close date. */
    APPCLOSEDATE,
    /** Current lifecycle status (see {@link Status}). */
    STATUS,
    /** Name of the hiring company. */
    COMPANYNAME,
    /** Number of available slots. */
    NUMSLOT,
    /** Visibility flag indicating whether the internship is shown to applicants. */
    VISIBILITY
}
