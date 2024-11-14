package domain;

import java.sql.Date;
import java.util.HashSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

public class Applicant {

	private int applicantId;
	private String userName, password, firstName, lastName;
	private Date dateOfBirth;
	private String billingMethod;

	private Set<String> emailAddresses = new HashSet<String>();
	private Set<Address> addresses = new HashSet<Address>();
	private SortedSet<School> savedSchools = new TreeSet<School>();
	private Set<TestingScore> scores = new HashSet<TestingScore>();
}
