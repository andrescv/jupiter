package vsim.assembler.lexer;

import vsim.Globals;
import java.util.ArrayList;
import vsim.utils.SyntaxError;


public final class Lexer {

    // lexer states
    private static final int INITIAL    = 0;
    private static final int COMMENT    = 1;
    private static final int STRING     = 2;
    private static final int BACKSLASH  = 3;
    private static final int NUM        = 4;
    private static final int HEX        = 5;
    private static final int BIN        = 6;
    private static final int IDENTIFIER = 7;

    private int state;
    private int lineno;
    private boolean eol;
    private Reader reader;
    private StringBuffer buffer;
    private ArrayList<Token> tokens;

    public Lexer() {
        this.reader = null;
        this.buffer = new StringBuffer();
    }

    public void setInput(String filename) {
        this.reader = new Reader(filename);
        this.lineno = 1;
    }

    // INITIAL state
    private void initial() {
        // get next char from current line
        char c = this.reader.get();
        // get match
        switch (c) {
            /* SIMPLE TOKENS */
            case ',':
                this.tokens.add(this.token(TokenType.COMMA, c));
                break;
            case '(':
                this.tokens.add(this.token(TokenType.LPAREN, c));
                break;
            case ')':
                this.tokens.add(this.token(TokenType.RPAREN, c));
                break;
            case '+':
                this.tokens.add(this.token(TokenType.PLUS, c));
                break;
            case '-':
                this.tokens.add(this.token(TokenType.MINUS, c));
                break;
            case ':':
                this.tokens.add(this.token(TokenType.COLON, c));
                break;
            case '.':
                this.tokens.add(this.token(TokenType.DOT, c));
                break;
            /* COMMENT */
            case '#': case ';':
                this.state = COMMENT;
                break;
            /* QUOTED STRING */
            case '"':
                this.state = STRING;
                this.buffer.setLength(0);
                this.buffer.append(c);
                break;
            /* NUMBER */
            case '0':
                this.buffer.setLength(0);
                switch (this.reader.get()) {
                    /* BINARY STRING */
                    case 'b':
                        this.state = BIN; break;
                    /* HEX STRING */
                    case 'x':
                        this.state = HEX; break;
                    /* INTEGER */
                    default:
                        this.state = NUM;
                        this.buffer.append(c);
                        this.reader.backward();
                }
                break;
            case '1': case '2': case '3':
            case '4': case '5': case '6':
            case '7': case '8': case '9':
                this.buffer.setLength(0);
                this.buffer.append(c);
                this.state = NUM;
                break;
            /* WHITE SPACE */
            case '\t': case '\r': case '\f':
            case ' ' : case '\b': case 11:
                break;
            /* END OF LINE */
            case Reader.EOL:
                this.eol = true;
                break;
            default:
                /* IDENTIFIER */
                if (Character.isAlphabetic(c) || c == '_') {
                    this.state = IDENTIFIER;
                    this.buffer.setLength(0);
                    this.buffer.append(c);
                    break;
                }
                /* UNEXPECTED CHAR */
                this.error(c + "", "unexpected character");
        }
    }

    // COMMENT state
    private void comment() {
        char c = this.reader.get();
        // accept all characters until EOL
        if (c == Reader.EOL)
            this.state = INITIAL;
    }

    // STRING state
    private void string() {
        char c = this.reader.get();
        switch (c) {
            case '\\':
                this.state = BACKSLASH;
                break;
            case '"':
                this.state = INITIAL;
                this.buffer.append(c);
                this.tokens.add(
                    this.token(
                        TokenType.STRING,
                        this.buffer.toString()
                    )
                );
                break;
            case Reader.EOL:
                this.state = INITIAL;
                this.error(
                    this.buffer.toString(),
                    "unterminated string constant"
                );
                break;
            default:
                this.buffer.append(c);
        }
    }

    // BACKSLASH state
    private void backslash() {
        char c = this.reader.get();
        switch (c) {
            case '0':
                this.buffer.append('\0'); break;
            case 'n':
                this.buffer.append('\n'); break;
            case 't':
                this.buffer.append('\t'); break;
            case 'f':
                this.buffer.append('\f'); break;
            case 'b':
                this.buffer.append('\b'); break;
            case '"':
                this.buffer.append('\"'); break;
            case '\\':
                this.buffer.append('\\'); break;
            default:
                this.reader.backward();
        }
        this.state = STRING;
    }

    private void addNumber() {
        this.state = INITIAL;
        String value = this.buffer.toString();
        this.tokens.add(this.token(TokenType.NUMBER, value));
        this.reader.backward();
    }

    // HEX state
    private void hex() {
        char c = this.reader.get();
        if ((c >= '0' && c <= '9') || (c >= 'a' && c <= 'f'))
            this.buffer.append(c);
        else
            this.addNumber();
    }

    // NUM state
    private void num() {
        char c = this.reader.get();
        if (c >= '0' && c <= '9')
            this.buffer.append(c);
        else
            this.addNumber();
    }

    // BIN state
    private void bin() {
        char c = this.reader.get();
        if (c == '0' || c == '1')
            this.buffer.append(c);
        else
            this.addNumber();
    }

    // IDENTIFIER state
    private void identifier() {
        char c = this.reader.get();
        if (Character.isAlphabetic(c) || Character.isDigit(c) || c == '_')
            this.buffer.append(c);
        else {
            this.state = INITIAL;
            String value = this.buffer.toString();
            if (Globals.regfile.validRegister(value))
                this.tokens.add(this.token(TokenType.REGISTER, value));
            else
                this.tokens.add(this.token(TokenType.IDENTIFIER, value));
            this.reader.backward();
        }
    }

    private void error(String value, String msg) {
        SyntaxError.emit(
            this.reader.getFilename(),
            this.lineno,
            value,
            msg
        );
    }

    private Token token(TokenType type, char value) {
        return new Token(type, value);
    }

    private Token token(TokenType type, String value) {
        return new Token(type, value);
    }

    public ArrayList<Token> tokenize() {
        // no input file specified
        if (reader == null) return null;
        // try to set next line in reader
        boolean success = this.reader.setNextLine();
        // if success = false => EOF
        if (!success) return null;
        // check line
        this.state = INITIAL;
        this.tokens = new ArrayList<Token>();
        this.eol = false;
        while (true) {
            switch (this.state) {
                case INITIAL:
                    this.initial();    break;
                case COMMENT:
                    this.comment();    break;
                case STRING:
                    this.string();     break;
                case BACKSLASH:
                    this.backslash();  break;
                case HEX:
                    this.hex();        break;
                case NUM:
                    this.num();        break;
                case BIN:
                    this.bin();        break;
                case IDENTIFIER:
                    this.identifier(); break;
            }
            // current line processed
            if (this.eol) break;
        }
        // increment line number
        this.lineno++;
        // return tokens
        this.tokens.trimToSize();
        return this.tokens;
    }

}