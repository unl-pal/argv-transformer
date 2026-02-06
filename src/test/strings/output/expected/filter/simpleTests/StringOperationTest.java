package strings.input;

public class StringOperationTest {

  public int stringOperations(String data) {
    int count = 0;
    if (data.length() > 0) {
      count += data.indexOf("test");
      count += data.lastIndexOf("end");
      count += data.substring(0, 1).length();
      count += data.replace("a", "b").length();
      count += data.split(",").length;
      count += data.compareTo("other");
    }
    return count;
  }

  public String variousConcatenations(String a, String b, String c) {
    String result1 = a + b;
    String result2 = result1 + c;
    String result3 = a + b + c;
    return (result1 + result2 + result3).trim();
  }

  public boolean stringComparisons(String input) {
    if (input.equals("exact") && input.length() > 0) {
      return true;
    }
    if (input.equalsIgnoreCase("ignore") && input.contains("test")) {
      return true;
    }
    if (input.startsWith("pre") && input.endsWith("post")) {
      return true;
    }
    return false;
  }

  public String noStringOps(String data) {
    return data.toString(); // Just returning, no operations
  }
}
