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
        System.out.println(
                "No. \t:\tCompany Rep ID \t:\tCompany Name \t:\tDepartment \t:\tPositon \t:\tCompany Rep Status");
        for (int i = 0; i < cRepList.size(); i++) {

            System.out.println(i +
                    cRepList.get(i).getId() + " \t:\t" +
                    cRepList.get(i).getName() + " \t:\t" +
                    cRepList.get(i).getDepartment() + " \t:\t" +
                    cRepList.get(i).getPosition() + " \t:\t" +
                    cRepList.get(i).getRepStatus() + " \t:\t");
        }

        System.out.println("=========================");
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
        List<Internship> notApprovedinternshipList = InternshipDbMgr.getInstance().filter(InternshipAttributes.STATUS,
                "PENDING");
        
        System.out.println("=========================");
        System.out.println("Internship Pending Approval");
        System.out.println("No. \t:\tInternship Title \t:\t Internship ID \t:\t Opening Date \t:\t Closing Date \t:\t Status");

        for(int i = 0; i< notApprovedinternshipList.size(); i++){
            System.out.println(
            notApprovedinternshipList.get(i).getTitle() + "\t:\t" +
            notApprovedinternshipList.get(i).getId() + "\t:\t" + 
            notApprovedinternshipList.get(i).getAppOpenDate() + "\t:\t" +
            notApprovedinternshipList.get(i).getAppCloseDate() + "\t:\t" +
            notApprovedinternshipList.get(i).getStatus().toString() + "\t:\t");
        }

        System.out.println("=========================");
    }

}
