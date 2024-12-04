package domain;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SchoolOld {

	private Long schoolId;

	private String name, website;

	private Address address;

	private Contact adminContact;

	private SchoolExpense expense;

	private SchoolRank rank;

	private SchoolFact schoolFact;

	private SchoolRequirement requirement;

	private SchoolStudentBody studentBody;

	private Set<Long> bestMajors = new HashSet<Long>();

	private Date lastModified;

	class SchoolComparator implements Comparator {

	}
}
