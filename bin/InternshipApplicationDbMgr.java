import java.util.List;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Manages a collection of InternshipApplication objects.
 * Provides methods to add, retrieve, and remove applications.
 */
public class InternshipApplicationDbMgr implements Serializable {
    private List<InternshipApplication> internshipApplicationList;
    
    /**
     * Creates a new manager with an empty list of internship applications.
     */
    public InternshipApplicationDbMgr() {
        internshipApplicationList = new ArrayList<InternshipApplication>();
    }


    /**
     * Retrieves an internship application based on the given internship.
     *
     * @param internship the internship whose application is requested
     * @return the matching InternshipApplication, or null if not found
     */
    public InternshipApplication get(Internship internship) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internship.getId()) {
                return i;
            }
        }
        return null;
    }

    /**
     * Returns all internship applications.
     *
     * @return the list of InternshipApplication objects
     */
    public List<InternshipApplication> getAll() {
        return internshipApplicationList;
    }

    /**
     * Retrieves an internship application by its index in the list.
     *
     * @param idx the index of the application
     * @return the InternshipApplication at the specified index
     */
    public InternshipApplication get(int idx) {
        return internshipApplicationList.get(idx);
    }

    /**
     * Adds a new internship application if it does not already exist
     * and the maximum allowed number of applications has not been reached.
     *
     * @param internshipApplication the application to add
     * @return true if the application was successfully added, false otherwise
     */
    public boolean add(InternshipApplication internshipApplication) {
        if (internshipApplicationList.size() > 3) {
            return false;
        }
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internshipApplication.getInternship().getId()) {
                return false;
            }
        }

        internshipApplicationList.add(internshipApplication);
        return true;   
    }


    /**
     * Removes a matching internship application from the list.
     *
     * @param internshipApplication the application to remove
     * @return true if the application was found and removed, false otherwise
     */
    public boolean remove(InternshipApplication internshipApplication) {
        Iterator<InternshipApplication> iterator = internshipApplicationList.iterator();
        while (iterator.hasNext()) {
            InternshipApplication i = iterator.next();
            if (i.getInternship().getId() == internshipApplication.getInternship().getId()) {
                iterator.remove();
                return true;
            }
        }
        return false;

    }


    /**
     * Checks whether the list contains an application matching both
     * the internship and application status.
     *
     * @param internshipApplication the application to check
     * @return true if a matching application exists, false otherwise
     */
    public boolean contains(InternshipApplication internshipApplication) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internshipApplication.getInternship().getId() &&
                i.getInternshipApplicationStatus() == internshipApplication.getInternshipApplicationStatus()) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether the list contains an application for the given internship.
     *
     * @param internship the internship to check
     * @return true if an application exists for this internship, false otherwise
     */
    public boolean contains(Internship internship) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internship.getId()) {
                return true;
            }
        }
        return false;
    }
}
