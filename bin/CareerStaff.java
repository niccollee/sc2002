public class CareerStaff {
    private String id;
    private String password;
    private String name;
    private String role;
    private String department;
    private String email;

    public CareerStaff(String id, String name, String role, String department, String email, String password) {
        this.id = id;
        this.password = password;
        this.name = name;
        this.role = role;
        this.department = department;
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public String getRole() {
        return this.role;
    }

    public String getDept() {
        return this.department;
    }

    public String getEmail() {
        return this.email;
    }

}
