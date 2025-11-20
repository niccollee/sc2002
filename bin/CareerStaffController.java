/**
 * Controller class that handles actions performed by career staff users.
 * This controller provides operations to approve or reject company representatives,
 * approve or reject internships, and process student internship withdrawal requests.
 * It is implemented as a singleton so that a single shared instance is used
 * throughout the application.
 */
public class CareerStaffController {
    private static CareerStaffController instance;

    /**
     * Returns the singleton instance of {@code CareerStaffController}.
     * If no instance exists yet, a new one is created.
     *
     * @return the single {@code CareerStaffController} instance
     */
    public static CareerStaffController getInstance() {
        if (instance == null) {
            instance = new CareerStaffController();
        }
        return instance;
    }

    /**
     * Approves a company representative with the given ID.
     * If the company representative exists in the provided database manager,
     * its status is set to {@link CompanyRepStatus#APPROVED}.
     *
     * @param companyRepId    the ID of the company representative to approve
     * @param companyRepDbMgr the database manager used to retrieve and update the representative
     * @return {@code true} if the representative was found and approved; {@code false} otherwise
     */
    public boolean approveCompanyRep(String companyRepId, CompanyRepDbMgr companyRepDbMgr) {
        CompanyRep companyRep = companyRepDbMgr.get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.APPROVED);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    /**
     * Rejects a company representative with the given ID.
     * If the company representative exists in the provided database manager,
     * its status is set to {@link CompanyRepStatus#REJECT}.
     *
     * @param companyRepId    the ID of the company representative to reject
     * @param companyRepDbMgr the database manager used to retrieve and update the representative
     * @return {@code true} if the representative was found and rejected; {@code false} otherwise
     */
    public boolean rejectCompanyRep(String companyRepId, CompanyRepDbMgr companyRepDbMgr) {
        CompanyRep companyRep = companyRepDbMgr.get(companyRepId);

        if (companyRep != null) {
            companyRep.setRepStatus(CompanyRepStatus.REJECT);
            return true;
        }

        // When cannot find CompanyRepId in companyRepList to approve
        return false;
    }

    /**
     * Approves an internship with the given ID.
     * If the internship exists in the provided database manager,
     * its status is set to {@link Status#APPROVED}.
     *
     * @param internshipId    the ID of the internship to approve
     * @param internshipDbMgr the database manager used to retrieve and update the internship
     * @return {@code true} if the internship was found and approved; {@code false} otherwise
     */
    public boolean approveInternship(int internshipId, InternshipDbMgr internshipDbMgr) {
        Internship internship = internshipDbMgr.get(internshipId);

        if (internship != null) {
            internship.setStatus(Status.APPROVED);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    /**
     * Rejects an internship with the given ID.
     * If the internship exists in the provided database manager,
     * its status is set to {@link Status#REJECT}.
     *
     * @param internshipId    the ID of the internship to reject
     * @param internshipDbMgr the database manager used to retrieve and update the internship
     * @return {@code true} if the internship was found and rejected; {@code false} otherwise
     */
    public boolean rejectInternship(int internshipId, InternshipDbMgr internshipDbMgr) {
        Internship internship = internshipDbMgr.get(internshipId);

        if (internship != null) {
            internship.setStatus(Status.REJECT);
            return true;
        }

        // when cannot find internshipId in internshipList
        return false;
    }

    //
    /**
     * Approves a student's internship withdrawal request.
     * <p>
     * If a matching {@link InternshipWithdrawalApplicants} record is found for the
     * given student ID, this method performs the withdrawal on that record.
     * </p>
     *
     * @param studentId                   the ID of the student requesting withdrawal
     * @param internshipWithdrawalDbMgr   the database manager used to look up withdrawal requests
     * @return {@code true} if a withdrawal request was found and processed; {@code false} otherwise
     */
    public boolean approveStudentWidthdrawl(String studentId, InternshipWithdrawalDbMgr internshipWithdrawalDbMgr) {
        InternshipWithdrawalApplicants internshipWithdrawalApplicants = internshipWithdrawalDbMgr
                .getFromStudentId(studentId);

        if (internshipWithdrawalApplicants == null) {
            return false;
        } else {
            internshipWithdrawalApplicants.withdrawInternship();
            return true;
        }

    }

    // rejecting studentWithdrawl does nothing, internshipWithdrawlApplicants remain
    // inside
    /**
     * Rejects a student's withdrawal request.
     * Currently, rejecting a withdrawal does not change any stored data;
     * the withdrawal record remains in the system.
     *
     * @param studentId                 the ID of the student whose withdrawal is rejected
     * @param internshipWithdrawalDbMgr the database manager for withdrawal requests
     * @return always {@code true}, as no changes are applied
     */
    public boolean rejectStudentWithdrawl(String studentId, InternshipWithdrawalDbMgr internshipWithdrawalDbMgr) {
        return true;
    }

}
