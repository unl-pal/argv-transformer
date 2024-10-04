package jpf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

import org.apache.bcel.Const;
import org.apache.bcel.Repository;
import org.apache.bcel.classfile.JavaClass;
import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ArrayType;
import org.apache.bcel.generic.ClassGen;
import org.apache.bcel.generic.ConstantPoolGen;
import org.apache.bcel.generic.DUP;
import org.apache.bcel.generic.ICONST;
import org.apache.bcel.generic.INVOKESPECIAL;
import org.apache.bcel.generic.INVOKESTATIC;
import org.apache.bcel.generic.INVOKEVIRTUAL;
import org.apache.bcel.generic.IfInstruction;
import org.apache.bcel.generic.InstructionHandle;
import org.apache.bcel.generic.InstructionList;
import org.apache.bcel.generic.LocalVariableGen;
import org.apache.bcel.generic.MethodGen;
import org.apache.bcel.generic.NEW;
import org.apache.bcel.generic.RETURN;
import org.apache.bcel.generic.Type;

import soot.Body;
import soot.Scene;
import soot.SootClass;
import soot.SootMethod;
import soot.toolkits.graph.LoopNestTree;
/**
 * Used to prepare and program for symbolic execution with JPF.
 * 
 * - Check for main method
 * - Check for loops (may need to bound search)
 * - Inserts method call from main to method(s) to be run with JPF.
 * 
 * @author mariapaquin
 *
 */
public class ProgramUnderTest {

	private File file;
	private String className;
	private String fullClassName;
	private String packageName;
	
	private InstructionList il_main;
	private MethodGen methodGen_main;
	private JavaClass currClass;
	private ClassGen modClass;
	private Method[] modMethods;
	private ConstantPoolGen constantPool;

	public ProgramUnderTest(File file) throws ClassNotFoundException {
		this.file = file;
		className = file.getName().replace(".java", "");
		checkForPackages();
		setupModClass();
		System.out.println("done with file");
	}

