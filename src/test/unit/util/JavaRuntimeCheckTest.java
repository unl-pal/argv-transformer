package unit.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Test;

import util.JavaRuntimeCheck;

/**
 * Tests for the Java 8 runtime verification.
 *
 * <p>
 * The suite itself can only run on a Java 8 JDK (that is the point of the class under test), so
 * only the "supported runtime" branches can be exercised here — there is no longer a property
 * override to force the "unsupported runtime" branches without actually running on one.
 */
public class JavaRuntimeCheckTest {

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
	public void describesTheRunningJvm() {
		String description = JavaRuntimeCheck.describeEnvironment();
		assertTrue(description.contains(System.getProperty("java.version")));
		assertTrue(description.contains(System.getProperty("java.home")));
	}
}
