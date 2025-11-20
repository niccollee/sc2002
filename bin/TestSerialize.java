import java.io.*;

public class TestSerialize {
    public static void main(String[] args) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("../data/test-appstate.dat"))) {
            AppState.getInstance().updateSelfState();
            oos.writeObject(AppState.getInstance());
            System.out.println("Serialized ok");
        } catch (java.io.NotSerializableException e) {
            System.err.println("Not serializable: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
