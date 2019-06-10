package download;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import filter.projectFilter.JavaProjectFilter;
import logging.Logger;

/**
 * Class for downloading GitHub projects that meet filter specifications.
 * 
 *  @author mariapaquin
 */
public class Downloader {
	
	private String filename;
	private List<GitProject> suitableGitProjects;
	private List<GitProject> downloadedGitProjects;
	private ProjectDatabase database;
	
	public Downloader(String filename) {
		this.filename = filename;
		downloadedGitProjects = new ArrayList<GitProject>();
	}
	
	public void createProjectDatabase() {
		database = new ProjectDatabase();
		FileReader in = null;

		try {
			in = new FileReader(filename);
		} catch (FileNotFoundException e) {
			
			System.err.println("File not found: "+filename);
			System.exit(1);
		}

		try {
			database.addFromCSV(in);
		} catch (IOException e) {
			System.err.println("IOException encountered while parsing records, aborting.");
			System.exit(1);
		}
	}

	public void filterProjects(int minLoc, int maxLoc) {
		JavaProjectFilter filter = new JavaProjectFilter(minLoc,maxLoc,0);
		suitableGitProjects = database.getFilteredList(filter);		
	}


	/**
	 * Downloads each project to a newly created or existing 
	 * directory with the path projectDirPath. 
	 * 
	 * @param projectCount
	 * @param projectDirPath
	 * @throws IOException 
	 */
	public void downloadProjects(int projectCount, String projectDirPath) throws IOException {
		FileWriter csvWriter = new FileWriter("filtered-dataset.csv");  

		File projectDir = new File(projectDirPath);
		
		if (projectDir.exists()) {
			if (!projectDir.isDirectory()) {
				System.err.println("ERROR: " + projectDirPath + " exists but is not a directory. Aborting.");
				System.exit(1);
			}
		} else {
			projectDir.mkdir();
		}
		
		if(suitableGitProjects.size() < projectCount) {
			System.err.println("Result set smaller than user specified project count. Aborting.");
			System.exit(1);
		}

		for (int i = 0; i < projectCount; i++) {
			GitProject p = suitableGitProjects.get(i);
			Logger.defaultLogger.log("Project " + (i) + ": ", 0);
			boolean download = true;
			File destinationDir = new File(projectDir.getPath() + File.separator + p.getRepository());
			p.setProjectDir(destinationDir);
			if (destinationDir.exists()) {
				if (destinationDir.isDirectory()) {
					download = false;
				} else {
					System.err.println(
							"ERROR: " + destinationDir.getPath() + " exists but is not a directory. Aborting.");
					System.exit(1);
				}
			} else {
				destinationDir.mkdirs();
			}
			downloadedGitProjects.add(p);

			csvWriter.append(p.getArchiveURL().toString() + "\n");
			if (download) {
				try {
					p.downloadTo(destinationDir);
				} catch (IOException e) {
					Logger.errorLogger.logln(p + " does not exist! Skipping.", 0);
					downloadedGitProjects.remove(p);
					continue;
				}
			}
			
		}
		
		csvWriter.flush();
	}
	
	public List<GitProject> getDownloadedGitProjects(){
		return downloadedGitProjects;
	}
}
