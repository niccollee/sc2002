import java.util.Scanner;

public class MainUI {
    private Scanner sc;
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

    public static void main(String[] args) {
        MainUI mainUI = new MainUI();
        mainUI.menu();
    }

    
}
