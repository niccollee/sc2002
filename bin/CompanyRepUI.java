import java.text.SimpleDateFormat;
import java.util.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
// import the classes over //

/**
 * Handles the console-based user interface flow for company representatives.
 * This class manages login, menu navigation and actions such as constructing internships,
 * deciding on student applications, toggling internship visibility, viewing opportunities
 * and changing passwords.
 */
public class CompanyRepUI {
    private CompanyRepDisplay companyRepDisplay;
    private InternshipUI internshipUI;
    private CompanyRep companyRep;
    private CompanyRepDbMgr companyRepDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;
    private DateTimeFormatter formatter;
    Scanner sc;

    /**
     * Creates a new {@code CompanyRepUI} and starts the interaction flow.
     * The constructor logs in the company representative, then repeatedly shows the menu
     * and handles user choices until the user quits or login fails too many times.
     *
     * @param companyRepDbMgr          database manager for company representatives
     * @param internshipDbMgr          database manager for internships
     * @param internshipWithdrawalDbMgr database manager for internship withdrawals
     */
    public CompanyRepUI(
        CompanyRepDbMgr companyRepDbMgr,
        InternshipDbMgr internshipDbMgr,
        InternshipWithdrawalDbMgr internshipWithdrawalDbMgr
        ) {
        formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        companyRepDisplay = new CompanyRepDisplay();
        internshipUI = new InternshipUI();
        this.companyRepDbMgr = companyRepDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        CompanyRepPasswordMgr companyRepPasswordMgr = new CompanyRepPasswordMgr();
        sc = Input.SC;
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

    /**
     * Prompts the user for username and password and attempts to log in
     * as a company representative.
     *
     * @param sc                     shared {@link Scanner} for user input
     * @param companyRepPasswordMgr  password manager used for validation
     * @return the logged-in {@link CompanyRep} if successful, or {@code null} if login fails
     */
    public CompanyRep login(Scanner sc, CompanyRepPasswordMgr companyRepPasswordMgr) {
        for (CompanyRep i : companyRepDbMgr.getAll()) {
            System.out.println(i.getId());
        }
        System.out.println("=========================");
        System.out.println("COMPANY REP");
        System.out.println("Enter Username: (email address)");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        CompanyRep companyRep = companyRepDbMgr.get(username);
        if (companyRep != null) {
            System.out.println(companyRep.getId());
            if (companyRepPasswordMgr.validate(companyRep, password)) {
                return companyRep;
            }
        } else {
            System.out.println("Company Rep does not exist!");
        }
        return null;
    }

    /**
     * Displays the main menu for company representatives and reads the user's choice.
     * Returns -1 if the choice is outside the valid range.
     *
     * @param sc shared {@link Scanner} for user input
     * @return the chosen menu option, or -1 if invalid
     */
    public int menu(Scanner sc) {
        companyRepDisplay.showMenu();
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice > 0 && choice < 7) {
            return choice;
        }
        return -1;
    }

    /**
     * Guides the company representative through the process of constructing
     * a new internship by prompting for the relevant details.
     *
     * @param companyRep the logged-in {@link CompanyRep} creating the internship
     * @param sc         shared {@link Scanner} for user input
     */
    public void constructInternship(CompanyRep companyRep, Scanner sc) {
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
        sc.nextLine();
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
        LocalDate appOpenDate;
        LocalDate appCloseDate;
        System.out.print("Application opening date (dd-mm-yyyy format): "); 
        while (true) {
            String date1 = sc.nextLine();
            try {
                appOpenDate = LocalDate.parse(date1, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Try again.");
            }
        }
        System.out.println();
        System.out.print("Application closing date (dd-mm-yyyy format): "); 
        while (true) {
            String date2 = sc.nextLine();
            try {
                appCloseDate = LocalDate.parse(date2, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format! Try again.");
            }
        }
        System.out.println();
        System.out.print("Company name: ");
        String companyName = sc.nextLine();
        System.out.println();
        System.out.print("Number of slots: ");
        int noSlots = sc.nextInt();
        System.out.println();
        boolean visibility = false;
        Internship internship = new Internship(title, description, level, preferredMajor, appOpenDate, appCloseDate, Status.PENDING, companyName, companyRep, noSlots, visibility);
        boolean successfulAdd = companyRep.addInternship(internship);
        if (successfulAdd) System.out.println("Internship added successfully.");
        else System.out.println("Internship adding failed.");
    }
    

    /**
     * Handles the flow for approving or rejecting a student's internship application.
     * Prompts for the student's matriculation number, the decision and the internship title.
     *
     * @param companyRep the logged-in {@link CompanyRep} making the decision
     * @param sc         shared {@link Scanner} for user input
     */
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

    /**
     * Toggles the visibility of an internship owned by the company representative.
     * Prompts for the internship name and the desired visibility state.
     *
     * @param companyRep the logged-in {@link CompanyRep} whose internship is updated
     */
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
        else {decision = true;}
        internship.setVisibility(decision);
        System.out.println("=========================");
    }

    /**
     * Displays internship opportunities that are relevant to the company representative.
     * The filtering logic uses the internship database manager and display components.
     *
     * @param companyRep the logged-in {@link CompanyRep}
     * @param sc         shared {@link Scanner} for user input
     */
    public void viewInternshipOpps(CompanyRep companyRep, Scanner sc){
        System.out.println("=========================");
        List<Internship> internshipList = internshipDbMgr.filter(InternshipAttributes.COMPANYNAME, companyRep.getName());
        InternshipDisplay internshipDisplay = new InternshipDisplay();
        internshipDisplay.showInternships(internshipList);
    }

    /**
     * Prompts the company representative to change their password and attempts
     * to update it using the provided password manager.
     *
     * @param companyRep             the logged-in {@link CompanyRep} whose password is changed
     * @param sc                     shared {@link Scanner} for user input
     * @param companyRepPasswordMgr  password manager used to perform the change
     */
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
