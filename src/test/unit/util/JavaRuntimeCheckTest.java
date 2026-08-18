package unit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;

import org.junit.After;
import org.junit.Test;

import util.JavaRuntimeCheck;

/**
 * Tests for the Java 8 runtime verification.
 *
 * <p>
 * The suite itself can only run on a Java 8 JDK (that is the point of the class under test), so
 * the "unsupported runtime" branches are exercised by pointing the {@code argv.rtJar} override
 * at a path that does not exist, which is the same code path a Java 9+ JVM takes.
 */
public class JavaRuntimeCheckTest {

	@After
	public void clearOverride() {
		System.clearProperty(JavaRuntimeCheck.RT_JAR_PROPERTY);
	}

	@Test
	public void findsRtJarOnTheRunningJvm() {
		File rtJar = JavaRuntimeCheck.findRtJar();
		assertNotNull("the test suite must itself run on a Java 8 JDK", rtJar);
		assertTrue(rtJar.isFile());
		assertEquals("rt.jar", rtJar.getName());
	}

	@Test
	public void acceptsTheRunningJvm() {
		assertNull(JavaRuntimeCheck.checkTransformEnvironment());
		JavaRuntimeCheck.requireTransformEnvironment();
	}

	@Test
	public void honoursAnExplicitRtJarOverride() {
		File actual = JavaRuntimeCheck.findRtJar();
		System.setProperty(JavaRuntimeCheck.RT_JAR_PROPERTY, actual.getAbsolutePath());

		assertEquals(actual.getAbsolutePath(), JavaRuntimeCheck.findRtJar().getAbsolutePath());
		assertNull(JavaRuntimeCheck.checkTransformEnvironment());
	}

	@Test
	public void reportsAMissingRtJar() {
		System.setProperty(JavaRuntimeCheck.RT_JAR_PROPERTY, "/no/such/rt.jar");

		assertNull(JavaRuntimeCheck.findRtJar());

		String problem = JavaRuntimeCheck.checkTransformEnvironment();
		assertNotNull("a missing rt.jar must be reported, not ignored", problem);
		assertTrue("the message should name rt.jar", problem.contains("rt.jar"));
		assertTrue("the message should tell the user how to fix it", problem.contains("JAVA_HOME"));
	}

	@Test
	public void requireThrowsWhenRtJarIsMissing() {
		System.setProperty(JavaRuntimeCheck.RT_JAR_PROPERTY, "/no/such/rt.jar");

		try {
			JavaRuntimeCheck.requireTransformEnvironment();
			fail("expected an IllegalStateException for a JVM without rt.jar");
		} catch (IllegalStateException expected) {
			assertTrue(expected.getMessage().contains("Unsupported Java runtime"));
		}
	}

	@Test
	public void describesTheRunningJvm() {
		String description = JavaRuntimeCheck.describeEnvironment();
		assertTrue(description.contains(System.getProperty("java.version")));
		assertTrue(description.contains(System.getProperty("java.home")));
	}
}