	private void checkForPackages() {
		System.out.println("packages");
		Scanner fileScan;
		try {
			fileScan = new Scanner(file);

			boolean packageFound = false;
			String packageName = null;

			while (fileScan.hasNextLine() && !packageFound) {
				Scanner lineScan = new Scanner(fileScan.nextLine());

				while (lineScan.hasNext()) {
					String token = lineScan.next();

					if (token.equals("package")) {
						packageName = lineScan.next().replace(";", "");
						packageFound = true;
						break;
					}
				}
				lineScan.close();
			}
			fileScan.close();

			if (packageFound) {
				this.packageName = packageName;
				fullClassName = packageName + "." + className;
			} else {
				fullClassName = className;
				this.packageName = "-";
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	private void setupModClass() throws ClassNotFoundException {
		System.out.println("setup classes " + fullClassName);
		String classpath = System.getProperty("java.class.path");
		System.out.println(classpath);
		//String[] classpathEntries = classpath.split(File.pathSeparator);
			currClass = Repository.lookupClass(fullClassName);
			System.out.println("classGen1");
			modClass = new ClassGen(currClass);
			System.out.println("classGen2");
			modMethods = modClass.getMethods();
			constantPool = modClass.getConstantPool();
	}
	

	public void insertMain() {
		System.out.println("Inserting main");
		for (int i = 0; i < modMethods.length; i++) {
			if (modMethods[i].getName().equals("main")) {
				modClass.removeMethod(modMethods[i]);
			}
		}
		System.out.println("Adding main");
		
		il_main = new InstructionList();

		// instruction list to associate with main method
		il_main.append(new RETURN());

		// MethodGen is template class for building up a method
		methodGen_main = new MethodGen(Const.ACC_PUBLIC | Const.ACC_STATIC, Type.VOID,
				new Type[] { new ArrayType(Type.STRING, 1) }, new String[] { "args" }, "main", fullClassName, il_main,
				constantPool);

		modClass.addMethod(methodGen_main.getMethod());
		modClass.update();

		modMethods = modClass.getMethods();
		constantPool = modClass.getConstantPool();

		try {
			JavaClass newClass = modClass.getJavaClass();
			String newClassName = fullClassName.replace(".", "/");
			newClass.dump("bin/" + newClassName + ".class");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/** 
	 * Insert a call to the SUT from main.
	 * This will be the only method call from main. 
	 * 
	 * @param curr_method 
	 * @param numIntArgs Number of integer parameters.
	 */
	public void insertMethodCall(Method curr_method, int numIntArgs) {

		// delete the current main method so we can add main with only SUT method call
		modClass.removeMethod(methodGen_main.getMethod());

		// delete any previous instructions in il_main (variable for main instruction list)
		il_main.dispose();

		// create a constant pool reference to the method
		MethodGen method_gen = new MethodGen(curr_method, fullClassName, constantPool);
		constantPool.addMethodref(method_gen);
		int method_ref = constantPool.lookupMethodref(method_gen);

		if (curr_method.isStatic()) {

			// push arguments onto the stack, doesn't matter what integer parameters are
			for (int a = 0; a < numIntArgs; a++) {
				il_main.append(new ICONST(1));
			}

			// generate an INVOKESTATIC opcode that performs the actual call
			il_main.append(new INVOKESTATIC(method_ref));

			// return
			il_main.append(new RETURN());

		} else {
			// new
			int class_ref = constantPool.lookupClass(fullClassName);
			il_main.append(new NEW(class_ref));

			// duplicate the value on top of the stack
			il_main.append(new DUP());

			// invoke the instance init method
			int init_ref = constantPool.lookupMethodref("java.lang.Object", "<init>", "()V");
			il_main.append(new INVOKESPECIAL(init_ref));

			// push arguments onto the stack
			for (int a = 0; a < numIntArgs; a++) {
				il_main.append(new ICONST(1));
			}

			// invoke the virtual method
			il_main.append(new INVOKEVIRTUAL(method_ref));

			// return 
			il_main.append(new RETURN());
		}

		modClass.setConstantPool(constantPool);

		methodGen_main.setInstructionList(il_main);
		methodGen_main.setMaxLocals();
		methodGen_main.setMaxStack();
		methodGen_main.update();

		modClass.addMethod(methodGen_main.getMethod());
		modClass.update();

		try {
			JavaClass newClass = modClass.getJavaClass();
			String newClassName = fullClassName.replace(".", "/");

			newClass.dump("bin/" + newClassName + ".class");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	/**
	 * Determine if there are any loops in the method under test.
	 * 
	 * @param methodName
	 * @return true if at least one loop was found, false otherwise.
	 */
	public boolean checkForLoops(String methodName) {
		
		String examples_build = "bin";
//		String rt_jar = "/usr/java/jdk1.8.0_161/jre/lib/rt.jar";
//		String jfxrt_jar = "/usr/java/jdk1.8.0_161/jre/lib/ext/jfxrt.jar";
//		String classPath_jpf_symbc_classes_jar = "/home/MariaPaquin/pathfinder/jpf-symbc/build/classes/";
//		String classPath_jpf_core_classes_jar = "/home/MariaPaquin/pathfinder/jpf-core/build/jpf-classes.jar";

//		Scene.v().setSootClassPath(rt_jar + ":" + jfxrt_jar + ":" + examples_build);
		Scene.v().setSootClassPath(Scene.v().getSootClassPath()+System.getProperty("path.separator")+System.getProperty("java.class.path") 
		+ System.getProperty("path.separator") + System.getProperty("sun.boot.class.path"));
		
		//Scene.v().setSootClassPath(rt_jar + ":" + jfxrt_jar + ":" + examples_build + ":" + classPath_jpf_symbc_classes_jar
		//		+ ":" + classPath_jpf_core_classes_jar);
		
		SootClass sootClass = Scene.v().forceResolve(fullClassName, SootClass.BODIES);

		Scene.v().loadNecessaryClasses();
		sootClass.setApplicationClass();

		Body body = null;
		for (SootMethod method : sootClass.getMethods()) {
			if (method.getName().equals(methodName)) {
				if (method.isConcrete()) {
					body = method.retrieveActiveBody();
					break;
				}
			}
		}

		int numLoops = 0;

		if (body != null) {
			LoopNestTree loopNestTree = new LoopNestTree(body);
			numLoops = loopNestTree.size();
		}

		if (numLoops != 0) {
			return true;
		}
		return false;
	}
	

	public int getNumArgs(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		int numArgs = methodGen.getArgumentNames().length;
		return numArgs;
	}

	public int getNumIntArgs(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		
		Type[] argTypes = methodGen.getArgumentTypes();
		int numIntArgs = 0;

		for (Type t : argTypes) {
			// check how it affects JPF if we allowed other integer types, char, long, short, boolean.
			if (t.getType() == Const.T_INT) {
				numIntArgs++;
			}
		}
		
		return numIntArgs;
	}

	public int getIfInstructions(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		InstructionList il = methodGen.getInstructionList();
		
		int ifInstructions = 0;
		for (InstructionHandle inst : il) {
			if (inst.getInstruction() instanceof IfInstruction) {
				ifInstructions++;
			}
		}
		return ifInstructions;
	}
	
	public String[] getArgNames(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		return methodGen.getArgumentNames();
	}
	
	public String getSignature(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		return methodGen.getSignature();
	}
	
	public LocalVariableGen[] getLocalVariables(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		return methodGen.getLocalVariables();
	}

	public int getTotalInstructions(Method method) {
		MethodGen methodGen = new MethodGen(method, modClass.getClassName(), constantPool);
		InstructionList il = methodGen.getInstructionList();
		return il.getLength();
	}
	
	public Method[] getMethods() {
		return modMethods;
	}
	
	public File getFile() {
		return file;
	}

	public ConstantPoolGen getConstantPool() {
		return constantPool;
	}

	public ClassGen getModClass() {
		return modClass;
	}

	public String getClassName() {
		return className;
	}
	
	public String getFullClassName() {
		return fullClassName;
	}
	
	public String getPackageName() {
		return packageName;
	}
}
