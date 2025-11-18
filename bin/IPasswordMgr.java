import java.security.*;
import java.util.Base64;

public interface IPasswordMgr<T> {

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

    public boolean validate(T t, String password);

    public boolean changePassword(T t, String oldPassword, String newPassword);
}
