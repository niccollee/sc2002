import java.util.List;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.Date;

public class InternshipDbMgr {
    private List<Internship> internshipList;
    private static InternshipDbMgr instance;
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
    // Getter method for internship based on title. If does not exist, return null.
    public Internship get(String title) {
        for (Internship i: internshipList) {
            if (i.getTitle() == title) {
                return i;
            }
        }
        return null;
    }
    // Add internship based on parameters.
    // If title does not exist, create internship and add it to list and return true,
    // otherwise return false.
    public boolean add(
        String title,
        String description,
        InternshipLevel level,
        String preferredMajor,
        Date appOpenDate,
        Date appCloseDate,
        Status status,
        String companyName,
        CompanyRep companyRep,
        int numSlot,
        boolean visibility
    ) {
        if (get(title) == null) {
            Internship internship = new Internship(
                title,
                description, 
                level, 
                preferredMajor, 
                appOpenDate, 
                appCloseDate, 
                status, 
                companyName, 
                companyRep, 
                numSlot, 
                visibility);
            internshipList.add(internship);
            return true;
        }
    return false;
    }
    // Remove internship from the list
    public void remove(Internship internship) {
        internshipList.remove(internship);
    }
    // Return the whole list
    public List<Internship> showAll() {
        return internshipList;
    }
    // Filter based on attributes, based on args
    public List<Internship> filter(InternshipAttributes filterBy, String args) {
        switch(filterBy) {
            // Filter by title in alphabetical order
            case InternshipAttributes.TITLE:
                return internshipList
                    .stream()
                    .filter(x -> x.getTitle().contains(args))
                    .collect(Collectors.toList());
            // Filter by preferredMajor in alphabetical order
            case InternshipAttributes.PREFERREDMAJOR:
                return internshipList   
                    .stream()
                    .filter(x -> x.getPreferredMajor().contains(args))
                    .collect(Collectors.toList());
            // Filter by companyName in alphabetical order
            case InternshipAttributes.COMPANYNAME:
                return internshipList
                    .stream()
                    .filter(x -> x.getCompanyName().contains(args))
                    .collect(Collectors.toList());
            default:
                return null;
        }
    }
    // Overloaded method for when filterBy is level, returning list of internship
    // filtered by level
    public List<Internship> filter(InternshipAttributes filterBy, InternshipLevel level) {
        if (filterBy != InternshipAttributes.LEVEL) {
            return null;
        }
        return internshipList
            .stream()
            .filter(x -> x.getLevel() == level)
            .collect(Collectors.toList());
    }
    // Overloaded filter method for date. 
    // If before is true, include all dates before and up to date.
    // If before is false, include all dates after and up to date.
    public List<Internship> filter(InternshipAttributes filterBy, Date date, boolean before) {
        switch (filterBy) {
            case InternshipAttributes.APPCLOSEDATE:
                if (before) {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppCloseDate().before(date) ||
                            x.getAppCloseDate() == date))
                        .collect(Collectors.toList());
                } else {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppCloseDate().after(date) ||
                            x.getAppCloseDate() == date))
                        .collect(Collectors.toList());
                }
            case InternshipAttributes.APPOPENDATE:
                if (before) {
                    return internshipList  
                        .stream()
                        .filter(x -> (
                            x.getAppOpenDate().before(date) ||
                            x.getAppOpenDate() == date))
                        .collect(Collectors.toList());
                } else {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppOpenDate().after(date) ||
                            x.getAppOpenDate() == date))
                        .collect(Collectors.toList());
                }
            default:
                return null;
        }
    }
    // Overloaded method to filter by numSlot.
    public List<Internship> filter(InternshipAttributes filterBy, int numSlot) {
        if (filterBy != InternshipAttributes.NUMSLOT) {
            return null;
        }
        return internshipList  
            .stream()
            .filter(x -> x.getNumSlots() == numSlot)
            .collect(Collectors.toList());
    }
    // Overloaded method for visibility
    public List<Internship> filter(InternshipAttributes filterBy, boolean visibility) {
        if (filterBy != InternshipAttributes.VISIBILITY) {
            return null;
        }
        return internshipList
            .stream()   
            .filter(x -> x.getVisibility() == visibility)
            .collect(Collectors.toList());
    }

    // Method to sort
    public List<Internship> sort(InternshipAttributes sortBy) {
        switch(sortBy) {
            // Sort by title in alphabetical order
            case InternshipAttributes.TITLE:
                return internshipList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getTitle()))
                    .collect(Collectors.toList());
            // Sort by level in ??? order
            case InternshipAttributes.LEVEL:
                return internshipList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getLevel()))
                    .collect(Collectors.toList());
            case InternshipAttributes.PREFERREDMAJOR:
                return internshipList 
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getPreferredMajor()))
                    .collect(Collectors.toList());
            case InternshipAttributes.APPCLOSEDATE:
                return internshipList   
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getAppCloseDate()))
                    .collect(Collectors.toList());
            case InternshipAttributes.APPOPENDATE:
                return internshipList       
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getAppOpenDate()))
                    .collect(Collectors.toList());
            case InternshipAttributes.STATUS:
                return internshipList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getStatus()))
                    .collect(Collectors.toList());
            case InternshipAttributes.COMPANYNAME:
                return internshipList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getCompanyName()))
                    .collect(Collectors.toList());
            case InternshipAttributes.NUMSLOT:
                return internshipList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getNumSlots()))
                    .collect(Collectors.toList());
            case InternshipAttributes.VISIBILITY:
                return internshipList   
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getVisibility()))
                    .collect(Collectors.toList());
            default:
                return null;            
        }
    }
}