import java.util.List;

public class CareerStaffController {
    private static CareerStaffController instance;

    public static CareerStaffController getInstance() {
        if (instance == null) {
            instance = new CareerStaffController();
        }
        return instance;
    }

    public boolean approveCompanyRep(String companyRepId, CompanyRepDbMgr companyRepDbMgr) {
        CompanyRep companyRep = companyRepDbMgr.get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.APPROVED);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean rejectCompanyRep(String companyRepId, CompanyRepDbMgr companyRepDbMgr) {
        CompanyRep companyRep = companyRepDbMgr.get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.REJECT);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean approveInternship(int internshipId, InternshipDbMgr internshipDbMgr) {
        Internship internship = internshipDbMgr.get(internshipId);

        if (internship != null) {
            internship.setStatus(Status.APPROVED);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    public boolean rejectInternship(int internshipId, InternshipDbMgr internshipDbMgr) {
        Internship internship = internshipDbMgr.get(internshipId);

        if (internship != null) {
            internship.setStatus(Status.REJECT);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    //
    public boolean approveStudentWidthdrawl(String studentId, InternshipWithdrawalDbMgr internshipWithdrawalDbMgr) {
        InternshipWithdrawalApplicants internshipWithdrawalApplicants = internshipWithdrawalDbMgr
                .getFromStudentId(studentId);

        if (internshipWithdrawalApplicants == null) {
            return false;
        } else {
            internshipWithdrawalApplicants.withdrawInternship();
            return true;
        }

    }

    // rejecting studentWithdrawl does nothing, internshipWithdrawlApplicants remain
    // inside
    public boolean rejectStudentWithdrawl(String studentId, InternshipWithdrawalDbMgr internshipWithdrawalDbMgr) {
        return true;
    }

}
