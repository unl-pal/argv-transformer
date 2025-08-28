

/** filtered and transformed by ARG-V */

import org.sosy_lab.sv_benchmarks.Verifier;

/** filtered by ARG-V */
 public class Main {


    /** 1. Nested empty block inside another block */
    public void nestedBlock() {
    }

    /** 2. if-statement with empty then and non-empty else (should invert condition + move else) */
    public void ifEmptyThenElse() {
    	int a = 1;
    	int b = 2;
        if (!(a > b)) {
			b = 3;
		}
    }

    /** 3. if-statement with non-empty then and empty else (should remove else) */
    public void ifNonEmptyThenEmptyElse() {
    	int a = 1;
    	int b = 2;
        if (a > b) {
            a = 3;
        }
    }

    /** 4. while-loop with empty body (should become an EmptyStatement) */
    public void whileEmptyBlock() {
    	int a = 1;
    	int b = 2;
    }

    /** 5. for-loop with empty body (should become an EmptyStatement) */
    public void forEmptyBlock() {
    }

    /** 6. synchronized block with empty body (should become an EmptyStatement) */
    public void synchronizedEmptyBlock() {
    }

	public static void main(String[] args) {
		Main instance = new Main();
		instance.nestedBlock();
		instance.ifEmptyThenElse();
		instance.ifNonEmptyThenEmptyElse();
		instance.whileEmptyBlock();
		instance.forEmptyBlock();
		instance.synchronizedEmptyBlock();
	}
}
