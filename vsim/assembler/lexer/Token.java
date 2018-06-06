package vsim.assembler.lexer;


public final class Token {

    private TokenType type;
    private String value;

    protected Token(TokenType type, char value) {
        this(type, "" + value);
    }

    protected Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    public TokenType getType() {
        return this.type;
    }

    public String getValue() {
        return this.value;
    }

    @Override
    public String toString() {
        return String.format(
            "<%s>(%s)",
            this.type.toString(),
            this.value
        );
    }

}