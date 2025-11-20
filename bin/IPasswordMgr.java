import java.security.*;
import java.util.Base64;

/**
 * Generic password manager interface for user types.
 * Provides methods for validating and changing passwords,
 * as well as a static helper method to hash passwords securely.
 *
 * @param <T> the type of user or entity whose password is being managed
 */
public interface IPasswordMgr<T> {

    /**
     * Hashes a plain-text password using the SHA-256 algorithm and encodes
     * the result as a Base64 string.
     *
     * @param password the plain-text password to hash
     * @return the Base64-encoded SHA-256 hash of the password, or null if an error occurs
     */
    // Method to hash password using SHA-256
    public static String hashPassword(String password) {
        try {
            // Get the SHA-256 MessageDigest instance
            MessageDigest digest = MessageDigest.getInstance("SHA-256");

            // Hash the password and get the byte array
            byte[] hashBytes = digest.digest(password.getBytes());

            // Convert the hash byte array into a Base64 encoded string
            return Base64.getEncoder().encodeToString(hashBytes);
        } catch (Exception e) {
            System.out.println((e.getMessage()));
            return null;
        }
    }

    /**
     * Validates a plain-text password against the stored credentials
     * of the given user or entity.
     *
     * @param t        the user or entity whose password is being validated
     * @param password the plain-text password to check
     * @return true if the password is valid, false otherwise
     */
    public boolean validate(T t, String password);

    /**
     * Attempts to change the password of the given user or entity.
     * The old password should be validated before applying the change.
     *
     * @param t           the user or entity whose password is to be changed
     * @param oldPassword the current plain-text password
     * @param newPassword the new plain-text password to set
     * @return true if the password was successfully changed, false otherwise
     */
    public boolean changePassword(T t, String oldPassword, String newPassword);
}
