import java.util.Scanner;

/**
 * Handles the console-based user interface flow for career centre staff.
 * This class coordinates login, menu navigation and actions such as
 * approving company representatives, processing student withdrawals
 * and managing internship opportunities.
 */
public class CareerStaffUI {
    private CareerStaffDisplay careerStaffDisplay;
    private CareerStaff careerStaff;
    private CareerStaffDbMgr careerStaffDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipUI internshipUi;
    private CompanyRepDbMgr companyRepDbMgr;
    private CareerStaffController careerStaffController;

    /**
     * Creates a new {@code CareerStaffUI} and starts the interaction flow.
     * The constructor performs login, then repeatedly shows the menu and
     * handles user choices until the user quits or login fails too many times.
     *
     * @param careerStaffDbMgr          database manager for career staff
     * @param internshipDbMgr           database manager for internships
     * @param internshipWithdrawalDbMgr database manager for internship withdrawals
     * @param internshipUi              UI component for internship-related actions
     * @param companyRepDbMgr           database manager for company representatives
     * @param careerStaffController     controller handling staff actions
     */
    public CareerStaffUI(CareerStaffDbMgr careerStaffDbMgr, InternshipDbMgr internshipDbMgr,
            InternshipWithdrawalDbMgr internshipWithdrawalDbMgr, InternshipUI internshipUi,
            CompanyRepDbMgr companyRepDbMgr, CareerStaffController careerStaffController) {
        this.careerStaffDisplay = new CareerStaffDisplay();
        this.careerStaffDbMgr = careerStaffDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipUi = internshipUi;
        this.companyRepDbMgr = companyRepDbMgr;
        this.careerStaffController = careerStaffController;

        CareerStaffPasswordMgr careerStaffPasswordMgr = new CareerStaffPasswordMgr();
        Scanner sc = Input.SC;

        careerStaff = login(sc, careerStaffPasswordMgr);
        /*
         * If login details is invalid, continue prompting user to login for 3 more
         * times. Afterwards return back to main page.
         */
        if (careerStaff == null) {
            for (int i = 0; i < 3; i++) {
                careerStaff = login(sc, careerStaffPasswordMgr);
                if (careerStaff != null) {
                    break;
                }
                if (i == 2) {
                    return;
                }
            }
        }

        // Traverse menu options
        while (true) {
            int choice = menu(sc);
            while (choice == -1) {
                System.out.println("Invalid input!");
                choice = menu(sc);
            }
            switch (choice) {
                case 1:
                    approveCompanyRepApplication(sc);
                    break;
                case 2:
                    approveStudentWithdrawlApplication(sc);
                    break;
                case 3:
                    approveInternshipOpportunity(sc);
                    break;
                case 4:
                    viewInternshipOpportunity(sc);
                    break;
                case 5:
                    changePassword(careerStaff, sc, careerStaffPasswordMgr);
                    break;
                case 6:
                    return;
                default:
                    break;
            }
        }

    }

    /**
     * Displays the main menu and reads the user's choice.
     * Returns -1 if the choice is out of the allowed range.
     *
     * @param sc shared {@link Scanner} for user input
     * @return the chosen menu option, or -1 if invalid
     */
    public int menu(Scanner sc) {
        careerStaffDisplay.showMenu();

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice > 0 && choice < 7) {
            return choice;
        }

