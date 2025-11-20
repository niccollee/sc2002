import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

/**
 * Manages the collection of Internship objects.
 * Provides loading from CSV, retrieval, filtering, and sorting operations.
 * Implements the singleton pattern to ensure a single shared instance.
 */
public class InternshipDbMgr{
    private List<Internship> internshipList;
    private static InternshipDbMgr instance;

    /**
     * Creates the manager and loads internship data from the CSV file.
     * Private constructor to enforce the singleton pattern.
     */
    private InternshipDbMgr() {
        internshipList = new ArrayList<Internship>();
        importDb(CompanyRepDbMgr.getInstance());
    }
    
    // Get an instance of this DbMgr. If it exist, return it, otherwise create a new instance of it.
    /**
     * Return the singleton InternshipDbMgr, creating it if necessary.
     *
     * @return the shared InternshipDbMgr instance
     */
    public static synchronized InternshipDbMgr getInstance() {
        if (instance == null) {
            instance = new InternshipDbMgr();
        }
        return instance;
    }

    /**
     * Replace the singleton instance. Synchronized to avoid races during tests or state restoration.
     *
     * @param newInstance non-null InternshipDbMgr to set as the singleton
     * @throws IllegalArgumentException if newInstance is null
     */
    public static synchronized void setInstance(InternshipDbMgr newInstance) {
        if (newInstance == null) {
            throw new IllegalArgumentException("newInstance must not be null");
        }
        instance = newInstance;
    }

    /**
     * Loads internship data from the CSV file and populates the internship list.
     *
     * @param companyRepDbMgr the manager used to match internships to company representatives
     * @return true if loading succeeds, false if any error occurs
     */
	private boolean importDb(CompanyRepDbMgr companyRepDbMgr) {
		String filepath = "../data/internship_list.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			String[] values;

			br.readLine(); // ignore first read line of column headers
			while ((line = br.readLine()) != null) {

				//System.out.println("reading in: " + line);

				values = line.split(","); // seperate at delimiter ','
                LocalDate openDate;
                LocalDate closeDate;
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                try {
                    openDate = LocalDate.parse(values[5], formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return false;
                }
                try {
                    closeDate = LocalDate.parse(values[6], formatter);
                } catch (DateTimeParseException e) {
                    System.out.println("Invalid date format!");
                    return false;
                }
                CompanyRep companyRep = companyRepDbMgr.get(values[9]);
                Internship internship = new Internship(
                    values[1], 
                    values[2], 
                    InternshipLevel.valueOf(values[3]), 
                    values[4], 
                    openDate, 
                    closeDate, 
                    Status.valueOf(values[7]), 
                    values[8], 
                    companyRep, 
                    Integer.parseInt(values[10]), 
                    Boolean.parseBoolean(values[11]));
				internshipList.add(internship);
			}
		} catch (IOException e) {
			System.out.println("ERROR: Unable to read file" + e.getMessage());
			return false;
		}

		return true;
	}

    /**
     * Retrieves an internship by its numeric ID.
     *
     * @param id the internship ID
     * @return the matching Internship, or null if none is found
     */
    public Internship get(int id) {
        for (Internship i: internshipList) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    /**
     * Adds a new internship to the list if its ID does not already exist.
     *
     * @param internship the internship to add
     * @return true if the internship was added, false if a duplicate ID exists
     */
    public boolean add(Internship internship) {
       for (Internship i: internshipList) {
        if (i.getId() == internship.getId()) {
            return false;
            }
        }
        internshipList.add(internship);
        return true;
    }

    /**
     * Removes the specified internship from the list.
     *
     * @param internship the internship to remove
     */
    public void remove(Internship internship) {
        internshipList.remove(internship);
    }

    /**
     * Returns all internships managed by this manager.
     *
     * @return the list of internships
     */
    public List<Internship> getAll() {
        return internshipList;
    }

    /**
     * Replaces the current list of internships.
     *
     * @param internshipList the new list to use
     */
    public void setAll(List<Internship> internshipList){
        this.internshipList = internshipList;
    }

    /**
     * Filters internships using a string-based criterion.
     *
     * @param filterBy the attribute to filter by
     * @param args the comparison value
     * @return the list of internships that match the filter
     */
    public List<Internship> filter(InternshipAttributes filterBy, String args) {
        return InternshipFilter.filter(internshipList, filterBy, args);
    }

    /**
     * Filters internships using a date criterion.
     *
     * @param filterBy the attribute to filter by
     * @param date the date used for comparison
     * @param before true to filter for dates before the given date, false otherwise
     * @return the list of internships that match the filter
     */
    public List<Internship> filter(InternshipAttributes filterBy, LocalDate date, boolean before) {
        return InternshipFilter.filter(internshipList, filterBy, date, before);
    }

    /**
     * Sorts the internship list by the specified attribute.
     *
     * @param sortBy the attribute to sort by
     * @return the sorted internship list
     */
    public List<Internship> filter(InternshipAttributes sortBy) {
        return InternshipSorter.sort(internshipList, sortBy);
    }
}