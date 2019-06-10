package download;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import logging.Logger;

/**
 * Class representing a Git project with its metadata as gathered by RepoReapers.
 * 
 * @author aminton
 *
 */
public class GitProject {
	private final String repository;
	private final String language;
	private final double architecture;
	private final int community;
	private final boolean hasContinuousIntegration;
	private final double documentation;
	private final double history;
	private final double issues;
	private final boolean hasLicense;
	private final int linesOfCode;
	private final double unitTestCoverage;
	private final ProjectState state;
	private final int stars;
	private final boolean scoreBasedOrg;
	private final boolean randomForestOrg;
	private final boolean scoreBasedUtl;
	private final boolean randomForestUtl;
	
	private ArrayList<File> files;
	private File projectDir;
	
	/**
	 * Create a new GitProject.
	 * 
	 * @param repository URL of the project's repository.
	 * @param language Language the project is in, e.g. "Java"
	 * @param architecture The repository's architecture score
	 * @param community The repository's community score
	 * @param hasContinuousIntegration True if the project uses CI testing.
	 * @param documentation The repository's documentation score
	 * @param history The repository's history score
	 * @param issues The repository's issues score
	 * @param hasLicense True if the project specifies a license.
	 * @param linesOfCode Number of lines of code in the project.
	 * @param unitTestCoverage Unit test coverage as a fraction.
	 * @param state State of the project (dormant, unknown...).
	 * @param stars Number of stars on that the project has on GitHub.
	 * @param scoreBasedOrg
	 * @param randomForestOrg
	 * @param scoreBasedUtl
	 * @param randomForestUtl
	 * @throws IOException 
	 */
	public GitProject(String repository,
			String language,
			double architecture,
			int community,
			boolean hasContinuousIntegration,
			double documentation,
			double history,
			double issues,
			boolean hasLicense,
			int linesOfCode,
			double unitTestCoverage,
			ProjectState state,
			int stars,
			boolean scoreBasedOrg,
			boolean randomForestOrg,
			boolean scoreBasedUtl,
			boolean randomForestUtl) throws IOException {
		this.repository = repository;
		this.language = language;
		this.architecture = architecture;
		this.community = community;
		this.hasContinuousIntegration = hasContinuousIntegration;
		this.documentation = documentation;
		this.history = history;
		this.issues = issues;
		this.hasLicense = hasLicense;
		this.linesOfCode = linesOfCode;
		this.unitTestCoverage = unitTestCoverage;
		this.state = state;
		this.stars = stars;
		this.scoreBasedOrg = scoreBasedOrg;
		this.randomForestOrg = randomForestOrg;
		this.scoreBasedUtl = scoreBasedUtl;
		this.randomForestUtl = randomForestUtl;
		
		files = new ArrayList<File>();
	}

	/**
	 * @return The URL of the repository.
	 */
	public String getRepository() {
		return repository;
	}

	/**
	 * @return The language the project is written in.
	 */
	public String getLanguage() {
		return language;
	}

	/**
	 * @return The architecture score of the repository.
	 */
	public double getArchitecture() {
		return architecture;
	}

	/**
	 * @return The community score of the repository.
	 */
	public int getCommunity() {
		return community;
	}

	/**
	 * @return True if the project uses continuous integration testing, false
	 * 	otherwise.
	 */
	public boolean getHasContinuousIntegration() {
		return hasContinuousIntegration;
	}

	/**
	 * @return The documentation score of the repository.
	 */
	public double getDocumentation() {
		return documentation;
	}

	/**
	 * @return The history score of the repository.
	 */
	public double getHistory() {
		return history;
	}

	/**
	 * @return The issues score of the repository.
	 */
	public double getIssues() {
		return issues;
	}

	/**
	 * @return True if the project specifies a license, false otherwise.
	 */
	public boolean getHasLicense() {
		return hasLicense;
	}

	/**
	 * @return Number of lines of code in the project.
	 */
	public int getLinesOfCode() {
		return linesOfCode;
	}

	/**
	 * @return Unit test coverage of the project as a fraction between 0 and 1.
	 */
	public double getUnitTestCoverage() {
		return unitTestCoverage;
	}

	/**
	 * @return The state of the project.
	 */
	public ProjectState getState() {
		return state;
	}

	/**
	 * @return The number of GitHub stars the project has.
	 */
	public int getStars() {
		return stars;
	}

	/**
	 * @return the scoreBasedOrg
	 */
	public boolean getScoreBasedOrg() {
		return scoreBasedOrg;
	}

	/**
	 * @return the randomForestOrg
	 */
	public boolean getRandomForestOrg() {
		return randomForestOrg;
	}

	/**
	 * @return the scoreBasedUtl
	 */
	public boolean getScoreBasedUtl() {
		return scoreBasedUtl;
	}

	/**
	 * @return the randomForestUtl
	 */
	public boolean getRandomForestUtl() {
		return randomForestUtl;
	}
	
