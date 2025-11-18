import java.time.LocalDate;

public class Test {
    public static void main(String[] args) {
        LocalDate d1 = LocalDate.now();
        CompanyRep c1 = new CompanyRep(
            "abc@ntu.edu.sg",
            "ABC Company",
            "HR",
            "Manager",
            CompanyRepStatus.APPROVED
        );
        Student s1 = new Student("null", "null", "null", 0, "null");
        InternshipDbMgr internshipDbMgr = InternshipDbMgr.getInstance();
        StudentDbMgr studentDbMgr = StudentDbMgr.getInstance();
        InternshipWithdrawalDbMgr internshipWithdrawalDbMgr = InternshipWithdrawalDbMgr.getInstance();
        internshipDbMgr.add(
            "null", 
            "null", 
            InternshipLevel.ADVANCE, 
            "null", 
            d1,
            d1, 
            Status.APPROVED, 
            "null", 
            c1, 
            0, 
            true);
        for (Student i: studentDbMgr.get()) {
            System.out.println(i.getId());
        }
        studentDbMgr.add(s1);
        new StudentUI(studentDbMgr, internshipDbMgr, internshipWithdrawalDbMgr);        
    }
}
