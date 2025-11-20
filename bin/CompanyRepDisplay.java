/**
 * Handles display screens for company representatives,
 * including login prompts, menus, and system messages.
 */
public class CompanyRepDisplay extends ADisplay{
    
    /**
     * Displays the login screen for a company representative.
     */
    public void showCompanyRepLogin() {
        String output = """
        =========================
        COMPANY REPRESENTATIVE
        Username:
        Password:
        =========================
        """;
        System.out.println(output);
    }

    /**
     * Displays the main menu options for a company representative.
     */
    public void showMenu(){
        String output = """
        =========================
        1) Create Internship Opportunity
        2) Approve/Reject Internship Application
        3) Toggle Internship Visibility
        4) View Internship Opportunities
        5) Change Password
        6) Quit
        =========================
        """;
        System.out.println(output);
    }

    /**
     * Displays the prompt sequence for changing a password.
     */
    public void showChangePassword() {
        String output = """
        =========================
        Enter current password:
        Enter new password:
        Enter new password again:
        Password Changed.
        =========================
        """;
        System.out.println(output);
    }
    
    /**
     * Displays the quit message.
     */
    public void showQuit() {
        String output = """
        =========================
        BYE!
        =========================
        """;
        System.out.println(output);
    }
}
