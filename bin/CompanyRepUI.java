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
        this.repDbMgr = companyRepDbMgr;
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
                if (rep != null) {
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
                approveRejectInternship(commpanyRep, sc);
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
            Internship level: <user input>
                1) Basic
                2) Intermediate
                3) Advanced
                """;)
        int level_no = sc.nextInt();
        switch(level_no){
            case 1: InternshipLevel level = InternshipLevel.BASIC; break;
            case 2: InternshipLevel level = InternshipLevel.INTERMEDIATE; break;
            case 3: InternshipLevel level = InternshipLevel.ADVANCE; break;
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
        Internship internship = new Internship(title, description, level, preferredMajor, appOpenDate, appCloseDate, status, companyName, companyRep, noSlots, visibility);
        boolean successfulAdd = rep.addInternship(internship);
        if (successfulAdd) System.out.println("Internship added successfully.");
        else System.out.println("Internship adding failed.")
    }
    

    public void approveRejectInternship(commpanyRep, sc){
        System.out.println("=========================");
        System.out.print("Applicant name:");
        String applicantName = sc.nextLine();
        System.out.println();
        Student student = companyRep.getStudent(applicantName); //need to write getStudent method
        System.out.print("Application decision. Enter 0 for reject, 1 for accept: ");
        int decision_no = sc.nextInt();
        System.out.println();
        while (decision_no!=0 and decision_no!=1){
            System.out.println("please enter either 0 or 1");
            decision_no = sc.nextInt();
        }
        if (decision_no==0) boolean decision = false;
        else if (decision==1) boolean decision = true;
        System.out.print("Internship title: ");
        String internshipTitle = sc.nextLine();
        Internship internship = companyRep.getInternship(internshipTitle);
        boolean successfulDecide = companyRep.acceptStudentInternship(Student student, Internship internship, int decisionStatus);
        if (successfulDecide and decision) System.out.println("Application accepted successfully");
        else if (successfulDecide and !decision) System.out.println("Application rejected successfully");
        else System.out.println("Application decision failed.");
        System.out.println("=========================");
    }

    public void toggleInternshipVisibility(companyRep){
        System.out.println("=========================");
        System.out.print("Internship name: ");
        String internshipTitle = sc.nextLine();
        Internship internship = companyRep.getInternship(internshipTitle);
        System.out.println("Set visibility to: press 0 for invisible, press 1 for visible.");
        int decision_no = sc.nextInt();
        System.out.println();
        while (decision_no!=0 and decision_no!=1){
            System.out.println("please enter either 0 or 1");
            decision_no = sc.nextInt();
        }
        if (decision_no==0) boolean decision = false;
        else if (decision==1) boolean decision = true;
        internship.setVisibility(decision);
        System.out.println("=========================");
    }


    public void viewInternshipOpps(companyRep, sc){

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
