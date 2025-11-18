public class InternshipWithdrawalApplicants {
    private Student student;
    private Internship internship;
    private int id;
    private static int counter = 0;
    public InternshipWithdrawalApplicants(Student student, Internship internship) {
        this.student = student;
        this.internship = internship;
        this.id = counter++;
    }
    // Getter method for student
    public Student getStudent() {
        return student;
    }

    // Getter method for internship
    public Internship getInternship() {
        return internship;
    }
    // Getter method for id
    public int getId() {
        return id;
    }
    // Method to withdraw internship.
    public boolean withdrawInternship() {
        if (student.getAppliedInternships().showAll().size() == 0) {
            return false;
        }
        student.withdrawAcceptedInternship();
        return true;
    }
}
