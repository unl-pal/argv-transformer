package filter.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import download.GitProject;
import sourceAnalysis.AnalyzedFile;

/**
 * Class to find java files suitable for symbolic execution.
 *
 * @author mariapaquin
 */
public class FileFilter {
	private ArrayList<File> spfSuitableFiles;
	private ArrayList<File> javaFiles;
	private File database;
	private int spfSuitableMethods;
	private List<GitProject> gitProjects;

	/**
	 * Create a new FileFilter
	 * @param gitProjects A list of GitHub projects
	 */
	public FileFilter(List<GitProject> gitProjects) {
		this.gitProjects = gitProjects;
		spfSuitableFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	/**
	 * Create a new FileFilter
	 * @param database A directory of GitHub projects
	 */
	public FileFilter(File database) {
		this.database = database;
		spfSuitableFiles = new ArrayList<File>();
		javaFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	/**
	 * Getter for suitable file count
	 * @return the number of SPF suitable files
	 */
	public ArrayList<File> getSuitableFiles() {
		return spfSuitableFiles;
	}

	/**
	 * Getter for suitable method count
	 * @return the number of SPF suitable methods
	 */
	public int getSuitableMethodCount() {
		return spfSuitableMethods;
	}

	/**
	 * Collect all the java files in the project and add them 
	 * to the list javaFiles.
	 */
	public void collectJavaFiles() {
		try {
			Files.find(Paths.get(database.getAbsolutePath()), 999,
					(p, bfa) -> p.toString().endsWith(".java"))
				.forEach(p -> javaFiles.add(p.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Use the SymbolicSuitableMethodFinder to search the javaFiles list for files 
	 * suitable for SPF. Add suitable files to the list spfSuitableFiles.
	 */
	public void collectSymbolicSuitableFiles() {
		for (File file: javaFiles) {
			try {
				SymbolicSuitableMethodFinder finder = new SymbolicSuitableMethodFinder(file);
				finder.analyze();
				AnalyzedFile af = finder.getAnalyzedFile();
				try {
					spfSuitableMethods += af.getSpfSuitableMethodCount();
					if (af.isSymbolicSuitable()) {
						spfSuitableFiles.add(file);
					}
				} catch (Exception e) {
					continue;
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Use the SymbolicSuitableMethodFinder to search the gitProjects list for files 
	 * suitable for SPF. Add suitable files to the list spfSuitableFiles.
	 */
	public void collectSuitableFilesInProjectList() {
		for (GitProject project : gitProjects) {
			ArrayList<File> files = project.getFiles();
			for (File file : files) {
				try {
					SymbolicSuitableMethodFinder finder = new SymbolicSuitableMethodFinder(file);
					finder.analyze();
					AnalyzedFile af = finder.getAnalyzedFile();
					try {
						spfSuitableMethods += af.getSpfSuitableMethodCount();
						if (af.isSymbolicSuitable()) {
							spfSuitableFiles.add(file);
						}
					} catch (Exception e) {
						continue;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
