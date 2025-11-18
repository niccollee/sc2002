import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyRepFilter {
    public static List<CompanyRep> filter(List<CompanyRep> companyRepList, CompanyRepAttributes filterBy, String args) {
        if (filterBy == null)
			return new ArrayList<>(companyRepList);

		return switch (filterBy) {
			case ID -> companyRepList.stream()
					.filter(rep -> rep.getId() != null && args != null && rep.getId().equalsIgnoreCase(args))
					.collect(Collectors.toList());

			case NAME -> companyRepList.stream()
					.filter(rep -> rep.getName() != null && args != null && rep.getName().equalsIgnoreCase(args))
					.collect(Collectors.toList());

			case DEPARTMENT -> companyRepList.stream()
					.filter(rep -> rep.getDepartment() != null && args != null
							&& rep.getDepartment().equalsIgnoreCase(args))
					.collect(Collectors.toList());

			case POSITION -> companyRepList.stream()
					.filter(rep -> rep.getPosition() != null && args != null
							&& rep.getPosition().equalsIgnoreCase(args))
					.collect(Collectors.toList());

			case REPSTATUS -> {
				CompanyRepStatus target = null;
				if (args != null) {
					try {
						target = CompanyRepStatus.valueOf(args.trim().toUpperCase());
					} catch (Exception ignored) {
					}
				}
				CompanyRepStatus t = target;
				yield companyRepList.stream()
						.filter(rep -> rep.getRepStatus() == t)
						.collect(Collectors.toList());
			}

			case INTERNSHIPADDED -> companyRepList.stream()
					.filter(rep -> rep.getInternshipCounter() == Integer.parseInt(args))
					.collect(Collectors.toList());
        };
    }
}
