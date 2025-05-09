package transformer.integration;

public class TestEmptyBlockRemoval {


    /** 1. Nested empty block inside another block */
    public void nestedBlock() {
        {
        }
    }

    /** 2. if-statement with empty then and non-empty else (should invert condition + move else) */
    public void ifEmptyThenElse() {
    	int a = 1;
    	int b = 2;
        if (a > b) {
        } else {
            b = 3;
        }
    }

    /** 3. if-statement with non-empty then and empty else (should remove else) */
    public void ifNonEmptyThenEmptyElse() {
    	int a = 1;
    	int b = 2;
        if (a > b) {
            a = 3;
        } else {
        }
    }

    /** 4. while-loop with empty body (should become an EmptyStatement) */
    public void whileEmptyBlock() {
    	int a = 1;
    	int b = 2;
        while (a < b) {
        }
    }

    /** 5. for-loop with empty body (should become an EmptyStatement) */
    public void forEmptyBlock() {
        for (int i = 0; i < 3; i++) {
        }
    }

    /** 6. synchronized block with empty body (should become an EmptyStatement) */
    public void synchronizedEmptyBlock() {
        synchronized (this) {
        }
    }
}
