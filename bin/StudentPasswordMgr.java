public class StudentPasswordMgr implements IPasswordMgr<Student>{
    @Override
    public boolean validate(Student student, String password) {
        if (hashPassword(password).equals(student.getPassword())) {
            return true;
        }
        return false;
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
