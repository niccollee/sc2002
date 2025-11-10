public interface IUser {
    public abstract String getId();
    public abstract String getName();
    public abstract String getPassword();
    public abstract void setPassword(String password);
}