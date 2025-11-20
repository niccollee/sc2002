import java.util.List;

/**
 * Console display for internships and related menus.
 *
 * Provides methods to print a formatted internship table and several
 * menu prompts used by student and staff UIs.
 */
public class InternshipDisplay extends ADisplay {
    /**
     * Print a formatted table of internships to stdout.
     *
     * @param internships the list of internships to display
     */
    public void showInternships(List<Internship> internships) {
        System.out.println("==============================================================");
        System.out.printf("%-3s | %-35s | %-20s | %-10s | %-12s | %-15s%n",
                "ID", "Title", "Company", "Level", "Closing Date", "Status");
        System.out.println("==============================================================");

        for (Internship internship : internships) {
            System.out.printf("%-3s | %-35s | %-20s | %-10s | %-12s | %-15s%n",
                    internship.getId(),
                    internship.getTitle(),
                    internship.getCompanyName(),
                    internship.getLevel(),
                    internship.getAppCloseDate(),
                    internship.getStatus());
        }

        System.out.println("==============================================================");
    }

    /**
     * Print the sort options menu.
     *
     * @param isStudent if true, print the student-specific sort options; otherwise
     *                  print full options
     */
    public void showSortMenu(boolean isStudent) {
        String outputNotStudent = """
                =========================
                Sort By:
                1)Title
                2)Level
                3)Preferred Major
                4)App Open Date
                5)App Close Date
                6)Status
                7)Company Name
                8)Number of Slots
                9)Visibility
                10)Quit
                =========================
                """;
        String outputIsStudent = """
                =========================
                Sort By:
                1)Title
                2)Company Name
                3)Number of Slots
                4)Level
                5)Application Open Date
                6)Application Close Date
                7)Quit
                =========================
                """;
        if (isStudent) {
            System.out.println(outputIsStudent);
        } else {
            System.out.println(outputNotStudent);
        }
    }

    /**
     * Print the filter options menu.
     *
     * @param isStudent if true, print the student-specific filter options;
     *                  otherwise print full options
     */
    public void showFilterMenu(boolean isStudent) {
        String outputNotStudent = """
                =========================
                Filter By:
                1)Title
                2)Level
                3)Preferred Major
                4)App Open Date
                5)App Close Date
                6)Status
                7)Company Name
                8)Number of Slots
                9)Visibility
                10)Quit
                =========================
                """;
        String outputIsStudent = """
                =========================
                Filter By:
                1)Title
                2)Company Name
                3)Number of Slots
                4)Level
                5)Application Open Date
                6)Application Close Date
                7)Quit
                =========================
                """;
        if (isStudent) {
            System.out.println(outputIsStudent);
        } else {
            System.out.println(outputNotStudent);
        }
    }

    /**
     * Print the internship level selection menu.
     */
    public void showLevelMenu() {
        String output = """
                =========================
                Level:
                1)Basic
                2)Intermediate
                3)Advance
                =========================
                """;
        System.out.println(output);
    }

    /**
     * Print the date filter selection menu (before/after).
     */
    public void showDateMenu() {
        String output = """
                =========================
                Filter date by:
                1)Before date
                2)After date
                =========================
                """;
        System.out.println(output);
    }

    /**
     * Print the status filter selection menu.
     */
    public void showStatusMenu() {
        String output = """
                =========================
                Filter status by:
                1)Approved
                2)Pending
                3)Rejected
                =========================
                """;
        System.out.println(output);
    }

    /**
     * Print the visibility filter selection menu.
     */
    public void showVisibilityMenu() {
        String output = """
                =========================
                Filter visibility by:
                1)Visible
                2)Not visible
                =========================
                """;
        System.out.println(output);
    }
}
