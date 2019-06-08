package filter.fileFilter;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;

import download.GitProject;
import sourceAnalysis.AnalyzedFile;

public class SuitableFileFilter {
	private ArrayList<File> spfSuitableFiles;
	private int spfSuitableMethods;

	public SuitableFileFilter() {
		spfSuitableFiles = new ArrayList<File>();
		spfSuitableMethods = 0;
	}

	public ArrayList<File> getSuitableFiles() {
		return spfSuitableFiles;
	}
	
	public int getSuitableMethodCount() {
		return spfSuitableMethods;
	}

	public void removeUnsuitableFiles(File benchmarkDir) {
		
		Iterator itr = FileUtils.iterateFiles(benchmarkDir, new String[] { "java" }, true);
		
		while (itr.hasNext()) {
			
			File file = (File) itr.next();
			
				try {
					SymbolicSuitableMethodFinder finder = new SymbolicSuitableMethodFinder(file);
					finder.analyze();
					AnalyzedFile af = finder.getAnalyzedFile();
					try {
						spfSuitableMethods += af.getSpfSuitableMethodCount();
						if (af.isSymbolicSuitable()) {
							spfSuitableFiles.add(file);
						}else {
							Files.delete(file.toPath());
						}
					} catch (Exception e) {
						continue;
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
	}

}
