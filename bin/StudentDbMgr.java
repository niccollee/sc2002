import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.List;

public class StudentDbMgr {
    private List<Student> studentList;
    private static StudentDbMgr instance;

    private boolean importDb() {
        String filepath = "../data/student_list.csv";
        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values;
            br.readLine(); // ignore first read line of column headers
            while ((line = br.readLine()) != null) {
                System.out.println("reading in: " + line);
                values = line.split(","); // seperate at delimiter ','
                // Student(String id, String name, String password, int yearOfStudy, String major)
                studentList.add(new Student(values[0], values[1], "password", Integer.parseInt(values[3]), values[4]));
            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
            return false;
        }
        return true;
    }

    private StudentDbMgr() {
        this.studentList = new ArrayList<Student>();
        importDb();
    }
    // Returns an instance of the this class
    public static StudentDbMgr getInstance(){
        if (instance == null) {
            instance = new StudentDbMgr();
        }
        return instance;
    }
    // Add student to studentList. If id of student exist, 
    // return false and student not added. 
    // Else return true and add student to studentList.
    public boolean add(Student student) {
        if (containsId(student.getId())) {
            return false;
        }
        studentList.add(student);
        return true;
    }
    // Getter method to get student
    public List<Student> get() {
        return studentList;
    }
    // Method to check if duplicate id exist. Return true if id exist, otherwise false.
    public boolean containsId(String id) {
        for (Student i: studentList) {
            if (i.getId() == id) {
                return true;
            }
        }
        return false;
    }
    // Getter method for student from the studentList. If student does not exist, return null
    public Student getStudent(String id) {
        for (Student i: studentList) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }
    // Sort studentList by attribute specified in sortBy and return the sorted list.
    public List<Student> sort(StudentAttributes sortBy) {
        return StudentSorter.sort(studentList, sortBy);
    }

    public List<Student> filter(StudentAttributes filterBy, String args) {
        return StudentFilter.filter(studentList, filterBy, args);
    }
}