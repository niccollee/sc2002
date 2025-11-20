import java.util.Scanner;

/**
 * Main application UI controller.
 *
 * Presents the top-level menu and dispatches user selections to the appropriate
 * sub-UIs until the user chooses to quit.
 */
public class MainUI {
    private Scanner sc;

    /**
     * Run the interactive main menu loop.
     *
     * Reads user input and launches the selected UI actions. Returns when the
     * user selects the quit option.
     */
    public void menu() {
        sc = Input.SC;
        MainDisplay mainDisplay = new MainDisplay();
        InternshipUI internshipUI = new InternshipUI();
        while (true) {
            mainDisplay.showMenu();
            System.out.println("Enter your input: ");
            String input = sc.nextLine();
            switch (input) {
                case "1":
                    new StudentUI(
                        StudentDbMgr.getInstance(), 
                        InternshipDbMgr.getInstance(),
                        InternshipWithdrawalDbMgr.getInstance(), 
                        new StudentPasswordMgr()).start();
                    break;
                case "2":
                    new CareerStaffUI(
                        CareerStaffDbMgr.getInstance(), 
                        InternshipDbMgr.getInstance(), 
                        InternshipWithdrawalDbMgr.getInstance(), 
                        internshipUI,
                        CompanyRepDbMgr.getInstance(),
                        CareerStaffController.getInstance());
                    break;
                case "4":
                    new RepRegisterUI(
                        CompanyRepDbMgr.getInstance()
                    ).start();
                    break;
                case "5":
                    mainDisplay.showQuit();
                    return;
                default:
                    System.out.println("Invalid input!");
                    
            }
        }
    }

    /**
     * Application entry point.
     *
     * Creates a MainUI instance and starts the menu loop.
     *
     * @param args command line arguments (ignored)
     */
    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.menu();
    }

    
}
