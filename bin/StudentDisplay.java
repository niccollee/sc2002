import java.util.List;

/**
 * Console display utilities for student-related screens.
 *
 * Provides methods to print login, menu, internship listings and related
 * student views to standard output.
 */
public class StudentDisplay extends ADisplay {
    /**
     * Show the student login prompt.
     */
    public void showStudentLogin() {
        String output = """
                ===================================
                STUDENT
                Username: (matriculation number)
                Password:
                ===================================
                """;
        System.out.println(output);
    }

    /**
     * Show the main student menu.
     */
    public void showMenu() {
        String output = """
                ===================================
                1) View Internship Opportunities
                2) Apply For Internship
                3) View Applied Internships
                4) Accept Internship
                5) Request Internship Withdrawal
                6) Change Password
                7) Quit
                ===================================
                """;
        System.out.println(output);
    }

    /**
     * Display a list of internships.
     *
     * @param internships the internships to display
     */
    public void showViewOpportunities(List<Internship> internships) {
    System.out.println("==============================================================================================");
    System.out.println("Internship Opportunities");
    System.out.printf("%-4s | %-30s | %-20s | %-10s | %-25s | %-12s%n",
            "No.", "Title", "Company", "Level", "Major", "Closing Date");
    System.out.println("==============================================================================================");

    for (int i = 0; i < internships.size(); i++) {
        Internship in = internships.get(i);
        System.out.printf(
            "%-4d | %-30s | %-20s | %-10s | %-25s | %-12s%n",
            i,
            in.getTitle(),
            in.getCompanyName(),
            in.getLevel(),
            in.getPreferredMajor(),
            in.getAppCloseDate()
        );
    }

    System.out.println("==============================================================================================");
}


    /**
     * Display a list of students.
     * 
     * @param student the students to display
     */
    public void showStudentList(List<Student> students) {
    System.out.println("==============================================================");
    System.out.println("Student List");
    System.out.printf("%-10s | %-25s | %-20s | %-4s%n",
            "ID", "Name", "Major", "Year");
    System.out.println("==============================================================");

    for (Student s : students) {
        System.out.printf(
            "%-10s | %-25s | %-20s | %-4d%n",
            s.getId(),
            s.getName(),
            s.getMajor(),
            s.getYearOfStudy()
        );
    }

    System.out.println("==============================================================");
}


    /**
     * Show the internship application header.
     */
    public void showApplyFor() {
        String output = """
                ===========================================
                INTERNSHIP APPLICATION
                Internship To Apply For: (user response)
                *student can only sign up for 3 max
                ===========================================
                """;
        System.out.println(output);
    }

    /**
     * Show internships the student has applied for.
     *
     * @param student the student whose applications are shown
     */
    public void showAppliedInternships(Student student) {
    System.out.println("==========================================================================");
    System.out.println("INTERNSHIPS APPLIED");
    System.out.printf("%-15s | %-15s | %-30s | %-10s%n",
            "Application No.", "Internship ID", "Title", "Status");
    System.out.println("==========================================================================");

    int idx = 0;
    for (InternshipApplication app : student.getAppliedInternships().getAll()) {
        System.out.printf(
            "%-15d | %-15s | %-30s | %-10s%n",
            idx++,
            app.getInternship().getId(),
            app.getInternship().getTitle(),
            app.getInternshipApplicationStatus()
        );
    }

    System.out.println("==========================================================================");
}


    /**
     * Show the student's accepted internship if any.
     *
     * @param student the student whose accepted internship is shown
     */
    public void showAcceptInternships(Student student) {
    Internship acceptedInternship = student.getAcceptedInternship();

    System.out.println("==============================================================");
    System.out.println("ACCEPTED INTERNSHIP");
    System.out.println("==============================================================");

    if (acceptedInternship != null) {
        System.out.printf("%-15s : %s%n", "Title", acceptedInternship.getTitle());
        System.out.printf("%-15s : %s%n", "Company", acceptedInternship.getCompanyName());
        System.out.printf("%-15s : %s%n", "Level", acceptedInternship.getLevel());
        System.out.printf("%-15s : %s%n", "Major", acceptedInternship.getPreferredMajor());
        System.out.printf("%-15s : %s%n", "Closing Date", acceptedInternship.getAppCloseDate());
    } else {
        System.out.println("No accepted internships.");
    }

    System.out.println("==============================================================");
}


    /**
     * Show sorting options for internships.
     */
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
