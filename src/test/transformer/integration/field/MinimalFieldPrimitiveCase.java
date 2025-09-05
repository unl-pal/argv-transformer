package transformer.integration.field;

public class MinimalFieldPrimitiveCase {
    
    public static int testVar = 1;
    
	public static int baseTestCase(int x) {
		if (x > testVar) {
			return 1;
		} else {
			return 0;
		}
	}

}
