import java.util.List;

public class StudentDisplay implements IDisplay {
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

    public void showViewOpportunities() {
        String output = """
                =========================
                Sort By:
                1)Status
                2)Preferred Majors
                3)Internship Levels
                4)Closing Date
                (print list of internship opportunities according to user specification)
                INTERNSHIP OPPORTUNITIES:
                Internship A : Company : Level : Major : Closing Date

                =========================
                """;
        System.out.println(output);
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
        for (Internship i: internships) {
            System.out.printf(i.getTitle());
            if (i.)
        }
    }
}
