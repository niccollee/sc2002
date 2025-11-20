import java.io.Serializable;
import java.util.List;

/**
 * Singleton application state container.
 *
 * Holds the single shared copy of all managed lists and a current user index.
 */
public class AppState implements Serializable {
    private static AppState instance;

    private CareerStaffDbMgr careerStaffDbMgrInstance;
    private List<CompanyRep> companyRepList;
    private List<Internship> internshipList;
    private List<Student> studentList;
    private List<InternshipApplication> internshipApplicationList;
    private List<InternshipWithdrawalApplicant> internshipWithdrawalApplicantList;

    private AppState() {
        // private ctor to enforce singleton
        this.careerStaffDbMgrInstance = CareerStaffDbMgr.getInstance();
        this.companyRepList = CompanyRepDbMgr.getInstance().getAll();
        this.internshipList = InternshipDbMgr.getInstance().getAll();
        this.studentList = StudentDbMgr.getInstance().getAll();
        this.internshipApplicationList = InternshipApplicationDbMgr.getInstance().getAll();
        this.internshipWithdrawalApplicantList = InternshipWithdrawalDbMgr.getInstance().getAll();
    }

    /**
     * Return the single AppState instance, creating it if necessary.
     *
     * @return shared AppState
     */
    public static AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public void updateSelfState(){
        this.careerStaffDbMgrInstance = CareerStaffDbMgr.getInstance();
        this.companyRepList = CompanyRepDbMgr.getInstance().getAll();
        this.internshipList = InternshipDbMgr.getInstance().getAll();
        this.studentList = StudentDbMgr.getInstance().getAll();
        this.internshipApplicationList = InternshipApplicationDbMgr.getInstance().getAll();
        this.internshipWithdrawalApplicantList = InternshipWithdrawalDbMgr.getInstance().getAll();
    }

    public void restoreOtherState(){
        CareerStaffDbMgr.setInstance(careerStaffDbMgrInstance);
    }

    /**
     * Replace the current singleton instance (useful when loading state).
     *
     * @param newState the AppState to set as the singleton
     */
    public static void setInstance(AppState newState) {
        instance = newState;
    }

    public List<CareerStaff> getCareerStaffList() {
        return careerStaffList;
    }

    public void setCareerStaffList(List<CareerStaff> careerStaffList) {
        this.careerStaffList = careerStaffList;
    }

    public List<CompanyRep> getCompanyRepList() {
        return companyRepList;
    }

    public void setCompanyRepList(List<CompanyRep> companyRepList) {
        this.companyRepList = companyRepList;
    }

    public List<Internship> getInternshipList() {
        return internshipList;
    }

    public void setInternshipList(List<Internship> internshipList) {
        this.internshipList = internshipList;
    }

    public List<Student> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    public List<InternshipApplication> getInternshipApplicationList() {
        return internshipApplicationList;
    }

    public void setInternshipApplicationList(List<InternshipApplication> internshipApplicationList) {
        this.internshipApplicationList = internshipApplicationList;
    }

    public List<InternshipWithdrawalApplicant> getInternshipWithdrawalApplicant() {
        return internshipWithdrawalApplicant;
    }

    public void setInternshipWithdrawalApplicant(List<InternshipWithdrawalApplicant> internshipWithdrawalApplicant) {
        this.internshipWithdrawalApplicant = internshipWithdrawalApplicant;
    }
}