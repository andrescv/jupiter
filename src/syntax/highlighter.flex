package jupiter.gui.highlighting;

%%

%{
  /** previous jflex state */
  private int prevState;

  /**
   * This method creates a new Token
   *
   * @param style token style class
   * @param length token length
   */
  private Token symbol(String style, int length) {
    return new Token(style, length);
  }

  /**
   * This method creates a new Token
   *
   * @param style token style class
   */
  private Token symbol(String style) {
    return this.symbol(style, yytext().length());
  }

%}

%init{
  prevState = YYINITIAL;
%init}

%eofval{
  return symbol("eof");
%eofval}

%public
%final
%class Lexer
%type Token
%line
%column
%unicode

%state COMMENT
%state STRING
%state SBACKSLASH
%state CHARACTER

// other language lements
COMMA = ","
LPAREN = "("
RPAREN = ")"

// platoform independent newline
NEWLINE = \R

// strings and chars
STRSTART = \"
BACKSLASH = \\
CHAR = .
CHARSTART = \'

// comments
COMMENTSTART = ("#"|";")

// keywords
I_LUI = [lL][uU][iI]
I_AUIPC = [aA][uU][iI][pP][cC]
I_JAL = [jJ][aA][lL]
I_JALR = [jJ][aA][lL][rR]
I_BEQ = [bB][eE][qQ]
I_BNE = [bB][nN][eE]
I_BLT = [bB][lL][tT]
I_BGE = [bB][gG][eE]
I_BLTU = [bB][lL][tT][uU]
I_BGEU = [bB][gG][eE][uU]
I_LB = [lL][bB]
I_LH = [lL][hH]
I_LW = [lL][wW]
I_LBU = [lL][bB][uU]
I_LHU = [lL][hH][uU]
I_SB = [sS][bB]
I_SH = [sS][hH]
I_SW = [sS][wW]
I_ADDI = [aA][dD][dD][iI]
I_SLTI = [sS][lL][tT][iI]
I_SLTIU = [sS][lL][tT][iI][uU]
I_XORI = [xX][oO][rR][iI]
I_ORI = [oO][rR][iI]
I_ANDI = [aA][nN][dD][iI]
I_SLLI = [sS][lL][lL][iI]
I_SRLI = [sS][rR][lL][iI]
I_SRAI = [sS][rR][aA][iI]
I_ADD = [aA][dD][dD]
I_SUB = [sS][uU][bB]
I_SLL = [sS][lL][lL]
I_SLT = [sS][lL][tT]
I_SLTU = [sS][lL][tT][uU]
I_XOR = [xX][oO][rR]
I_SRL = [sS][rR][lL]
I_SRA = [sS][rR][aA]
I_OR = [oO][rR]
I_AND = [aA][nN][dD]
I_FENCE = [fF][eE][nN][cC][eE]
I_ECALL = [eE][cC][aA][lL][lL]
I_EBREAK = [eE][bB][rR][eE][aA][kK]
// RV32 Zicsr
I_CSRRW = [cC][sS][rR][rR][wW]
I_CSRRS = [cC][sS][rR][rR][sS]
I_CSRRC = [cC][sS][rR][rR][cC]
I_CSRRWI = [cC][sS][rR][rR][wW][iI]
I_CSRRSI = [cC][sS][rR][rR][sS][iI]
I_CSRRCI = [cC][sS][rR][rR][cC][iI]
// RV32M
I_MUL = [mM][uU][lL]
I_MULH = [mM][uU][lL][hH]
I_MULHSU = [mM][uU][lL][hH][sS][uU]
I_MULHU = [mM][uU][lL][hH][uU]
I_DIV = [dD][iI][vV]
I_DIVU = [dD][iI][vV][uU]
I_REM = [rR][eE][mM]
I_REMU = [rR][eE][mM][uU]
// RV32F
I_FLW = [fF][lL][wW]
I_FSW = [fF][sS][wW]
I_FMADDS = [fF][mM][aA][dD][dD]"."[sS]
I_FMSUBS = [fF][mM][sS][uU][bB]"."[sS]
I_FNMSUBS = [fF][nN][mM][sS][uU][bB]"."[sS]
I_FNMADDS = [fF][nN][mM][aA][dD][dD]"."[sS]
I_FADDS = [fF][aA][dD][dD]"."[sS]
I_FSUBS = [fF][sS][uU][bB]"."[sS]
I_FMULS = [fF][mM][uU][lL]"."[sS]
I_FDIVS = [fF][dD][iI][vV]"."[sS]
I_FSQRTS = [fF][sS][qQ][rR][tT]"."[sS]
I_FSGNJS = [fF][sS][gG][nN][jJ]"."[sS]
I_FSGNJNS = [fF][sS][gG][nN][jJ][nN]"."[sS]
I_FSGNJXS = [fF][sS][gG][nN][jJ][xX]"."[sS]
I_FMINS = [fF][mM][iI][nN]"."[sS]
I_FMAXS = [fF][mM][aA][xX]"."[sS]
I_FCVTWS = [fF][cC][vV][tT]"."[wW]"."[sS]
I_FCVTWUS = [fF][cC][vV][tT]"."[wW][uU]"."[sS]
I_FMVXW = [fF][mM][vV]"."[xX]"."[wW]
I_FEQS = [fF][eE][qQ]"."[sS]
I_FLTS = [fF][lL][tT]"."[sS]
I_FLES = [fF][lL][eE]"."[sS]
I_FCLASSS = [fF][cC][lL][aA][sS][sS]"."[sS]
I_FCVTSW = [fF][cC][vV][tT]"."[sS]"."[wW]
I_FCVTSWU = [fF][cC][vV][tT]"."[sS]"."[wW][uU]
I_FMVWX = [fF][mM][vV]"."[wW]"."[xX]
// pseudos
I_LA = [lL][aA]
I_NOP = [nN][oO][pP]
I_LI = [lL][iI]
I_MV = [mM][vV]
I_NOT = [nN][oO][tT]
I_NEG = [nN][eE][gG]
I_SEQZ = [sS][eE][qQ][zZ]
I_SNEZ = [sS][nN][eE][zZ]
I_SLTZ = [sS][lL][tT][zZ]
I_SGTZ = [sS][gG][tT][zZ]
I_FMVS = [fF][mM][vV]"."[sS]
I_FABSS = [fF][aA][bB][sS]"."[sS]
I_FNEGS = [fF][nN][eE][gG]"."[sS]
I_BEQZ = [bB][eE][qQ][zZ]
I_BNEZ = [bB][nN][eE][zZ]
I_BLEZ = [bB][lL][eE][zZ]
I_BGEZ = [bB][gG][eE][zZ]
I_BLTZ = [bB][lL][tT][zZ]
I_BGTZ = [bB][gG][tT][zZ]
I_BGT = [bB][gG][tT]
I_BLE = [bB][lL][eE]
I_BGTU = [bB][gG][tT][uU]
I_BLEU = [bB][lL][eE][uU]
I_J = [jJ]
I_JR = [jJ][rR]
I_RET = [rR][eE][tT]
I_CALL = [cC][aA][lL][lL]
I_TAIL = [tT][aA][iI][lL]
I_CSRR = [cC][sS][rR][rR]
I_CSRW = [cC][sS][rR][wW]
I_CSRS = [cC][sS][rR][sS]
I_CSRC = [cC][sS][rR][cC]
I_CSRWI = [cC][sS][rR][wW][iI]
I_CSRSI = [cC][sS][rR][sS][iI]
I_CSRCI = [cC][sS][rR][cC][iI]
I_FRCSR = [fF][rR][cC][sS][rR]
I_FSCSR = [fF][sS][cC][sS][rR]
I_FRRM = [fF][rR][rR][mM]
I_FSRM = [fF][sS][rR][mM]
I_FRFLAGS = [fF][rR][fF][lL][aA][gG][sS]
I_FSFLAGS = [fF][sS][fF][lL][aA][gG][sS]

