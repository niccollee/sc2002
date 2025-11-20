import java.io.Serializable;
import java.time.LocalDate;

/**
 * Represents an internship opportunity offered by a company.
 * Stores details such as title, description, application dates,
 * preferred major, number of slots, and visibility status.
 */
public class Internship implements Serializable {
    private int id;
    private String title;
    private String description;
    private InternshipLevel level;
    private String preferredMajor;
    private LocalDate appOpenDate;
    private LocalDate appCloseDate;
    private Status status;
    private String companyName;
    private CompanyRep companyRep;
    private int numSlots;
    private boolean visibility;
    private int confirmedSlots = 0;
    private static int count = 0;

    /**
     * Creates a new internship with the specified details.
     *
     * @param title          the internship title
     * @param description    a description of the internship
     * @param level          the internship level
     * @param preferredMajor the preferred student major
     * @param appOpenDate    the application opening date
     * @param appCloseDate   the application closing date
     * @param status         the current internship status
     * @param companyName    the name of the company offering the internship
     * @param companyRep     the company representative responsible
     * @param numSlots       the total number of available slots
     * @param visibility     whether the internship is visible to students
     */
    public Internship(
            String title,
            String description,
            InternshipLevel level,
            String preferredMajor,
            LocalDate appOpenDate,
            LocalDate appCloseDate,
            Status status,
            String companyName,
            CompanyRep companyRep,
            int numSlots,
            boolean visibility) {
        this.id = count++;
        this.title = title;
        this.description = description;
        this.level = level;
        this.preferredMajor = preferredMajor;
        this.appOpenDate = appOpenDate;
        this.appCloseDate = appCloseDate;
        this.status = status;
        this.companyName = companyName;
        this.companyRep = companyRep;
        this.numSlots = numSlots;
        this.visibility = visibility;
        this.confirmedSlots = 0;
    }

    /** @return the unique internship ID */
    public int getId() {
        return id;
    }

    /** @return the internship title */
    public String getTitle() {
        return title;
    }

    /** @return the internship description */
    public String getDescription() {
        return description;
    }

    /** @return the internship level */
    public InternshipLevel getLevel() {
        return level;
    }

    /** @return the preferred student major */
    public String getPreferredMajor() {
        return preferredMajor;
    }

    /** @return the date applications open */
    public LocalDate getAppOpenDate() {
        return appOpenDate;
    }

    /** @return the date applications close */
    public LocalDate getAppCloseDate() {
        return appCloseDate;
    }

    /** @return the current status of the internship */
    public Status getStatus() {
        return status;
    }

    /** @return the offering company's name */
    public String getCompanyName() {
        return companyName;
    }

    /** @return the company representative responsible for this internship */
    public CompanyRep getCompanyRep() {
        return companyRep;
    }

    /** @return the number of available slots */
    public int getNumSlots() {
        return numSlots;
    }

    /** @return whether the internship is visible to students */
    public boolean getVisibility() {
        return visibility;
    }

    /** @return the number of confirmed slots */
    public int getConfirmedSlots() {
        return confirmedSlots;
    }

    // Setter methods
    public void setStatus(Status status) {
        this.status = status;
    }

    // setter for visibility
    public void setVisibility(boolean v) {
        this.visibility = v;
    }

    /**
     * Updates the status of the internship.
     *
     * @param status the new status to assign
     */
    public void incrementConfirmedSlots() {
        if (confirmedSlots < numSlots) {
            confirmedSlots++;
            if (confirmedSlots >= numSlots) {
                this.status = Status.FILLED;
            }
        }
    }
}
