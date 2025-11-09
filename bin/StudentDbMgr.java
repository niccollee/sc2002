import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

public class StudentDbMgr {
    private List<Student> studentList;

    public StudentDbMgr() {
        this.studentList = new ArrayList<Student>();
    }
    // Add students either by creating new student object
    public boolean add(Student student) {
        for (Student i : studentList) {
            if (i.getId() == student.getId()) {
                return false;
            }
        }
        studentList.add(student);
        return true;
    }
    // Getter method for
    public Student get(int idx) {
        return studentList.get(idx);
    }
    // Sort studentList by attribute specified in sortBy and return the sorted list.
    public List<Student> sort(StudentAttributes sortBy) {
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
    // Filter studentList by attributes specified by filter by and return sorted list.
    public List<Student> filter(StudentAttributes filterBy, String args) {
        switch(filterBy) {
            // Filter by name based on alphabetical order
            case StudentAttributes.ID:
                return studentList
                    .stream()
                    .filter(student -> student.getId() == args)
                    .collect(Collectors.toList());
            // Sort by name based on alphabetical order
            case StudentAttributes.NAME:
                return studentList
                    .stream()
                    .filter(student -> student.getName() == args)
                    .collect(Collectors.toList());
            // sort by major in alphabetical order
            case StudentAttributes.MAJOR:
                return studentList
                    .stream()
                    .filter(student -> student.getMajor() == args)
                    .collect(Collectors.toList());
            default:
                return new ArrayList<Student>(); 
        }
    }

    public List<Student> filter(StudentAttributes filterBy, int args) {
        return studentList 
            .stream() 
            .filter(student -> student.getYearOfStudy() == args)
            .collect(Collectors.toList());
    }

    public List<Student> filter(StudentAttributes filterBy, Internship internship) {
        switch(filterBy) {
            case StudentAttributes.ACCEPTEDINTERNSHIP:
                return studentList     
                    .stream()
                    .filter(student -> student.getAcceptedInternship() == internship)
                    .collect(Collectors.toList());
            case StudentAttributes.APPLIEDINTERNSHIP:
                return studentList
                    .stream()
                    .filter(student -> student.getAppliedInternship().contains(internship))
                    .collect(Collectors.toList());
            default:
                return new ArrayList<Student>();
        }
    }
}