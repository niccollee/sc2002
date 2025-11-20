import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

/**
 * Manages the collection of InternshipWithdrawalApplicant objects.
 * Implements the singleton pattern and provides methods to add, remove,
 * filter, sort, and retrieve withdrawal applicants.
 */
public class InternshipWithdrawalDbMgr{
    private List<InternshipWithdrawalApplicant> internshipWithdrawalList;
    private static InternshipWithdrawalDbMgr instance;

    /**
     * Private constructor to initialize the withdrawal applicant list.
     */
    private InternshipWithdrawalDbMgr() {
        internshipWithdrawalList = new ArrayList<InternshipWithdrawalApplicant>();
    }
    
    /**
     * Returns the singleton instance of InternshipWithdrawalDbMgr, creating it if necessary.
     *
     * @return the shared InternshipWithdrawalDbMgr instance
     */
    public static synchronized InternshipWithdrawalDbMgr getInstance() {
        if (instance == null) {
            instance = new InternshipWithdrawalDbMgr();
        }
        return instance;
    }


    /**
     * Replaces the singleton instance. Useful for testing or resetting state.
     *
     * @param newInstance the new instance to set
     * @throws IllegalArgumentException if newInstance is null
     */
    public static synchronized void setInstance(InternshipWithdrawalDbMgr newInstance) {
        if (newInstance == null) {
            throw new IllegalArgumentException("newInstance must not be null");
        }
        instance = newInstance;
    }


    /**
     * Retrieves a withdrawal applicant by their unique ID.
     *
     * @param id the ID of the applicant
     * @return the matching applicant, or null if not found
     */
    public InternshipWithdrawalApplicant get(int id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    /**
     * Retrieves a withdrawal applicant by the student's ID.
     *
     * @param id the student ID
     * @return the matching applicant, or null if not found
     */
    public InternshipWithdrawalApplicant getFromStudentId(String id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getStudent().getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Adds a new withdrawal applicant if the student has not already applied.
     *
     * @param student the student applying for withdrawal
     * @param internship the internship associated with the withdrawal
     * @return true if the applicant was added, false if the student already exists
     */
    public boolean add(Student student, Internship internship) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getStudent() == student) {
                return false;
            }
        }
        InternshipWithdrawalApplicant internshipWithdrawalApplicants = new InternshipWithdrawalApplicant(student, internship);
        internshipWithdrawalList.add(internshipWithdrawalApplicants);
        return true;
    }

    /**
     * Removes a withdrawal applicant by their ID.
     *
     * @param id the ID of the applicant to remove
     * @return true if the applicant existed and was removed, false otherwise
     */
    public boolean remove(int id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getId() == id) {
                internshipWithdrawalList.remove(get(id));
                return true;
            }
        }
        return false;
    }

    /**
     * Returns a sorted list of withdrawal applicants based on the given attribute.
     *
     * @param sortBy the attribute to sort by
     * @return the sorted list of applicants
     */
    public List<InternshipWithdrawalApplicant> sort(InternshipWithdrawalApplicantsAttributes sortBy) {
        switch (sortBy) {
            case InternshipWithdrawalApplicantsAttributes.ID:
                return internshipWithdrawalList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getId()))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.STUDENT:
                return internshipWithdrawalList
                    .stream()   
                    .sorted(Comparator.comparing(x -> x.getStudent().getName()))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.INTERNSHIP:
                return internshipWithdrawalList
                    .stream()
                    .sorted(Comparator.comparing(c -> c.getInternship().getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Filters withdrawal applicants based on the given attribute and search string.
     *
     * @param filterBy the attribute to filter by
     * @param args the string to match against the attribute
     * @return the list of applicants that match the filter
     */
    public List<InternshipWithdrawalApplicant> filter(InternshipWithdrawalApplicantsAttributes filterBy, String args) {
        switch (filterBy) {
            case InternshipWithdrawalApplicantsAttributes.ID:
                return internshipWithdrawalList
                    .stream()
                    .filter(x -> ((Integer)x.getId()).toString().contains(args))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.STUDENT:
                return internshipWithdrawalList
                    .stream()   
                    .filter(x -> x.getStudent().getName().contains(args))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.INTERNSHIP:
                return internshipWithdrawalList
                    .stream()
                    .filter(c -> c.getInternship().getTitle().contains(args))
                    .collect(Collectors.toList());
        }
        return null;
    }

    /**
     * Returns the full list of withdrawal applicants.
     *
     * @return the list of all applicants
     */
    public List<InternshipWithdrawalApplicant> getAll(){
        return this.internshipWithdrawalList;
    }
    
    /**
     * Replaces the current list of withdrawal applicants.
     *
     * @param internshipWithdrawalList the new list of applicants
     */
    public void setAll(List<InternshipWithdrawalApplicant> internshipWithdrawalList){
        this.internshipWithdrawalList = internshipWithdrawalList;
    }
}
