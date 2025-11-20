import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Provides filtering operations for lists of {@link CompanyRep}.
 * Supports filtering by different {@link CompanyRepAttributes} such as
 * ID, name, department, position, representative status and number of internships added.
 */
public class CompanyRepFilter {

    /**
     * Filters the given list of company representatives based on the specified attribute
     * and filter argument.
     * If {@code filterBy} is {@code null}, this method returns a shallow copy of the
     * original list without applying any filtering.
     *
     * @param companyRepList the list of {@link CompanyRep} to filter
     * @param filterBy       the attribute to filter on; if {@code null}, all items are returned
     * @param args           the filter value as a string, whose meaning depends on {@code filterBy}
     * @return a new {@link List} containing the representatives that match the filter condition
     */
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
