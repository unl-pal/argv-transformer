package strings.input;

public class StringBuilderTest {

  public String processBuilder(StringBuilder input) {
    if (input.length() > 0) {
      return input.append("suffix").toString();
    }
    return input.toString();
  }

  public String processBuffer(StringBuffer buffer) {
    if (buffer.length() > 0) {
      return buffer.toString().trim();
    }
    return buffer.toString();
  }

  public StringBuilder createBuilder(String base) {
    return new StringBuilder(base);
  }

  public StringBuilder mixedBuilder(StringBuilder sb, int count) {
    sb.append(count);
    return new StringBuilder(sb.toString());
  }

  public void builderOperations(StringBuilder sb) {
    sb.append("test");
    sb.insert(0, "prefix");
    sb.reverse();
  }
}
