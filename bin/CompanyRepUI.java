import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.*;
// import the classes over //

public class CompanyRepUI {
    private CompanyRepDisplay companyRepDisplay;
    private InternshipUI internshipUI;
    private CompanyRep companyRep;
    private CompanyRepDbMgr companyRepDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;

    public CompanyRepUI(
        CompanyRepDbMgr companyRepDbMgr,
        InternshipDbMgr internshipDbMgr,
        InternshipWithdrawalDbMgr internshipWithdrawalDbMgr
        ) {
        companyRepDisplay = new CompanyRepDisplay();
        internshipUI = new InternshipUI();
        this.companyRepDbMgr = companyRepDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        CompanyRepPasswordMgr companyRepPasswordMgr = new CompanyRepPasswordMgr();
        Scanner sc = Input.SC;
        companyRep = login(sc, companyRepPasswordMgr);
        /* If login details is invalid, continue prompting user to login for 3 more
         * times. Afterwards return back to main page.
         */
        if (companyRep == null) {
            for (int i = 0; i != 3; i++) {
                companyRep = login(sc, companyRepPasswordMgr);
                if (companyRep != null) {
                    break;
                }
                if (i == 2) {
                    return;
                }
            }
        }
        int choice = menu(sc);
        while (choice == -1) {
            System.out.println("Invalid input!");
            choice = menu(sc);
        }
        while (true) {
            switch (choice) {
            case 1:
                constructInternship(companyRep, sc);
                break;
            case 2:
                approveRejectInternship(companyRep, sc);
                break;
            case 3:
                toggleInternshipVisibility(companyRep);
                break;
            case 4:
                viewInternshipOpps(companyRep, sc);
                break;
            case 5:
                changePassword(companyRep, sc, companyRepPasswordMgr);
                break;
            case 6:
                companyRepDisplay.showQuit();
                return;
            }
            choice = menu(sc);
            while (choice == -1) {
                System.out.println("Invalid input!");
                choice = menu(sc);
            }
        }        
    }
    public CompanyRep login(Scanner sc, CompanyRepPasswordMgr companyRepPasswordMgr) {
        System.out.println("=========================");
        System.out.println("COMPANY REP");
        System.out.println("Enter Username: (email address)");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        CompanyRep companyRep = companyRepDbMgr.get(username);
        if (companyRep != null) {
            if (companyRepPasswordMgr.validate(companyRep, password)) {
                return companyRep;
            }
        } else {
            System.out.println("Company Rep does not exist!");
        }
        return null;
    }
    public int menu(Scanner sc) {
        companyRepDisplay.showMenu();
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice > 0 && choice < 8) {
            return choice;
        }
        return -1;
    }
    public void constructInternship(CompanyRep rep, Scanner sc) {
        System.out.println("=========================");
        System.out.print("Internship title: ");
        String title = sc.nextLine();
        System.out.println();
        System.out.print("Description: ");
        String description = sc.nextLine();
        System.out.println();
        System.out.print("""
            Internship level:
                1) Basic
                2) Intermediate
                3) Advanced
                """);
        int level_no = sc.nextInt();
        InternshipLevel level = null;
switch(level_no) {
    case 1:
        level = InternshipLevel.BASIC;
        break;
    case 2:
        level = InternshipLevel.INTERMEDIATE;
        break;
    case 3:
        level = InternshipLevel.ADVANCE;
        break;
    default:
        System.out.println("Invalid choice!");
}
        System.out.println();
        System.out.print("Preferred major: ");
        String preferredMajor = sc.nextLine();
        System.out.println();
        System.out.print("Application opening date (dd-mm-yyyy format): "); 
        String date1 = sc.nextLine();
        Date appOpenDate = new SimpleDateFormat("dd-MM-yyyy").parse(date1);  
        System.out.println();
        System.out.print("Application closing date (dd-mm-yyyy format): "); 
        String date2 = sc.nextLine();
        Date appCloseDate = new SimpleDateFormat("dd-MM-yyyy").parse(date2);  
        System.out.println();
        System.out.print("Company name: ");
        String companyName = sc.nextLine();
        System.out.println();
        System.out.print("Number of slots: ");
        int noSlots = sc.nextInt();
        System.out.println();
        CompanyRep companyRep = rep;
        boolean visibility = false;
        Internship internship = new Internship(title, description, level, preferredMajor, appOpenDate, appCloseDate, visibility, companyName, companyRep, noSlots, visibility);
        boolean successfulAdd = rep.addInternship(internship);
        if (successfulAdd) System.out.println("Internship added successfully.");
        else System.out.println("Internship adding failed.");
    }
    

    public void approveRejectInternship(CompanyRep companyRep, Scanner sc){
        StudentDbMgr studentDbMgr = StudentDbMgr.getInstance();
        System.out.println("=========================");
        System.out.print("Applicant matriculation number:");
        String applicantMatric = sc.nextLine();
        System.out.println();
        Student student = studentDbMgr.getStudent(applicantMatric);
        System.out.print("Application decision. Enter 1 for accept, 2 for reject: ");
        int decision_no = sc.nextInt();
        System.out.println();
        while (decision_no!=1 && decision_no!=2){
            System.out.println("please enter either 1 or 2");
            decision_no = sc.nextInt();
        }
        System.out.print("Internship title: ");
        String internshipTitle = sc.nextLine();
        Internship internship = companyRep.getInternship(internshipTitle);
        boolean successfulDecide = companyRep.acceptStudentInternship(student, internship, decision_no);
        if (successfulDecide) System.out.println("Application decision successful. ");
        else System.out.println("Application decision failed. ");
        System.out.println("=========================");
    }

    public void toggleInternshipVisibility(CompanyRep companyRep){
        System.out.println("=========================");
        System.out.print("Internship name: ");
        String internshipTitle = sc.nextLine();
        Internship internship = companyRep.getInternship(internshipTitle);
        System.out.println("Set visibility to: press 0 for invisible, press 1 for visible.");
        int decision_no = sc.nextInt();
        System.out.println();
        while (decision_no!=0 && decision_no!=1){
            System.out.println("please enter either 0 or 1");
            decision_no = sc.nextInt();
        }
        boolean decision;
        if (decision_no==0){decision = false;}
        else if (decision==1){decision = true;}
        internship.setVisibility(decision);
        System.out.println("=========================");
    }


    public void viewInternshipOpps(CompanyRep companyRep, Scanner sc){
        internshipList = internshipDbMgr.filter(InternshipAttributes.companyRep.companyName, argString);
        internshipDisplay.showInternships(internshipList);
    }

    public void changePassword(CompanyRep companyRep, Scanner sc, CompanyRepPasswordMgr companyRepPasswordMgr) {
        System.out.println("=========================");
        System.out.println("Change Password");
        System.out.println("Old password: ");
        String oldPassword = sc.nextLine();
        System.out.println("New password: ");
        String newPassword = sc.nextLine();
        boolean changed = companyRepPasswordMgr.changePassword(companyRep, oldPassword, newPassword);
        if (changed) {
            System.out.println("Successfully changed password!");
            System.out.println("=========================");
        } else {
            System.out.println("Failed to change password...");
            System.out.println("=========================");
        }
    }
}
