import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class InternshipApplicationDbMgr {
    private List<InternshipApplication> internshipApplicationList;
    private int count = 0;

    public InternshipApplicationDbMgr() {
        internshipApplicationList = new ArrayList<InternshipApplication>();
    }

    public List<InternshipApplication> showAll() {
        return internshipApplicationList;
    }

    public InternshipApplication get(Internship internship) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internship.getId()) {
                return i;
            }
        }
        return null;
    }

    public InternshipApplication get(int idx) {
        return internshipApplicationList.get(idx);
    }

    public boolean add(InternshipApplication internshipApplication) {
        if (count < 3) {
            return false;
        }
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internshipApplication.getInternship().getId()) {
                return false;
            }
        }
        count++;
        internshipApplicationList.add(internshipApplication);
        return true;   
    }

    public boolean remove(InternshipApplication internshipApplication) {
        Iterator<InternshipApplication> iterator = internshipApplicationList.iterator();
        while (iterator.hasNext()) {
            InternshipApplication i = iterator.next();
            if (i.getInternship().getId() == internshipApplication.getInternship().getId()) {
                iterator.remove();
                return true;
            }
        }
        return false;

    }

    public boolean contains(InternshipApplication internshipApplication) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internshipApplication.getInternship().getId() &&
                i.getInternshipApplicationStatus() == internshipApplication.getInternshipApplicationStatus()) {
                return true;
            }
        }
        return false;
    }

    public boolean contains(Internship internship) {
        for (InternshipApplication i: internshipApplicationList) {
            if (i.getInternship().getId() == internship.getId()) {
                return true;
            }
        }
        return false;
    }
}
