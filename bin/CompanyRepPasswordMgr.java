public class CompanyRepPasswordMgr extends APasswordMgr<CompanyRep> {
    @Override
    public boolean validate(CompanyRep rep, String password) {
        if (hashPassword(password) == rep.getPassword()) {
            return true;
        }
        return false;
    }
    @Override
    public boolean changePassword(CompanyRep rep, String oldPassword, String newPassword) {
        if (this.validate(rep, newPassword)) {
            rep.setPassword(newPassword);
        } else {
            return false;
        }
        return true;
    }
}