import java.util.List;

public class CompanyRepDisplay extends ADisplay{
    
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
    }

    
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
    public void showQuit() {
        String output = """
        =========================
        BYE!
        =========================
        """;
        System.out.println(output);
    }
}
