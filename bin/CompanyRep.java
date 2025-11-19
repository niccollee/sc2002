
import java.util.List;

public class CompanyRep implements IUser {

	private String id;
	private String name;
	private String department;
	private String position;
	private CompanyRepStatus repStatus;
	private int internshipcounter;
	private String password;
	/**
	 * 
	 * @param id			company rep ID (email)
	 * @param companyName   company name
	 * @param department    department
	 * @param position      position/title
	 * @param repStatus     (PENDING/APROVED/REJECTED)
	 */
	public CompanyRep(String id, String companyName, String department, String position, CompanyRepStatus repStatus, String passwordHash){
		this.id = id;
		this.name = companyName;
		this.department = department;
		this.position = position;
		this.repStatus = repStatus;
		this.password = passwordHash;
		this.internshipcounter = 0;
	}

	// getters

	public String getId() {return this.id;}
	public String getName() {return this.name;}
	public String getDepartment() {return this.department;}
	public String getPosition() {return this.position;}
	public CompanyRepStatus getRepStatus() {return this.repStatus;}
	public String getPassword() {return password;}
	public int getInternshipCounter() {return internshipcounter;}

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
		InternshipDbMgr internshipdbmanager = InternshipDbMgr.getInstance();
		if (internship == null) return false;
		if (repStatus != CompanyRepStatus.APPROVED) return false;
		if(internship.getCompanyRep() != this) return false;

		int countForThisRep = 0;
		for (Internship i : internshipdbmanager.showAll()) {
			if (i.getCompanyRep() == this) {
				countForThisRep++;
			}
		}
		if (countForThisRep >= 5) return false;

		boolean added = internshipdbmanager.add(internship);
		if (added) {
			internshipcounter = countForThisRep + 1;
		}
		return added;
	}


	// finds exact internship added by rep TO EDIT
	public Internship getInternship(String title) {
		InternshipDbMgr internshipdbmanager = InternshipDbMgr.getInstance();
		if (title == null) return null;

		List<Internship> byTitle = internshipdbmanager.filter(InternshipAttributes.TITLE, title);

		for (Internship i : byTitle) {
			if (i.getCompanyRep() == this && i.getTitle() != null && i.getTitle().equalsIgnoreCase(title)) {
				return i;
			}
		}
		return null;
	}

	// accept student internship
	public boolean acceptStudentInternship(Student student, Internship internship, int decisionStatus) {
		InternshipDbMgr internshipdbmanager = InternshipDbMgr.getInstance();
		if (student == null || internship == null) return false;
		if (repStatus != CompanyRepStatus.APPROVED) return false;

		Internship dbInternship = internshipdbmanager.get(internship.getId());
		if (dbInternship == null) return false;
		if (dbInternship.getCompanyRep() != this) return false;
		if (dbInternship.getStatus() != Status.APPROVED) return false;

		InternshipApplicationDbMgr appDb = student.getAppliedInternships();
		InternshipApplication application = appDb.get(dbInternship);
		if (application == null) {
			// student did not apply for this internship
			return false;
		}

		InternshipApplicationStatus newStatus;
		switch (decisionStatus) {
			case 1:
				newStatus = InternshipApplicationStatus.SUCCESSFUL;
				break;
			case 2:
				newStatus = InternshipApplicationStatus.UNSUCCESSFUL;
				break;
			default:
				// invalid input
				return false;
		}

		// update the application status
		application.setInternshipApplicationStatus(newStatus);
		return true;
	}


	// toggle visibility
	public boolean toggleVisibility(Internship internship) {
		InternshipDbMgr internshipdbmanager = InternshipDbMgr.getInstance();
		if (repStatus != CompanyRepStatus.APPROVED) return false;
		if (internship == null) return false;

		Internship dbInternship = internshipdbmanager.get(internship.getId());
		if (dbInternship == null) return false;

		// only allow this rep to toggle visibility for their own internships
		if (dbInternship.getCompanyRep() != this) return false;
		if (dbInternship.getStatus() != Status.APPROVED) return false;

		dbInternship.setVisibility(!dbInternship.getVisibility());
		return true;
	}
}
