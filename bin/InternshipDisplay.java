import java.util.List;

public class InternshipDisplay extends ADisplay{
    public void showInternships(List<Internship> internships) {
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
    public void showSortMenu(boolean isStudent) {
        String output = """
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
                """;
        String outputIsStudent = 
                """
                9)Quit
                =========================
                """;
        String outputNotStudent = """
                9)Visibility
                10)Quit
                =========================
                """;
        System.out.printf(output);
        if (isStudent) {
            System.out.println(outputIsStudent);
        } else {
            System.out.println(outputNotStudent);
        }
    }
    public void showFilterMenu(boolean isStudent) {
        String output = """
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
                """;
        String outputIsStudent = """
                9)Quit
                =========================
                """;
        String outputNotStudent = """
                9)Visibility
                10)Quit
                =========================
                """;
        System.out.printf(output);
        if (isStudent) {
            System.out.println(outputIsStudent);
        } else {
            System.out.println(outputNotStudent);
        }
    }
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
