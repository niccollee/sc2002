import java.util.List;

/**
 * Represents a company representative user in the internship portal.
 * A company representative can create and manage internships, accept or reject
 * student applications and toggle visibility of their approved internships.
 */
public class CompanyRep implements IUser {

	private String id;
	private String name;
	private String department;
	private String position;
	private CompanyRepStatus repStatus;
	private int internshipcounter;
	private String password;

	/**
	 * Creates a new company representative with the given details.
	 *
	 * @param id          company rep ID (email)
	 * @param companyName company name
	 * @param department  department
	 * @param position    position/title
	 * @param repStatus   (PENDING/APROVED/REJECTED)
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

	/**
	 * Returns the ID of this company representative.
	 *
	 * @return the representative ID
	 */
	public String getId() {return this.id;}

	/**
	 * Returns the company name associated with this representative.
	 *
	 * @return the company name
	 */
	public String getName() {return this.name;}

	/**
	 * Returns the department of this representative.
	 *
	 * @return the department name
	 */
	public String getDepartment() {return this.department;}

	/**
	 * Returns the position or title of this representative.
	 *
	 * @return the position/title
	 */
	public String getPosition() {return this.position;}

	/**
	 * Returns the current status of this representative.
	 *
	 * @return the representative status
	 */
	public CompanyRepStatus getRepStatus() {return this.repStatus;}

	/**
	 * Returns the stored (hashed) password for this representative.
	 *
	 * @return the hashed password
	 */
	public String getPassword() {return password;}

	/**
	 * Returns the number of internships currently associated with this representative.
	 *
	 * @return the internship counter
	 */
	public int getInternshipCounter() {return internshipcounter;}

	// setters

	/**
	 * Updates the status of this company representative.
	 *
	 * @param repStatus the new status to set
	 */
	public void setRepStatus(CompanyRepStatus repStatus) {this.repStatus = repStatus;}

	/**
	 * Sets the password for this representative.
	 * The caller is expected to pass in a hashed password if required.
	 *
	 * @param newPassword the new password value
	 */
	public void setPassword(String newPassword) {this.password = newPassword;}



	/*  adds an internship created by this company rep 
	 * rules:
	 * - only allowed if rep status is APPROVED
	 * at most 5 internships per rep
	 * no duplicate titles uneder the same rep
	*/
	/**
	 * Adds an internship created by this company representative to the database.
	 * This is only allowed if the representative is APPROVED, the internship
	 * belongs to this representative and the representative has fewer than 5 internships.
	 *
	 * @param internship the internship to add
	 * @return true if the internship was added successfully, false otherwise
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


	/**
	 * Finds an internship created by this representative by its title.
	 * The search is case-insensitive and only internships owned by this
	 * representative are considered.
	 *
	 * @param title the title of the internship to search for
	 * @return the matching internship, or null if not found
	 */
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

	/**
	 * Accepts or rejects a student's internship application for an internship
	 * owned by this representative.
	 * The internship must be approved and belong to this representative.
	 *
	 * @param student        the student whose application is being decided on
	 * @param internship     the internship in question
	 * @param decisionStatus 1 for successful, 2 for unsuccessful, any other value is invalid
	 * @return true if the application status was updated, false otherwise
	 */
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


	/**
	 * Toggles the visibility of an approved internship owned by this representative.
	 * Only approved internships belonging to this representative can have their
	 * visibility changed.
	 *
	 * @param internship the internship whose visibility is to be toggled
	 * @return true if the visibility was toggled, false otherwise
	 */
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