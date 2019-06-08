package download;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;

import filter.projectFilter.ProjectFilter;

/**
 * Manages a list of GitProjects.
 * 
 * @author aminton
 *
 */
public class ProjectDatabase {
	private List<GitProject> projects;

	/**
	 * Create a new ProjectDatabase.
	 */
	public ProjectDatabase() {
		projects = new ArrayList<GitProject>();
	}
	
	private boolean parseBooleanInteger(String i) {
		if (i.equals("1")) {
			return true;
		} else if (i.equals("0")) {
			return false;
		} else {
			throw new NumberFormatException();
		}
	}
	
	private int parseStars(String i) {
		if (i.equals("None")) {
			return 0;
		} else {
			return Integer.parseUnsignedInt(i);
		}
	}
	
	/**
	 * Add all projects from a CSV file to this database. The CSV must have a
	 * header on the first line, with all of these fields:
	 * 
	 * repository,language,architecture,community,continuous_integration,
	 * documentation,history,issues,license,size,unit_test,stars,scorebased_org,
	 * randomforest_org,scorebased_utl,randomforest_utl
	 * 
	 * (The fields need not be in a specific order). This is the format of
	 * dataset.csv from RepoReapers.
	 * 
	 * @param r Reader that the CSV data can be read from.
	 * @throws IOException
	 */
	public void addFromCSV(Reader r) throws IOException {
		CSVParser records = CSVFormat.DEFAULT.withFirstRecordAsHeader().parse(r);
		Map<String,Integer> headerMap = records.getHeaderMap();
		
		// Use integer indices in the loop for speed
		int repository = headerMap.get("repository");
		int language = headerMap.get("language");
		int architecture = headerMap.get("architecture");
		int community = headerMap.get("community");
		int continuous_integration = headerMap.get("continuous_integration");
		int documentation = headerMap.get("documentation");
		int history = headerMap.get("history");
		int issues = headerMap.get("issues");
		int license = headerMap.get("license");
		int size = headerMap.get("size");
		int unit_test = headerMap.get("unit_test");
		int stars = headerMap.get("stars");
		int scorebased_org = headerMap.get("scorebased_org");
		int randomforest_org = headerMap.get("randomforest_org");
		int scorebased_utl = headerMap.get("scorebased_utl");
		int randomforest_utl = headerMap.get("randomforest_utl");
		
		for (CSVRecord p : records) {
			projects.add(new GitProject(
					p.get(repository),
					p.get(language),
					Double.parseDouble(p.get(architecture)),
					Integer.parseUnsignedInt(p.get(community)),
					parseBooleanInteger(p.get(continuous_integration)),
					Double.parseDouble(p.get(documentation)),
					Double.parseDouble(p.get(history)),
					Double.parseDouble(p.get(issues)),
					parseBooleanInteger(p.get(license)),
					Integer.parseUnsignedInt(p.get(size)),
					Double.parseDouble(p.get(unit_test)),
					ProjectState.UNKNOWN,
					parseStars(p.get(stars)),
					parseBooleanInteger(p.get(scorebased_org)),
					parseBooleanInteger(p.get(randomforest_org)),
					parseBooleanInteger(p.get(scorebased_utl)),
					parseBooleanInteger(p.get(randomforest_utl))
					));
		}
	}
	
	/**
	 * Get collection of all the GitProjects in this database that satisfy a
	 * filter.
	 * 
	 * @param filter Filter used to determine which projects to include.
	 * @return A collection of all the projects accepted by the filter.
	 */
	public List<GitProject> getFilteredList(ProjectFilter filter) {
		return filter.filter(projects);
	}
	
	/**
	 * Get how many projects are in this database.
	 * 
	 * @return the number of projects in this ProjectDatabase
	 */
	public int getProjectCount() {
		return projects.size();
	}
}
