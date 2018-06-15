package vsim.assembler;


public final class DebugInfo {

  private int lineno;
  private String source;

  public DebugInfo(int lineno, String source) {
    this.lineno = lineno;
    this.source = source;
  }

  public int getLineNumber() {
    return this.lineno;
  }

  public String getSource() {
    return this.source;
  }

  @Override
  public String toString() {
    return String.format(
      "%d: %s",
      this.lineno,
      this.source
    );
  }

}
