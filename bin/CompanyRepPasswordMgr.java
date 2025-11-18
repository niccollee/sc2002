public class CompanyRepPasswordMgr implements IPasswordMgr<CompanyRep> {
    @Override
    public boolean validate(CompanyRep companyRep, String password) {

        if (IPasswordMgr.hashPassword(password).equals(companyRep.getPassword()))
            return true;
        else
            return false;
    }

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