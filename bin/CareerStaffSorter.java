import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CareerStaffSorter {
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
