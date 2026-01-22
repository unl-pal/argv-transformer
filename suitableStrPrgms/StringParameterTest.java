package strings.input;

public class StringParameterTest {

  public String concatAll(String a, String b, String c) {
    if (a.length() > 0 && b.length() > 0) {
      return a + b + c;
    }
    return c;
  }

  public String processString(String input) {
    if (input.length() > 10) {
      return input.substring(0, 5).toUpperCase();
    }
    return input.trim();
  }

  public boolean compareStrings(String str1, String str2) {
    if (str1.equals(str2) && str1.length() > 0) {
      return true;
    }
    return str1.equalsIgnoreCase(str2);
  }

  public int conditionalString(String data) {
    if (data != null && data.contains("test")) {
      return data.indexOf("test");
    }
    return -1;
  }

  public String processMixed(String input, int count) {
    return input.substring(count);
  }

  public boolean mixedTypes(String text, boolean flag) {
    return flag && text.length() > 0;
  }

  public String stringConcatenation(String a, String b) {
    String result = a + b;
    if (result.length() > 10) {
      return result.substring(0, 10);
    }
    return result;
  }

  public boolean stringMethodCalls(String data) {
    if (data.startsWith("prefix") && data.endsWith("suffix")) {
      return data.replace("old", "new").contains("new");
    }
    return false;
  }
}
