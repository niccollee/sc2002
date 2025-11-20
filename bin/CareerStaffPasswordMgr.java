/**
 * Password manager implementation for {@link CareerStaff} users.
 * This class provides methods to validate a staff member's password
 * and to change it in a controlled way.
 */
public class CareerStaffPasswordMgr implements IPasswordMgr<CareerStaff> {

    /**
     * Validates the given plain-text password against the stored hashed password
     * of the specified {@link CareerStaff}.
     *
     * @param careerStaff the staff whose password is being validated
     * @param password    the plain-text password to check
     * @return {@code true} if the hashed input matches the stored hash;
     *         {@code false} otherwise
     */
    @Override
    public boolean validate(CareerStaff careerStaff, String password) {
        if (IPasswordMgr.hashPassword(password).equals(careerStaff.getPassword()))
            return true;
        else
            return false;
    }

    /**
     * Attempts to change the password of the specified {@link CareerStaff}.
     * The old password must first be validated before the new password is set.
     *
     * @param careerStaff the staff whose password is to be changed
     * @param oldPassword the current plain-text password for validation
     * @param newPassword the new plain-text password to be stored (hashed internally)
     * @return {@code true} if the password was successfully changed;
     *         {@code false} if validation of the old password failed
     */
    @Override
    public boolean changePassword(CareerStaff careerStaff, String oldPassword, String newPassword) {
        if (this.validate(careerStaff, oldPassword)) {
            careerStaff.setPassword(newPassword);
        } else {
            return false;
        }
        return true;
    }
}
