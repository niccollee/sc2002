import java.time.LocalDate;

public class Internship {
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

    public Internship(
            int id,
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
        this.id = id;
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
    // Getter methods
    public int getId() {return id;}
    public String getTitle() {return title;}
    public String getDescription() {return description;}
    public InternshipLevel getLevel() {return level;}
    public String getPreferredMajor() {return preferredMajor;}
    public LocalDate getAppOpenDate() {return appOpenDate;}
    public LocalDate getAppCloseDate() {return appCloseDate;}
    public Status getStatus() {return status;}
    public String getCompanyName() {return companyName;}
    public CompanyRep getCompanyRep() {return companyRep;}
    public int getNumSlots() {return numSlots;}
    public boolean getVisibility() {return visibility;}
    public int getConfirmedSlots() {return confirmedSlots;}

    // Setter methods
    public void setStatus(Status status){
        this.status = status;
    }

    // setter for visibility
    public void setVisibility(boolean v) {
        this.visibility = v;
    }

    public void incrementConfirmedSlots() {
        if (confirmedSlots < numSlots) {
            confirmedSlots++:
            if (confirmedSlots >= numSlots) {
                this.status = Status.FILLED;
            }
        }
    }
}
