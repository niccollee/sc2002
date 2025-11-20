import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

/**
 * Handles user interactions for viewing and filtering internships.
 * Provides methods for displaying internships and filtering them
 * by various attributes for general users or specific students.
 */
public class InternshipUI {
    private InternshipDisplay internshipDisplay;
    private InternshipDbMgr internshipDbMgr;
    Scanner sc;

    /**
     * Creates a new InternshipUI instance and initializes required components.
     */
    public InternshipUI() {
        internshipDisplay = new InternshipDisplay();
        internshipDbMgr = InternshipDbMgr.getInstance();
        sc = Input.SC;
    }

    /**
     * Displays all internships without filtering.
     */
    public void viewAllInternships() {
        internshipDisplay.showInternships(internshipDbMgr.getAll());
    }

    /**
     * Displays internships filtered based on a specific student's major and year of study.
     *
     * @param student the student used to filter internships
     */
    public void viewAllInternships(Student student) {
        List<Internship> internshipList = internshipDbMgr.getAll();
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.PREFERREDMAJOR, student.getMajor());
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.VISIBILITY, "true");
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.STATUS, "APPROVED");
        switch(student.getYearOfStudy()) {
            case 1:
            case 2:
                internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.LEVEL, "BASIC");
                break;
            default:
                break;
        };
        internshipDisplay.showInternships(internshipList);
    }

    /**
     * Prompts the user to filter internships by various criteria.
     * Supports filtering by title, level, preferred major, application dates,
     * status, company name, number of slots, and visibility.
     */
    public void filterInternshipsBy() {
        internshipDisplay.showFilterMenu(false);
        System.out.println("Enter your input:");
        int choice = sc.nextInt();
        String argString;
        int argInt;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean before;
        List<Internship> internshipList;
        switch (choice) {
            case 1:
                System.out.println("Filtering by title. Enter title: ");
                argString = sc.next();
                internshipList = internshipDbMgr.filter(InternshipAttributes.TITLE, argString);
                break;
            case 2:
                System.out.println("Filtering by level. ");
                internshipDisplay.showLevelMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                InternshipLevel level;
                switch (argInt) {
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
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.LEVEL, level.toString());
                break;
            case 3: 
                System.out.println("Filtering by preferred major. Enter preferred major: ");
                argString = sc.next();
                internshipList = internshipDbMgr.filter(InternshipAttributes.PREFERREDMAJOR, argString);
                break;
            case 4:
                System.out.println("Filtering by application open date. Enter application open date (dd-mm-yyyy): ");
                argString = sc.next();
                LocalDate openDate;
                try {
                    openDate = LocalDate.parse(argString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return;
                }
                internshipDisplay.showDateMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                switch (argInt) {
                    case 1:
                        before = true;
                        break;
                    case 2:
                        before = false;
                        break;
                    default:
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.APPOPENDATE, openDate, before);
                break;
            case 5:
                System.out.println("Filtering by application close date. Enter application open date (dd-mm-yyyy): ");
                argString = sc.next();
                LocalDate closeDate;
                try {
                    closeDate = LocalDate.parse(argString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return;
                }
                internshipDisplay.showDateMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                switch (argInt) {
                    case 1:
                        before = true;
                        break;
                    case 2:
                        before = false;
                        break;
                    default:
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.APPCLOSEDATE, closeDate, before);
                break;
            case 6:
                System.out.println("Filtering by status.");
                internshipDisplay.showStatusMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                Status status;
                switch (argInt) {
                    case 1:
                        status = Status.APPROVED;
                        break;
                    case 2:
                        status = Status.PENDING;
                        break;
                    case 3:
                        status = Status.REJECT;
                        break;
                    default:
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.STATUS, status.toString());
                break;
            case 7:
                System.out.println("Filtering by company name. Enter company name: ");
                argString = sc.next();
                internshipList = internshipDbMgr.filter(InternshipAttributes.COMPANYNAME, argString);
                break;
            case 8:
                System.out.println("Filtering by number of slots. Enter number of slots: ");
                argInt = sc.nextInt();
                internshipList = internshipDbMgr.filter(InternshipAttributes.NUMSLOT, Integer.toString(argInt));
                break;
            case 9:
                System.out.println("Filtering by visibility.");
                internshipDisplay.showVisibilityMenu();
                System.out.println("Enter your inputs: ");
                argString = sc.nextLine();
                switch (argString) {
                    case "1":
                        internshipList = internshipDbMgr.filter(InternshipAttributes.VISIBILITY, "true");
                        break;
                    case "2":
                        internshipList = internshipDbMgr.filter(InternshipAttributes.VISIBILITY, "false");
                        break;
                }
            case 10:
                System.out.println("Quiting...");
                return;
            default:
                System.out.println("Invalid input!");
                return;
        }
        internshipDisplay.showInternships(internshipList);
    } 

    /**
     * Prompts a specific student to filter internships according to criteria relevant to them.
     * Automatically applies additional filters based on student's major, year, visibility, and approved status.
     *
     * @param student the student for whom the internships are filtered
     */
    public void filterInternshipsBy(Student student) {
        internshipDisplay.showFilterMenu(true);
        System.out.println("Enter your input:");
        int choice = sc.nextInt();
        String argString;
        int argInt;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        boolean before;
        List<Internship> internshipList;
        switch (choice) {
            case 1:
                System.out.println("Filtering by title. Enter title: ");
                argString = sc.next();
                internshipList = internshipDbMgr.filter(InternshipAttributes.TITLE, argString);
                break;
            case 2:
                System.out.println("Filtering by company name. Enter company name: ");
                argString = sc.next();
                internshipList = internshipDbMgr.filter(InternshipAttributes.COMPANYNAME, argString);
                break;
            case 3:
                System.out.println("Filtering by number of slots. Enter number of slots: ");
                argInt = sc.nextInt();
                internshipList = internshipDbMgr.filter(InternshipAttributes.NUMSLOT, Integer.toString(argInt));
                break;
            case 4:
                System.out.println("Filtering by level. ");
                internshipDisplay.showLevelMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                InternshipLevel level;
                switch (argInt) {
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
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.LEVEL, level.toString());
                break;
            case 5:
                System.out.println("Filtering by application open date. Enter application open date (dd-mm-yyyy): ");
                argString = sc.next();
                LocalDate openDate;
                try {
                    openDate = LocalDate.parse(argString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return;
                }
                internshipDisplay.showDateMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                switch (argInt) {
                    case 1:
                        before = true;
                        break;
                    case 2:
                        before = false;
                        break;
                    default:
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.APPOPENDATE, openDate, before);
                break;
            case 6:
                System.out.println("Filtering by application close date. Enter application open date (dd-mm-yyyy): ");
                argString = sc.next();
                LocalDate closeDate;
                try {
                    closeDate = LocalDate.parse(argString, formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return;
                }
                internshipDisplay.showDateMenu();
                System.out.println("Enter your input: ");
                argInt = sc.nextInt();
                switch (argInt) {
                    case 1:
                        before = true;
                        break;
                    case 2:
                        before = false;
                        break;
                    default:
                        System.out.println("Invalid input!");
                        return;
                }
                internshipList = internshipDbMgr.filter(InternshipAttributes.APPCLOSEDATE, closeDate, before);
                break;
            case 7:
                System.out.println("Quiting...");
                return;
            default:
                System.out.println("Invalid input!");
                return;
        }
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.PREFERREDMAJOR, student.getMajor());
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.VISIBILITY, "true");
        internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.STATUS, "APPROVED");
        switch(student.getYearOfStudy()) {
            case 1:
            case 2:
                internshipList = InternshipFilter.filter(internshipList, InternshipAttributes.LEVEL, "BASIC");
                break;
            default:
                break;
        };
        internshipDisplay.showInternships(internshipList);
    }
}
