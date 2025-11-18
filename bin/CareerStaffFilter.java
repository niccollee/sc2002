import java.util.List;
import java.util.stream.Collectors;

public class CareerStaffFilter {
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
