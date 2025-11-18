import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class CompanyRepDbMgr {
	
	private List<CompanyRep> companyRepList;
	private static CompanyRepDbMgr instance;

	private CompanyRepDbMgr() {
		this.companyRepList = new ArrayList<CompanyRep>();
		// maybe import?
	}

	public static CompanyRepDbMgr getInstance() {
		if (instance == null) {
			instance = new CompanyRepDbMgr();
		}
		return instance;
	}

	// returns rep with given id or null is not found
	public CompanyRep get(String id) {
		if (id == null) return null;
		for (CompanyRep rep : companyRepList) {
			if (rep.getId() != null && rep.getId().equalsIgnoreCase(id)) {
				return rep;
			}
		}
		return null;
	}

	// add new rep to the list of CompanyReps
	// before adding need to check rep status!
	public boolean add(String id, String companyName, String department, String position, CompanyRepStatus repStatus) {
		if (id == null) return false;
		if (get(id) != null) return false; // no duplicates
		CompanyRep rep = new CompanyRep(id, companyName, department, position, repStatus);
		companyRepList.add(rep);
		return true;
	}

	// remove rep from list
	public boolean removeCompanyRep(CompanyRep companyRep) {
		return companyRepList.removeIf(r -> r.getId() != null && r.getId().equalsIgnoreCase(companyRep.getId()));
	}

	// returns a new list sorted by the specified attribute
	public List<CompanyRep> sort(CompanyRepAttributes sortBy) {
		if (sortBy == null) return new ArrayList<>(companyRepList);

		Comparator<String> S = Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER);
		Comparator<CompanyRep> cmp = switch(sortBy) {
			case ID -> Comparator.comparing(CompanyRep::getId, S);
			case NAME -> Comparator.comparing(CompanyRep::getName, S);
			case DEPARTMENT -> Comparator.comparing(CompanyRep::getDepartment, S);
			case POSITION -> Comparator.comparing(CompanyRep::getPosition, S);
			case REPSTATUS -> Comparator.comparing(r -> r.getRepStatus() == null ? null : r.getRepStatus().name(), S);
			case INTERNSHIPADDED -> Comparator.comparingInt(
    			(CompanyRep r) -> {
        			var list = r.getInternshipAdded();
       		 		return (list == null) ? 0 : list.size();
    		}
			);
		};

		return companyRepList.stream().sorted(cmp).collect(Collectors.toList());
	}

	// filter by attribute
	public List<CompanyRep> filter(CompanyRepAttributes filterBy, String args) {
		if (filterBy == null) return new ArrayList<>(companyRepList);

		return switch (filterBy) {
			case ID -> companyRepList.stream()
				.filter(rep -> rep.getId() != null && args != null && rep.getId().equalsIgnoreCase(args))
				.collect(Collectors.toList());

			case NAME -> companyRepList.stream()
				.filter(rep -> rep.getName() != null && args != null && rep.getName().equalsIgnoreCase(args))
				.collect(Collectors.toList());

			case DEPARTMENT -> companyRepList.stream()
				.filter(rep -> rep.getDepartment() != null && args != null && rep.getDepartment().equalsIgnoreCase(args))
				.collect(Collectors.toList());

			case POSITION -> companyRepList.stream()
				.filter(rep -> rep.getPosition() != null && args != null && rep.getPosition().equalsIgnoreCase(args))
				.collect(Collectors.toList());

			case REPSTATUS -> {
				CompanyRepStatus target = null;
				if (args != null) {
					try {target = CompanyRepStatus.valueOf(args.trim().toUpperCase());} catch (Exception ignored) {}
				}
				CompanyRepStatus t = target;
				yield companyRepList.stream()
					.filter(rep -> rep.getRepStatus() == t)
					.collect(Collectors.toList());
			}

			case INTERNSHIPADDED -> companyRepList.stream()
				.filter(rep -> rep.getInternshipAdded() != null && !rep.getInternshipAdded().isEmpty())
				.collect(Collectors.toList());
		};
	}

	public List<CompanyRep> showAll() {
		return this.companyRepList;
	}

	// read in from Company Rep list csv and initalize list of Company Rep
    public boolean importDb(String filename) {
        String filepath = "data/company_representative_list.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(filepath))) {
            String line;
            String[] values;

            br.readLine(); // ignore first read line of column headers
            while ((line = br.readLine()) != null) {

                System.out.println("reading in: " + line);

                values = line.split(","); // seperate at delimiter ','
                companyRepList.add(new CompanyRep(values[0], values[2], values[3], values[4], values[4], "password"));

            }
        } catch (IOException e) {
            System.out.println("ERROR: Unable to read file");
            return false;
        }

        return true;

    }
}
