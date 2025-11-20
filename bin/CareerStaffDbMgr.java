import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Manages the in-memory list and CSV persistence of {@link CareerStaff} records.
 * This class is implemented as a singleton and is responsible for importing
 * career staff data from a CSV file at startup and exporting the current state
 * back to the CSV file when requested. It also provides helper methods for
 * searching, adding, removing, sorting, and filtering career staff.
 */
public class CareerStaffDbMgr {

    private List<CareerStaff> careerStaffList;
    private static CareerStaffDbMgr instance;

    /**
     * Creates a new {@code CareerStaffDbMgr} and initializes the internal list
     * of career staff by importing from the CSV file.
     */
    private CareerStaffDbMgr() {
        this.careerStaffList = new ArrayList<>();
        this.importDb();
    }

    /**
     * Returns the singleton instance of {@code CareerStaffDbMgr}.
     * If no instance exists yet, a new one is created and initialized.
     *
     * @return the single {@code CareerStaffDbMgr} instance
     */
    public static synchronized CareerStaffDbMgr getInstance() {
        if (instance == null) {
            instance = new CareerStaffDbMgr();
        }
        return instance;
    }

    /**
     * Replace the singleton instance. This is synchronized to avoid races
     * during tests or state restoration. newInstance must not be null.
     *
     * @param newInstance the CareerStaffDbMgr to set as the singleton
     * @throws IllegalArgumentException if newInstance is null
     */
    public static synchronized void setInstance(CareerStaffDbMgr newInstance) {
        if (newInstance == null) {
            throw new IllegalArgumentException("newInstance must not be null");
        }
        instance = newInstance;
    }

    // read in from career staff list csv and initalize list of career staff
    /**
     * Imports the career staff data from the CSV file into memory.
     * Each line after the header is parsed into a {@link CareerStaff} object and
     * added to the internal list.
     *
     * @return {@code true} if the file was read successfully; {@code false} if an I/O error occurred
     */
    private boolean importDb() {
        String filepath = "../data/career_staff_list.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values;

            br.readLine(); // ignore first read line of column headers
            while ((line = br.readLine()) != null) {

                System.out.println("reading in: " + line);

                values = line.split(","); // seperate at delimiter ','
                careerStaffList.add(new CareerStaff(values[0], values[1], values[2], values[3], values[4], values[5]));

            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file: " + e.getMessage());
            return false;
        }

        return true;
    }

    // export list of career staff into csv
    /**
     * Exports the current list of career staff to the CSV file.
     * The existing file is overwritten with a new header row followed by one
     * line per {@link CareerStaff}, with values separated by commas.
     */
    private void exportDb() {
        String filepath = "../data/career_staff_list.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            // write CSV header
            bw.write("StaffID,Name,Role,Department,Email,PasswordHash");
            bw.newLine();

            for (CareerStaff cStaff : this.careerStaffList) {
                String[] fields = new String[] { cStaff.getId(), cStaff.getName(), cStaff.getRole(), cStaff.getDept(),
                        cStaff.getEmail(), cStaff.getPassword() };

                String line = String.join(",", fields);

                System.out.println("writing out: " + line);
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("ERROR: Unable to write file: " + e.getMessage());
        }
    }

    /**
     * Returns a sorted view of the career staff list based on the given attribute.
     *
     * @param sortBy the attribute used to sort the staff list
     * @return a new {@link List} of {@link CareerStaff} sorted by the specified attribute
     */
    public List<CareerStaff> sort(CareerStaffAttributes sortBy) {
        return CareerStaffSorter.sort(careerStaffList, sortBy);
    }

    /**
     * Returns a filtered view of the career staff list based on the given attribute
     * and filter argument.
     *
     * @param filterBy the attribute used for filtering
     * @param args     the argument or value used as the filter criterion
     * @return a new {@link List} of {@link CareerStaff} that match the filter
     */
    public List<CareerStaff> filter(CareerStaffAttributes filterBy, String args) {
        return CareerStaffFilter.filter(careerStaffList, filterBy, args);
    }

    /**
     * Removes the specified {@link CareerStaff} from the internal list.
     *
     * @param careerStaff the staff record to be removed
     * @return {@code true} if the staff was present and removed; {@code false} otherwise
     */
    public boolean remove(CareerStaff careerStaff) {
        return careerStaffList.remove(careerStaff);
    }

    /**
     * Retrieves a {@link CareerStaff} by its ID.
     *
     * @param id the ID of the staff to look up
     * @return the matching {@link CareerStaff}, or {@code null} if not found
     */
    public CareerStaff getCareerStaff(String id) {
        for (CareerStaff cStaff : this.careerStaffList) {
            if (cStaff.getId().equals(id)) {
                return cStaff;
            }
        }

        return null;
    }

    /**
     * Adds a new {@link CareerStaff} record to the internal list if the ID passes
     * the check performed by {@link #getCareerStaff(String)}.
     *
     * @param id          the staff ID
     * @param name        the staff name
     * @param role        the staff role
     * @param department  the staff department
     * @param email       the staff email
     * @param passwordTxt the plain-text password to be hashed and stored
     * @return {@code true} if the staff was added; {@code false} otherwise
     */
    public boolean add(String id, String name, String role, String department, String email, String passwordTxt) {
        if (this.getCareerStaff(id) == null) {
            return false;
        } else {
            careerStaffList
                    .add(new CareerStaff(id, name, role, department, email, IPasswordMgr.hashPassword(passwordTxt)));
            return true;
        }
    }

    /**
     * Returns the full list of {@link CareerStaff} managed by this database manager.
     *
     * @return the internal list of career staff
     */
    public List<CareerStaff> getAll() {
        return this.careerStaffList;
    }

}
