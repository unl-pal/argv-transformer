package filter.file;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import util.AnalyzedFile;
import transform.TypeChecking.TypeChecker.CType;

/**
 * Class to find java files suitable for symbolic execution.
 *
 * @author mariapaquin
 */
public class FileFilter {
	private final ArrayList<File> spfSuitableFiles;
	private ArrayList<File> javaFiles;
	private File database;
	private CType type;
	private int minExpr;
	private int minIfStmt;
	private int minParams;
	private boolean simplifyFilter;

    public FileFilter(File database, String type, int minExpr, int minIfStmt, int minParams, boolean simplifyFilter) {
        this.database = database;
        spfSuitableFiles = new ArrayList<File>();
        javaFiles = new ArrayList<File>();
        this.simplifyFilter = simplifyFilter;
		switch(type) {
		case "I": this.type = CType.INT; break;
		case "R" : this.type = CType.REAL; break;
		case "B" : this.type = CType.BOOLEAN; break;
		case "S" : this.type = CType.STRING; break;
		default : this.type = CType.ANY; break;
		}
		this.minExpr = minExpr;
		this.minIfStmt = minIfStmt;
		this.minParams = minParams;
    }

	/**
	 * Getter for suitable file count
	 * @return the number of SPF suitable files
	 */
	public ArrayList<File> getSuitableFiles() {
		return spfSuitableFiles;
	}

	public ArrayList<File> getJavaFiles() {
		return javaFiles;
	}

	/**
	 * Collect all the java files in the project and add them
	 * to the list javaFiles.
	 */
	public void collectJavaFiles() {
		try {
			Files.find(Paths.get(database.getAbsolutePath()), 999,
					(p, bfa) -> p.toString().endsWith(".java"))
				.forEach(p -> javaFiles.add(p.toFile()));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Use the SymbolicSuitableMethodFinder to search the javaFiles list for files
	 * suitable for SPF. Add suitable files to the list spfSuitableFiles.
	 */
	public void collectSuitableFiles() {
		for (File file: javaFiles) {
			try {
			    if (simplifyFilter) {
	                SimplifiedSuitableClassFinder finder = new SimplifiedSuitableClassFinder(file, type, minExpr, minIfStmt, minParams);
	                finder.analyze();
	                if (finder.isSuitable()) {
	                    spfSuitableFiles.add(file);
	                }
			    } else {
	                SuitableMethodFinder finder = new SuitableMethodFinder(file, type, minExpr, minIfStmt, minParams);
	                finder.analyze();
	                AnalyzedFile af = finder.getAnalyzedFile();
	                if (!af.getSuitableMethods().isEmpty()) {
	                    spfSuitableFiles.add(file);
	                }
			    }
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

}
