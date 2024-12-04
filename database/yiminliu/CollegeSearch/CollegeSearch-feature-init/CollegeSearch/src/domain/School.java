package domain;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class School {

	private Long schoolId;
	private String name;
	private Set<String> alternativeNames;
	private String website;
	private Date lastModified;

	private Set<BestMajors> bestMajors = new HashSet<BestMajors>();

	private Address schoolAddress;
	private Contact schoolContact;
	private SchoolExpense schoolExpense;
	private SchoolFact schoolFact;
	private SchoolRank schoolRank;
	private SchoolRequirement schoolRequirement;
	private SchoolStudentBody schoolStudentBody;

	class SchoolComparator implements Comparator {
	}

}
