package tests.IntOpCounterTests;
import java.io.File;

import javax.tools.JavaCompiler;
import javax.tools.ToolProvider;


public class TestCompile {
	
	public static void main(String[] args) throws Exception {
		JavaCompiler javac = ToolProvider.getSystemJavaCompiler();
		if (javac == null) {
			throw new Exception("No Java compiler found. Ensure the classpath contains tools.jar and try again.");
		}
		
		String f = "/home/MariaPaquin/examples/demo/MyClass1.java";
		
		boolean independentSuccess = javac.run(null,System.out,System.err, f) == 0;
		
		System.out.println(independentSuccess);
		
	}

}
