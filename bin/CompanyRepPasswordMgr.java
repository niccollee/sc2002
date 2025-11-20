/**
 * Password manager implementation for {@link CompanyRep} users.
 * Provides methods to validate a representative's password and to change it
 * after verifying the old password.
 */
public class CompanyRepPasswordMgr implements IPasswordMgr<CompanyRep> {

    /**
     * Validates the given plain-text password against the stored hashed password
     * of the specified {@link CompanyRep}.
     *
     * @param companyRep the company representative whose password is being checked
     * @param password   the plain-text password to validate
     * @return true if the hashed input matches the stored hash, false otherwise
     */
    @Override
    public boolean validate(CompanyRep companyRep, String password) {

        if (IPasswordMgr.hashPassword(password).equals(companyRep.getPassword()))
            return true;
        else
            return false;
    }

    /**
     * Attempts to change the password of the given {@link CompanyRep}.
     * The old password is first validated before the new password is set.
     *
     * @param rep         the company representative whose password is to be changed
     * @param oldPassword the current plain-text password for validation
     * @param newPassword the new plain-text password to be stored (hashed internally)
     * @return true if the password was successfully changed, false if validation failed
     */
    @Override
    public boolean changePassword(CompanyRep rep, String oldPassword, String newPassword) {
        if (this.validate(rep, oldPassword)) {
            rep.setPassword(newPassword);
        } else {
            return false;
        }
        return true;
    }
}
