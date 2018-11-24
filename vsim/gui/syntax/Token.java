package vsim.gui.syntax;


public final class Token {

  private String style;
  private int length;

  public Token(String style, int length) {
    this.style = style;
    this.length = length;
  }

  public String getStyle() {
    return this.style;
  }

  public int getLength() {
    return this.length;
  }

}
