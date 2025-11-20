import java.io.Serializable;

/**
 * Represents an application made by a student for a specific internship.
 * Stores the internship being applied for and the current application status.
 */
public class InternshipApplication implements Serializable {
    private Internship internship;
    private InternshipApplicationStatus internshipApplicationStatus;

    /**
     * Creates a new internship application with the given internship
     * and application status.
     *
     * @param internship              the internship applied for
     * @param internApplicationStatus the initial application status
     */
    public InternshipApplication(Internship internship, InternshipApplicationStatus internApplicationStatus) {
        this.internship = internship;
        this.internshipApplicationStatus = internApplicationStatus;
    }

    /**
     * Returns the internship associated with this application.
     *
     * @return the internship
     */
    public Internship getInternship() {
        return internship;
    }

    /**
     * Returns the current status of the internship application.
     *
     * @return the application status
     */
    public InternshipApplicationStatus getInternshipApplicationStatus() {
        return internshipApplicationStatus;
    }

    /**
     * Updates the status of this internship application.
     *
     * @param internshipApplicationStatus the new status to assign
     */
    public void setInternshipApplicationStatus(InternshipApplicationStatus internshipApplicationStatus) {
        this.internshipApplicationStatus = internshipApplicationStatus;
    }
}