// registers
XREG = ([xX][0-9]+|"zero"|"ra"|"sp"|"gp"|"tp"|"fp"|"t"[0-9]+|"a"[0-9]+|"s"[0-9]+)
FREG = ([fF][0-9]+|"ft"[0-9]+|"fa"[0-9]+|"fs"[0-9]+)

// identifier
ID = [a-zA-Z_$]([a-zA-Z0-9_$]*("."[a-zA-Z0-9_"$"]+)?)*

// labels
LABEL = {ID}:

// directives
D_DIRECTIVE = "."{ID}

// relocation functions
HI = "%hi"
LO = "%lo"
PCREL_HI = "%pcrel_hi"
PCREL_LO = "%pcrel_lo"

// numbers
INT = [+-]?[0-9]+
HEX = 0[xX][0-9a-fA-F]+
BIN = 0[bB][01]+
FLOAT = [+-]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?

// valid whitespace
WHITESPACE = (" "|\t)

// everything else error
ERROR = .

%%

<YYINITIAL>{

  // other language elements
  {COMMA}  {
    return symbol("syntax");
  }

  {LPAREN} {
    return symbol("syntax");
  }

  {RPAREN} {
    return symbol("syntax");
  }

  // strings
  {STRSTART} {
    yybegin(STRING);
    return symbol("string");
  }

  // chars
  {CHARSTART} {
    yybegin(CHARACTER);
    return symbol("string");
  }

  // comments
  {COMMENTSTART} {
    yybegin(COMMENT);
    return symbol("comment");
  }

  // labels
  {LABEL} {
    return symbol("lbl");
  }

  // directives
  {D_DIRECTIVE} {
    return symbol("directive");
  }

  // relocation functions
  {HI} {
    return symbol("syntax");
  }

  {LO} {
    return symbol("syntax");
  }

  {PCREL_HI} {
    return symbol("syntax");
  }

  {PCREL_LO} {
    return symbol("syntax");
  }

  // keywords
  {I_LUI} {
    return symbol("keyword");
  }

  {I_AUIPC} {
    return symbol("keyword");
  }

  {I_JAL} {
    return symbol("keyword");
  }

  {I_JALR} {
    return symbol("keyword");
  }

  {I_BEQ} {
    return symbol("keyword");
  }

  {I_BNE} {
    return symbol("keyword");
  }

  {I_BLT} {
    return symbol("keyword");
  }

  {I_BGE} {
    return symbol("keyword");
  }

  {I_BLTU} {
    return symbol("keyword");
  }

  {I_BGEU} {
    return symbol("keyword");
  }

  {I_LB} {
    return symbol("keyword");
  }

  {I_LH} {
    return symbol("keyword");
  }

  {I_LW} {
    return symbol("keyword");
  }

  {I_LBU} {
    return symbol("keyword");
  }

  {I_LHU} {
    return symbol("keyword");
  }

  {I_SB} {
    return symbol("keyword");
  }

  {I_SH} {
    return symbol("keyword");
  }

  {I_SW} {
    return symbol("keyword");
  }

  {I_ADDI} {
    return symbol("keyword");
  }

  {I_SLTI} {
    return symbol("keyword");
  }

  {I_SLTIU} {
    return symbol("keyword");
  }

  {I_XORI} {
    return symbol("keyword");
  }

  {I_ORI} {
    return symbol("keyword");
  }

  {I_ANDI} {
    return symbol("keyword");
  }

  {I_SLLI} {
    return symbol("keyword");
  }

  {I_SRLI} {
    return symbol("keyword");
  }

  {I_SRAI} {
    return symbol("keyword");
  }

  {I_ADD} {
    return symbol("keyword");
  }

  {I_SUB} {
    return symbol("keyword");
  }

  {I_SLL} {
    return symbol("keyword");
  }

  {I_SLT} {
    return symbol("keyword");
  }

  {I_SLTU} {
    return symbol("keyword");
  }

  {I_XOR} {
    return symbol("keyword");
  }

  {I_SRL} {
    return symbol("keyword");
  }

  {I_SRA} {
    return symbol("keyword");
  }

  {I_OR} {
    return symbol("keyword");
  }

  {I_AND} {
    return symbol("keyword");
  }

  {I_FENCE} {
    return symbol("keyword");
  }

  {I_ECALL} {
    return symbol("keyword");
  }

  {I_EBREAK} {
    return symbol("keyword");
  }

  {I_CSRRW} {
    return symbol("keyword");
  }

  {I_CSRRS} {
    return symbol("keyword");
  }

  {I_CSRRC} {
    return symbol("keyword");
  }

  {I_CSRRWI} {
    return symbol("keyword");
  }

  {I_CSRRSI} {
    return symbol("keyword");
  }

  {I_CSRRCI} {
    return symbol("keyword");
  }

  {I_MUL} {
    return symbol("keyword");
  }

  {I_MULH} {
    return symbol("keyword");
  }

  {I_MULHSU} {
    return symbol("keyword");
  }

  {I_MULHU} {
    return symbol("keyword");
  }

  {I_DIV} {
    return symbol("keyword");
  }

  {I_DIVU} {
    return symbol("keyword");
  }

  {I_REM} {
    return symbol("keyword");
  }

  {I_REMU} {
    return symbol("keyword");
  }

  {I_FLW} {
    return symbol("keyword");

  }

  {I_FSW} {
    return symbol("keyword");
  }

  {I_FMADDS} {
    return symbol("keyword");
  }

  {I_FMSUBS} {
    return symbol("keyword");
  }

  {I_FNMSUBS} {
    return symbol("keyword");
  }

  {I_FNMADDS} {
    return symbol("keyword");
  }

  {I_FADDS} {
    return symbol("keyword");
  }

  {I_FSUBS} {
    return symbol("keyword");
  }

  {I_FMULS} {
    return symbol("keyword");
  }

  {I_FDIVS} {
    return symbol("keyword");
  }

  {I_FSQRTS} {
    return symbol("keyword");
  }

  {I_FSGNJS} {
    return symbol("keyword");
  }

  {I_FSGNJNS} {
    return symbol("keyword");
  }

  {I_FSGNJXS} {
    return symbol("keyword");
  }

  {I_FMINS} {
    return symbol("keyword");
  }

  {I_FMAXS} {
    return symbol("keyword");
  }

  {I_FCVTWS} {
    return symbol("keyword");
  }

  {I_FCVTWUS} {
    return symbol("keyword");
  }

  {I_FMVXW} {
    return symbol("keyword");
  }

  {I_FEQS} {
    return symbol("keyword");
  }

  {I_FLTS} {
    return symbol("keyword");
  }

  {I_FLES} {
    return symbol("keyword");
  }

  {I_FCLASSS} {
    return symbol("keyword");
  }

  {I_FCVTSW} {
    return symbol("keyword");
  }

  {I_FCVTSWU} {
    return symbol("keyword");
  }

  {I_FMVWX} {
    return symbol("keyword");
  }

  {I_LA} {
    return symbol("keyword");
  }

  {I_NOP} {
    return symbol("keyword");
  }

  {I_LI} {
    return symbol("keyword");
  }

  {I_MV} {
    return symbol("keyword");
  }

  {I_NOT} {
    return symbol("keyword");
  }

  {I_NEG} {
    return symbol("keyword");
  }

  {I_SEQZ} {
    return symbol("keyword");
  }

  {I_SNEZ} {
    return symbol("keyword");
  }

  {I_SLTZ} {
    return symbol("keyword");
  }

  {I_SGTZ} {
    return symbol("keyword");
  }

  {I_FMVS} {
    return symbol("keyword");
  }

  {I_FABSS} {
    return symbol("keyword");
  }

  {I_FNEGS} {
    return symbol("keyword");
  }

  {I_BEQZ} {
    return symbol("keyword");
  }

  {I_BNEZ} {
    return symbol("keyword");
  }

  {I_BLEZ} {
    return symbol("keyword");
  }

  {I_BGEZ} {
    return symbol("keyword");
  }

  {I_BLTZ} {
    return symbol("keyword");
  }

  {I_BGTZ} {
    return symbol("keyword");
  }

  {I_BGT} {
    return symbol("keyword");
  }

  {I_BLE} {
    return symbol("keyword");
  }

  {I_BGTU} {
    return symbol("keyword");
  }

  {I_BLEU} {
    return symbol("keyword");
  }

  {I_J} {
    return symbol("keyword");
  }


  {I_JR} {
    return symbol("keyword");
  }

  {I_RET} {
    return symbol("keyword");
  }

  {I_CALL} {
    return symbol("keyword");
  }

  {I_TAIL} {
    return symbol("keyword");
  }

  {I_CSRR} {
    return symbol("keyword");
  }

  {I_CSRW} {
    return symbol("keyword");
  }

  {I_CSRS} {
    return symbol("keyword");
  }

  {I_CSRC} {
    return symbol("keyword");
  }

  {I_CSRWI} {
    return symbol("keyword");
  }

  {I_CSRSI} {
    return symbol("keyword");
  }

  {I_CSRCI} {
    return symbol("keyword");
  }

  {I_FRCSR} {
    return symbol("keyword");
  }

  {I_FSCSR} {
    return symbol("keyword");
  }

  {I_FRRM} {
    return symbol("keyword");
  }

  {I_FSRM} {
    return symbol("keyword");
  }

  {I_FRFLAGS} {
    return symbol("keyword");
  }

  {I_FSFLAGS} {
    return symbol("keyword");
  }

  // registers
  {XREG} {
    return symbol("register");
  }

  {FREG} {
    return symbol("register");
  }

  // identifiers
  {ID} {
    return symbol("identifier");
  }

  // numbers
  {INT} {
    return symbol("number");
  }

  {HEX} {
    return symbol("number");
  }

  {BIN} {
    return symbol("number");
  }

  {FLOAT} {
    return symbol("number");
  }

  // newlines
  {NEWLINE}+ {
    return symbol("empty");
  }

  // ignore whitespace
  {WHITESPACE} {
    return symbol("empty");
  }

  // report error
  {ERROR}  {
    return symbol("error");
  }

}

<STRING> {

  {BACKSLASH} {
    prevState = STRING;
    yybegin(SBACKSLASH);
    return symbol("stringb");
  }

  {NEWLINE} {
    return symbol("string");
  }

  {STRSTART} {
    yybegin(YYINITIAL);
    return symbol("string");
  }

  {CHAR} {
    return symbol("string");
  }

}

<CHARACTER> {

  {BACKSLASH} {
    prevState = CHARACTER;
    yybegin(SBACKSLASH);
    return symbol("stringb");
  }

  {NEWLINE} {
    return symbol("string");
  }

  {CHARSTART} {
    yybegin(YYINITIAL);
    return symbol("string");
  }

  {CHAR} {
    return symbol("string");
  }

}

<SBACKSLASH> {

  "\n" {
    yybegin(prevState);
    return symbol("stringb");
  }

  {CHAR} {
    yybegin(prevState);
    return symbol("stringb");
  }

}

<COMMENT> {

  {CHAR} {
    return symbol("comment");
  }

  {NEWLINE} {
    yybegin(YYINITIAL);
    return symbol("empty");
  }

}
