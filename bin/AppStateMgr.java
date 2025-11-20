import java.io.*;

/**
 * Singleton manager for saving and loading the application state to and from a file.
 * This class provides methods to serialize the current {@link AppState} to disk
 * and restore it later. The state is stored in a file defined by {@link #STATE_FILE}.
 * The class uses a singleton pattern to ensure only one instance exists.
 */
public class AppStateMgr{

    /** File path where the serialized application state is stored. */
    private static final String STATE_FILE = "../data/appstate.dat";
    
    /** Singleton instance of {@code AppStateMgr}. */
    private static AppStateMgr instance;

    /** Reference to the {@link AppState} managed by this manager. */
    private AppState appState;

    /**
     * Private constructor for singleton pattern.
     * Initializes {@link #appState} by getting the instance of {@link AppState}.
     */
    private AppStateMgr(){
        appState = AppState.getInstance();
    }

    /**
     * Returns the singleton instance of {@code AppStateMgr}.
     * Creates a new instance if none exists.
     *
     * @return the singleton {@code AppStateMgr} instance
     */
    public static synchronized AppStateMgr getInstance(){
        if(instance == null){
            instance = new AppStateMgr();
        }
        return instance;
    }

    /**
     * Serializes the current application state to disk.
     * This method updates the {@link AppState} with the current in-memory lists
     * from the DbMgr singletons before writing to the file. 
     * If the state or any object is not serializable, an error is printed.
     */
    public void writeFileState() {
        File f = new File(STATE_FILE);
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(f))) {
            appState.updateSelfState();

            oos.writeObject(AppState.getInstance());
            System.out.println("Serialized ok");
        } catch (java.io.NotSerializableException e) {
            System.err.println("Not serializable: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Reads the application state from disk and restores it into memory.
     * If the file does not exist, a message is printed and no changes are made.
     * After successful deserialization, the stored lists in {@link AppState} are
     * restored back into the singleton DbMgr instances.
     */
    public void readFileState() {
        File f = new File(STATE_FILE);
        if (!f.exists()) {
            System.out.println("No saved state found (" + STATE_FILE + ").");
            return;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(f))) {
            Object obj = ois.readObject();
            if (obj instanceof AppState) {
                AppState.setInstance((AppState) obj);
                appState = AppState.getInstance();
                appState.restoreOtherState();

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