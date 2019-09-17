package filter.file;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import download.GitProject;
import sourceAnalysis.AnalyzedFile;

/**
 * Class to find files suitable for symbolic execution.
 * (i.e. classes that contain methods suitable for symbolic execution).
 * 
 * @author mariapaquin
 *
 */
public class FileFilter {
	private ArrayList<File> spfSuitableFiles;
	private ArrayList<File> javaFiles;
	private File database;
	private int spfSuitableMethods;
	private List<GitProject> gitProjects;

	public FileFilter(List<GitProject> gitProjects) {
		this.gitProjects = gitProjects;
		spfSuitableFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	public FileFilter(File database) {
		this.database = database;
		spfSuitableFiles = new ArrayList<File>();
		javaFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	public ArrayList<File> getSuitableFiles() {
		return spfSuitableFiles;
	}

	public int getSuitableMethodCount() {
		return spfSuitableMethods;
	}
	
	public void collectJavaFiles() {
		addDirectory(database);
	}
	
	public void addDirectory(File directory) {
		String path = directory.getAbsolutePath();
		
		for (String filename : directory.list()) {
			File f = new File(path+"/"+filename);
			if (f.isDirectory()) {
				// Recursively gather files in subdirectories
				addDirectory(f);
			} else {
				int dotIndex = filename.indexOf(".");
				if (dotIndex != -1 && filename.substring(dotIndex).equalsIgnoreCase(".java")) {
					javaFiles.add(f);
				}
			}
		}
	}
	
	public void collectSymbolicSuitableFiles() {
		for(File file: javaFiles) {
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
