import java.io.*;

public class StateMgr {

    private static final String STATE_FILE = "../data/appstate.dat";
    

    public static void writeState() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(STATE_FILE))) {
            oos.writeObject(AppState.getInstance());
            System.out.println("State saved to " + STATE_FILE);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void readState() {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(STATE_FILE))) {
            Object obj = ois.readObject();
            if (obj instanceof AppState) {
                System.out.println("State loaded from " + STATE_FILE);
                
            } else {
                System.err.println("Invalid state file.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("No saved state found (" + STATE_FILE + ").");
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}