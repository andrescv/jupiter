package vsim.assembler.syntax;

import vsim.utils.Message;
import java.util.ArrayList;
import vsim.simulator.State;
import vsim.riscv.InstructionSet;


public final class Lexer {

  // lexer states
  private static final int INITIAL       = 0;
  private static final int INTNUM        = 1;
  private static final int HEXNUM        = 2;
  private static final int BINNUM        = 3;
  private static final int STRING        = 4;
  private static final int STRBACKSLASH  = 5;
  private static final int CHARACTER     = 6;
  private static final int CHARBACKSLASH = 7;
  private static final int IDENTIFIER    = 8;

  // special EOL char
  private static final char EOL = 0xffff;

  // global lexer
  private static final Lexer lexer = new Lexer();

  private int pos;
  private int state;
  private boolean eol;
  private String line;
  private StringBuffer text;

  private Lexer() {
    this.text = new StringBuffer(0);
  }

  private void setCurrentLine(String line) {
    this.line = (line != null) ? line : "";
    this.eol = false;
    this.pos = 0;
    this.state = INITIAL;
    this.text.setLength(0);
  }

  private void begin(int state) {
    this.state = state;
  }

  private void skip() {
    this.pos++;
  }

  private char get() {
    if (this.pos < this.line.length())
      return this.line.charAt(this.pos++);
    return EOL;
  }

  private char peek() {
    if (this.pos < this.line.length())
      return this.line.charAt(this.pos);
    return EOL;
  }

  private void initialState(ArrayList<Token> tokens) {
    // next input char
    char c = this.get();
    switch (c) {
      /* SIMPLE TOKENS */
      case ',':
        tokens.add(new Token(Token.COMMA));  break;
      case '(':
        tokens.add(new Token(Token.LPAREN)); break;
      case ')':
        tokens.add(new Token(Token.RPAREN)); break;
      case ':':
        tokens.add(new Token(Token.COLON));  break;
      case '.':
        tokens.add(new Token(Token.DOT));    break;
      /* COMMENT */
      case ';': case '#':
        this.eol = true;
        break;
      /* HEX AND BINARY NUMBER */
      case '0':
        this.text.setLength(0);
        switch (this.peek()) {
          /* HEX */
          case 'x':
            this.begin(HEXNUM);
            this.skip();
            break;
          /* BINARY */
          case 'b':
            this.begin(BINNUM);
            this.skip();
            break;
          /* INT */
          default:
            this.begin(INTNUM);
            this.text.append(c);
        }
        break;
      /* INT */
      case '-': case '+':
      case '1': case '2': case '3':
      case '4': case '5': case '6':
      case '7': case '8': case '9':
        this.begin(INTNUM);
        this.text.setLength(0);
        this.text.append(c);
        break;
      /* STRING */
      case '"':
        this.begin(STRING);
        this.text.setLength(0);
        break;
      /* CHARACTER */
      case '\'':
        this.text.setLength(0);
        this.begin(CHARACTER);
        break;
      /* WHITESPACE */
      case '\t': case '\b': case '\f':
      case '\r': case ' ': case 11:
        break;
      case EOL:
        this.eol = true;
        break;
      default:
        if (c >= 'a' && c <= 'z' || c == '_') {
          this.begin(IDENTIFIER);
          this.text.setLength(0);
          this.text.append(c);
          break;
        }
        Message.panic("syntax: invalid character: '" + c + "'");
    }
  }

  private void parseInt(ArrayList<Token> tokens, int base) {
    this.begin(INITIAL);
    String number = this.text.toString();
    try {
      if (base == 10)
        tokens.add(new Token(Token.NUMBER, Integer.parseInt(number, base)));
      else
        tokens.add(new Token(Token.NUMBER, Integer.parseUnsignedInt(number, base)));
    } catch (NumberFormatException e) {
      Message.panic("syntax: invalid number: " + number);
    }
  }

