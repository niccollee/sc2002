public class StudentPasswordMgr extends APasswordMgr<Student>{
    @Override
    public boolean validate(Student student, String password) {
        if (hashPassword(password) == student.getPassword()) {
            return true;
        }
        return false;
    }
    @Override
    public boolean changePassword(Student student, String oldPassword, String newPassword) {
        if (this.validate(student, newPassword)) {
            student.setPassword(newPassword);
        } else {
            return false;
        }
        return true;
    }
}
