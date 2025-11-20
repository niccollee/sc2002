/**
 * Represents a student applying to withdraw from an accepted internship.
 * Tracks the student, the associated internship, and a unique ID.
 */
public class InternshipWithdrawalApplicant {
    private Student student;
    private Internship internship;
    private int id;
    private static int counter = 0;

    /**
     * Creates a new withdrawal applicant with the given student and internship.
     *
     * @param student the student applying for withdrawal
     * @param internship the internship the student wants to withdraw from
     */
    public InternshipWithdrawalApplicant(Student student, Internship internship) {
        this.student = student;
        this.internship = internship;
        this.id = counter++;
    }


    /**
     * Returns the current counter value used for assigning IDs.
     *
     * @return the current counter
     */
    public static int getCounter(){
        return InternshipWithdrawalApplicant.counter;
    }
    
    /**
     * Returns the student applying for withdrawal.
     *
     * @return the student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * Returns the internship the student wants to withdraw from.
     *
     * @return the internship
     */
    public Internship getInternship() {
        return internship;
    }

    /**
     * Returns the unique ID of this withdrawal application.
     *
     * @return the application ID
     */
    public int getId() {
        return id;
    }

    /**
     * Performs the withdrawal action for the student.
     *
     * @return true if the withdrawal was successfully processed
     */
    public boolean withdrawInternship() {
        student.withdrawAcceptedInternship();
        return true;
    }
}
