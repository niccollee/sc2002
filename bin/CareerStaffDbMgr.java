import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CareerStaffDbMgr {

    private List<CareerStaff> careerStaffList;
    private static CareerStaffDbMgr instance;

    private CareerStaffDbMgr() {
        this.careerStaffList = new ArrayList<>();
        this.importDb();
    }

    public static CareerStaffDbMgr getInstance() {
        if (instance == null) {
            instance = new CareerStaffDbMgr();
        }
        return instance;
    }

    // read in from career staff list csv and initalize list of career staff
    public boolean importDb() {
        String filepath = "data/career_staff_list.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values;

            br.readLine(); // ignore first read line of column headers
            while ((line = br.readLine()) != null) {

                System.out.println("reading in: " + line);

                values = line.split(","); // seperate at delimiter ','
                careerStaffList.add(new CareerStaff(values[0], values[1], values[2], values[3], values[4], values[5]));

            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
            return false;
        }

        return true;

    }

    public List<CareerStaff> sort(CareerStaffAttributes sortBy) {
        return CareerStaffSorter.sort(careerStaffList, sortBy);
    }

    public List<CareerStaff> filter(CareerStaffAttributes filterBy, String args) {
        return CareerStaffFilter.filter(careerStaffList, filterBy, args);
    }

    public boolean remove(CareerStaff careerStaff) {
        return careerStaffList.remove(careerStaff);
    }

    public CareerStaff get(String id) {
        for (CareerStaff cStaff : this.careerStaffList) {
            if (cStaff.getId() == id) {
                return cStaff;
            }
        }

        return null;
    }

    public boolean add(String id, String name, String role, String department, String email, String passwordTxt) {
        if (this.get(id) == null) {
            return false;
        } else {
            careerStaffList
                    .add(new CareerStaff(id, name, role, department, email, IPasswordMgr.hashPassword(passwordTxt)));
            return true;
        }
    }

    public List<CareerStaff> showAll() {
        return this.careerStaffList;
    }

}
