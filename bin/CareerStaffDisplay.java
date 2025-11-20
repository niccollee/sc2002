import java.util.List;

public class CareerStaffDisplay {

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
