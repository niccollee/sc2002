import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class StudentSorter {
    // Sort studentList by attribute specified in sortBy and return the sorted list.
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
                    .filter(student -> student.getAppliedInternship().size() != 0)
                    .sorted(Comparator.comparing(student -> student.getAppliedInternship().get(0).getTitle()))
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
