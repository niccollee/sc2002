import java.util.Scanner;
/**
 * Handles the registration process for company representatives. 
 * Prompts user for registration details which creates a PENDING
 * {@CompanyRep} inside {@CompanyRepMgr}.
 */
public class RepRegisterUI {
    private Scanner sc;
    private CompanyRepDbMgr companyRepDbMgr;

    /**
     * Creates a new UI component for company representative
     * registration.
     * @param companyRepDbMgr is the data manager responsible 
     * for storing and creating company information.
     */
    public RepRegisterUI(CompanyRepDbMgr companyRepDbMgr) {
        sc = Input.SC;
        this.companyRepDbMgr = companyRepDbMgr;
    }

    /**
     * Run the registration flow.
     *
     * Prompts for id, company name, department and position. Attempts to add a new
     * {@link CompanyRep} with a default password ("password", hashed) and prints
     * success or failure messages to stdout.
     */
    public void start() {
        String output = """
                =========================
                Company representative registration form.
                Enter id:
                """;
        System.out.println(output);
        String id = sc.nextLine().trim();
        System.out.println("Enter company name: ");
        String companyName = sc.nextLine().trim();
        System.out.println("Enter department: ");
        String department = sc.nextLine().trim();
        System.out.println("Enter position: ");
        String position = sc.nextLine().trim();
        if (companyRepDbMgr.add(
            id, 
            companyName, 
            department, 
            position, 
            CompanyRepStatus.PENDING, 
            "password"
            )) {
                System.out.println("Successfully registered!");
            } else {
                System.out.println("Error in registration.");
            }
        System.out.println("=========================");
    }
}
