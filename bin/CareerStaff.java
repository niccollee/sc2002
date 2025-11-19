import java.util.List;

public class CareerStaff implements IUser {
    private String id;
    private String password;
    private String name;
    private String role;
    private String department;
    private String email;

    public CareerStaff(String id, String name, String role, String department, String email, String passwordHash) {
        this.id = id;
        this.password = passwordHash;
        this.name = name;
        this.role = role;
        this.department = department;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getDept() {
        return this.department;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String password) {
        this.password = IPasswordMgr.hashPassword(password);
    }

    public String getPassword() {
        return this.password;
    }

    public boolean approveCompanyRep(String companyRepId) {
        CompanyRep companyRep = CompanyRepDbMgr.getInstance().get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.APPROVED);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean rejectCompanyRep(String companyRepId) {
        CompanyRep companyRep = CompanyRepDbMgr.getInstance().get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.REJECT);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean approveInternship(int internshipId) {
        Internship internship = InternshipDbMgr.getInstance().get(internshipId);

        if(internship != null){
            internship.setStatus(Status.APPROVED);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    public boolean rejectInternship(int internshipId) {
        Internship internship = InternshipDbMgr.getInstance().get(internshipId);

        if(internship != null){
            internship.setStatus(Status.REJECT);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    //
    public boolean approveStudentWidthdrawl(String studentID) {
        List<InternshipWithdrawalApplicants> internshipWithdrawalApplicantsList = InternshipWithdrawalDbMgr
                .getInstance().showAll();
        for (int i = 0; i < internshipWithdrawalApplicantsList.size(); i++) {
            if (internshipWithdrawalApplicantsList.get(i).getStudent().getId() == studentID) {
                internshipWithdrawalApplicantsList.get(i).withdrawInternship();
                return true;
            }
        }
        return false;
    }

    // rejecting studentWithdrawl does nothing, internshipWithdrawlApplicants remain
    // inside
    public boolean rejectStudentWithdrawl(String studentID) {
        return true;
    }

}
