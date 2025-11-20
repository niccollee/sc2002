import java.time.LocalDate;
/**
 * Represents a student who can apply for internships, accept a confirmed internship, 
 * and manage applied internships.
 * Implements {@link IUser} interface.
 */
public class Student implements IUser {
    private String id;
    private String name;
    private String password;
    private int yearOfStudy;
    private String major;
    private InternshipApplicationDbMgr appliedInternships;
    private String email;
    private Internship acceptedInternships;

    /**
     * Creates a student object.
     * @param id the id of the student, which is their matriculation number.
     * @param name the name of the student
     * @param passwordHash the hashed password of the student
     * @param yearOfStudy the year of study of the student
     * @param major the major of the student
     * @param email the email of the student
     */
    public Student(String id, String name, String passwordHash, int yearOfStudy, String major, String email) {
        this.id = id;
        this.name = name;
        this.password = passwordHash;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.appliedInternships = new InternshipApplicationDbMgr();
        this.email = email;
        InternshipApplicationDbMgr appliedInternships = new InternshipApplicationDbMgr();
        this.appliedInternships = appliedInternships;
        this.acceptedInternships = null;
    }
    // Getter method
    public String getId() {return id;}
    public String getName() {return name;}  
    public int getYearOfStudy() {return yearOfStudy;}
    public String getMajor() {return major;}
    public InternshipApplicationDbMgr getAppliedInternships() {return appliedInternships;}
    public Internship getAcceptedInternship() {return acceptedInternships;}   
    public String getPassword() {return password;}
    public String getEmail(){return email;}
    
    // Setters
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setPassword(String password) {
        this.password = IPasswordMgr.hashPassword(password);
    }
    
    /**
     * Method to apply internship. 
     * @param internship is the internship object that this student is applying for.
     * @return true if application is successful, otherwise false. 
     */
    public boolean applyInternship(Internship internship) {
        if (appliedInternships.contains(internship)) {
            return false;
        }

        if (internship.getStatus() == Status.FILLED) {
            return false;
        }

        if (internship.getStatus() != Status.APPROVED) {
            return false;
        }

        if (!internship.getVisibility()) {
            
        }

        LocalDate today = LocalDate.now();
        if (today.isBefore(internship.getAppOpenDate()) ||today.isAfter(internship.getAppCloseDate())) {
            return false;
        }

        if (getAppliedInternships().showAll().size() > 2) {
            return false;
        }

        InternshipApplication internshipApplication = new InternshipApplication(internship, InternshipApplicationStatus.PENDING);
        if (!appliedInternships.add(internshipApplication)) {
            return false;
        }
        return true;
    }
    /**
     * Method to accept internship
     * @param internship is the internship object to be accepted.
     * @return true if internship is successfully accepted, otherwise false.
     */
    public boolean acceptInternship(Internship internship) {
        if (internship == null) return false;
        // only can accept one internship
		// if (acceptedInternships != null) {
		// 	return false;
		// }
        
        InternshipApplication internshipApplication = appliedInternships.get(internship);
        if (internshipApplication == null) return false;
        
		if (internshipApplication.getInternshipApplicationStatus() != InternshipApplicationStatus.SUCCESSFUL) {
			return false;
		}

        appliedInternships.remove(internshipApplication);
        acceptedInternships = internship;

        InternshipDbMgr internshipDbMgr = InternshipDbMgr.getInstance();
        Internship dbInternship = internshipDbMgr.get(internship.getId());
        if (dbInternship != null) {
            dbInternship.incrementConfirmedSlots();
        }
        return true;
    }
    /**
     * Method to withdraw internship. Sets the acceptedInternships attribute
     * to null.
     */
    public void withdrawAcceptedInternship() {
        acceptedInternships = null;
    }    
}
