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

    /**
     * Load students from the CSV file "../data/student_list.csv" into the
     * internal list.
     *
     * @return true when the file was read successfully, false on I/O error
     */
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
     * Write the internal student list to the CSV file "../data/student_list.csv".
     * Existing file content is overwritten.
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
     * Private constructor to enforce singleton. Initializes the internal list
     * and imports data from CSV.
     */
    private StudentDbMgr() {
        this.studentList = new ArrayList<Student>();
        importDb();
    }

    /**
     * Obtain the singleton instance of StudentDbMgr, creating it if necessary.
     *
     * @return the singleton StudentDbMgr
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

    /**
     * Return the internal list of students.
     *
     * @return the list of Student instances
     */
    public List<Student> getAll() {
        return studentList;
    }

    /**
     * Check whether a student id already exists.
     *
     * @param id the student id to check
     * @return true if an entry with the given id exists, false otherwise
     */
    public boolean containsId(String id) {
        for (Student i : studentList) {
            if (i.getId() == id) {
                return true;
            }
        }
        return false;
    }

    /**
     * Retrieve a Student by id.
     *
     * @param id the student id to find
     * @return the Student with the matching id, or null if not found
     */
    public Student getStudent(String id) {
        for (Student i : studentList) {
            if (i.getId().equals(id)) {
                return i;
            }
        }
        return null;
    }

    /**
     * Return a new list of students sorted by the given attribute.
     *
     * @param sortBy attribute to sort by
     * @return a sorted List of Student
     */
    public List<Student> sort(StudentAttributes sortBy) {
        return StudentSorter.sort(studentList, sortBy);
    }

    /**
     * Return a filtered list of students matching the given attribute and argument.
     *
     * @param filterBy attribute to filter by
     * @param args filter argument (string form; numeric filters expect parseable integer)
     * @return a List of Student that match the filter
     */
    public List<Student> filter(StudentAttributes filterBy, String args) {
        return StudentFilter.filter(studentList, filterBy, args);
    }
}