import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Manages the list of {@link CompanyRep} objects and their persistence to and from CSV.
 * This class is implemented as a singleton and provides methods to add, remove,
 * retrieve, sort and filter company representatives.
 */
public class CompanyRepDbMgr{

	private List<CompanyRep> companyRepList;
	private static CompanyRepDbMgr instance;

	/**
	 * Creates a new {@code CompanyRepDbMgr} and initializes the internal list
	 * by importing data from the CSV file.
	 */
	private CompanyRepDbMgr() {
		this.companyRepList = new ArrayList<CompanyRep>();
		this.importDb();
	}

	/**
	 * Returns the singleton instance of {@code CompanyRepDbMgr}.
	 * If no instance exists yet, a new one is created.
	 *
	 * @return the shared {@code CompanyRepDbMgr} instance
	 */
	public static synchronized CompanyRepDbMgr getInstance() {
		if (instance == null) {
			instance = new CompanyRepDbMgr();
		}
		return instance;
	}

	/**
     * Replace the singleton instance. Synchronized to avoid races during tests or state restoration.
     *
     * @param newInstance non-null CompanyRepDbMgr to set as the singleton
     * @throws IllegalArgumentException if newInstance is null
     */
    public static synchronized void setInstance(CompanyRepDbMgr newInstance) {
        if (newInstance == null) {
            throw new IllegalArgumentException("newInstance must not be null");
        }
        instance = newInstance;
    }

	/**
	 * Returns the company representative with the given ID, or null if not found.
	 *
	 * @param id the ID of the company representative to retrieve
	 * @return the matching {@link CompanyRep}, or null if not found or if {@code id} is null
	 */
	// returns rep with given id or null is not found
	public CompanyRep get(String id) {
		if (id == null)
			return null;
		for (CompanyRep rep : companyRepList) {
			if (rep.getId() != null && rep.getId().equalsIgnoreCase(id)) {
				return rep;
			}
		}
		return null;
	}

	/**
	 * Adds a new company representative to the list.
	 * This method checks for null IDs and prevents duplicate IDs.
	 *
	 * @param id          company representative ID
	 * @param companyName company name
	 * @param department  department
	 * @param position    position or title
	 * @param repStatus   approval status of the representative
	 * @param passwordTxt plain-text password to be hashed and stored
	 * @return true if the representative was added, false if the ID is null or already exists
	 */
	// add new rep to the list of CompanyReps
	// before adding need to check rep status!
	public boolean add(String id, String companyName, String department, String position, CompanyRepStatus repStatus,
			String passwordTxt) {
		if (id == null)
			return false;
		if (get(id) != null)
			return false; // no duplicates
		CompanyRep rep = new CompanyRep(id, companyName, department, position, repStatus,
				IPasswordMgr.hashPassword(passwordTxt));
		companyRepList.add(rep);
		return true;
	}

	/**
	 * Removes the given company representative from the list by matching its ID.
	 *
	 * @param companyRep the representative to remove
	 * @return true if a representative with the same ID was found and removed, false otherwise
	 */
	// remove rep from list
	public boolean removeCompanyRep(CompanyRep companyRep) {
		return companyRepList.removeIf(r -> r.getId() != null && r.getId().equalsIgnoreCase(companyRep.getId()));
	}

	/**
	 * Returns a new list of company representatives sorted by the specified attribute.
	 *
	 * @param sortBy the attribute used for sorting
	 * @return a new sorted {@link List} of {@link CompanyRep}
	 */
	// returns a new list sorted by the specified attribute
	public List<CompanyRep> sort(CompanyRepAttributes sortBy) {
		return CompanyRepSorter.sort(companyRepList, sortBy);
	}

	/**
	 * Returns a new list of company representatives filtered by the specified attribute.
	 *
	 * @param filterBy the attribute to filter by
	 * @param args     the filter value, whose meaning depends on {@code filterBy}
	 * @return a new {@link List} of {@link CompanyRep} that match the filter condition
	 */
	// filter by attribute
	public List<CompanyRep> filter(CompanyRepAttributes filterBy, String args) {
		return CompanyRepFilter.filter(companyRepList, filterBy, args);
	}

	/**
	 * Returns the internal list of all company representatives managed by this class.
	 *
	 * @return the list of all {@link CompanyRep}
	 */
	public List<CompanyRep> getAll() {
		return this.companyRepList;
	}

	public void setAll(List<CompanyRep> companyRepList){
		this.companyRepList = companyRepList;
	}

	/**
	 * Imports company representative data from the CSV file into memory.
	 * Each line is parsed into a {@link CompanyRep} using fixed column indices.
	 *
	 * @return true if the import succeeded, false if an I/O error occurred
	 */
	// read in from Company Rep list csv and initalize list of Company Rep
	private boolean importDb() {
		String filepath = "../data/company_representative_list.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			String[] values;

			br.readLine(); // ignore first read line of column headers
			while ((line = br.readLine()) != null) {

				System.out.println("reading in: " + line);

				values = line.split(","); // seperate at delimiter ','
				companyRepList.add(new CompanyRep(values[5], values[2], values[3], values[4],
						CompanyRepStatus.valueOf(values[6]), values[7]));
			}
		} catch (IOException e) {
			System.out.println("ERROR: Unable to read file" + e.getMessage());
			return false;
		}

		return true;
	}

	/**
	 * Exports the current list of company representatives to the CSV file.
	 * The file is overwritten with a header and one line per representative.
	 */
	// export company rep list into csv
	private void exportDb() {
		String filepath = "../data/company_representative_list.csv";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
			// write CSV header
			bw.write("CompanyRepID,Name,CompanyName,Department,Position,Email,Status,PasswordHash");
			bw.newLine();

			for (CompanyRep cRep : this.companyRepList) {
				String[] fields = new String[] { cRep.getId(), "", cRep.getName(), cRep.getDepartment(),
						cRep.getPosition(), cRep.getId(), cRep.getRepStatus().toString(), cRep.getPassword() };

				String line = String.join(",", fields);

				System.out.println("writing out: " + line);
				bw.write(line);
				bw.newLine();
			}

		} catch (IOException e) {
			System.out.println("ERROR: Unable to write file: " + e.getMessage());
		}
	}
}
