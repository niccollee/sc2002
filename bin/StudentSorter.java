import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Utility for sorting lists of Student by a specified StudentAttributes key.
 *
 * Provides a single static method that returns a new sorted list and does
 * not modify the input list.
 */
public class StudentSorter {
    /**
     * Sort the provided studentList by the attribute specified in sortBy.
     *
     * Returns a new List containing the sorted students. If the attribute is
     * not recognised an empty list is returned.
     *
     * @param studentList the list of students to sort (may be empty, not null)
     * @param sortBy the attribute to sort by
     * @return a new List of Student sorted according to sortBy, or an empty list
     *         if sortBy is not supported
     */
    public static List<Student> sort(List<Student> studentList, StudentAttributes sortBy) {
        switch(sortBy) {
            // Sort by name based on alphabetical order
            case StudentAttributes.ID:
                return studentList
                    .stream()
                    .sorted(Comparator.comparing(Student::getId))
                    .collect(Collectors.toList());
            // Sort by name based on alphabetical order
            case StudentAttributes.NAME:
                return studentList
                    .stream()
                    .sorted(Comparator.comparing(Student::getName))
                    .collect(Collectors.toList());
            // Sory by yearOfStudy in numerical order
            case StudentAttributes.YEAROFSTUDY:
                return studentList
                    .stream()
                    .sorted(Comparator.comparing(Student::getYearOfStudy))
                    .collect(Collectors.toList());
            // sort by major in alphabetical order
            case StudentAttributes.MAJOR:
                return studentList
                    .stream()
                    .sorted(Comparator.comparing(Student::getMajor))
                    .collect(Collectors.toList());
            case StudentAttributes.APPLIEDINTERNSHIP:
                return studentList
                    .stream()
                    .filter(student -> student.getAppliedInternships().getAll().size() != 0)
                    .sorted(Comparator.comparing(student -> student.getAppliedInternships().getAll().get(0).getInternship().getTitle()))
                    .collect(Collectors.toList());
            // sort by acceptedInternship by alphabetical order
            case StudentAttributes.ACCEPTEDINTERNSHIP:
                return studentList
                    .stream()
                    .filter(student -> student.getAcceptedInternship() != null)
                    .sorted(Comparator.comparing(student -> student.getAcceptedInternship().getTitle()))  
                    .collect(Collectors.toList());  
            default:
                return new ArrayList<Student>();    
        }
    }
}
