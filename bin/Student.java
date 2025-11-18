public class Student implements IUser {
    private String id;
    private String name;
    private String password;
    private int yearOfStudy;
    private String major;
    private InternshipApplicationDbMgr appliedInternships;
    private String email;
    private Internship acceptedInternships;

    // Initially appliedInternships will be an empty ArrayList and appliedInternship is null
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
    
    // Method to apply internship. Return true if operation is successful, otherwise false.
    // False when internship is already in the list.
    public boolean applyInternship(Internship internship) {
        if (appliedInternships.contains(internship)) {
            return false;
        }
        InternshipApplication internshipApplication = new InternshipApplication(internship, InternshipApplicationStatus.PENDING);
        appliedInternships.add(internshipApplication);
        return true;
    }
    // Method to accept internship. Return true if operation is successful, otherwise false.
    // False when internship is not in appliedInternship.
    public boolean acceptInternship(Internship internship) {
        InternshipApplication internshipApplication = appliedInternships.get(internship);
        if (appliedInternships.contains(internship)) {
            appliedInternships.remove(internshipApplication);
        } else {
            return false;
        }
        acceptedInternships = internship;
        return true;
    }
    // Method to reject internship, set appliedInternships to null.
    public void withdrawAcceptedInternship() {
        acceptedInternships = null;
    }    

	//Check password
	public boolean validatePassword(String password){
        StudentPasswordMgr passwordMgr = new StudentPasswordMgr();
        return passwordMgr.validate(this, password);
    }
}