package transformer.integration.field;

public class MinimalFieldStringCase {
    
    public static String testVar = "testString";
    
	public static int baseTestCase(int x) {
		if (x > testVar.length()) {
			return 1;
		} else {
			return 0;
		}
	}

}
