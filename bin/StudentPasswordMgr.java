public class StudentPasswordMgr implements IPasswordMgr<Student> {
    @Override
    public boolean validate(Student student, String password) {
        try {
            if (IPasswordMgr.hashPassword(password).equals(student.getPassword()))
                return true;
            else
                return false;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    @Override
    public boolean changePassword(Student student, String oldPassword, String newPassword) {
        if (this.validate(student, oldPassword)) {
            student.setPassword(newPassword);
        } else {
            return false;
        }
        return true;
    }
}
