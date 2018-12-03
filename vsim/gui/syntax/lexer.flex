/*
This is the lexical analysis of the VSim simulator. This file
is used with JFlex to generate a Lexer class. The content of
this file represents all the lexical rules of the RISC-V assembly
language.

Copyright (C) 2018 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package vsim.gui.syntax;

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
  this.prevState = YYINITIAL;
%init}

%eofval{
  return symbol("eof");
%eofval}


%public
%class Lexer
%final
%type Token
%column
%full

%state STRING
%state SBACKSLASH
%state CHARACTER
%state COMMENT

// syntax
DOT = "."
COMMA = ","
LPAREN = "("
RPAREN = ")"

// operators
TIMES = "*"
DIVIDE = "/"
MOD = "%"
PLUS = "+"
MINUS = "-"
SLL = "<<"
SRL = ">>>"
SRA = ">>"
AND = "&"
OR = "|"
XOR = "^"
NEG = "~"

// strings and chars
STRSTART = \"
BACKSLASH = "\\"
CHAR = .
CHARSTART = \'

// directives
D_ZERO = ".zero"
D_SPACE = ".space"
D_ASCIIZ = ".asciiz"
D_ASCIZ = ".asciz"
D_STRING = ".string"
D_ASCII = ".ascii"
D_BYTE = ".byte"
D_HALF = ".half"
D_SHORT = ".short"
D_2BYTE = ".2byte"
D_WORD = ".word"
D_LONG = ".long"
D_4BYTE = ".4byte"
D_FLOAT = ".float"
D_ALIGN = ".align"
D_P2ALIGN = ".p2align"
D_BALIGN = ".balign"
D_GLOBL = ".globl"
D_GLOBAL = ".global"
D_SECTION = ".section"
D_DATA = ".data"
D_TEXT = ".text"
D_RODATA = ".rodata"
D_BSS = ".bss"

// B Format
I_BEQ = [bB][eE][qQ]
I_BGE = [bB][gG][eE]
I_BGEU = [bB][gG][eE][uU]
I_BLT = [bB][lL][tT]
I_BLTU = [bB][lL][tT][uU]
I_BNE = [bB][nN][eE]
// B Pseudos
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

// U Format
I_AUIPC = [aA][uU][iI][pP][cC]
I_LUI = [lL][uU][iI]

// J Format
I_JAL = [jJ][aA][lL]
// J Pseudos
I_J = [jJ]

// R Format
I_ADD = [aA][dD][dD]
I_AND = [aA][nN][dD]
I_DIVU = [dD][iI][vV][uU]
I_DIV = [dD][iI][vV]
I_MULHSU = [mM][uU][lL][hH][sS][uU]
I_MULHU = [mM][uU][lL][hH][uU]
I_MULH = [mM][uU][lL][hH]
I_MUL = [mM][uU][lL]
I_OR = [oO][rR]
I_REMU = [rR][eE][mM][uU]
I_REM = [rR][eE][mM]
I_SLTU = [sS][lL][tT][uU]
I_SLL = [sS][lL][lL]
I_SLT = [sS][lL][tT]
I_SRA = [sS][rR][aA]
I_SRL = [sS][rR][lL]
I_SUB = [sS][uU][bB]
I_XOR = [xX][oO][rR]
// R Pseudos
I_NEG = [nN][eE][gG]
I_SNEZ = [sS][nN][eE][zZ]
I_SLTZ = [sS][lL][tT][zZ]
I_SGTZ = [sS][gG][tT][zZ]
// single-precision floating point
F_FMVWX = [fF][mM][vV]"."[wW]"."[xX]
F_FMVXW = [fF][mM][vV]"."[xX]"."[wW]
F_FMVSX = [fF][mM][vV]"."[sS]"."[xX]
F_FMVXS = [fF][mM][vV]"."[xX]"."[sS]
F_FCVTSW = [fF][cC][vV][tT]"."[sS]"."[wW]
F_FCVTSWU = [fF][cC][vV][tT]"."[sS]"."[wW][uU]
F_FCVTWS = [fF][cC][vV][tT]"."[wW]"."[sS]
F_FCVTWUS = [fF][cC][vV][tT]"."[wW][uU]"."[sS]
F_FADDS = [fF][aA][dD][dD]"."[sS]
F_FSUBS = [fF][sS][uU][bB]"."[sS]
F_FMULS = [fF][mM][uU][lL]"."[sS]
F_FDIVS = [fF][dD][iI][vV]"."[sS]
F_FSQRTS = [fF][sS][qQ][rR][tT]"."[sS]
F_FMADDS = [fF][mM][aA][dD][dD]"."[sS]
F_FMSUBS = [fF][mM][sS][uU][bB]"."[sS]
F_FNMSUBS = [fF][nN][mM][sS][uU][bB]"."[sS]
F_FNMADDS = [fF][nN][mM][aA][dD][dD]"."[sS]
F_FSGNJS = [fF][sS][gG][nN][jJ]"."[sS]
F_FSGNJNS = [fF][sS][gG][nN][jJ][nN]"."[sS]
F_FSGNJXS = [fF][sS][gG][nN][jJ][xX]"."[sS]
F_FMINS = [fF][mM][iI][nN]"."[sS]
F_FMAXS = [fF][mM][aA][xX]"."[sS]
F_FEQS = [fF][eE][qQ]"."[sS]
F_FLTS = [fF][lL][tT]"."[sS]
F_FLES = [fF][lL][eE]"."[sS]
F_FCLASSS = [fF][cC][lL][aA][sS][sS]"."[sS]
// floating point Pseudos
F_FMVS = [fF][mM][vV]"."[sS]
F_FABSS = [fF][aA][bB][sS]"."[sS]
F_FNEGS = [fF][nN][eE][gG]"."[sS]

// I Format
I_ADDI = [aA][dD][dD][iI]
I_ANDI = [aA][nN][dD][iI]
I_ECALL = [eE][cC][aA][lL][lL]
I_BREAK = [eE][bB][rR][eE][aA][kK]
I_JALR = [jJ][aA][lL][rR]
I_LB = [lL][bB]
I_LBU = [lL][bB][uU]
I_LH = [lL][hH]
I_LHU = [lL][hH][uU]
I_LW = [lL][wW]
I_ORI = [oO][rR][iI]
I_SLLI = [sS][lL][lL][iI]
I_SLTI = [sS][lL][tT][iI]
I_SLTIU = [sS][lL][tT][iI][uU]
I_SRAI = [sS][rR][aA][iI]
I_SRLI = [sS][rR][lL][iI]
I_XORI = [xX][oO][rR][iI]
// I Pseudos
I_NOP = [nN][oO][pP]
I_MV = [mM][vV]
I_NOT = [nN][oO][tT]
I_SEQZ = [sS][eE][qQ][zZ]
I_JR = [jJ][rR]
I_RET = [rR][eE][tT]
// single-precision floating point
F_FLW = [fF][lL][wW]

// S Format
I_SB = [sS][bB]
I_SH = [sS][hH]
I_SW = [sS][wW]
// single-precision floating point
F_FSW = [fF][sS][wW]

// Pseudos
I_LA = [lL][aA]
I_LI = [lL][iI]
I_CALL = [cC][aA][lL][lL]
I_TAIL = [tT][aA][iI][lL]

// registers
R_NUMBER = [xX]([0-9]|[12][0-9]|[3][01])
R_NAME = ("zero"|"ra"|"sp"|"gp"|"tp"|"fp"|"t"[0-6]|"a"[0-7]|"s"([0-9]|1[01]))
REGISTER = ({R_NUMBER}|{R_NAME})

// floating point registers
F_NUMBER = [fF]([0-9]|[12][0-9]|[3][01])
F_NAME = ("ft"([0-9]|1[01])|"fa"[0-8]|"fs"([0-9]|1[01]))
FREGISTER = ({F_NUMBER}|{F_NAME})

// ids
IDENTIFIER = [a-zA-Z_]([a-zA-Z0-9_]*("."[a-zA-Z0-9_]+)?)

// labels
LABEL = {IDENTIFIER}:

// numbers
NUMBER = [+-]?[0-9]+
HEXADECIMAL = 0[xX][0-9a-fA-F]+
BINARY = 0[bB][01]+

// floats
FLOAT = [+-]?[0-9]*\.?[0-9]+([eE][-+]?[0-9]+)?

// valid whitespace
WHITESPACE = (" "|\t|\r|\n)

// newline
NEWLINE = \n|\r\n

// comments
STARTOFSCOMMENT = ";"|"#"

// everything else error
ERROR = .
%%

<YYINITIAL>{

  // syntax symbols
  {DOT} {
    return symbol("syntax");
  }

  {COMMA}  {
    return symbol("syntax");
  }

  {LPAREN} {
    return symbol("syntax");
  }

  {RPAREN} {
    return symbol("syntax");
  }

  // operators
  {TIMES} {
    return symbol("operator");
  }

  {DIVIDE} {
    return symbol("operator");
  }

  {MOD} {
    return symbol("operator");
  }

  {PLUS} {
    return symbol("operator");
  }

  {MINUS} {
    return symbol("operator");
  }

  {SLL} {
    return symbol("operator");
  }

  {SRL} {
    return symbol("operator");
  }

  {SRA} {
    return symbol("operator");
  }

  {AND} {
    return symbol("operator");
  }

  {OR} {
    return symbol("operator");
  }

  {XOR} {
    return symbol("operator");
  }

  {NEG} {
    return symbol("operator");
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
  {STARTOFSCOMMENT} {
    yybegin(COMMENT);
    return symbol("comment");
  }

  // labels
  {LABEL} {
    return symbol("label");
  }

  // directives
  {D_ZERO} {
    return symbol("directive");
  }

  {D_SPACE} {
    return symbol("directive");
  }

  {D_ASCIIZ} {
    return symbol("directive");
  }

  {D_ASCIZ} {
    return symbol("directive");
  }

  {D_ASCII} {
    return symbol("directive");
  }

  {D_STRING} {
    return symbol("directive");
  }

  {D_BYTE} {
    return symbol("directive");
  }

  {D_HALF} {
    return symbol("directive");
  }

  {D_SHORT} {
    return symbol("directive");
  }

  {D_2BYTE} {
    return symbol("directive");
  }

  {D_WORD} {
    return symbol("directive");
  }

  {D_LONG} {
    return symbol("directive");
  }

  {D_4BYTE} {
    return symbol("directive");
  }

  {D_ALIGN} {
    return symbol("directive");
  }

  {D_P2ALIGN} {
    return symbol("directive");
  }

  {D_BALIGN} {
    return symbol("directive");
  }

  {D_GLOBL} {
    return symbol("directive");
  }

  {D_GLOBAL} {
    return symbol("directive");
  }

  {D_SECTION} {
    return symbol("directive");
  }

  {D_DATA} {
    return symbol("directive");
  }

  {D_TEXT} {
    return symbol("directive");
  }

  {D_RODATA} {
    return symbol("directive");
  }

  {D_BSS} {
    return symbol("directive");
  }

  {D_FLOAT} {
    return symbol("directive");
  }

  // B Format
  {I_BEQ} {
    return symbol("keyword");
  }

  {I_BGE} {
    return symbol("keyword");
  }

  {I_BGEU} {
    return symbol("keyword");
  }

  {I_BLT} {
    return symbol("keyword");
  }

  {I_BLTU} {
    return symbol("keyword");
  }

  {I_BNE} {
    return symbol("keyword");
  }

  // U Format
  {I_AUIPC} {
    return symbol("keyword");
  }

  {I_LUI} {
    return symbol("keyword");
  }

  // J Format
  {I_JAL} {
    return symbol("keyword");
  }

  // S Format
  {I_SB} {
    return symbol("keyword");
  }

  {I_SH} {
    return symbol("keyword");
  }

  {I_SW} {
    return symbol("keyword");
  }

  {F_FSW} {
    return symbol("keyword");
  }

  // R Format
  {I_ADD} {
    return symbol("keyword");
  }

  {I_AND} {
    return symbol("keyword");
  }

  {I_DIV} {
    return symbol("keyword");
  }

  {I_DIVU} {
    return symbol("keyword");
  }

  {I_MULHSU} {
    return symbol("keyword");
  }

  {I_MULHU} {
    return symbol("keyword");
  }

  {I_MUL} {
    return symbol("keyword");
  }

  {I_MULH} {
    return symbol("keyword");
  }

  {I_OR} {
    return symbol("keyword");
  }

  {I_REM} {
    return symbol("keyword");
  }

  {I_REMU} {
    return symbol("keyword");
  }

  {I_SLTU} {
    return symbol("keyword");
  }

  {I_SLL} {
    return symbol("keyword");
  }

  {I_SLT} {
    return symbol("keyword");
  }

  {I_SRA} {
    return symbol("keyword");
  }

  {I_SRL} {
    return symbol("keyword");
  }

  {I_SUB} {
    return symbol("keyword");
  }

  {I_XOR} {
    return symbol("keyword");
  }

  {F_FMVWX} {
    return symbol("keyword");
  }

  {F_FMVSX} {
    return symbol("keyword");
  }

  {F_FMVXW} {
    return symbol("keyword");
  }

  {F_FMVXS} {
    return symbol("keyword");
  }

  {F_FCVTSW} {
    return symbol("keyword");
  }

  {F_FCVTSWU} {
    return symbol("keyword");
  }

  {F_FCVTWS} {
    return symbol("keyword");
  }

  {F_FCVTWUS} {
    return symbol("keyword");
  }

  {F_FADDS} {
    return symbol("keyword");
  }

  {F_FSUBS} {
    return symbol("keyword");
  }

  {F_FMULS} {
    return symbol("keyword");
  }

  {F_FDIVS} {
    return symbol("keyword");
  }

  {F_FSQRTS} {
    return symbol("keyword");
  }

  {F_FMADDS} {
    return symbol("keyword");
  }

  {F_FMSUBS} {
    return symbol("keyword");
  }

  {F_FNMSUBS} {
    return symbol("keyword");
  }

  {F_FNMADDS} {
    return symbol("keyword");
  }

  {F_FSGNJS} {
    return symbol("keyword");
  }

  {F_FSGNJNS} {
    return symbol("keyword");
  }

  {F_FSGNJXS} {
    return symbol("keyword");
  }

  {F_FMINS} {
    return symbol("keyword");
  }

  {F_FMAXS} {
    return symbol("keyword");
  }

  {F_FEQS} {
    return symbol("keyword");
  }

  {F_FLTS} {
    return symbol("keyword");
  }

  {F_FLES} {
    return symbol("keyword");
  }

  {F_FCLASSS} {
    return symbol("keyword");
  }

  // I Format
  {I_ADDI} {
    return symbol("keyword");
  }

  {I_ANDI} {
    return symbol("keyword");
  }

  {I_ECALL} {
    return symbol("keyword");
  }

  {I_BREAK} {
    return symbol("keyword");
  }

  {I_JALR} {
    return symbol("keyword");
  }

  {I_LB} {
    return symbol("keyword");
  }

  {I_LBU} {
    return symbol("keyword");
  }

  {I_LH} {
    return symbol("keyword");
  }

  {I_LHU} {
    return symbol("keyword");
  }

  {I_LW} {
    return symbol("keyword");
  }

  {I_ORI} {
    return symbol("keyword");
  }

  {I_SLLI} {
    return symbol("keyword");
  }

  {I_SLTI} {
    return symbol("keyword");
  }

  {I_SLTIU} {
    return symbol("keyword");
  }

  {I_SRAI} {
    return symbol("keyword");
  }

  {I_SRLI} {
    return symbol("keyword");
  }

  {I_XORI} {
    return symbol("keyword");
  }

  {F_FLW} {
    return symbol("keyword");
  }

  // Pseudos
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

  {I_BLTU} {
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

  {F_FMVS} {
    return symbol("keyword");
  }

  {F_FABSS} {
    return symbol("keyword");
  }

  {F_FNEGS} {
    return symbol("keyword");
  }

  // registers
  {REGISTER} {
    return symbol("register");
  }

  {FREGISTER} {
    return symbol("register");
  }

  // identifiers
  {IDENTIFIER} {
    return symbol("identifier");
  }

  // number
  {NUMBER} {
    return symbol("number");
  }

  // floats
  {FLOAT} {
    return symbol("number");
  }

  // hex number
  {HEXADECIMAL} {
    return symbol("number");
  }

  // binary number
  {BINARY} {
    return symbol("number");
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
    this.prevState = STRING;
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
    this.prevState = CHARACTER;
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

  "0" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "n" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "t" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "f" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "b" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "v" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "r" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "\"" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "\'" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "\n" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  "\\" {
    yybegin(this.prevState);
    return symbol("stringb");
  }

  {CHAR} {
    yybegin(this.prevState);
    return symbol("stringb");
  }

}

<COMMENT> {

  . {
    return symbol("comment");
  }

  {NEWLINE} {
    yybegin(YYINITIAL);
    return symbol("empty");
  }

}
