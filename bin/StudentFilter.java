import java.util.List;
import java.util.stream.Collectors;
import java.util.ArrayList;

/**
 * Utility for filtering lists of Student by a StudentAttributes key.
 *
 * Provides a single static method that returns a new list of students that
 * match the supplied filter criteria.
 */
public class StudentFilter {
    /**
     * Filter studentList by attributes specified by filterBy and return matching
     * list.
     *
     * @param studentList the list of students to filter (must not be null)
     * @param filterBy    the attribute to filter by
     * @param args        the filter argument; for numeric filters (YEAROFSTUDY,
     *                    APPLIEDINTERNSHIP)
     *                    this should be a string representation of an integer
     * @return a List of Student that satisfy the filter criteria (may be empty)
     * @throws NumberFormatException if args cannot be parsed as an integer when
     *                               required
     */
    public static List<Student> filter(List<Student> studentList, StudentAttributes filterBy, String args) {
        return switch (filterBy) {
            // Filter by name based on alphabetical order
            case StudentAttributes.ID -> studentList.stream()
                    .filter(student -> student.getId().toLowerCase().contains(args.toLowerCase()))
                    .collect(Collectors.toList());
            // Sort by name based on alphabetical order
            case StudentAttributes.NAME ->
                studentList
                        .stream()
                        .filter(student -> student.getName().toLowerCase().contains(args.toLowerCase()))
                        .collect(Collectors.toList());
            // sort by major in alphabetical order
            case StudentAttributes.MAJOR ->
                studentList
                        .stream()
                        .filter(student -> student.getMajor().toLowerCase().contains(args.toLowerCase()))
                        .collect(Collectors.toList());
            case StudentAttributes.YEAROFSTUDY ->
                studentList
                        .stream()
                        .filter(student -> student.getYearOfStudy() == Integer.parseInt(args))
                        .collect(Collectors.toList());
            case StudentAttributes.ACCEPTEDINTERNSHIP ->
                studentList
                        .stream()
                        .filter(student -> student.getAcceptedInternship() != null && student.getAcceptedInternship()
                                .getTitle()
                                .toLowerCase()
                                .trim()
                                .contains(args.toLowerCase().trim()))
                        .collect(Collectors.toList());
            case StudentAttributes.APPLIEDINTERNSHIP -> {
                try {
                    yield studentList
                            .stream()
                            .filter(student -> student.getAppliedInternships().getAll()
                                    .stream()
                                    .anyMatch(internshipApplication -> internshipApplication.getInternship()
                                            .getId() == Integer.parseInt(args)))
                            .collect(Collectors.toList());
                } catch (Exception e) {
                    yield new ArrayList<Student>();
                }
            }
        };
    }
}
