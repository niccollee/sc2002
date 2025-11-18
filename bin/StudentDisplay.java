import java.util.List;
public class StudentDisplay extends ADisplay {
    public void showStudentLogin() {
        String output = """
                =========================
                STUDENT
                Username: (matriculation number)
                Password:
                =========================
                """;
        System.out.println(output);
    }
    public void showMenu() {
        String output = """
                =========================
                1) View Internship Opportunities
                2) Apply For Internship
                3) View Applied Internships
                4) Accept Internship
                5) Request Internship Withdrawal
                6) Change Password
                7) Quit
                =========================
                """;
        System.out.println(output);
    }

    public void showViewOpportunities(List<Internship> internships) {
        System.out.println("=========================");
        System.out.println("Internship Opportunities");
        System.out.println("No. \t:\tTitle \t:\tCompany \t:\tLevel \t:\tMajor \t:\tClosing Date");
        for (int i = 0; i != internships.size(); i++) {
            System.out.println(i + " \t:\t" + 
                internships.get(i).getTitle() + " \t:\t" + 
                internships.get(i).getCompanyName() + "\t\t:\t" +
                internships.get(i).getLevel() + "\t:\t" + 
                internships.get(i).getPreferredMajor() + "\t:\t" + 
                internships.get(i).getAppCloseDate());
        }
        System.out.println("=========================");
    }
    public void showApplyFor() {
        String output = """
                =========================
                INTERNSHIP APPLICATION
                Internship To Apply For: (user response)
                *student can only sign up for 3 max
                =========================
                """;
        System.out.println(output);
    }
    public void showAppliedInternships(Student student) {
        System.out.println("=========================");
        System.out.println("INTERNSHIPS APPLIED");
        System.out.println("Intenship id \t\t:\tTitle \t\t:\tStatus");
        for (InternshipApplication i: student.getAppliedInternships().showAll()) {
            System.out.println(i.getInternship().getId() +  "\t\t\t:\t" + i.getInternship().getTitle() + "\t\t:\t" +i.getInternshipApplicationStatus());
        }
        System.out.println("=========================");
    }
    public void showAcceptInternships(Student student) {
        Internship acceptedInternship = student.getAcceptedInternship();
        System.out.println("=========================");
        if (acceptedInternship != null) {
            System.out.println("Accepted Internship: " +
                acceptedInternship.getTitle() + "\t:\t" +
                acceptedInternship.getCompanyName());
        } else {
            System.out.println("No Accepted Internships");
        }
        System.out.println("=========================");
    }
    public void showSortInternshipsBy() {
        String output = """
                Sort By:
                1)Title
                2)Internship Levels
                3)Preferred Major
                4)Application Open Date
                5)Application Close Date
                6)Status
                7)Company Name
                8)Number of Slots
                """;
        System.out.println("=========================");
        System.out.println(output);
        System.out.println("=========================");
    }
}
