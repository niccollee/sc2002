public abstract class APasswordMgr<T> {

    public String hashPassword(String password) {
        return password;
    }
    public abstract boolean validate(T t, String password);
    public abstract boolean changePassword(T t, String oldPassword, String newPassword);
}
