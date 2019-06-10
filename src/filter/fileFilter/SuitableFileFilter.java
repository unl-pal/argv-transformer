package filter.fileFilter;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import download.GitProject;
import sourceAnalysis.AnalyzedFile;

public class SuitableFileFilter {
	private ArrayList<File> spfSuitableFiles;
	private int spfSuitableMethods;
	private List<GitProject> gitProjects;

	public SuitableFileFilter(List<GitProject> gitProjects) {
		this.gitProjects = gitProjects;
		spfSuitableFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	public ArrayList<File> getSuitableFiles() {
		return spfSuitableFiles;
	}
	
	public int getSuitableMethodCount() {
		return spfSuitableMethods;
	}

	public void findSuitableFiles() {
		for (GitProject project : gitProjects) {
		//	project.collectFilesInProject();	
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
						// what exceptions happen here?
						continue;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
