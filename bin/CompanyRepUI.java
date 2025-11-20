import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

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
            InternshipWithdrawalDbMgr internshipWithdrawalDbMgr) {

        this.companyRepDisplay = new CompanyRepDisplay();
        this.internshipUI = new InternshipUI();

        this.companyRepDbMgr = companyRepDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;

        CompanyRepPasswordMgr companyRepPasswordMgr = new CompanyRepPasswordMgr();
        Scanner sc = Input.SC;

        companyRep = login(sc, companyRepPasswordMgr);

        if (companyRep == null) {
            for (int i = 0; i < 3; i++) {
                companyRep = login(sc, companyRepPasswordMgr);
                if (companyRep != null) break;
                if (i == 2) return;
            }
        }

        int choice = menu(sc);
        while (choice == -1) {
            System.out.println("Invalid input!");
            choice = menu(sc);
        }

        while (true) {
            switch (choice) {
                case 1 -> constructInternship(companyRep, sc);
                case 2 -> approveRejectInternship(companyRep, sc);
                case 3 -> toggleInternshipVisibility(companyRep, sc);
                case 4 -> viewInternshipOpps(companyRep);
                case 5 -> changePassword(companyRep, sc, companyRepPasswordMgr);
                case 6 -> {
                    companyRepDisplay.showQuit();
                    return;
                }
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
        System.out.print("Enter Username (email address): ");
        String username = sc.nextLine();
        System.out.print("Enter Password: ");
        String password = sc.nextLine();

        CompanyRep rep = companyRepDbMgr.get(username);

        if (rep != null) {
            if (companyRepPasswordMgr.validate(rep, password)) return rep;
        } else {
            System.out.println("Company Rep does not exist!");
        }
        return null;
    }

    public int menu(Scanner sc) {
        companyRepDisplay.showMenu();
        int choice = sc.nextInt();
        sc.nextLine();
        return (choice > 0 && choice < 7) ? choice : -1;
    }

    public void constructInternship(CompanyRep rep, Scanner sc) {
        try {
            System.out.println("=========================");
            System.out.print("Internship title: ");
            String title = sc.nextLine();

            System.out.print("Description: ");
            String description = sc.nextLine();

            System.out.println("""
                    Internship level:
                    1) Basic
                    2) Intermediate
                    3) Advanced
                    """);

            int levelNo = sc.nextInt();
            sc.nextLine();

            InternshipLevel level;
            switch (levelNo) {
                case 1 -> level = InternshipLevel.BASIC;
                case 2 -> level = InternshipLevel.INTERMEDIATE;
                case 3 -> level = InternshipLevel.ADVANCED;
                default -> {
                    System.out.println("Invalid level.");
                    return;
                }
            }

            System.out.print("Preferred major: ");
            String preferredMajor = sc.nextLine();

            System.out.print("Application opening date (dd-MM-yyyy): ");
            Date openDate = new SimpleDateFormat("dd-MM-yyyy").parse(sc.nextLine());

            System.out.print("Application closing date (dd-MM-yyyy): ");
            Date closeDate = new SimpleDateFormat("dd-MM-yyyy").parse(sc.nextLine());

            System.out.print("Company name: ");
            String companyName = sc.nextLine();

            System.out.print("Number of slots: ");
            int slots = sc.nextInt();
            sc.nextLine();

            Internship internship = new Internship(
                    title, description, level,
                    preferredMajor, openDate, closeDate,
                    false, companyName, rep, slots, false
            );

            boolean added = rep.addInternship(internship);

            System.out.println(added ? "Internship added successfully." : "Failed to add internship.");

        } catch (Exception e) {
            System.out.println("Error creating internship: " + e.getMessage());
        }
    }

    public void approveRejectInternship(CompanyRep rep, Scanner sc) {
        StudentDbMgr studentDbMgr = StudentDbMgr.getInstance();

        System.out.println("=========================");
        System.out.print("Applicant matriculation number: ");
        String matric = sc.nextLine();

        Student student = studentDbMgr.getStudent(matric);

        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        System.out.print("Enter 0 to reject, 1 to accept: ");
        int d = sc.nextInt();
        sc.nextLine();

        while (d != 0 && d != 1) {
            System.out.println("Please enter 0 or 1:");
            d = sc.nextInt();
            sc.nextLine();
        }

        boolean decision = (d == 1);

        System.out.print("Internship title: ");
        String internshipTitle = sc.nextLine();

        Internship internship = rep.getInternship(internshipTitle);

        if (internship == null) {
            System.out.println("Internship not found!");
            return;
        }

        boolean success = rep.acceptStudentInternship(student, internship, decision);

        if (success)
            System.out.println(decision ? "Application accepted." : "Application rejected.");
        else
            System.out.println("Action failed.");

        System.out.println("=========================");
    }

    public void toggleInternshipVisibility(CompanyRep rep, Scanner sc) {
        System.out.println("=========================");
        System.out.print("Internship name: ");
        String title = sc.nextLine();

        Internship internship = rep.getInternship(title);
        if (internship == null) {
            System.out.println("Internship not found!");
            return;
        }

        System.out.print("Enter 0 for invisible, 1 for visible: ");
        int d = sc.nextInt();
        sc.nextLine();

        while (d != 0 && d != 1) {
            System.out.println("Please enter 0 or 1");
            d = sc.nextInt();
            sc.nextLine();
        }

        internship.setVisibility(d == 1);
        System.out.println("Visibility updated.");
        System.out.println("=========================");
    }

    public void viewInternshipOpps(CompanyRep rep) {
        var list = internshipDbMgr.filter(InternshipAttributes.companyRepCompanyName, rep.getCompanyName());
        internshipUI.showInternships(list);
    }

    public void changePassword(CompanyRep rep, Scanner sc, CompanyRepPasswordMgr mgr) {
        System.out.println("=========================");
        System.out.print("Old password: ");
        String oldP = sc.nextLine();

        System.out.print("New password: ");
        String newP = sc.nextLine();

        boolean changed = mgr.changePassword(rep, oldP, newP);

        System.out.println(changed ? "Successfully changed password!" : "Failed to change password...");
        System.out.println("=========================");
    }
}
