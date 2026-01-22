package strings.input;

public class StringEdgeCaseTest {

  public String emptyString(String input) {
    if (input.length() == 0) {
      return "empty";
    }
    return "not empty: " + input.length();
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
}
