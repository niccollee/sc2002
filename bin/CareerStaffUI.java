import java.util.Scanner;

public class CareerStaffUI {
    private CareerStaffDisplay careerStaffDisplay;
    private CareerStaff careerStaff;
    private CareerStaffDbMgr careerStaffDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipUI internshipUi;
    private CompanyRepDbMgr companyRepDbMgr;
    private CareerStaffController careerStaffController;

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
                case 6:
                    return;
                default:
                    break;
            }
        }

    }

    public int menu(Scanner sc) {
        careerStaffDisplay.showMenu();

        int choice = sc.nextInt();
        sc.nextLine();

        if (choice > 0 && choice < 7) {
            return choice;
        }

        return -1;
    }

    public CareerStaff login(Scanner sc, CareerStaffPasswordMgr careerStaffPasswordMgr) {
        System.out.println("=========================");
        System.out.println("CAREER STAFF");
        System.out.println("Enter StaffID: ");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();

        CareerStaff careerStaff = careerStaffDbMgr.getCareerStaff(username);
        if (careerStaff != null) {
            if (careerStaffPasswordMgr.validate(careerStaff, password)) {
                return careerStaff;
            }
        } else {
            System.out.println("Error: Either StaffId Or Password is wrong");
        }

        return null;
    }

    public void approveCompanyRepApplication(Scanner sc) {
        careerStaffDisplay.showCompanyRepApplications();
        System.out.println("=========================");

        System.out.println("Enter CompanyRepID to approve");
        System.out.println("or -1 to return back to menu");
        String companyRepId = sc.nextLine();
        if (Integer.parseInt(companyRepId) == -1) {
            return;
        }

        while (companyRepDbMgr.get(companyRepId) == null) {
            System.out.println("Invalid CompanyRepID, enter again");
            companyRepId = sc.nextLine();
        }

        System.out.println("Enter 1 or 0 to (Approve or reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again");
            approveOrReject = sc.nextInt();
            sc.nextLine();
        }

        if (approveOrReject == 1) {
            careerStaffController.approveCompanyRep(companyRepId, companyRepDbMgr);
        } else {
            careerStaffController.rejectCompanyRep(companyRepId, companyRepDbMgr);
        }

    }

    public void approveStudentWithdrawlApplication(Scanner sc) {
        careerStaffDisplay.showWithdrawalRequest();
        if (InternshipWithdrawalApplicants.getCounter() == 0) {
            System.out.println("No Student Withdrawal Applicants");
            return;
        }

        System.out.println("=========================");
        System.out.println("Enter Index Option of interest: ");
        System.out.println("or -1 to return back to menu");

        String studentId = sc.nextLine();
        if (Integer.parseInt(studentId) == -1) {
            return;
        }

        System.out.println("Enter 1 or 0 to (Approve or reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again");
            approveOrReject = sc.nextInt();
            sc.nextLine();
        }

        if (approveOrReject == 1) {
            careerStaffController.approveStudentWidthdrawl(studentId, internshipWithdrawalDbMgr);
        } else {
            careerStaffController.rejectStudentWithdrawl(studentId, internshipWithdrawalDbMgr);
        }

    }

    public void approveInternshipOpportunity(Scanner sc) {
        careerStaffDisplay.showInternshipsPending();
        System.out.println("=========================");
        System.out.println("Enter Intership ID of interest: ");
        System.out.println("or -1 to return back to menu");
        int internshipID = sc.nextInt();
        sc.nextLine();

        if (internshipID == -1) {
            return;
        }

        while (internshipDbMgr.get(internshipID) == null) {
            System.out.println("Invalid internship ID, Enter again");
            internshipID = sc.nextInt();
            sc.nextLine();
        }

        System.out.println("Enter 1 or 0 to (Approve or Reject)");
        int approveOrReject = sc.nextInt();
        sc.nextLine();

        while (approveOrReject < 0 || approveOrReject > 1) {
            System.out.println("Invalid option, enter again");
            approveOrReject = sc.nextInt();
            sc.nextLine();
        }

        if (approveOrReject == 1) {
            careerStaffController.approveInternship(internshipID, internshipDbMgr);
        } else {
            careerStaffController.rejectInternship(internshipID, internshipDbMgr);
        }

    }

    public void viewInternshipOpportunity(Scanner sc) {
        System.out.println("=========================");
        System.out.println("Viewing Internship opportunites: ");

        internshipUi.filterInternshipsBy();
    }

    public void changePassword(CareerStaff careerStaff, Scanner sc, CareerStaffPasswordMgr careerStaffPasswordMgr) {
        System.out.println("=========================");
        System.out.println("Change Password");

        System.out.println("Old password: ");
        String oldPassword = sc.nextLine();

        System.out.println("New password: ");
        String newPassword = sc.nextLine();

        boolean changed = careerStaffPasswordMgr.changePassword(careerStaff, oldPassword, newPassword);
        if (changed) {
            System.out.println("Successfully changed password!");
            System.out.println("=========================");
        } else {
            System.out.println("Failed to change password...");
            System.out.println("=========================");
        }
    }

}