  private void intNumState(ArrayList<Token> tokens) {
    char c = this.peek();
    if (c >= '0' && c <= '9') {
      this.text.append(c);
      this.skip();
    } else
      this.parseInt(tokens, 10);
  }

  private void hexNumState(ArrayList<Token> tokens) {
    char c = this.peek();
    if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f')) {
      this.text.append(c);
      this.skip();
    } else
      this.parseInt(tokens, 16);
  }

  private void binNumState(ArrayList<Token> tokens) {
    char c = this.peek();
    if (c == '0' || c == '1') {
      this.text.append(c);
      this.skip();
    } else
      this.parseInt(tokens, 2);
  }

  private void stringState(ArrayList<Token> tokens) {
    char c = this.get();
    switch (c) {
      case '\\':
        this.begin(STRBACKSLASH);
        break;
      case '"':
        this.begin(INITIAL);
        tokens.add(new Token(Token.STRING, this.text.toString()));
        break;
      case EOL:
        Message.panic("syntax: unterminated string constant: " + this.text.toString());
        break;
      default:
        this.text.append(c);
    }
  }

  private void charState(ArrayList<Token> tokens) {
    char c = this.get();
    switch (c) {
      case '\\':
        this.begin(CHARBACKSLASH);
        break;
      case '\'':
        this.begin(INITIAL);
        String ch = this.text.toString();
        if (ch.length() == 1) {
          tokens.add(new Token(Token.NUMBER, (int)ch.charAt(0)));
          break;
        }
        Message.panic("syntax: invalid character: " + ch);
        break;
      case EOL:
        Message.panic("syntax: unterminated char constant");
        break;
      default:
        this.text.append(c);
    }
  }

  private void backslashState(int nextState) {
    char c = this.get();
    switch (c) {
      case 'n':
        this.text.append('\n');
        break;
      case 't':
        this.text.append('\t');
        break;
      case 'f':
        this.text.append('\f');
        break;
      case 'b':
        this.text.append('\b');
        break;
      case 'v':
        this.text.append((char)11);
        break;
      case '"':
        this.text.append('"');
        break;
      case '\'':
        this.text.append('\'');
        break;
      case '\n':
        this.text.append('\n');
        break;
      case '\\':
        this.text.append('\\');
        break;
      default:
        this.text.append(c);
    }
    this.begin(nextState);
  }

  private void strBackslashState() {
    this.backslashState(STRING);
  }

  private void charBackslashState() {
    this.backslashState(CHARACTER);
  }

  private void identifierState(ArrayList<Token> tokens) {
    char c = this.peek();
    if ((c >= 'a' && c <= 'z') || c == '_' || (c >= '0' && c <= '9')) {
      this.text.append(c);
      this.skip();
    } else {
      this.begin(INITIAL);
      String id = this.text.toString();
      if (State.regfile.isValidRegister(id))
        tokens.add(new Token(Token.REGISTER, id));
      else if (InstructionSet.insts.validInstruction(id))
        tokens.add(new Token(Token.INSTRUCTION, id));
      else
        tokens.add(new Token(Token.IDENTIFIER, id));
    }
  }

  private ArrayList<Token> tokenizeLine(String line) {
    ArrayList<Token> tokens = new ArrayList<Token>();
    this.setCurrentLine(line);
    while(!this.eol) {
      switch (this.state) {
        case INITIAL:
          this.initialState(tokens);    break;
        case INTNUM:
          this.intNumState(tokens);     break;
        case HEXNUM:
          this.hexNumState(tokens);     break;
        case BINNUM:
          this.binNumState(tokens);     break;
        case STRING:
          this.stringState(tokens);     break;
        case STRBACKSLASH:
          this.strBackslashState();     break;
        case CHARACTER:
          this.charState(tokens);       break;
        case CHARBACKSLASH:
          this.charBackslashState();    break;
        case IDENTIFIER:
          this.identifierState(tokens); break;
      }
    }
    tokens.trimToSize();
    return tokens;
  }

  public static ArrayList<Token> tokenize(String line) {
    return lexer.tokenizeLine(line);
  }

}
