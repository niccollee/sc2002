import java.io.*;

public class AppStateMgr{

    private static final String STATE_FILE = "../data/appstate.dat";
    private static AppStateMgr instance;
    private AppState appState;

    private AppStateMgr(){
        appState = AppState.getInstance();
    }

    public static synchronized AppStateMgr getInstance(){
        if(instance == null){
            instance = new AppStateMgr();
        }
        return instance;
    }

    /**
     * Capture current in-memory lists from the DbMgr singletons into AppState.
     */
    private void updateSelfState(){
        appState.updateSelfState();
    }

    /**
     * Restore the DbMgr singletons from the lists stored inside AppState.
     */
    private void restoreOtherState(){
        appState.restoreOtherState();
    }

    public void writeFileState() {
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
                restoreOtherState();
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