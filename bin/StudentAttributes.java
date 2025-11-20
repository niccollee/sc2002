/**
 * Attributes of a Student used as keys for filtering and sorting.
 *
 * Each constant identifies a Student field or derived value that can be passed
 * to StudentFilter and StudentSorter.
 */
public enum StudentAttributes {
    /** Student identifier (matriculation number). */
    ID,
    /** Full name of the student. */
    NAME,
    /** Numeric year of study. */
    YEAROFSTUDY,
    /** Student's major subject. */
    MAJOR,
    /** Internship(s) the student has applied for. */
    APPLIEDINTERNSHIP,
    /** Internship the student has accepted, if any. */
    ACCEPTEDINTERNSHIP
}
