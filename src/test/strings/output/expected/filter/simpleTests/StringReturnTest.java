package strings.input;

public class StringReturnTest {

  public String stringReturn(String input) {
    return input.trim().toUpperCase();
  }

  public void voidStringMethod(String data) {
    if (data != null) {
      String processed = data.replace("old", "new");
      System.out.println(processed.length());
    }
  }

  public int nonStringReturn(String input) {
    return input.length(); // int return, not string
  }

  public int mixedReturn(String input, int multiplier) {
    return input.length() * multiplier;
  }

  public StringBuilder builderReturn(String base) {
    return new StringBuilder(base);
  }
}
