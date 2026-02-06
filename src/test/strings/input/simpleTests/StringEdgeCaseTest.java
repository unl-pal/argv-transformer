package strings.input;

public class StringEdgeCaseTest {

  public String emptyString(String input) {
    if (input.length() == 0) {
      return "empty";
    }
    return "not empty: " + input.length();
  }

  public String processStringArray(String[] inputs) {
    String result = "";
    for (String s : inputs) {
      if (s.length() > 0) {
        result += s + ",";
      }
    }
    return result.endsWith(",") ? result.substring(0, result.length() - 1) : result;
  }

  public String returnNull(String input) {
    if (input == null) {
      return null; // Should fail return type check
    }
    return "not null";
  }

  public void voidStringOp(String data) {
    System.out.println(data.length()); // No meaningful operations
  }

  public char stringCharMethod(char c) {
    return Character.toUpperCase(c);
  }

  public int processObject(Object obj) {
    return obj.toString().length();
  }
}
