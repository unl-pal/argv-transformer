package jpf;

import java.io.IOException;

import logging.Logger;
import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;

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
		config.setProperty("classpath","build/");
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
}
