import java.time.LocalDate;

/**
 * Represents a student who can apply for internships, accept a confirmed
 * internship,
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
     * 
     * @param id           the id of the student, which is their matriculation
     *                     number.
     * @param name         the name of the student
     * @param passwordHash the hashed password of the student
     * @param yearOfStudy  the year of study of the student
     * @param major        the major of the student
     * @param email        the email of the student
     */
    public Student(String id, String name, String passwordHash, int yearOfStudy, String major, String email) {
        this.id = id;
        this.name = name;
        this.password = passwordHash;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        this.appliedInternships = new InternshipApplicationDbMgr();
        this.email = email;
        this.acceptedInternships = null;
    }

    /**
     * Get the student's id.
     *
     * @return the matriculation number
     */
    public String getId() {
        return id;
    }

    /**
     * Get the student's name.
     *
     * @return the full name
     */
    public String getName() {
        return name;
    }

    /**
     * Get the student's year of study.
     *
     * @return year of study as an int
     */
    public int getYearOfStudy() {
        return yearOfStudy;
    }

    /**
     * Get the student's major.
     *
     * @return the major
     */
    public String getMajor() {
        return major;
    }

    /**
     * Get the student's applied internships manager.
     *
     * @return the InternshipApplicationDbMgr containing applications
     */
    public InternshipApplicationDbMgr getAppliedInternships() {
        return appliedInternships;
    }
    /**
     * Get the internship the student has accepted, if any.
     *
     * @return the accepted Internship or null if none accepted
     */
    public Internship getAcceptedInternship() {
        return acceptedInternships;
    }

    /**
     * Get the stored password hash.
     *
     * @return the password hash string
     */
    public String getPassword() {
        return password;
    }

    /**
     * Get the student's email.
     *
     * @return the email address
     */
    public String getEmail() {
        return email;
    }

    /**
     * Set the student's year of study.
     *
     * @param yearOfStudy new year of study
     */
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }

    /**
     * Set the student's major.
     *
     * @param major the new major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * Set the student's password. The provided plain text password will be hashed.
     *
     * @param password the new plain-text password
     */
    public void setPassword(String password) {
        this.password = IPasswordMgr.hashPassword(password);
    }

    /**
     * Method to apply internship.
     * 
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
        if (today.isBefore(internship.getAppOpenDate()) || today.isAfter(internship.getAppCloseDate())) {
            return false;
        }

        if (getAppliedInternships().getAll().size() > 2) {
            return false;
        }

        InternshipApplication internshipApplication = new InternshipApplication(internship,
                InternshipApplicationStatus.PENDING);
        if (!appliedInternships.add(internshipApplication)) {
            return false;
        }
        return true;
    }

    /**
     * Method to accept internship
     * 
     * @param internship is the internship object to be accepted.
     * @return true if internship is successfully accepted, otherwise false.
     */
    public boolean acceptInternship(Internship internship) {
        if (internship == null)
            return false;
        // only can accept one internship
        // if (acceptedInternships != null) {
        // return false;
        // }

        InternshipApplication internshipApplication = appliedInternships.get(internship);
        if (internshipApplication == null)
            return false;

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
