package vsim.assembler.lexer;


public enum TokenType {
    COMMA,           // ,
    LPAREN,          // (
    RPAREN,          // )
    MINUS,           // -
    PLUS,            // +
    COLON,           // :
    DOT,             // .
    NUMBER,
    STRING,
    REGISTER,
    IDENTIFIER,
}