package sourceAnalysis;

import java.io.File;
import java.util.HashMap;
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

  public String getFileInfo() {
    StringBuilder sb = new StringBuilder();

    sb.append("File: " + file.getName() + "\n");
    sb.append("Suitable Methods: " + suitableMethods.size() + "/" + analyzedMethods.size() + "\n");
    sb.append("Total Type Conditionals: " + getTotalConditionals() + "\n");
    sb.append("Total Type Operations: " + getTotalOperations() + "\n");
    for (HashMap.Entry<String, Integer> entry : getFileOpCounts().entrySet()) {
      sb.append("\t" + entry.getKey() + ": " + entry.getValue() + "\n");
    }
    sb.append("Methods:\n");
    for (AnalyzedMethod m : suitableMethods) {
      sb.append("\tMethod Name: " + m.getName() + "\n");
      sb.append("\tType Parameters: " + m.getTypeParameterCount() + "\n");
      sb.append("\tType Conditionals: " + m.getTypeConditionalCount() + "\n");
      sb.append("\tType Operations: " + m.getTypeOperationCount() + "\n");
      for (HashMap.Entry<String, Integer> entry : m.getOpCounts().entrySet()) {
        sb.append("\t\t" + entry.getKey() + ": " + entry.getValue() + "\n");
      }
      sb.append("\n");
    }

    return sb.toString();
  }

  public HashMap<String, Integer> getFileOpCounts() {
    HashMap<String, Integer> fileOpCounts = new HashMap<>();
    for (AnalyzedMethod m : suitableMethods) {
      HashMap<String, Integer> methodOpCounts = m.getOpCounts();
      for (HashMap.Entry<String, Integer> entry : methodOpCounts.entrySet()) {
        fileOpCounts.merge(entry.getKey(), entry.getValue(), Integer::sum);
      }
    }
    return fileOpCounts;
  }


  public int getTotalOperations() {
    int count = 0;
    for (AnalyzedMethod m : suitableMethods) {
      count += m.getTypeOperationCount();
    }
    return count;
  }

  public int getTotalConditionals() {
    int count = 0;
    for (AnalyzedMethod m : suitableMethods) {
      count += m.getTypeConditionalCount();
    }
    return count;
  }

  public String getSummary() {
    return file.getName() + "," + suitableMethods.size() + "," + getTotalOperations();
  }
}