	/**
	 * Get the GitHub URL of this project's archive.
	 * 
	 * @param ssl True for HTTPS, false for HTTP
	 * @return URL to a zip file containing the current master branch.
	 * @throws MalformedURLException
	 */
	public URL getArchiveURL(boolean ssl) throws MalformedURLException {
		try {
			if (ssl) {
				return new URL("https://github.com/"+getRepository()+"/archive/master.zip");
			} else {
				return new URL("http://github.com/"+getRepository()+"/archive/master.zip");
			}
		} catch (MalformedURLException e) {
			Logger.errorLogger.enterContext("GITPROJECT");
			Logger.errorLogger.logln("Could not construct URL for repository "+getRepository(),0);
			Logger.errorLogger.exitContext("GITPROJECT");
			throw e;
		}
	}
	
	/**
	 * Get the GitHub URL of this project's archive.
	 * 
	 * @return URL to a zip file containing the current master branch.
	 * @throws MalformedURLException
	 */
	public URL getArchiveURL() throws MalformedURLException {
		return getArchiveURL(true);
	}
	
	/**
	 * Get a list of all java files in the project directory and sub directories.
	 * @return list of files
	 */
	public ArrayList<File> getFiles(){
		return files;
	}
	
	/**
	 * Download a repository's current master version to the given destination.
	 * This does not do a full clone of the repository; it only downloads the
	 * .zip for the current state of master and extracts that.
	 * 
	 * @param destination destination folder for the project
	 * @throws IOException
	 */
	public void downloadTo(File destination) throws IOException {

		if (!destination.isDirectory()) {
			throw new IOException("Destination location "+destination.getPath()+" is not a directory");
		}
		if (!destination.canWrite()) {
			throw new IOException("Destination location "+destination.getPath()+" cannot be written to");
		}

		URL url = getArchiveURL();

		Logger.defaultLogger.enterContext("DOWNLOAD");
		try {
			Logger.defaultLogger.logln("Downloading "+url.toString(),1);
			File tempZip = Files.createTempFile("gitgrabber", null).toFile();
			tempZip.deleteOnExit();
			FileOutputStream out = new FileOutputStream(tempZip);
			FileChannel outc = out.getChannel();
			ReadableByteChannel in = Channels.newChannel(url.openStream());
			outc.transferFrom(in, 0, Long.MAX_VALUE);
			outc.close();
			out.close();
			Logger.defaultLogger.logln("Finished downloading "+url.toString()+" to "+tempZip.getPath(),1);
			
			// Now unzip the archive
			FileInputStream zipIn = new FileInputStream(tempZip);
			ZipInputStream zis = new ZipInputStream(zipIn);
			ZipEntry ze;
			byte[] buff = new byte[65536];
			while ((ze = zis.getNextEntry()) != null) {
				String destinationFilename = destination.getAbsolutePath()+"/"+ze.getName();
				File destinationFile = new File(destinationFilename);
				// Create the correct directory structure...
				if (destinationFilename.endsWith("/")) {
					destinationFile.mkdirs();
				} else {
					File parentDir = destinationFile.getParentFile();
					if (parentDir != null) {
						parentDir.mkdirs();
					}
					
					// Now actually unzip the file
					destinationFile.createNewFile();
					int length;
					FileOutputStream zout = new FileOutputStream(destinationFile);
					while ((length = zis.read(buff)) > 0) {
						zout.write(buff, 0, length);
					}
					zout.close();
				}
				Logger.defaultLogger.logln("Decompressed "+destinationFile.getAbsolutePath(),3);
			}
			zipIn.close();
			zis.close();
			
			Logger.defaultLogger.logln("Finished decompressing "+url.toString()+" to "+destination.getPath(),1);
			// Done with the temporary file
			tempZip.delete();
		} catch (Exception e) {
			throw e;
		} finally {
			Logger.defaultLogger.exitContext("DOWNLOAD");
		}
	}
	
	public void setProjectDir(File destinationDir) {
		projectDir = destinationDir;
		
	}
	
	public void collectFilesInProject() {
		addDirectory(projectDir);
	}
	
	public void addFile(File f) {
		files.add(f);
	}
	
	public void addDirectory(File directory) {
		
		String path = directory.getAbsolutePath();
		
		for (String filename : directory.list()) {
			File f = new File(path+"/"+filename);
			if (f.isDirectory()) {
				// Recursively gather files in subdirectories
				addDirectory(f);
			} else {
				// XXX: It's possible to have Java source files that don't end
				// in .java, and it's obviously possible to have files ending in
				// .java that aren't Java source files. But since Java source
				// files don't have a special header or anything, this is about
				// the best we can do...
				int dotIndex = filename.indexOf(".");
				if (dotIndex != -1 && filename.substring(dotIndex).equalsIgnoreCase(".java")) {
					addFile(f);
				}
			}
		}
	}
	
}
