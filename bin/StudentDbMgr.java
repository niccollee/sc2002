import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
 * Database manager which contains all the {@link Student} object and the associated 
 * actions. 
 */
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
                // Student(String id, String name, String password, int yearOfStudy, String
                // major)
                studentList.add(new Student(values[0], values[1], values[5], Integer.parseInt(values[3]), values[2],
                        values[4]));
            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file" + e.getMessage());
            return false;
        }
        return true;
    }

    /**
     * Exports the csv from the filepath and 
     */
    private void exportDb() {
        String filepath = "../data/student_list.csv";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
            // write CSV header
            bw.write("StudentID,Name,Major,Year,Email,PasswordHash");
            bw.newLine();

            for (Student s : this.studentList) {
                String[] fields = new String[] { s.getId(), s.getName(), s.getMajor(),
                        Integer.toString(s.getYearOfStudy()), s.getEmail(), s.getPassword() };

                String line = String.join(",", fields);

                System.out.println("writing out: " + line);
                bw.write(line);
                bw.newLine();
            }

        } catch (IOException e) {
            System.out.println("ERROR: Unable to write file: " + e.getMessage());
        }
    }
    /**
     * Private contructor to ensure only one instance of itself. Initialise ArrayList and import the csv 
     * into this class.
     */
    private StudentDbMgr() {
        this.studentList = new ArrayList<Student>();
        importDb();
    }

    /**
     * Gets an instance of this. If not initialised yet, initialise.
     * @return an instance of itself.
     */
    public static StudentDbMgr getInstance() {
        if (instance == null) {
            instance = new StudentDbMgr();
        }
        return instance;
    }
    /**
     * Add {@link Student} into the list.
     * @param student the student that is added into the list.
     * @return true if {@link Student} id does not exist
     */
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

    // Method to check if duplicate id exist. Return true if id exist, otherwise
    // false.
    public boolean containsId(String id) {
        for (Student i : studentList) {
            if (i.getId() == id) {
                return true;
            }
        }
        return false;
    }

    // Getter method for student from the studentList. If student does not exist,
    // return null
    public Student getStudent(String id) {
        for (Student i : studentList) {
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