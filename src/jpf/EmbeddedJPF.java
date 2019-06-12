package jpf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGenException;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import logging.Logger;
/**
 * Class used to create a configuration file and run JPF.
 * 
 * @author mariapaquin
 *
 */
public class EmbeddedJPF {
	
	/**
	 * Create a configuration file for the given class and method, then run JPF.
	 * 
	 * @param fileClassName
	 * @param methodName
	 * @param numArgs
	 * @param boundSearch
	 * @throws IOException
	 */
	public static void runJPF(String fileClassName, String methodName, int numArgs, boolean boundSearch)
			throws IOException { 
		
		// create the config file
		String[] jpfArgs = {"-log"};
		Config config = JPF.createConfig(jpfArgs);
		
		config.setProperty("classpath", "bin");
//		config.setProperty("classpath", "/home/MariaPaquin/jpf-symbc/build/examples");
		config.setProperty("target", fileClassName);
		
		// prints path conditions
		config.setProperty("symbolic.debug", "true");

		// don't stop after finding fist error
		config.setProperty("search.multiple_errors", "true");

		// set the symbolic method
		String symArgs = "(sym";
		for (int i = 0; i < numArgs - 1; i++) {
			symArgs += "#sym";
		}
		symArgs += ")";

		config.setProperty("symbolic.method", methodName + symArgs);
		
		// set the decision procedure
		config.setProperty("symbolic.dp", "choco");
		
		// if there was a loop in the method, set the search depth limit to bound loop unwindings
		if (boundSearch) {
			config.setProperty("search.depth_limit", "100");
		}

		// run jpf
		JPF jpf = new JPF(config);
		
		try {
			jpf.run();
		}catch(Exception e){
			Logger.errorLogger.logln("Pathfinder encountered an error!",0);

		}
	}
	
	public static void main(String[] args) {
		try {
			runJPF(new File("./src/tests/NumericExample.java"));

//			runJPF("demo.NumericExample", "demo.NumericExample.test", 2, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * Run JPF on each file in a list of files.
	 * 
	 * @param files Files to run JPF. 
	 * @throws IOException 
	 */
	private static void runJPF(File file) throws IOException {
			try {
				ProgramUnderTest sut = new ProgramUnderTest(file);
				String className = sut.getClassName();

				sut.insertMain();

				Method[] methods = sut.getMethods();
				for (Method method : methods) {
					String fullMethodName = className + "." + method.getName();

					// skip main
					if (method.getName().equals("main") || method.getName().equals("<init>")
							|| method.getName().equals("<clinit>") || method.getName().contains("access$")) {
						continue;
					}

					// check suitability of method for SPF analysis
					int numArgs = sut.getNumArgs(method);
					int numIntArgs = sut.getNumIntArgs(method);

					if (numIntArgs == 0 || numIntArgs != numArgs) {
						continue;
					}

					boolean boundSearch = sut.checkForLoops(method.getName());

					sut.insertMethodCall(method, numIntArgs);
					
					EmbeddedJPF.runJPF(className, fullMethodName, numIntArgs, boundSearch);

				}
			} catch (ClassGenException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
			}
		}
}
