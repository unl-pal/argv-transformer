package jpf;

import java.io.IOException;

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
//		try {
//			runJPF("SPFTest", "SPFTest.test", 2, false);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}
