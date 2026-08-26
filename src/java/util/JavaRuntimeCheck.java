package util;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.tools.ToolProvider;

/**
 * Verifies that the JVM running ARG-V can actually support the transform stage.
 *
 * <p>
 * The transformer resolves type bindings with the Eclipse JDT parser, and JDT can only
 * resolve JDK types when the Java 8 class library archive <code>rt.jar</code> is on the parser's classpath.
 *
 * @see transform.Transformer#getParser(String, String[], String[], File)
 */
public final class JavaRuntimeCheck {

	private JavaRuntimeCheck() {
		// static utility
	}

	/**
	 * Locates the Java 8 class library archive for the running JVM.
	 *
	 * <p>
	 * {@code java.home} points at the JRE directory when launched from a JDK 8's
	 * {@code bin/java}, but at the JDK root in some layouts (and when launched from an embedded
	 * JRE), so both locations are probed.
	 *
	 * @return the existing rt.jar, or {@code null} if this JVM has none
	 */
	public static File findRtJar() {
		String javaHome = System.getProperty("java.home");
		if (javaHome == null) {
			return null;
		}
		Path[] candidates = {
				Paths.get(javaHome, "lib", "rt.jar"),
				Paths.get(javaHome, "jre", "lib", "rt.jar")
		};
		for (Path candidate : candidates) {
			File file = candidate.toFile();
			if (file.isFile()) {
				return file;
			}
		}
		return null;
	}

	/**
	 * Checks whether this JVM can run the transform stage.
	 *
	 * @return {@code null} when the environment is usable, otherwise a human-readable,
	 *         actionable description of what is wrong
	 */
	public static String checkTransformEnvironment() {
		if (findRtJar() == null) {
			return "no Java 8 class library (rt.jar) was found for this JVM.\n"
					+ describeEnvironment()
					+ "\nARG-V's transform stage requires a Java 8 JDK\n"
					+ "\nFix: install a Java 8 JDK and point JAVA_HOME (and Gradle) at it, e.g.\n"
					+ "  JAVA_HOME=/path/to/jdk8 ./gradlew transform\n"
					+ "or, for Gradle, add org.gradle.java.home=/path/to/jdk8 to gradle.properties.";
		}

		if (ToolProvider.getSystemJavaCompiler() == null) {
			return "no Java compiler is available on this JVM.\n"
					+ describeEnvironment()
					+ "\nThe transform stage compiles each candidate benchmark in-process via\n"
					+ "javax.tools.ToolProvider, which a JRE does not provide.\n"
					+ "\nFix: run ARG-V with a Java 8 JDK rather than a JRE.";
		}

		return null;
	}

	/**
	 * @return a short description of the running JVM, for diagnostics
	 */
	public static String describeEnvironment() {
		return "  java.version   = " + System.getProperty("java.version")
				+ "\n  java.home      = " + System.getProperty("java.home")
				+ "\n  java.vendor    = " + System.getProperty("java.vendor");
	}

	/**
	 * Fails fast if this JVM cannot run the transform stage. Intended for library entry points
	 * (the transformer itself) where an exception is the appropriate signal.
	 *
	 * @throws IllegalStateException if the environment is unusable
	 */
	public static void requireTransformEnvironment() {
		String problem = checkTransformEnvironment();
		if (problem != null) {
			throw new IllegalStateException("Unsupported Java runtime: " + problem);
		}
	}

	/**
	 * Fails fast if this JVM cannot run the transform stage, reporting the problem on stderr and
	 * exiting. Intended for {@code main} methods, where a stack trace would only obscure an
	 * environment problem the user needs to read.
	 */
	public static void requireTransformEnvironmentOrExit() {
		String problem = checkTransformEnvironment();
		if (problem != null) {
			System.err.println("================================================");
			System.err.println("ARG-V cannot run on this Java runtime: " + problem);
			System.err.println("================================================");
			System.exit(1);
		}
	}
}
