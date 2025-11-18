public class InternshipApplication {
    private Internship internship;
    private InternshipApplicationStatus internshipApplicationStatus;

    public InternshipApplication(Internship internship, InternshipApplicationStatus internApplicationStatus) {
        this.internship = internship;
        this.internshipApplicationStatus = internApplicationStatus;
    }

    // Getter method
    public Internship getInternship() {return internship;}
    public InternshipApplicationStatus getInternshipApplicationStatus() {return internshipApplicationStatus;}

    // Setter method
    public void setInternshipApplicationStatus(InternshipApplicationStatus internshipApplicationStatus) {
        this.internshipApplicationStatus = internshipApplicationStatus;
    }
}
