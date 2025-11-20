import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;

/**
 * Singleton application state container that saves DbMgr singletons.
 *
 * Stores references to each DbMgr instance so state can be restored later.
 */
public class AppState implements Serializable {
    private static final long serialVersionUID = 1L;
    private static AppState instance;

    private CareerStaffDbMgr careerStaffDbMgrInstance;
    private CompanyRepDbMgr companyRepDbMgrInstance;
    private InternshipDbMgr internshipDbMgrInstance;
    private StudentDbMgr studentDbMgrInstance;
    private List<InternshipApplicationDbMgr> internshipApplicationDbMgrInstanceList;
    private InternshipWithdrawalDbMgr internshipWithdrawalDbMgrInstance;

    private AppState() {
        // capture current singleton instances
        updateSelfState();
    }

    /**
     * Return the single AppState instance, creating it if necessary.
     *
     * @return shared AppState
     */
    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    /**
     * Replace the current singleton instance (useful when loading state).
     *
     * @param newState the AppState to set as the singleton
     */
    public static synchronized void setInstance(AppState newState) {
        instance = newState;
    }

    /**
     * Capture current DbMgr singletons into this AppState.
     */
    public void updateSelfState() {
        this.careerStaffDbMgrInstance = CareerStaffDbMgr.getInstance();
        this.companyRepDbMgrInstance = CompanyRepDbMgr.getInstance();
        this.internshipDbMgrInstance = InternshipDbMgr.getInstance();
        this.studentDbMgrInstance = StudentDbMgr.getInstance();

        // store the InternshipApplicationDbMgr singleton in a list to allow
        // storing multiple instances if needed in future
        this.internshipApplicationDbMgrInstanceList = new ArrayList<>();
        InternshipApplicationDbMgr iadb = InternshipApplicationDbMgr.getInstance();
        if (iadb != null) {
            this.internshipApplicationDbMgrInstanceList.add(iadb);
        }

        this.internshipWithdrawalDbMgrInstance = InternshipWithdrawalDbMgr.getInstance();
    }

    /**
     * Restore the application's singleton DbMgrs from the saved instances in this AppState.
     */
    public void restoreOtherState() {
        if (careerStaffDbMgrInstance != null) CareerStaffDbMgr.setInstance(careerStaffDbMgrInstance);
        if (companyRepDbMgrInstance != null) CompanyRepDbMgr.setInstance(companyRepDbMgrInstance);
        if (internshipDbMgrInstance != null) InternshipDbMgr.setInstance(internshipDbMgrInstance);
        if (studentDbMgrInstance != null) StudentDbMgr.setInstance(studentDbMgrInstance);

        // restore InternshipApplicationDbMgr: if multiple saved, restore the first one
        if (internshipApplicationDbMgrInstanceList != null && !internshipApplicationDbMgrInstanceList.isEmpty()) {
            InternshipApplicationDbMgr.setInstance(internshipApplicationDbMgrInstanceList.get(0));
        }

        if (internshipWithdrawalDbMgrInstance != null) InternshipWithdrawalDbMgr.setInstance(internshipWithdrawalDbMgrInstance);
    }

    // Getters and setters for saved DbMgr instances

    public CareerStaffDbMgr getCareerStaffDbMgrInstance() {
        return careerStaffDbMgrInstance;
    }

    public void setCareerStaffDbMgrInstance(CareerStaffDbMgr careerStaffDbMgrInstance) {
        this.careerStaffDbMgrInstance = careerStaffDbMgrInstance;
    }

    public CompanyRepDbMgr getCompanyRepDbMgrInstance() {
        return companyRepDbMgrInstance;
    }

    public void setCompanyRepDbMgrInstance(CompanyRepDbMgr companyRepDbMgrInstance) {
        this.companyRepDbMgrInstance = companyRepDbMgrInstance;
    }

    public InternshipDbMgr getInternshipDbMgrInstance() {
        return internshipDbMgrInstance;
    }

    public void setInternshipDbMgrInstance(InternshipDbMgr internshipDbMgrInstance) {
        this.internshipDbMgrInstance = internshipDbMgrInstance;
    }

    public StudentDbMgr getStudentDbMgrInstance() {
        return studentDbMgrInstance;
    }

    public void setStudentDbMgrInstance(StudentDbMgr studentDbMgrInstance) {
        this.studentDbMgrInstance = studentDbMgrInstance;
    }

    public List<InternshipApplicationDbMgr> getInternshipApplicationDbMgrInstanceList() {
        return internshipApplicationDbMgrInstanceList;
    }

    public void setInternshipApplicationDbMgrInstanceList(List<InternshipApplicationDbMgr> internshipApplicationDbMgrInstanceList) {
        this.internshipApplicationDbMgrInstanceList = internshipApplicationDbMgrInstanceList;
    }

    public InternshipWithdrawalDbMgr getInternshipWithdrawalDbMgrInstance() {
        return internshipWithdrawalDbMgrInstance;
    }

    public void setInternshipWithdrawalDbMgrInstance(InternshipWithdrawalDbMgr internshipWithdrawalDbMgrInstance) {
        this.internshipWithdrawalDbMgrInstance = internshipWithdrawalDbMgrInstance;
    }
}