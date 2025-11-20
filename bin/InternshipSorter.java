import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility for sorting lists of {@link Internship} by a given {@link InternshipAttributes} key.
 *
 * Returns a new List containing the sorted internships and does not modify the input list.
 */
public class InternshipSorter {
    /**
     * Sort the provided internshipList by the attribute specified in sortBy.
     *
     * @param internshipList the list of internships to sort (must not be null)
     * @param sortBy the attribute to sort by
     * @return a new List of Internship sorted according to sortBy; if sortBy is not recognised the original list is returned
     */
    public static List<Internship> sort(List<Internship> internshipList, InternshipAttributes sortBy) {
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
                return internshipList;            
        }
    }
}
