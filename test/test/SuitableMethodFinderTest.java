package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import filter.file.SuitableMethodFinder;
import sourceAnalysis.AnalyzedMethod;

public class SuitableMethodFinderTest {

	@Test
	public void test() {
		File file = new File("./programs/prog/Simple_01.java");
		try {
			SuitableMethodFinder finder = new SuitableMethodFinder(file);
			finder.analyze();
			System.out.println(finder.getAnalyzedFile().getSpfSuitableMethodCount());
			System.out.println(finder.getTotalIntOperations());
			System.out.println(finder.getTotalConditionals());
			
			for(AnalyzedMethod m : finder.getAnalyzedFile().getSuitableMethods()) {
				System.out.println(m.getName() + " " + m.getConditionalCount() + " " + m.getIntOperationCount());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fail("Not yet implemented");
	}

}
