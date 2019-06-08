package sourceAnalysis;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

/**
 * Used to track AnalyzedMethods in each file.
 * Keep track of how many methods (if any)
 * are suitable for symbolic execution.
 *
 * Also track here whether the file has a 
 * main method. 
 */
public class AnalyzedFile {
	

	private File file;
	private String path;
	private Set<AnalyzedMethod> analyzedMethods;	
	private AnalyzedMethod mainMethod;
	
	private boolean hasMain;
	
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
	
	public boolean isHasMain() {
		return hasMain;
	}
	
	public void setHasMain(boolean hasMain) {
		this.hasMain = hasMain;
	}
	
	public AnalyzedMethod getMainMethod() {
		return mainMethod;
	}
	
	public void setMainMethod(AnalyzedMethod mainMethod) {
		this.mainMethod = mainMethod;
	}
	
	public File getFile() {
		return file;
	}
	
	public boolean isSymbolicSuitable() {
		return (getSpfSuitableMethodCount() > 0);
	}

}
