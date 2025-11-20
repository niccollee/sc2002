import java.util.List;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class InternshipDbMgr{
    private List<Internship> internshipList;
    private static InternshipDbMgr instance;
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

    // read in from Company Rep list csv and initalize list of Company Rep
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
    // Getter method for internship based on id. If does not exist, return null.
    public Internship get(int id) {
        for (Internship i: internshipList) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }
    // Add internship based on parameters.
    // If title does not exist, create internship and add it to list and return true,
    // otherwise return false.
    public boolean add(Internship internship) {
       for (Internship i: internshipList) {
        if (i.getId() == internship.getId()) {
            return false;
            }
        }
        internshipList.add(internship);
        return true;
    }
    // Remove internship from the list
    public void remove(Internship internship) {
        internshipList.remove(internship);
    }
    // Return the whole list
    public List<Internship> getAll() {
        return internshipList;
    }

    public void setAll(List<Internship> internshipList){
        this.internshipList = internshipList;
    }
    public List<Internship> filter(InternshipAttributes filterBy, String args) {
        return InternshipFilter.filter(internshipList, filterBy, args);
    }
    public List<Internship> filter(InternshipAttributes filterBy, LocalDate date, boolean before) {
        return InternshipFilter.filter(internshipList, filterBy, date, before);
    }
    public List<Internship> filter(InternshipAttributes sortBy) {
        return InternshipSorter.sort(internshipList, sortBy);
    }
}