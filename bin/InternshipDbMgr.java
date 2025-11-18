import java.util.List;
import java.util.ArrayList;
import java.time.LocalDate;

public class InternshipDbMgr {
    private List<Internship> internshipList;
    private static InternshipDbMgr instance;
    private static int numInternship = 0;
    private InternshipDbMgr() {
        internshipList = new ArrayList<Internship>();
    }
    // Get an instance of this DbMgr. If it exist, return it, otherwise create a new instance of it.
    public static InternshipDbMgr getInstance() {
        if (instance == null) {
            instance = new InternshipDbMgr();
        }
        return instance;
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
    public List<Internship> showAll() {
        return internshipList;
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