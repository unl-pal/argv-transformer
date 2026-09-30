package unit.transform.benchmark;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;
import org.yaml.snakeyaml.Yaml;

import transform.benchmark.CreateYmlFile;

public class CreateYmlFileTest {

	@Rule
	public TemporaryFolder tempFolder = new TemporaryFolder();

	@SuppressWarnings("unchecked")
	private Map<String, Object> buildAndLoad(boolean isAssertionTrue, boolean noRuntimeExceptions) throws Exception {
		String dir = tempFolder.getRoot().getAbsolutePath();
		CreateYmlFile.buildFile(dir, "Sample", isAssertionTrue, noRuntimeExceptions);

		File ymlFile = new File(dir, "Sample.yml");
		assertTrue("expected yml file to be written to " + ymlFile, ymlFile.exists());

		try (FileReader reader = new FileReader(ymlFile)) {
			Yaml yaml = new Yaml();
			return yaml.load(reader);
		}
	}

	@Test
	public void producesExpectedTopLevelStructure() throws Exception {
		Map<String, Object> content = buildAndLoad(true, true);

		assertEquals("2.0", content.get("format_version"));

		@SuppressWarnings("unchecked")
		List<String> inputFiles = (List<String>) content.get("input_files");
		assertEquals(Arrays.asList("../common/", "Sample/"), inputFiles);

		@SuppressWarnings("unchecked")
		Map<String, Object> options = (Map<String, Object>) content.get("options");
		assertEquals("Java", options.get("language"));
	}

	@Test
	public void recordsExpectedVerdictsForBothProperties() throws Exception {
		Map<String, Object> content = buildAndLoad(true, false);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> properties = (List<Map<String, Object>>) content.get("properties");
		assertEquals(2, properties.size());

		Map<String, Object> assertProperty = properties.get(0);
		assertEquals("../properties/assert_java.prp", assertProperty.get("property_file"));
		assertEquals(Boolean.TRUE, assertProperty.get("expected_verdict"));

		Map<String, Object> exceptionProperty = properties.get(1);
		assertEquals("../properties/runtime-exception.prp", exceptionProperty.get("property_file"));
		assertEquals(Boolean.FALSE, exceptionProperty.get("expected_verdict"));
	}

	@Test
	public void bothVerdictsCanBeFalse() throws Exception {
		Map<String, Object> content = buildAndLoad(false, false);

		@SuppressWarnings("unchecked")
		List<Map<String, Object>> properties = (List<Map<String, Object>>) content.get("properties");
		assertEquals(Boolean.FALSE, properties.get(0).get("expected_verdict"));
		assertEquals(Boolean.FALSE, properties.get(1).get("expected_verdict"));
	}
}
