package vsim.assembler.syntax;

import vsim.utils.Colorize;


public final class Token {

  protected enum TokenType {
    COMMA,
    LPAREN,
    RPAREN,
    COLON,
    DOT,
    NUMBER,
    STRING,
    REGISTER,
    IDENTIFIER,
    INSTRUCTION;
  };

  // tokens
  public static final TokenType COMMA       = TokenType.COMMA;
  public static final TokenType LPAREN      = TokenType.LPAREN;
  public static final TokenType RPAREN      = TokenType.RPAREN;
  public static final TokenType COLON       = TokenType.COLON;
  public static final TokenType DOT         = TokenType.DOT;
  public static final TokenType NUMBER      = TokenType.NUMBER;
  public static final TokenType STRING      = TokenType.STRING;
  public static final TokenType REGISTER    = TokenType.REGISTER;
  public static final TokenType IDENTIFIER  = TokenType.IDENTIFIER;
  public static final TokenType INSTRUCTION = TokenType.INSTRUCTION;

  private TokenType type;
  private Object value;

  protected Token(TokenType type) {
    this(type, null);
  }

  protected Token(TokenType type, Object value) {
    this.type = type;
    this.value = value;
  }

  public TokenType getType() {
    return this.type;
  }

  public Object getValue() {
    return this.value;
  }

  @Override
  public String toString() {
    if (this.value != null) {
      return String.format(
        "<%s>(%s)",
        Colorize.green(this.type.toString()),
        Colorize.blue(this.value.toString())
      );
    }
    return String.format(
      "<%s>",
      Colorize.green(this.type.toString())
    );
  }

}
