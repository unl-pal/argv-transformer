package filter.project;

import download.GitProject;
import download.ProjectState;

/**
 * Selects projects that are potentially suitable and interesting for automated
 * use of Java Pathfinder.
 * 
 * This means:
 * - Language is Java
 * - Project is not dormant
 * - Project does not have too few or too many lines of code
 * - Depending on how large of a selection is wanted, may require a minimum
 *   number of GitHub stars
 * 
 * @author aminton
 *
 */
public class JavaProjectFilter extends ProjectFilter {
	private final int minLinesOfCode;
	private final int maxLinesOfCode;
	private final int minStars;

	/**
	 * Create a new filter.
	 * 
	 * @param minLinesOfCode Minimum number of lines of code for projects.
	 * @param maxLinesOfCode Maximum number of lines of code for projects.
	 * @param minStars Minimum number of stars for projects.
	 */
	public JavaProjectFilter(int minLinesOfCode, int maxLinesOfCode, int minStars) {
		this.minLinesOfCode = minLinesOfCode;
		this.maxLinesOfCode = maxLinesOfCode;
		this.minStars = minStars;
	}
	
	/**
	 * Determine whether a GitProject object satisfies the filter criteria.
	 * 
	 * @param p Project to check against the filter
	 * @return true if the project satisfies the filter, false if it does not
	 */
	public boolean filter(GitProject p) {
		if (p.getState() != ProjectState.DORMANT &&
				p.getStars() >= minStars &&
				p.getLinesOfCode() >= minLinesOfCode &&
				p.getLinesOfCode() <= maxLinesOfCode &&
				p.getLanguage().equals("Java")) {
			return true;
		} else {
			return false;
		}
	}
}