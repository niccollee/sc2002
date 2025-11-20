import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;
import java.util.stream.Collectors;

public class InternshipWithdrawalDbMgr {
    private List<InternshipWithdrawalApplicant> internshipWithdrawalList;
    private static InternshipWithdrawalDbMgr instance;
    private InternshipWithdrawalDbMgr() {
        internshipWithdrawalList = new ArrayList<InternshipWithdrawalApplicant>();
    }

    public static InternshipWithdrawalDbMgr getInstance() {
        if (instance == null) {
            instance = new InternshipWithdrawalDbMgr();
        }
        return instance;
    }
    // Getter methods for applicants
    public InternshipWithdrawalApplicant get(int id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getId() == id) {
                return i;
            }
        }
        return null;
    }

    public InternshipWithdrawalApplicant getFromStudentId(String id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getStudent().getId().equals(id)) {
                return i;
            }
        }
        return null;
    }
    // Add applicants based on student and internship. If student already inside, return false.
    // Otherwise add applicants to list and return true.
    public boolean add(Student student, Internship internship) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getStudent() == student) {
                return false;
            }
        }
        InternshipWithdrawalApplicant internshipWithdrawalApplicants = new InternshipWithdrawalApplicant(student, internship);
        internshipWithdrawalList.add(internshipWithdrawalApplicants);
        return true;
    }
    // Remove appplicants. Return true if applicants in list, then remove them.
    // False if applicants not in list.
    public boolean remove(int id) {
        for (InternshipWithdrawalApplicant i: internshipWithdrawalList) {
            if (i.getId() == id) {
                internshipWithdrawalList.remove(get(id));
                return true;
            }
        }
        return false;
    }
    // Sort based in attributes. 
    public List<InternshipWithdrawalApplicant> sort(InternshipWithdrawalApplicantsAttributes sortBy) {
        switch (sortBy) {
            case InternshipWithdrawalApplicantsAttributes.ID:
                return internshipWithdrawalList
                    .stream()
                    .sorted(Comparator.comparing(x -> x.getId()))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.STUDENT:
                return internshipWithdrawalList
                    .stream()   
                    .sorted(Comparator.comparing(x -> x.getStudent().getName()))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.INTERNSHIP:
                return internshipWithdrawalList
                    .stream()
                    .sorted(Comparator.comparing(c -> c.getInternship().getTitle()))
                    .collect(Collectors.toList());
        }
        return null;
    }
    // Filter based on attributes.
    // For student, args comapares with the name,
    // for internship, args compares with the title.
    public List<InternshipWithdrawalApplicant> filter(InternshipWithdrawalApplicantsAttributes filterBy, String args) {
        switch (filterBy) {
            case InternshipWithdrawalApplicantsAttributes.ID:
                return internshipWithdrawalList
                    .stream()
                    .filter(x -> ((Integer)x.getId()).toString().contains(args))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.STUDENT:
                return internshipWithdrawalList
                    .stream()   
                    .filter(x -> x.getStudent().getName().contains(args))
                    .collect(Collectors.toList());
            case InternshipWithdrawalApplicantsAttributes.INTERNSHIP:
                return internshipWithdrawalList
                    .stream()
                    .filter(c -> c.getInternship().getTitle().contains(args))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<InternshipWithdrawalApplicant> getAll(){
        return this.internshipWithdrawalList;
    }        
}
