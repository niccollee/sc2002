import java.io.Serializable;
import java.util.List;

/**
 * Singleton application state container that stores the underlying data lists.
 *
 * Use updateSelfState() to capture current in-memory lists from the DbMgr singletons.
 * Use restoreOtherState() to push the stored lists back to the singletons (DbMgrs must
 * provide a suitable setter or a way to replace their internal lists).
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

    public static synchronized AppState getInstance() {
        if (instance == null) {
            instance = new AppState();
        }
        return instance;
    }

    public static synchronized void setInstance(AppState newState) {
        instance = newState;
    }

    /**
     * Capture the current lists from the DbMgr singletons into this AppState.
     * This method expects the DbMgr classes to expose read accessors such as
     * get(), getAll() or similar.
     */
    public void updateSelfState() {
        // adapt method names if your DbMgr uses different accessors
        this.careerStaffList = CareerStaffDbMgr.getInstance().getAll();
        this.companyRepList = CompanyRepDbMgr.getInstance().getAll();
        this.internshipList = InternshipDbMgr.getInstance().getAll();
        this.studentList = StudentDbMgr.getInstance().getAll();
        this.internshipWithdrawalList = InternshipWithdrawalDbMgr.getInstance().getAll();
    }

    /**
     * Restore the stored lists back into the singleton DbMgrs.
     * Each DbMgr must provide a setter or a way to replace its internal list.
     * If such setters do not exist implement them (for example setList(List<T>)).
     */
    public void restoreOtherState() {
        if (careerStaffList != null) CareerStaffDbMgr.getInstance().setAll(careerStaffList);
        if (companyRepList != null) CompanyRepDbMgr.getInstance().setAll(companyRepList);
        if (internshipList != null) InternshipDbMgr.getInstance().setAll(internshipList);
        if (studentList != null) StudentDbMgr.getInstance().setAll(studentList);
        if (internshipWithdrawalList != null) InternshipWithdrawalDbMgr.getInstance().setAll(internshipWithdrawalList);
    }

    // Getters / setters for serialization and external access

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

    public List<InternshipWithdrawalApplicant> getInternshipWithdrawalList() {
        return internshipWithdrawalList;
    }

    public void setInternshipWithdrawalList(List<InternshipWithdrawalApplicant> internshipWithdrawalList) {
        this.internshipWithdrawalList = internshipWithdrawalList;
    }
}