import java.util.List;
import java.util.stream.Collectors;

public class StudentFilter {
        // Filter studentList by attributes specified by filterBy and return sorted list.
    public static List<Student> filter(List<Student> studentList, StudentAttributes filterBy, String args) {
        return switch(filterBy) {
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
            case StudentAttributes.APPLIEDINTERNSHIP ->
                studentList
                    .stream()
                    .filter(student -> student.getAppliedInternship()
                        .stream()
                        .anyMatch(internship -> internship.getTitle()
                        .toLowerCase()
                        .trim()
                        .contains(args.toLowerCase().trim())))
                    .collect(Collectors.toList());
        };
    }
}
