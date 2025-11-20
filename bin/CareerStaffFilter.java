import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides filtering operations for {@link CareerStaff} lists.
 * Supports filtering by different {@link CareerStaffAttributes},
 * such as ID and name, using a case-insensitive substring match.
 */
public class CareerStaffFilter {

    /**
     * Filters the given list of career staff based on the specified attribute
     * and filter argument.
     * The match is case-insensitive and checks whether the attribute value
     * contains the given argument as a substring.
     *
     * @param careerStaffList the list of {@link CareerStaff} to filter
     * @param filterBy        the {@link CareerStaffAttributes} to filter on
     * @param args            the text used as the filter criterion
     * @return a new list containing only the staff that match the filter condition
     */
    public static List<CareerStaff> filter(List<CareerStaff> careerStaffList, CareerStaffAttributes filterBy,
            String args) {
        return switch (filterBy) {
            // Filter by ID based on alphabetical order
            case CareerStaffAttributes.ID -> careerStaffList.stream()
                    .filter(careerStaff -> careerStaff.getId().toLowerCase().contains(args.toLowerCase()))
                    .collect(Collectors.toList());
            // Filter by name based on alphabetical order
            case CareerStaffAttributes.NAME ->
                careerStaffList
                        .stream()
                        .filter(careerStaff -> careerStaff.getName().toLowerCase().contains(args.toLowerCase()))
                        .collect(Collectors.toList());
        };
    }
}
