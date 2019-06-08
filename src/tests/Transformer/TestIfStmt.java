package tests.Transformer;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import sourceAnalysis.*;

public class TestIfStmt {
	String s;
	double x;
	AnalyzedMethod am;
	
	public int test1() {
		File file = new File("/home/MariaPaquin/project/SPF_Transformations/src/IntOpCounterTests/Tricky.java");
		
		AnalyzedFile af = null;
		
		if(file.exists()) {
			af = new AnalyzedFile(file);
		}
		boolean c = false;
		if(c) {
			
		}
		
		if(af.isSymbolicSuitable()) {
			System.out.println("file is symbolic suitable.");
		}
		
		return af.getSpfSuitableMethodCount();
		
	}
	
	
	void testInfixExpression(int x) {
		int[] arr = new int[10];
		arr[6] = 8;
		if (arr[x] == 3) {}
		
		Dog[] d;
		d = new Dog[3];
		d[2] = 0;
		
	}
	
	class Cat{
		
	}

}
