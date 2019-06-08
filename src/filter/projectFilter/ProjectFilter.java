package filter.projectFilter;

import java.util.List;

import download.GitProject;

import java.util.ArrayList;

/**
 * A filter object for GitProjects.
 * 
 * @author aminton
 *
 */
public abstract class ProjectFilter {
	/**
	 * The filter function should return true to "accept" the project and false
	 * to "reject" it.
	 * 
	 * @param p The GitProject being checked
	 * @return true if the project satisfies the filter's conditions, otherwise
	 * 	false
	 */
	public abstract boolean filter(GitProject p);
	
	/**
	 * Filters multiple GitProjects.
	 * 
	 * @param ps A collection of projects to test.
	 * @return A collection of only the projects that satisfied the filter.
	 */
	public List<GitProject> filter(List<GitProject> ps) {
		List<GitProject> rval = new ArrayList<GitProject>();
		for (GitProject p : ps) {
			if (filter(p)) rval.add(p);
		}
		return rval;
	}
}
