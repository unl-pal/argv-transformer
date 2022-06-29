package jpf;

import java.io.File;
import org.apache.bcel.classfile.Method;
/**
 * Class used to collect method info for Green solver evaluation.
 * 
 * @author mariapaquin
 *
 */
public class MethodUnderTest {
	private File file;
	private ProgramUnderTest sut;
	private String projectName;
	private String packageName;
	private String fullClassName;
	private String className;
	private String fullMethodName;
	private String methodSig;
	private Method method;
	private int numIntArgs;
	private boolean hasLoops;
	
	public File getFile() {
		return file;
	}
	
	public void setFile(File file) {
		this.file = file;
	}
	
	public ProgramUnderTest getProgramUnderTest() {
		return sut;
	}
	
	public void setProgramUnderTest(ProgramUnderTest sut) {
		this.sut = sut;
	}
	
	public String getProjectName() {
		return projectName;
	}
	
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	
	public String getPackageName() {
		return packageName;
	}
	
	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}
	
	public String getFullClassName() {
		return fullClassName;
	}
	public void setFullClassName(String fullClassName) {
		this.fullClassName = fullClassName;
	}
	
	public String getClassName() {
		return className;
	}
	
	public void setClassName(String className) {
		this.className = className;
	}
	
	public String getFullMethodName() {
		return fullMethodName;
	}
	
	public void setFullMethodName(String fullMethodName) {
		this.fullMethodName = fullMethodName;
	}
	
	public String getMethodSig() {
		return methodSig;
	}
	
	public void setMethodSig(String methodSig) {
		this.methodSig = methodSig;
	}
	
	public Method getMethod() {
		return method;
	}
	
	public void setMethod(Method method) {
		this.method = method;
	}
	
	public int getNumIntArgs() {
		return numIntArgs;
	}
	
	public void setNumIntArgs(int numIntArgs) {
		this.numIntArgs = numIntArgs;
	}
	
	public boolean hasLoops() {
		return hasLoops;
	}
	
	public void setHasLoops(boolean hasLoops) {
		this.hasLoops = hasLoops;
	}

}
