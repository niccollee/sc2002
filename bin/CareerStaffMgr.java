import java.util.ArrayList;
import java.util.List;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CareerStaffMgr {

    private List<CareerStaff> careerStaffList;

    public CareerStaffMgr() {
        this.careerStaffList = new ArrayList<>();
    }

    // read in from career staff list csv and initalize list of career staff
    public boolean loadCareerStaff() {
        String filepath = "data/career_staff_list.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values;

            br.readLine(); // ignore first read line of column headers
            while ((line = br.readLine()) != null) {

                System.out.println("reading in: " + line);

                values = line.split(","); // seperate at delimiter ','
                careerStaffList.add(new CareerStaff(values[0], values[1], values[2], values[3], values[4], "password"));
                
            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
            return false;
        }

        return true;

    }

    /*
     * public boolean approveRep(){
     * 
     * }
     * 
     * public boolean approveInternOpp(){
     * 
     * }
     * 
     * public boolean approveStudentWidthdrawl(){
     * 
     * }
     * 
     * public void viewInternOpp(){
     * 
     * }
     */

}
