import java.io.Serializable;
import java.util.List;

/**
 * Singleton container that stores the application's core data lists for serialization.
 *
 * updateSelfState() captures current lists from DbMgr singletons.
 * restoreOtherState() writes stored lists back into the DbMgr singletons.
 */
public class AppState implements Serializable {
    private static final long serialVersionUID = 1L;
    private static AppState instance;

    private List<CareerStaff> careerStaffList;
    private List<CompanyRep> companyRepList;
    private List<Internship> internshipList;
    private List<Student> studentList;
    private List<InternshipWithdrawalApplicant> internshipWithdrawalList;

    private AppState() {
    }

    /**
     * Return the shared AppState instance, creating it if necessary.
     *
     * @return the singleton AppState
     */
    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }


    /**
     * Replace the current singleton AppState.
     *
     * @param newState the AppState to set
     */
    public static synchronized void setInstance(AppState newState) {
        instance = newState;
    }

    /**
     * Capture the current lists from DbMgr singletons into this AppState.
     *
     * Expects each DbMgr to expose a read accessor such as getAll().
     */
    public void updateSelfState() {
        this.careerStaffList = CareerStaffDbMgr.getInstance().getAll();
        this.companyRepList = CompanyRepDbMgr.getInstance().getAll();
        this.internshipList = InternshipDbMgr.getInstance().getAll();
        this.studentList = StudentDbMgr.getInstance().getAll();
        this.internshipWithdrawalList = InternshipWithdrawalDbMgr.getInstance().getAll();
    }

    /**
     * Restore stored lists back into the DbMgr singletons.
     *
     * Expects each DbMgr to provide a setter such as setAll(List<T>).
     */
    public void restoreOtherState() {
        if (careerStaffList != null) CareerStaffDbMgr.getInstance().setAll(careerStaffList);
        if (companyRepList != null) CompanyRepDbMgr.getInstance().setAll(companyRepList);
        if (internshipList != null) InternshipDbMgr.getInstance().setAll(internshipList);
        if (studentList != null) StudentDbMgr.getInstance().setAll(studentList);
        if (internshipWithdrawalList != null) InternshipWithdrawalDbMgr.getInstance().setAll(internshipWithdrawalList);
    }


    /**
     * Get the stored career staff list.
     *
     * @return career staff list
     */
    public List<CareerStaff> getCareerStaffList() {
        return careerStaffList;
    }

    /**
     * Set the stored career staff list.
     *
     * @param careerStaffList list to store
     */
    public void setCareerStaffList(List<CareerStaff> careerStaffList) {
        this.careerStaffList = careerStaffList;
    }

    /**
     * Get the stored company representative list.
     *
     * @return company representative list
     */
    public List<CompanyRep> getCompanyRepList() {
        return companyRepList;
    }

    /**
     * Set the stored company representative list.
     *
     * @param companyRepList list to store
     */
    public void setCompanyRepList(List<CompanyRep> companyRepList) {
        this.companyRepList = companyRepList;
    }

    /**
     * Get the stored internship list.
     *
     * @return internship list
     */
    public List<Internship> getInternshipList() {
        return internshipList;
    }

    /**
     * Set the stored internship list.
     *
     * @param internshipList list to store
     */
    public void setInternshipList(List<Internship> internshipList) {
        this.internshipList = internshipList;
    }

    /**
     * Get the stored student list.
     *
     * @return student list
     */
    public List<Student> getStudentList() {
        return studentList;
    }

    /**
     * Set the stored student list.
     *
     * @param studentList list to store
     */
    public void setStudentList(List<Student> studentList) {
        this.studentList = studentList;
    }

    /**
     * Get the stored internship withdrawal applicant list.
     *
     * @return withdrawal applicant list
     */
    public List<InternshipWithdrawalApplicant> getInternshipWithdrawalList() {
        return internshipWithdrawalList;
    }

    /**
     * Set the stored internship withdrawal applicant list.
     *
     * @param internshipWithdrawalList list to store
     */
    public void setInternshipWithdrawalList(List<InternshipWithdrawalApplicant> internshipWithdrawalList) {
        this.internshipWithdrawalList = internshipWithdrawalList;
    }
}