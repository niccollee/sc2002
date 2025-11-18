import java.util.Scanner;

public class StudentUI {
    private StudentDisplay studentDisplay;
    private InternshipDisplay internshipDisplay;
    private InternshipUI internshipUI;
    private Student student;
    private StudentDbMgr studentDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;
    private StudentPasswordMgr studentPasswordMgr;
    private Scanner sc;

    public StudentUI(
        StudentDbMgr studentDbMgr,
        InternshipDbMgr internshipDbMgr,
        InternshipWithdrawalDbMgr internshipWithdrawalDbMgr,
        StudentPasswordMgr studentPasswordMgr
        ) {
        studentDisplay = new StudentDisplay();
        internshipDisplay = new InternshipDisplay();
        internshipUI = new InternshipUI();
        this.studentDbMgr = studentDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        this.studentPasswordMgr = studentPasswordMgr;
        sc = Input.SC;
        student = login();
        /* If login details is invalid, continue prompting user to login for 3 more
         * times. Afterwards return back to main page.
         */
        if (student == null) {
            for (int i = 0; i != 3; i++) {
                student = login();
                if (student != null) {
                    break;
                }
                if (i == 2) {
                    return;
                }
            }
        }
        int choice = menu();
        while (choice == -1) {
            System.out.println("Invalid input!");
            choice = menu();
        }
        while (true) {
            switch (choice) {
            case 1:
                viewOpportunities();
                break;
            case 2:
                applyForInternship();
                break;
            case 3:
                viewAppliedInternships();
                break;
            case 4:
                acceptInternship();
                break;
            case 5:
                requestWithdrawal();
                break;
            case 6:
                changePassword();
                break;
            case 7:
                studentDisplay.showQuit();
                return;
            }
            choice = menu();
            while (choice == -1) {
                System.out.println("Invalid input!");
                choice = menu();
            }
        }        
    }
    // Login system. Returns the Student object with that username and password, otherwise return null
    public Student login() {
        System.out.println("=========================");
        System.out.println("STUDENT");
        System.out.println("Enter Username: (matriculation number)");
        String username = sc.nextLine();
        System.out.println("Enter Password: ");
        String password = sc.nextLine();
        Student student = studentDbMgr.getStudent(username);
        if (student != null) {
            if (studentPasswordMgr.validate(student, password)) {
                return student;
            }
        } else {
            System.out.println("Student does not exist!");
        }
        return null;
    }
    public int menu() {
        studentDisplay.showMenu();
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice > 0 && choice < 8) {
            return choice;
        }
        return -1;
    }
    public void viewOpportunities() {
        internshipDisplay.showInternships(internshipDbMgr.showAll());
        internshipUI.filterInternshipsBy(student);      
    }
    public void applyForInternship() {
        System.out.println("=========================");
        System.out.println("Apply for internship. Enter internship id: ");
        int id = sc.nextInt();
        sc.nextLine();
        Internship applyingInternship = internshipDbMgr.get(id);
        if (applyingInternship != null && applyingInternship.getVisibility() && applyingInternship.getStatus() == Status.APPROVED) {
            if (student.applyInternship(applyingInternship)) {
                System.out.println("Internship id: " + applyingInternship.getId() + " added!");
                System.out.println("=========================");
                return;
            } 
        }
        System.out.println("Invalid internship!");
        System.out.println("=========================");
    }
    public void viewAppliedInternships() {
        studentDisplay.showAppliedInternships(student);
    }
    public void acceptInternship() {
        studentDisplay.showAcceptInternships(student);
        System.out.println("=========================");
        if (student.getAcceptedInternship() == null) {
            System.out.println("Enter internship id to accept: ");
        } else {
            System.out.println("You have accepted an internship.");
            System.out.println("Accepted internship: ");
            System.out.println("Intenship id \t\t:\tTitle");
            System.out.println(student.getAcceptedInternship().getId() +  "\t\t\t:\t" + student.getAcceptedInternship().getTitle() + "\t\t");
            System.out.println("=========================");
            return;
        }
        int choice = sc.nextInt();
        if (choice < 0 || choice >= student.getAppliedInternships().showAll().size()) {
            System.out.println("Invalid response!");
            return;
        }
        if (student.acceptInternship(student.getAppliedInternships().showAll().get(choice).getInternship())) {
            System.out.println("Internship accepted!");
        } else {
            System.out.println("Internship not accepted!");
        }
        
        System.out.println("=========================");
    }
    public void requestWithdrawal() {
        System.out.println("=========================");
        System.out.println("Enter internship id to withdraw: ");
        int id = sc.nextInt();
        Internship internship = internshipDbMgr.get(id);
        if (internship == null) {
            System.out.println("Invalid internship id!");
            System.out.println("=========================");
            return;
        }
        internshipWithdrawalDbMgr.add(student, internship);
        System.out.println("Internship id " + id + " added to withdrawal request.");
        System.out.println("=========================");
    }
    public void changePassword() {
        System.out.println("=========================");
        System.out.println("Change Password");
        System.out.println("Old password: ");
        String oldPassword = sc.nextLine();
        System.out.println("New password: ");
        String newPassword = sc.nextLine();
        boolean changed = studentPasswordMgr.changePassword(student, oldPassword, newPassword);
        if (changed) {
            System.out.println("Successfully changed password!");
            System.out.println("=========================");
        } else {
            System.out.println("Failed to change password...");
            System.out.println("=========================");
        }
    }
}