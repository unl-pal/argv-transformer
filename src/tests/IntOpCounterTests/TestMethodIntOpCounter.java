package tests.IntOpCounterTests;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import other.MethodIntegerOperationCounter;

public class TestMethodIntOpCounter {

//	@Test
//	public void testCasting() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestCasting.java"));
//		assertEquals(count.getTotalIntOperations(), 14);
//	}
//	
//	@Test
//	public void test1() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/Test1.java"));
//		assertEquals(count.getTotalIntOperations(), 8);
//	}
//	
//	@Test
//	public void test2() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/Test2.java"));
//		assertEquals(count.getTotalIntOperations(),  11);
//	}
//	
//	@Test
//	public void test3() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/Test3.java"));
//		assertEquals(count.getTotalIntOperations(), 8);
//	}
//	
//	@Test
//	public void test4() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/Test4.java"));
//		assertEquals(count.getTotalIntOperations(), 10);
//	}
//	
//	@Test
//	public void testChar() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestChar.java"));
//		assertEquals(count.getTotalIntOperations(), 8);
//	}
//	
//	@Test
//	public void testByte() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestByte.java"));
//		assertEquals(count.getTotalIntOperations(), 2);
//	}
//	
//	@Test
//	public void testLoopDeclarations() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestLoopDeclarations.java"));
//		assertEquals(count.getTotalIntOperations(), 4);
//	}
//
//	@Test
//	public void testPrePostFix() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestPrePostFix.java"));
//		assertEquals(count.getTotalIntOperations(), 17);
//	}
//	
//	@Test
//	public void testScope() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestScope.java"));
//		assertEquals(count.getTotalIntOperations(), 3);
//	}
//	
//	@Test
//	public void testNestedClasses() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestNestedClasses.java"));
//		assertEquals(count.getTotalIntOperations(), 2);
//	}
//	
//	@Test
//	public void testAssign() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/TestAssign.java"));
//		assertEquals(count.getTotalIntOperations(), 1);
//	}
//	
//	@Test
//	public void testTricky() throws IOException {
//		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
//				new File("./src/tests/IntOpCounterTests/Tricky.java"));
//		assertEquals(count.getTotalIntOperations(), 12);
//	}
	
	@Test
	public void testPerson() throws IOException {
		MethodIntegerOperationCounter count = new MethodIntegerOperationCounter(
				new File("/home/MariaPaquin/project/database/tsinux/JavaSE/JavaSE-master/src/day05/TestPerson.java"));
		System.out.println(count.getIntOpPerMethodCount());
	}
	
}
