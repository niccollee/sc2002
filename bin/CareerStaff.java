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
        List<CompanyRep> companyRepList = CompanyRepDbMgr.getInstance().showAll();

        CompanyRep companyRep;

        // search for companyRepId in companyRepList
        for (int i = 0; i < companyRepList.size(); i++) {
            companyRep = companyRepList.get(i);
            if (companyRep.getId() == companyRepId) {
                companyRep.setRepStatus(CompanyRepStatus.APPROVED);
                return true;
            }
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean rejectCompanyRep(String companyRepId) {
        List<CompanyRep> companyRepList = CompanyRepDbMgr.getInstance().showAll();

        CompanyRep companyRep;

        // Search for companyRepId in companyRepList
        for (int i = 0; i < companyRepList.size(); i++) {
            companyRep = companyRepList.get(i);
            if (companyRep.getId() == companyRepId) {
                companyRep.setRepStatus(CompanyRepStatus.REJECT);
                return true;
            }
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    public boolean approveInternship(int internshipId) {
        List<Internship> internshipList = InternshipDbMgr.getInstance().showAll();

        Internship internship;

        // Search for internshipId in internshipList
        for (int i = 0; i < internshipList.size(); i++) {
            internship = internshipList.get(i);
            if (internship.getId() == internshipId) {
                internship.setStatus(Status.APPROVED);
                return true;
            }
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    public boolean rejectInternship(int internshipId) {
        List<Internship> internshipList = InternshipDbMgr.getInstance().showAll();

        Internship internship;

        // Search for internshipId in internshipList
        for (int i = 0; i < internshipList.size(); i++) {
            internship = internshipList.get(i);
            if (internship.getId() == internshipId) {
                internship.setStatus(Status.REJECT);
                return true;
            }
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    public boolean validatePassword(String password) {
        CareerStaffPasswordMgr passwordMgr = new CareerStaffPasswordMgr();
        return passwordMgr.validate(this, password);
    }

}
