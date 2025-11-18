import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepDbMgr {

	private List<CompanyRep> companyRepList;
	private static CompanyRepDbMgr instance;

	private CompanyRepDbMgr() {
		this.companyRepList = new ArrayList<CompanyRep>();
		this.importDb();
	}

	public static CompanyRepDbMgr getInstance() {
		if (instance == null) {
			instance = new CompanyRepDbMgr();
		}
		return instance;
	}

	// returns rep with given id or null is not found
	public CompanyRep get(String id) {
		if (id == null)
			return null;
		for (CompanyRep rep : companyRepList) {
			if (rep.getId() != null && rep.getId().equalsIgnoreCase(id)) {
				return rep;
			}
		}
		return null;
	}

	// add new rep to the list of CompanyReps
	// before adding need to check rep status!
	public boolean add(String id, String companyName, String department, String position, CompanyRepStatus repStatus,
			String passwordTxt) {
		if (id == null)
			return false;
		if (get(id) != null)
			return false; // no duplicates
		CompanyRep rep = new CompanyRep(id, companyName, department, position, repStatus,
				IPasswordMgr.hashPassword(passwordTxt));
		companyRepList.add(rep);
		return true;
	}

	// remove rep from list
	public boolean removeCompanyRep(CompanyRep companyRep) {
		return companyRepList.removeIf(r -> r.getId() != null && r.getId().equalsIgnoreCase(companyRep.getId()));
	}

	// returns a new list sorted by the specified attribute
	public List<CompanyRep> sort(CompanyRepAttributes sortBy) {
		return CompanyRepSorter.sort(companyRepList, sortBy);
	}

	// filter by attribute
	public List<CompanyRep> filter(CompanyRepAttributes filterBy, String args) {
		return CompanyRepFilter.filter(companyRepList, filterBy, args);
	}

	public List<CompanyRep> showAll() {
		return this.companyRepList;
	}

	// read in from Company Rep list csv and initalize list of Company Rep
	private boolean importDb() {
		String filepath = "../data/company_representative_list.csv";

		try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
			String line;
			String[] values;

			br.readLine(); // ignore first read line of column headers
			while ((line = br.readLine()) != null) {

				System.out.println("reading in: " + line);

				values = line.split(","); // seperate at delimiter ','
				companyRepList.add(new CompanyRep(values[0], values[2], values[3], values[4],
						CompanyRepStatus.valueOf(values[6]), values[7]));
			}
		} catch (IOException e) {
			System.out.println("ERROR: Unable to read file" + e.getMessage());
			return false;
		}

		return true;
	}

	// export company rep list into csv
	private void exportDb() {
		String filepath = "../data/company_representative_list.csv";

		try (BufferedWriter bw = new BufferedWriter(new FileWriter(filepath))) {
			// write CSV header
			bw.write("CompanyRepID,Name,CompanyName,Department,Position,Email,Status,PasswordHash");
			bw.newLine();

			for (CompanyRep cRep : this.companyRepList) {
				String[] fields = new String[] { cRep.getId(), "", cRep.getName(), cRep.getDepartment(),
						cRep.getPosition(), cRep.getId(), cRep.getRepStatus().toString(), cRep.getPassword() };

				String line = String.join(",", fields);

				System.out.println("writing out: " + line);
				bw.write(line);
				bw.newLine();
			}

		} catch (IOException e) {
			System.out.println("ERROR: Unable to write file: " + e.getMessage());
		}
	}
}
