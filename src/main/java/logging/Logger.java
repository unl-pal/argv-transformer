package logging;

import java.io.PrintStream;
import java.util.Stack;

/**
 * A simple logging system, for more organized output than System.out.print.
 * 
 * @author aminton
 *
 */
public class Logger {
	private Stack<String> contextStack;
	private PrintStream out;
	private int debugLevel = 0;
	
	public static final Logger defaultLogger = new Logger(System.out);
	public static final Logger errorLogger = new Logger(System.err).enterContext("ERROR");
	
	/**
	 * Create a new Logger with a specific print output stream.
	 * 
	 * @param out PrintStream that will be used to print output
	 */
	public Logger(PrintStream out) {
		contextStack = new Stack<String>();
		this.out = out;
	}
	
	/**
	 * Enter a logger context with the specified name. The context is printed in
	 * brackets before each logged line.
	 * 
	 * @param name Name of the context to enter
	 * @return this logger
	 */
	public Logger enterContext(String name) {
		contextStack.push(name);
		return this;
	}
	
	/**
	 * Exits the logger context with the specified name. The context must be the
	 * most recently entered context.
	 * 
	 * @param name Name of the context to exit
	 * @return this logger
	 * @throws RuntimeException if there is no logger context to exit, or the
	 *   current logger context does not match the one passed in
	 */
	public Logger exitContext(String name) {
		if (contextStack.isEmpty()) {
			throw new RuntimeException("No logger context to exit");
		}
		if (!contextStack.peek().equals(name)) {
			throw new RuntimeException("Attempt to exit logger context "+name+" but current context is "+contextStack.peek());
		}
		contextStack.pop();
		return this;
	}
	
	/**
	 * Log a message and terminate the line. This is analogous to
	 * println(String msg), and is equivalent to calling
	 * Logger.log(msg, debugLevel) followed by Logger.println().
	 * 
	 * @param msg The message to log
	 * @param debugLevel Minimum debug level for the message to be logged
	 */
	public void logln(String msg, int debugLevel) {
		log(msg, debugLevel);
		logln(debugLevel);
	}
	
	/**
	 * Terminate the current line. This is analogous to println().
	 * 
	 * @param debugLevel Minimum debug level for the message to be logged
	 */
	public void logln(int debugLevel) {
		if (this.debugLevel >= debugLevel) {
			out.println();
		}
	}
	
	/**
	 * Log a message. This is analogous to print().
	 * 
	 * @param msg The message to log
	 * @param debugLevel Minimum debug level for the message to be logged
	 */
	public void log(String msg, int debugLevel) {
		if (contextStack.isEmpty()) {
			throw new RuntimeException("Attempt to log with no context");
		}
		if (this.debugLevel >= debugLevel) {
			out.print("["+contextStack.peek()+"] "+msg);
		}
	}
	
	/**
	 * Set the debug level for the Logger. The initial debug level is 0.
	 * Messages with debug levels higher than the Logger's debug level will not
	 * be logged.
	 * 
	 * @param level
	 */
	public void setDebugLevel(int level) {
		debugLevel = level;
	}
}
