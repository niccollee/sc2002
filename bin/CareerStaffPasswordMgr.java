public class CareerStaffPasswordMgr implements IPasswordMgr<CareerStaff> {
    @Override
    public boolean validate(CareerStaff careerStaff, String password) {
        if (IPasswordMgr.hashPassword(password).equals(careerStaff.getPassword()))
            return true;
        else
            return false;
    }

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
