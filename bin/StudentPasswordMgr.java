/**
 * Manager responsible for validating and changing passwords for Student objects.
 */
public class StudentPasswordMgr implements IPasswordMgr<Student> {
    /**
     * Validate a candidate password against the stored password hash of the student.
     *
     * @param student the Student whose password is being validated
     * @param password the plain-text password to validate
     * @return true if the hashed candidate matches the student's stored password hash, otherwise false
     */
    @Override
    public boolean validate(Student student, String password) {

        if (IPasswordMgr.hashPassword(password).equals(student.getPassword()))
            return true;
        else
            return false;

    }
    
    /**
     * Change the student's password after verifying the old password.
     *
     * @param student the Student whose password will be changed
     * @param oldPassword the current plain-text password for verification
     * @param newPassword the new plain-text password to set
     * @return true if the password was changed successfully, otherwise false
     */
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
