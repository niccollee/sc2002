import java.util.ArrayList;
import java.util.List;

public class CompanyRep implements IUser {

	private String id;
	private String name;
	private String department;
	private String position;
	private CompanyRepStatus repStatus;
	private List<Internship> internshipAdded;
	private String password;

	/**
	 * 
	 * @param id			company rep ID (email)
	 * @param companyName   company name
	 * @param department    department
	 * @param position      position/title
	 * @param repStatus     (PENDING/APROVED/REJECTED)
	 */
	public CompanyRep(String id, String companyName, String department, String position, CompanyRepStatus repStatus) {
		this.id = id;
		this.name = companyName;
		this.department = department;
		this.position = position;
		this.repStatus = repStatus;
		this.internshipAdded = new ArrayList<>();
		this.password = "password";
	}

	// getters

	public String getId() {return this.id;}
	public String getName() {return this.name;}
	public String getDepartment() {return this.department;}
	public String getPosition() {return this.position;}
	public CompanyRepStatus getRepStatus() {return this.repStatus;}
	public List<Internship> getInternshipAdded() {return internshipAdded;}
	public String getPassword() {return password;}

	// setters
	public void setRepStatus(CompanyRepStatus repStatus) {this.repStatus = repStatus;}
	public void setPassword(String newPassword) {this.password = newPassword;}

	/*  adds an internship created by this company rep 
	 * rules:
	 * - only allowed if rep status is APPROVED
	 * at most 5 internships per rep
	 * no duplicate titles uneder the same rep
	*/
	public boolean addInternship(Internship internship) {
		if (internship == null) return false;
		if (repStatus != CompanyRepStatus.APPROVED) return false;
		if (internshipAdded.size() >= 5) return false;

		String title = internship.getTitle();
		for (Internship in : internshipAdded)
		{
			if (in.getTitle() == null && in.getTitle().equalsIgnoreCase(title))
			{
				return false;
			}
		}
		internshipAdded.add(internship);
		return true;
	}

	// remove internship created by this rep
	public boolean removeInternship(Internship internship) {
		if (internship == null) return false;
		return internshipAdded.remove(internship);
	}

	// finds exact internship added by rep
	public Internship getInternship(String title) {
		if (title == null) return null;
		for (Internship in : internshipAdded)
		{
			if (in.getTitle() != null && in.getTitle().equalsIgnoreCase(title)) {
				return in;
			}
		}
		return null;
	}

	// toggle visibility
	public boolean toggleVisibility(Internship internship) {
		if (repStatus != CompanyRepStatus.APPROVED) return false;
		if (internship == null) return false;

		if (internship.getStatus() != Status.APPROVED) return false;

		internship.setVisibility(!internship.getVisibility());
		return true;
	}
}
