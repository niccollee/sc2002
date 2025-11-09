import java.util.ArrayList;
import java.util.List;

public class Student implements IUser {
    private String id;
    private String name;
    private String password;
    private int yearOfStudy;
    private String major;
    private List<Internship> appliedInternships;
    private Internship acceptedInternships;

    // Initially appliedInternships will be an empty ArrayList and appliedInternship is null
    public Student(String id, String name, String password, int yearOfStudy, String major) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.yearOfStudy = yearOfStudy;
        this.major = major;
        ArrayList<Internship> appliedInternships = new ArrayList<Internship>();
        this.appliedInternships = appliedInternships;
        this.acceptedInternships = null;
    }
    // Getter method for id
    public String getId() {
        return id;
    }
    // Getter method for name
    public String getName() {
        return name;
    }  
    // Getter method for yearOfStudy
    public int getYearOfStudy() {
        return yearOfStudy;
    }
    // Setter method for yearOfStudy
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    // Getter method for major
    public String getMajor() {
        return major;
    }   
    // Setter method for major
    public void setMajor(String major) {
        this.major = major;
    }
    // Getter method for appliedInternship
    public List<Internship> getAppliedInternship() {
        return appliedInternships;
    }
    // Getter method for acceptdInternship
    public Internship getAcceptedInternship() {
        return acceptedInternships;
    }   
    // Method to apply internship. Return true if operation is successful, otherwise false.
    // False when internship is already in the list.
    public boolean applyInternship(Internship internship) {
        if (appliedInternships.contains(internship)) {
            return false;
        }
        appliedInternships.add(internship);
        return true;
    }
    // Method to accept internship. Return true if operation is successful, otherwise false.
    // False when internship is not in appliedInternship.
    public boolean acceptInternship(Internship internship) {
        if (appliedInternships.contains(internship)) {
            appliedInternships.remove(internship);
        } else {
            return false;
        }
        acceptedInternships = internship;
        return true;
    }
    // Method to reject internship. Return true if operation successful, otherwise false.
    // False when internship is not in appliedInternship or if internship is not acceptedInternship
    public boolean rejectInternship(Internship internship) {
        if (appliedInternships.contains(internship)) {
            appliedInternships.remove(internship);
        } else if (acceptedInternships == internship) {
            acceptedInternships = null;
        } else {
            return false;
        }
        return true;
    }
    // Getter method for password
    public String getPassword() {
        return password;
    }
    // Setter method for password
    public void setPassword(String password) {
        this.password = password;
    }
}
