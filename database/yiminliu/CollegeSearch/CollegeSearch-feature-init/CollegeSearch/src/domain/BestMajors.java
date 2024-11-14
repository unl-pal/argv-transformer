package domain;

import java.util.HashSet;
import java.util.Set;

public class BestMajors {

	private int majorId;
	private String majorName;
	private int majorRank;

	private Set<BestMajorSchools> bestMajoredSchools = new HashSet<BestMajorSchools>();

	// public void addABestSchool(School aSchool) {
	// bestMajorSchools.add(aSchool);
	// aSchool.getBestMajorSchools().add(this);
	// }

}
