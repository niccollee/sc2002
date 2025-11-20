import java.io.Serializable;

/**
 * Common user interface for entities that have identity and credentials.
 *
 * Implementations represent an application user and must provide accessors
 * for id, name and password, and a mutator for updating the password.
 */
public interface IUser extends Serializable{
    /**
     * Return the unique identifier for the user.
     *
     * @return user id
     */
    public abstract String getId();

    /**
     * Return the user's display name.
     *
     * @return user name
     */
    public abstract String getName();

    /**
     * Return the stored password value (typically a hash).
     *
     * @return password or password hash
     */
    public abstract String getPassword();

    /**
     * Update the user's password. Implementations may hash the provided value.
     *
     * @param password new plain-text password
     */
    public abstract void setPassword(String password);
}