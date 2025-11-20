import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides sorting operations for {@link CareerStaff} lists.
 * Supports sorting by different {@link CareerStaffAttributes},
 * such as ID and name, in alphabetical order.
 */
public class CareerStaffSorter {

    /**
     * Sorts the given list of career staff based on the specified attribute.
     * The returned list is a new list and does not modify the original.
     *
     * @param careerStaffList the list of {@link CareerStaff} to sort
     * @param sortBy          the {@link CareerStaffAttributes} used as the sort key
     * @return a new list containing the staff sorted by the chosen attribute
     */
    public static List<CareerStaff> sort(List<CareerStaff> careerStaffList, CareerStaffAttributes sortBy) {
        return switch (sortBy) {
            // Sort by ID based on alphabetical order
            case CareerStaffAttributes.ID -> careerStaffList.stream()
                    .sorted(Comparator.comparing(CareerStaff::getId))
                    .collect(Collectors.toList());
            // Sort by name based on alphabetical order
            case CareerStaffAttributes.NAME ->
                careerStaffList
                        .stream()
                        .sorted(Comparator.comparing(CareerStaff::getName))
                        .collect(Collectors.toList());
        };
    }
}
