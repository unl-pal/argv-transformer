package util;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Resolves command-line arguments for a pipeline stage's Main: pulls out an optional
 * --config=<path> flag (defaulting to config.properties in the directory the JVM was
 * invoked from), and fills in default values for whatever positional args remain.
 */
public class ConfigUtils {

	private static final String FLAG_PREFIX = "--config=";

	public static class Result {
		public final File configFile;
		public final String[] positional;

		public Result(File configFile, String[] positional) {
			this.configFile = configFile;
			this.positional = positional;
		}
	}

	/**
	 * Strips --config=<path> out of args wherever it appears, then matches whatever
	 * positional args remain against defaults: if the counts line up exactly, the
	 * remaining args are used as-is (positionally), otherwise defaults are used.
	 */
	public static Result resolve(String[] args, String... defaults) {
		String configPath = "config.properties";
		List<String> remaining = new ArrayList<>();

		for (String arg : args) {
			if (arg.startsWith(FLAG_PREFIX)) {
				configPath = arg.substring(FLAG_PREFIX.length());
			} else {
				remaining.add(arg);
			}
		}

		String[] positional = remaining.size() == defaults.length
				? remaining.toArray(new String[0])
				: defaults;

		return new Result(new File(configPath), positional);
	}
}
