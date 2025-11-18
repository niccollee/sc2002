import java.util.Scanner;

public class StudentUI {
    private StudentDisplay studentDisplay;
    private InternshipUI internshipUI;
    private Student student;
    private StudentDbMgr studentDbMgr;
    private InternshipDbMgr internshipDbMgr;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgr;

    public StudentUI(
        StudentDbMgr studentDbMgr,
        InternshipDbMgr internshipDbMgr,
        InternshipWithdrawalDbMgr internshipWithdrawalDbMgr
        ) {
        studentDisplay = new StudentDisplay();
        internshipUI = new InternshipUI();
        this.studentDbMgr = studentDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        StudentPasswordMgr studentPasswordMgr = new StudentPasswordMgr();
        Scanner sc = Input.SC;
        student = login(sc, studentPasswordMgr);
        /* If login details is invalid, continue prompting user to login for 3 more
         * times. Afterwards return back to main page.
         */
        if (student == null) {
            for (int i = 0; i != 3; i++) {
                student = login(sc, studentPasswordMgr);
                if (student != null) {
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
                viewOpportunities(sc);
                break;
            case 2:
                applyForInternship(student, sc);
                break;
            case 3:
                viewAppliedInternships(student);
                break;
            case 4:
                acceptInternship(student, sc);
                break;
            case 5:
                requestWithdrawal(student, sc);
                break;
            case 6:
                changePassword(student, sc, studentPasswordMgr);
                break;
            case 7:
                studentDisplay.showQuit();
                return;
            }
            choice = menu(sc);
            while (choice == -1) {
                System.out.println("Invalid input!");
                choice = menu(sc);
            }
        }        
    }
    public Student login(Scanner sc, StudentPasswordMgr studentPasswordMgr) {
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
    public int menu(Scanner sc) {
        studentDisplay.showMenu();
        int choice = sc.nextInt();
        sc.nextLine();
        if (choice > 0 && choice < 8) {
            return choice;
        }
        return -1;
    }
    public void viewOpportunities(Scanner sc) {
        studentDisplay.showViewOpportunities(internshipDbMgr.showAll());
        internshipUI.filterInternshipsBy(true);      
    }
    public void applyForInternship(Student student, Scanner sc) {
        System.out.println("=========================");
        System.out.println("Apply for internship. Enter internship id: ");
        int id = sc.nextInt();
        sc.nextLine();
        Internship applyingInternship = internshipDbMgr.get(id);
        if (applyingInternship != null && applyingInternship.getVisibility()) {
            student.applyInternship(applyingInternship);
            System.out.println("Internship id: " + applyingInternship.getId() + " added!");
            System.out.println("=========================");
        } else {
            System.out.println("Invalid internship!");
            System.out.println("=========================");
            return;
        } 
    }
    public void viewAppliedInternships(Student student) {
        studentDisplay.showAppliedInternships(student);
    }
    public void acceptInternship(Student student, Scanner sc) {
        studentDisplay.showAcceptInternships(student);
        int choice = sc.nextInt();
        if (choice < 0 || choice >= student.getAppliedInternship().size()) {
            System.out.println("Invalid response!");
            return;
        }
        student.acceptInternship(student.getAppliedInternship().get(choice));
        System.out.println("Internship accepted!");
        System.out.println("=========================");
    }
    public void requestWithdrawal(Student student, Scanner sc) {
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
    public void changePassword(Student student, Scanner sc, StudentPasswordMgr studentPasswordMgr) {
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