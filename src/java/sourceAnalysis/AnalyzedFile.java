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
	private Set<AnalyzedMethod> suitableMethods;
	
	public AnalyzedFile(File file) {
		this.file = file;
		path = file.getAbsolutePath();
		analyzedMethods = new HashSet<AnalyzedMethod>();
		suitableMethods = new HashSet<AnalyzedMethod>();
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
	
	public Set<AnalyzedMethod> getSuitableMethods(){
		return suitableMethods;
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
	
	public boolean isSuitable() {
		return suitableMethods.size() > 0;
	}
	
	public void addSuitableMethod(AnalyzedMethod suitableMethod) {
		suitableMethods.add(suitableMethod);
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
