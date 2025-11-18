import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class CompanyRepSorter {
    public static List<CompanyRep> sort(List<CompanyRep> companyRepList, CompanyRepAttributes sortBy) {
		if (sortBy == null)
			return new ArrayList<>(companyRepList);

		Comparator<String> S = Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER);
		Comparator<CompanyRep> cmp = switch (sortBy) {
			case ID -> Comparator.comparing(CompanyRep::getId, S);
			case NAME -> Comparator.comparing(CompanyRep::getName, S);
			case DEPARTMENT -> Comparator.comparing(CompanyRep::getDepartment, S);
			case POSITION -> Comparator.comparing(CompanyRep::getPosition, S);
			case REPSTATUS -> Comparator.comparing(r -> r.getRepStatus() == null ? null : r.getRepStatus().name(), S);
			case INTERNSHIPADDED -> Comparator.comparingInt(CompanyRep::getInternshipCounter);
		};

		return companyRepList.stream().sorted(cmp).collect(Collectors.toList());
	}
}
