package sourceAnalysis;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Represents a class file with a collection of AnalyzedMethods. 
 * Used to track how many methods are suitable for symbolic execution.
 * 
 * @author mariapaquin
 * 
 */
public class AnalyzedFile {

	private File file;
	private String path;
	private Set<AnalyzedMethod> analyzedMethods;	
	
	public AnalyzedFile(File file) {
		this.file = file;
		path = file.getAbsolutePath();
		analyzedMethods = new HashSet<AnalyzedMethod>();
	}

	public void addMethod(AnalyzedMethod am) {
		analyzedMethods.add(am);
	}
	
	public Set<AnalyzedMethod> getAnalyzedMethods() {
		return analyzedMethods;
	}
	
	public void setAnalyzedMethods(Set<AnalyzedMethod> analyzedMethods) {
		this.analyzedMethods = analyzedMethods;
	}
	
	public int getSpfSuitableMethodCount() {
		int count = 0;
		for(AnalyzedMethod am : analyzedMethods) {
			if(am.isSymbolicSuitable()) {
				count++;
			}
		}
		return count;
	}
	
	public String getPath() {
		return path;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isSymbolicSuitable() {
		return (getSpfSuitableMethodCount() > 0);
	}
}
