import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class InternshipUI {
    private InternshipDisplay internshipDisplay;
    private InternshipDbMgr internshipDbMgr;
    Scanner sc;

    public InternshipUI() {
        internshipDisplay = new InternshipDisplay();
        internshipDbMgr = InternshipDbMgr.getInstance();
        sc = Input.SC;
    }

    public void viewAllInternships() {
        internshipDisplay.showInternships(internshipDbMgr.showAll());
    }

    public void filterInternshipsBy(boolean isStudent) {
        internshipDisplay.showFilterMenu();
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
                internshipDisplay.showStatusmenu();
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
                if (isStudent) {
                    System.out.println("Invalid input!");
                    return;
                }
            default:
                System.out.println("Invalid input!");
                return;
        }
        if (isStudent) {
            internshipDisplay.showInternships(InternshipFilter.filter(internshipList, InternshipAttributes.VISIBILITY, "true"));
        } else {
            internshipDisplay.showInternships(internshipList);;
        }
    } 
}
