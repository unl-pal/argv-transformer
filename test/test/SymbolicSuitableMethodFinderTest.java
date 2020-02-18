package test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import filter.file.SuitableMethodFinder;

public class SymbolicSuitableMethodFinderTest {

	@Test
	public void test() {
		File file = new File("./programs/prog/Simple_01.java");
		try {
			SuitableMethodFinder finder = new SuitableMethodFinder(file);
			finder.analyze();
			System.out.println(finder.getAnalyzedFile().getSpfSuitableMethodCount());
			System.out.println(finder.getTotalIntOperations());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//fail("Not yet implemented");
	}

}
