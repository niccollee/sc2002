/**
 * Console display for the main application menu.
 *
 * Shows the top-level options presented when the application starts.
 */
public class MainDisplay extends ADisplay{
    /**
     * Print the main menu to stdout.
     */
    public void showMenu() {
        String output = """
                ==========================================
                Internship Management System
                ==========================================
                Log in as:
                1)Student
                2)Career Staff
                3)Company Representative
                4)Register as Company Representative
                5)Quit
                ==========================================
                """;
        System.out.println(output);
    }
}
