package jpf;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.bcel.classfile.Method;
import org.apache.bcel.generic.ClassGenException;

import logging.Logger;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.vm.VM;

public class EmbeddedJPF {
	
	/**
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
		config.setProperty("classpath", "${SPF_Transformations}/build");
		config.setProperty("target", fileClassName);

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
			runJPF("SPFTest", "SPFTest.test", 2, false);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private static void runJPF(File file) {
			try {
//				System.out.println(file.getAbsolutePath());
				ProgramUnderTest sut = new ProgramUnderTest(file);
				String className = sut.getClassName();

				sut.insertMain();

				Method[] methods = sut.getMethods();
				for (Method method : methods) {
//					System.out.println(method.getName());
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
					System.out.println(className);
		//			EmbeddedJPF.runJPF(className, fullMethodName, numIntArgs, boundSearch);

				}
			} catch (ClassGenException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
				// TODO Auto-generated catch block
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
			}
		}

}
