package domain;

import java.util.Comparator;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

public class SchoolBbackup {

	private Long schoolId;
	private String name, type, category, setting;
	private String streetAddress, city, state, zipCode, location, country;
	private String phoneNumber, emailAddress;
	private int tuitionAndFee, roomAndBoard, appplicationFee, totalCost;
	private int toefl, sat1Min, sat1Up, numOfSat2, numOfRecomLetter;
	private int overallRank, selectivityRank, reputationScore, acceptRate,
			facultyStudentRatio;
	private String academicCalendar, internationalAid, website;
	private int numOfStudents, foreignPercent, asianPercent;
	private Date lastModified, applicationDeadline;
	private Set<Long> bestMajors = new HashSet<Long>();

	class SchoolComparator implements Comparator {
	}

}
