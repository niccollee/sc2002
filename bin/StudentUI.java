import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Displays the main student UI in order to perform the student function.
 */
public class StudentUI {
    private StudentDisplay studentDisplay;
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
            StudentPasswordMgr studentPasswordMgr) {
        studentDisplay = new StudentDisplay();
        internshipUI = new InternshipUI();
        this.studentDbMgr = studentDbMgr;
        this.internshipDbMgr = internshipDbMgr;
        this.internshipWithdrawalDbMgr = internshipWithdrawalDbMgr;
        this.studentPasswordMgr = studentPasswordMgr;
        sc = Input.SC;
        start(sc, studentDbMgr);
    }

    /**
     * Method to start and run the main student ui class.
     */
    public void start(Scanner sc, StudentDbMgr studentDbMgr) {
        student = login(sc, studentDbMgr);
        /*
         * If login details is invalid, continue prompting user to login for 3 more
         * times. Afterwards return back to main page.
         */
        if (student == null) {
            for (int i = 0; i != 3; i++) {
                student = login(sc, studentDbMgr);
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
            System.out.println("Invalid input! Enter input again.");
            choice = menu(sc);
        }
        while (true) {
            switch (choice) {
                case 1:
                    viewOpportunities(internshipUI, student);
                    break;
                case 2:
                    applyForInternship(sc, internshipDbMgr, student);
                    break;
                case 3:
                    viewAppliedInternships(studentDisplay, student);
                    break;
                case 4:
                    acceptInternship(sc, studentDisplay, student);
                    break;
                case 5:
                    requestWithdrawal(sc, internshipDbMgr, student);
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
                System.out.println("Invalid input! Enter input again.");
                choice = menu(sc);
            }
        }
    }

    /**
     * Prompts user to enter their user name and password and verify it. Display
     * the respective success and failure outcomes.
     * 
     * @param sc         shared {@link Scanner} for user input
     * @return true if validation successful, otherwise return false.
     */
    public Student login(Scanner sc, StudentDbMgr studentDbMgr) {
        System.out.println("=======================================");
        System.out.println("STUDENT");
        System.out.println("=======================================");
        System.out.println("Enter Username: (matriculation number)");
        String username = sc.next();
        System.out.println("Enter Password: ");
        String password = sc.next();
        Student student = studentDbMgr.getStudent(username);
        if (student != null) {
            if (studentPasswordMgr.validate(student, password)) {
                return student;
            } else {
                System.out.println("Invalid Password!");
            }
        } else {
            System.out.println("Student does not exist!");
        }
        return null;
    }

    /**
     * Displays the student main menu options and takes in the user inputs.
     * Repeatedly prompts user to enter the correct input.
     * @param sc         shared {@link Scanner} for user input
     * @return the value of that user input.
     * 
     */
    public int menu(Scanner sc) {
        studentDisplay.showMenu();
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter input again.");
                sc.nextLine();
            }

        }
        if (choice > 0 && choice < 8) {
            return choice;
        }
        return -1;
    }

    /**
     * Display all internship opportunity that is given to that particular student
     * 
     * @param internshipUI helper function to display internships
     * @param student student for whom internships are shown
     */
    public void viewOpportunities(InternshipUI internshipUI, Student student) {
        internshipUI.viewAllInternships(student);
        internshipUI.filterInternshipsBy(student);
    }

    /**
     * Display process for applying internships.
     * 
     * @param sc         shared {@link Scanner} for user input
     * @param internshipDbMgr database manager used to retrieve internship
     * @param student student applying for internship
     */
    public void applyForInternship(Scanner sc, InternshipDbMgr internshipDbMgr, Student student) {
        System.out.println("=======================================");
        System.out.println("Apply for internship. Enter internship id: ");
        int id;
        while (true) {
            try {
                id = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter input again.");
                sc.nextLine();
                continue;
            }
        }
        Internship applyingInternship = internshipDbMgr.get(id);
        if (student.applyInternship(applyingInternship)) {
            System.out.println("Internship id: " + applyingInternship.getId() + " added!");
            System.out.println("=======================================");
            return;
        }
        System.out.println("Invalid internship!");
        System.out.println("=======================================");
        if (applyingInternship != null && applyingInternship.getVisibility()
                && applyingInternship.getStatus() == Status.APPROVED) {
            if (student.applyInternship(applyingInternship)) {

            }
        }
        System.out.println("Invalid internship!");
        System.out.println("=======================================");
    }

    /**
     * View applied internship for that student.
     * 
     * @param studentDisplay helper to display applied internships
     * @param student student whom applied internship will be displayed
     */
    public void viewAppliedInternships(StudentDisplay studentDisplay, Student student) {
        studentDisplay.showAppliedInternships(student);
    }

    /**
     * Display process for accepting internship.
     * 
     * @param sc         shared {@link Scanner} for user input
     * @param studentDisplay helper to display the applied internships
     * @param student   student accepting internship
     */
    public void acceptInternship(Scanner sc, StudentDisplay studentDisplay, Student student) {
        studentDisplay.showAppliedInternships(student);

        System.out.println("=======================================");
        if (student.getAcceptedInternship() == null) {
            System.out.println("Enter application id to accept: ");
        } else {
            System.out.println("You have accepted an internship.");
            System.out.println("Accepted internship: ");
            System.out.printf("%-15s | %-30s%n", "Internship ID", "Title");

            Internship in = student.getAcceptedInternship();
            System.out.printf("%-15s | %-30s%n", in.getId(), in.getTitle());

            System.out.println("=======================================");
            return;
        }
        int choice;
        while (true) {
            try {
                choice = sc.nextInt();
                sc.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter input again.");
                sc.nextLine();
                continue;
            }
        }
        if (choice < 0 || choice >= student.getAppliedInternships().getAll().size()) {
            System.out.println("Invalid response!");
            System.out.println("=======================================");
            return;
        }
        if (student.acceptInternship(student.getAppliedInternships().getAll().get(choice).getInternship())) {
            System.out.println("Internship accepted!");
        } else {
            System.out.println("Internship not accepted!");
        }

        System.out.println("=======================================");
    }

    /**
     * Display process for withdrawing from internship after it has been accepted.
     * 
     * @param sc         shared {@link Scanner} for user input
     * @param internshipDbMgr  database manager used to retrieve internships
     * @param student   student who requesting the withdrawal
     */
    public void requestWithdrawal(Scanner sc, InternshipDbMgr internshipDbMgr, Student student) {
        System.out.println("=======================================");
        System.out.println("Enter internship id to withdraw: ");
        int id;
        while (true) {
            try {
                id = sc.nextInt();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Invalid input! Enter input again.");
                sc.nextLine();
                continue;
            }
        }
        Internship internship = internshipDbMgr.get(id);
        if (internship == null) {
            System.out.println("Invalid internship id!");
            System.out.println("=======================================");
            return;
        }
        if (internshipWithdrawalDbMgr.add(student, internship)) {
            System.out.println("Internship id " + id + " added to withdrawal request.");
        } else {
            System.out.println("Error: internship id " + id + " not added to withdrawal request.");
        }

        System.out.println("=======================================");
    }

    /**
     * Display process for changing password.
     * 
     * @param sc         shared {@link Scanner} for user input
     * @param student    student whose password will be changed
     * @param studentPasswordMgr    manager used to perform operations.
     */
    public void changePassword(Student student, Scanner sc, StudentPasswordMgr studentPasswordMgr) {
        System.out.println("=======================================");
        System.out.println("Change Password");
        System.out.println("=======================================");

        System.out.println("Old password: ");
        String oldPassword = sc.next();

        System.out.println("New password: ");
        String newPassword = sc.next();

        boolean changed = studentPasswordMgr.changePassword(student, oldPassword, newPassword);
        if (changed) {
            System.out.println("Successfully changed password!");
            System.out.println("=======================================");
        } else {
            System.out.println("Failed to change password...");
            System.out.println("=======================================");
        }
    }
}
