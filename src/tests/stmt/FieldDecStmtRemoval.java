package tests.stmt;

import gov.nasa.jpf.symbc.Symbolic;

public class FieldDecStmtRemoval {
    @Symbolic("true")
    public static int field1;

	@Symbolic("true")
    public static int field2;
    
    public static int test(int x) {
		x = field1 + field2;
		if (x > 0) {
			x = 1;
		} else {
			x = x - field1;
		}
		return x;
    }
    
    public static void main(String[] args) {
    	test(0);
	}
    
// (1)   (transform.stmt.FieldDecStmtRemoval.field2 + transform.stmt.FieldDecStmtRemoval.field1) <= 0
// (2)   (transform.stmt.FieldDecStmtRemoval.field2 + transform.stmt.FieldDecStmtRemoval.field1) > 0 
}