        return -1;
    }

    /**
     * Prompts the user for staff ID and password and attempts to log in.
     *
     * @param sc                     shared {@link Scanner} for user input
     * @param careerStaffPasswordMgr password manager used for validation
     * @return the logged-in {@link CareerStaff} if successful; {@code null}
     *         otherwise
     */
    public CareerStaff login(Scanner sc, CareerStaffPasswordMgr careerStaffPasswordMgr) {
        System.out.println("=========================");
        System.out.println("CAREER STAFF");
        System.out.println("Enter StaffID: ");
        String username = sc.nextLine();
        System.out.println("\nEnter Password: ");
        String password = sc.nextLine();

        CareerStaff careerStaff = careerStaffDbMgr.getCareerStaff(username);
        if (careerStaff != null) {
            if (careerStaffPasswordMgr.validate(careerStaff, password)) {
                System.out.println("\nlogin Successful!!!");
                return careerStaff;
            }
        }
        System.out.println("\nError: Either StaffId Or Password is wrong");
        return null;
    }

    /**
     * Handles the flow for approving or rejecting a company representative
     * application. Prompts for a representative ID and an approve/reject choice.
     *
     * @param sc shared {@link Scanner} for user input
     */
    public void approveCompanyRepApplication(Scanner sc) {
        careerStaffDisplay.showCompanyRepApplications();
        System.out.println("=========================");

        System.out.println("Enter CompanyRepID to approve (-1 to escape)");
        String companyRepId = sc.nextLine();
        if (companyRepId.equals("-1")) {
            return;
        }

        while (companyRepDbMgr.get(companyRepId) == null
                || companyRepDbMgr.get(companyRepId).getRepStatus() != CompanyRepStatus.PENDING) {
            System.out.println("Invalid CompanyRepID, enter again (-1 to escape)");
            companyRepId = sc.nextLine();
            if (companyRepId.equals("-1")) {
                return;
            }
        }

        System.out.println("Enter 1 or 0 to (Approve or reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again (-1 to escape");
            approveOrReject = sc.nextInt();
            sc.nextLine();
            if (approveOrReject == -1) {
                return;
            }
        }

        if (approveOrReject == 1) {
            careerStaffController.approveCompanyRep(companyRepId, companyRepDbMgr);
        } else {
            careerStaffController.rejectCompanyRep(companyRepId, companyRepDbMgr);
        }

    }

    /**
     * Handles the flow for approving or rejecting a student's internship
     * withdrawal request.
     *
     * @param sc shared {@link Scanner} for user input
     */
    public void approveStudentWithdrawlApplication(Scanner sc) {
        careerStaffDisplay.showWithdrawalRequest();
        if (InternshipWithdrawalApplicant.getCounter() == 0) {
            System.out.println("\nNo Student Withdrawal Applicants\n");
            return;
        }

        System.out.println("=========================");
        System.out.println("Enter Index Option of interest (-1 to escape): ");
        String studentId = sc.nextLine();
        if (studentId.equals("-1")) {
            return;
        }

        System.out.println("Enter 1 or 0 to (Approve or reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again (-1 to escape)");
            approveOrReject = sc.nextInt();
            sc.nextLine();
            if (studentId.equals("-1")) {
                return;
            }
        }

        if (approveOrReject == 1) {
            careerStaffController.approveStudentWidthdrawl(studentId, internshipWithdrawalDbMgr);
        } else {
            careerStaffController.rejectStudentWithdrawl(studentId, internshipWithdrawalDbMgr);
        }

    }

    /**
     * Handles the flow for approving or rejecting an internship opportunity.
     *
     * @param sc shared {@link Scanner} for user input
     */
    public void approveInternshipOpportunity(Scanner sc) {
        careerStaffDisplay.showInternshipsPending();
        System.out.println("=========================");
        System.out.println("Enter Intership ID of interest (-1 to escape): ");
        int internshipID = sc.nextInt();
        sc.nextLine();
        if (internshipID == -1) {
            return;
        }

        while (internshipDbMgr.get(internshipID) == null) {
            System.out.println("Invalid internship ID, Enter again (-1 to escape)");
            internshipID = sc.nextInt();
            sc.nextLine();
            if (internshipID == -1)) {
            return;
        }
        }

        System.out.println("Enter 1 or 0 to (Approve or Reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again (-1 to escape)");
            approveOrReject = sc.nextInt();
            sc.nextLine();
             if (approveOrReject == -1) {
            return;
        }
        }

        if (approveOrReject == 1) {
            careerStaffController.approveInternship(internshipID, internshipDbMgr);
        } else {
            careerStaffController.rejectInternship(internshipID, internshipDbMgr);
        }

    }

    /**
     * Displays internship opportunities using the internship UI component.
     *
     * @param sc shared {@link Scanner} for user input
     */
    public void viewInternshipOpportunity(Scanner sc) {
        System.out.println("=========================");
        System.out.println("Viewing Internship opportunites: ");

        internshipUi.filterInternshipsBy();
    }

    /**
     * Prompts the user to change their password and attempts to update it
     * using the provided password manager.
     *
     * @param careerStaff            the logged-in staff whose password is changed
     * @param sc                     shared {@link Scanner} for user input
     * @param careerStaffPasswordMgr password manager used to perform the change
     */
    public void changePassword(CareerStaff careerStaff, Scanner sc, CareerStaffPasswordMgr careerStaffPasswordMgr) {
        System.out.println("=========================");
        System.out.println("Change Password");

        System.out.println("Old password: ");
        String oldPassword = sc.nextLine();

        System.out.println("New password: ");
        String newPassword = sc.nextLine();

        boolean changed = careerStaffPasswordMgr.changePassword(careerStaff, oldPassword, newPassword);
        if (changed) {
            System.out.println("\nSuccessfully changed password!");
            System.out.println("=========================");
        } else {
            System.out.println("\nFailed to change password...");
            System.out.println("=========================");
        }
    }

}
