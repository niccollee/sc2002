import java.util.Date;

public class Internship {
    private String title;
    private String description;
    private InternshipLevel level;
    private String preferredMajor;
    private Date appOpenDate;
    private Date appCloseDate;
    private Status status;
    private String companyName;
    private CompanyRep companyRep;
    private int numSlots;
    private boolean visibility;

    public Internship(
            String title, 
            String description, 
            InternshipLevel level, 
            String preferredMajor, 
            Date appOpenDate, 
            Date appCloseDate, 
            Status status, 
            String companyName, 
            CompanyRep companyRep, 
            int numSlots, 
            boolean visibility) {
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
    }
    // Getter method for title
    public String getTitle() {
        return title;
    }
    // Getter method for description
    public String getDescription() {
        return description;
    }
    // Getter method for level
    public InternshipLevel getLevel() {
        return level;
    }

    // Getter method for preferredMajor
    public String getPreferredMajor() {
        return preferredMajor;
    }

    // Getter method for appOpenDate
    public Date getAppOpenDate() {
        return appOpenDate;
    }

    // Getter method for appCloseDate
    public Date getAppCloseDate() {
        return appCloseDate;
    }
    // Getter method for status
    public Status getStatus() {
        return status;
    }

    // Getter method for companyName
    public String getCompanyName() {
        return companyName;
    }

    // Getter method for companyRep
    public CompanyRep getCompanyRep() {
        return companyRep;
    }

    // Getter method for numSlots
    public int getNumSlots() {
        return numSlots;
    }

    // Getter method for visibility
    public boolean isVisibility() {
        return visibility;
    }

    // setter for visibility
    public void setVisible(boolean v) {
        this.visibility = v;
    }
}
