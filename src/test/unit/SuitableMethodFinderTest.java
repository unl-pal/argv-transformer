package unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Set;

import org.junit.After;
import org.junit.Test;

import filter.file.SuitableMethodFinder;
import util.AnalyzedMethod;
import transform.TypeChecking.TypeChecker.CType;

/**
 * NOTE: SuitableMethodFinder#analyze() rewrites the file it analyzes in place
 * (removing unsuitable methods), so every test operates on a throwaway temp
 * file rather than a checked-in resource.
 */
public class SuitableMethodFinderTest {

	private File tempFile;

	@After
	public void cleanup() {
		if (tempFile != null) {
			tempFile.delete();
		}
	}

	private File writeSource(String source) throws IOException {
		tempFile = File.createTempFile("SuitableMethodFinderTest", ".java");
		Files.write(tempFile.toPath(), source.getBytes(StandardCharsets.UTF_8));
		return tempFile;
	}

	@Test
	public void methodWithIntParamsAndIntOperationIsSuitable() throws IOException {
		String source =
				"public class Sample {\n" +
				"    public int add(int a, int b) {\n" +
				"        int sum = a + b;\n" +
				"        return sum;\n" +
				"    }\n" +
				"}";
		File file = writeSource(source);

		SuitableMethodFinder finder = new SuitableMethodFinder(file, CType.INT, 1, 0, 1);
		finder.analyze();

		Set<AnalyzedMethod> suitable = finder.getAnalyzedFile().getSuitableMethods();
		assertEquals(1, suitable.size());
		assertEquals("add", suitable.iterator().next().getName());
	}

	@Test
	public void methodWithNoParamsIsNotSuitableWhenParamsRequired() throws IOException {
		String source =
				"public class Sample {\n" +
				"    public void noop() {\n" +
				"    }\n" +
				"}";
		File file = writeSource(source);

		SuitableMethodFinder finder = new SuitableMethodFinder(file, CType.INT, 1, 0, 1);
		finder.analyze();

		assertTrue(finder.getAnalyzedFile().getSuitableMethods().isEmpty());
	}

	@Test
	public void onlyMatchingTypeCountsTowardOperationsAndConditionals() throws IOException {
		String source =
				"public class Sample {\n" +
				"    public int compute(int a, int b) {\n" +
				"        int sum = a + b;\n" +
				"        if (sum > 0) {\n" +
				"            sum = sum - 1;\n" +
				"        }\n" +
				"        return sum;\n" +
				"    }\n" +
				"    public String describe(String s) {\n" +
				"        return s + \"!\";\n" +
				"    }\n" +
				"}";
		File file = writeSource(source);

		SuitableMethodFinder finder = new SuitableMethodFinder(file, CType.INT, 1, 0, 1);
		finder.analyze();

		// describe() only has String-typed operations, so it shouldn't contribute
		// to the INT-typed operation/conditional counts collected across the file.
		assertTrue("expected int operations from compute() only", finder.getTotalIntOperations() > 0);
		Set<AnalyzedMethod> suitable = finder.getAnalyzedFile().getSuitableMethods();
		assertEquals(1, suitable.size());
		assertEquals("compute", suitable.iterator().next().getName());
	}
}
