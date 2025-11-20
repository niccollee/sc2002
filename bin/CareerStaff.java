/**
 * Represents a career services staff user in the internship portal.
 * A {@code CareerStaff} user can log in to the system and is identified by
 * an ID, name, role, department and email. The password is stored in hashed
 * form for security.
 */
public class CareerStaff implements IUser {
    private String id;
    private String password;
    private String name;
    private String role;
    private String department;
    private String email;

    /**
     * Creates a new {@code CareerStaff} user with the given attributes.
     *
     * @param id            unique identifier for the staff
     * @param name          full name of the staff
     * @param role          job title or role of the staff
     * @param department    department the staff belongs to
     * @param email         contact email of the staff
     * @param passwordHash  hashed password to be stored for authentication
     */
    
    public CareerStaff(String id, String name, String role, String department, String email, String passwordHash) {
        this.id = id;
        this.password = passwordHash;
        this.name = name;
        this.role = role;
        this.department = department;
        this.email = email;
    }

    /**
     * Returns the unique ID of this staff.
     *
     * @return the staff ID
     */
    public String getId() {
        return this.id;
    }

    /**
     * Returns the full name of this staff.
     *
     * @return the staff name
     */
    public String getName() {
        return this.name;
    }

    /**
     * Returns the role or job title of this staff.
     *
     * @return the staff role
     */
    public String getRole() {
        return this.role;
    }

    /**
     * Returns the department this staff belongs to.
     *
     * @return the staff department
     */
    public String getDept() {
        return this.department;
    }

    /**
     * Returns the contact email of this staff.
     *
     * @return the staff email address
     */
    public String getEmail() {
        return this.email;
    }

    /**
     * Updates the password of this staff user.
     * <p>
     * The provided plain-text password will be hashed using
     * {@link IPasswordMgr#hashPassword(String)} before being stored.
     * </p>
     *
     * @param password the new plain-text password
     */
    public void setPassword(String password) {
        this.password = IPasswordMgr.hashPassword(password);
    }

    /**
     * Returns the stored (hashed) password for this staff user.
     *
     * @return the hashed password string
     */
    public String getPassword() {
        return this.password;
    }



}
