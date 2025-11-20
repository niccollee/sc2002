import java.util.List;

/**
 * Handles console display operations for career centre staff.
 * This includes login prompts, main menu options, and formatted tables
 * for company representative applications, student withdrawal requests,
 * and internship opportunities.
 */
public class CareerStaffDisplay {

        /**
         * Displays the login prompt for career centre staff.
         * Shows fields for username and password in a formatted block.
         */
        public void showStaffLogin() {
                String output = """
                                =========================
                                CAREER CENTRE STAFF
                                Username:
                                Password:

                                =========================
                                """;
                System.out.println(output);
        }

        /**
         * Displays the main menu for career centre staff.
         * Lists the available actions such as reviewing applications,
         * handling withdrawal requests and managing internships.
         */
        public void showMenu() {
                String output = """
                                =========================
                                1) Company Representatives Applications
                                2) Student Withdrawal Requests
                                3) Approve Internship Opportunities
                                4) View Internship Opportunities
                                5) Change Password
                                6) Quit
                                =========================
                                """;
                System.out.println(output);
        }

        /**
         * Displays a formatted table of pending company representative applications.
         * The data is retrieved from {@link CompanyRepDbMgr} and only representatives
         * with PENDING status are shown.
         */
        public void showCompanyRepApplications() {
                List<CompanyRep> cRepList = CompanyRepDbMgr.getInstance().filter(CompanyRepAttributes.REPSTATUS,
                                CompanyRepStatus.PENDING.toString());

                System.out.println("=========================");
                System.out.println("Company Representative Applications");

                // Header
                System.out.printf(
                                "%-5s | %-20s | %-25s | %-20s | %-15s | %-20s%n",
                                "No.", "Company Rep ID", "Company Name", "Department", "Position", "Rep Status");

                // Separator line
                System.out.println(
                                "===============================================================================================================");

                // Rows
                for (int i = 0; i < cRepList.size(); i++) {
                        System.out.printf(
                                        "%-5d | %-20s | %-25s | %-20s | %-15s | %-20s%n",
                                        i,
                                        cRepList.get(i).getId(),
                                        cRepList.get(i).getName(),
                                        cRepList.get(i).getDepartment(),
                                        cRepList.get(i).getPosition(),
                                        cRepList.get(i).getRepStatus());
                }

                System.out.println(
                                "===============================================================================================================");

        }

        /**
         * Displays a list of internship withdrawal requests made by students.
         * Each line shows the applicant number, student name and internship ID.
         * The data is retrieved from {@link InternshipWithdrawalDbMgr}.
         */
        public void showWithdrawalRequest() {
                List<InternshipWithdrawalApplicants> internshipWithdrawlList = InternshipWithdrawalDbMgr.getInstance()
                                .showAll();

                System.out.println("=========================");
                System.out.println("Internship Withdrawl Applicants");
                System.out.println("No. \t:\tStudent Name \t:\t Internship ID");

                for (int i = 0; i < internshipWithdrawlList.size(); i++) {
                        System.out.println(internshipWithdrawlList.get(i).getId() + "\t:\t"
                                        + internshipWithdrawlList.get(i).getStudent().getName() + "\t:\t"
                                        + internshipWithdrawlList.get(i).getInternship().getId());
                }

                System.out.println("=========================");
        }

        /**
         * Displays a formatted table of internships that are pending approval.
         * The data is retrieved from {@link InternshipDbMgr} and filtered by
         * PENDING status.
         */
        public void showInternshipsPending() {
                List<Internship> notApprovedinternshipList = InternshipDbMgr.getInstance().filter(
                                InternshipAttributes.STATUS,
                                "PENDING");

                System.out.println("=========================");
                System.out.println("Internship Pending Approval");

                // Header
                System.out.printf(
                                "%-5s | %-30s | %-15s | %-15s | %-15s | %-15s%n",
                                "No.", "Internship Title", "Internship ID", "Opening Date", "Closing Date", "Status");

                System.out.println(
                                "============================================================================================================");
                // Rows
                for (int i = 0; i < notApprovedinternshipList.size(); i++) {
                        System.out.printf(
                                        "%-5d | %-30s | %-15s | %-15s | %-15s | %-15s%n",
                                        i,
                                        notApprovedinternshipList.get(i).getTitle(),
                                        notApprovedinternshipList.get(i).getId(),
                                        notApprovedinternshipList.get(i).getAppOpenDate(),
                                        notApprovedinternshipList.get(i).getAppCloseDate(),
                                        notApprovedinternshipList.get(i).getStatus().toString());
                }

                System.out.println(
                                "============================================================================================================");
        }

}
