import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;
import java.util.stream.Collectors;

public class InternshipFilter {
        // Filter based on attributes, based on args
    public static List<Internship> filter(List<Internship> internshipList, InternshipAttributes filterBy, String args) {
        return switch(filterBy) {
            case ID -> internshipList.stream()
                .filter(x -> x.getId() == Integer.parseInt(args.toLowerCase()))
                .collect(Collectors.toList());
            case TITLE -> internshipList.stream()
                .filter(x -> x.getTitle().toLowerCase().contains(args.toLowerCase()))
                .collect(Collectors.toList());
            case PREFERREDMAJOR -> internshipList.stream()
                .filter(x -> x.getPreferredMajor().toLowerCase().contains(args.toLowerCase()))
                .collect(Collectors.toList());
            case LEVEL -> {
                InternshipLevel level;
                try {
                    level = InternshipLevel.valueOf(args.trim().toUpperCase());
                } catch (Exception e) {
                    System.err.println("Invalid InternshipLevel!");
                    yield new ArrayList<Internship>();
                }
                yield internshipList.stream()
                    .filter(x -> x.getLevel() == level)
                    .collect(Collectors.toList());
            }
            case STATUS -> {
                Status status;
                try {
                    status = Status.valueOf(args.trim().toUpperCase());
                } catch (Exception e) {
                    System.err.println("Invalid Status!");
                    yield new ArrayList<Internship>();
                }
                yield internshipList.stream()
                    .filter(x -> x.getStatus() == status)
                    .collect(Collectors.toList());
            }
            case COMPANYNAME -> internshipList.stream() 
                .filter(x -> x.getCompanyName().toLowerCase().contains(args.toLowerCase()))
                .collect(Collectors.toList());
            case NUMSLOT -> internshipList.stream()
                .filter(x -> x.getNumSlots() == Integer.parseInt(args))
                .collect(Collectors.toList());
            case VISIBILITY -> internshipList.stream()
                .filter(x -> x.getVisibility() == Boolean.parseBoolean(args))
                .collect(Collectors.toList());
            default -> new ArrayList<Internship>();
        };
    }

    public static List<Internship> filter(List<Internship> internshipList, InternshipAttributes filterBy, LocalDate date, boolean before) {
        switch (filterBy) {
            case InternshipAttributes.APPCLOSEDATE:
                if (before) {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppCloseDate().isBefore(date) ||
                            x.getAppCloseDate() == date))
                        .collect(Collectors.toList());
                } else {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppCloseDate().isAfter(date) ||
                            x.getAppCloseDate() == date))
                        .collect(Collectors.toList());
                }
            case InternshipAttributes.APPOPENDATE:
                if (before) {
                    return internshipList  
                        .stream()
                        .filter(x -> (
                            x.getAppOpenDate().isBefore(date) ||
                            x.getAppOpenDate() == date))
                        .collect(Collectors.toList());
                } else {
                    return internshipList
                        .stream()
                        .filter(x -> (
                            x.getAppOpenDate().isAfter(date) ||
                            x.getAppOpenDate() == date))
                        .collect(Collectors.toList());
                }
            default:
                return null;
        }
    }

}
