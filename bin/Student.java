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
    // Getter method
    public String getId() {return id;}
    public String getName() {return name;}  
    public int getYearOfStudy() {return yearOfStudy;}
    public String getMajor() {return major;}
    public List<Internship> getAppliedInternship() {return appliedInternships;}
    public Internship getAcceptedInternship() {return acceptedInternships;}   
    public String getPassword() {return password;}
    
    // Setters
    public void setYearOfStudy(int yearOfStudy) {
        this.yearOfStudy = yearOfStudy;
    }
    public void setMajor(String major) {
        this.major = major;
    }
    public void setPassword(String password) {
        this.password = password;
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
    // Method to reject internship, set appliedInternships to null.
    public void withdrawAcceptedInternship() {
        acceptedInternships = null;
    }    
}
