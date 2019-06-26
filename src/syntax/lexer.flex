package vsim.asm;

import java.util.HashMap;
import java_cup.runtime.Symbol;

%%

%{

  // RVI mnemonics
  private static final String[] RVI = {
    "zero", "ra", "sp", "gp", "tp", "t0", "t1", "t2", "s0", "s1", "a0", "a1", "a2",
    "a3", "a4", "a5", "a6", "a7", "s2", "s3", "s4", "s5", "s6", "s7", "s8", "s9", "s10",
    "s11", "t3", "t4", "t5", "t6"
  };

  // RVF mnemonics
  private static final String[] RVF = {
    "ft0", "ft1", "ft2", "ft3", "ft4", "ft5", "ft6", "ft7", "fs0", "fs1", "fa0", "fa1", "fa2",
    "fa3", "fa4", "fa5", "fa6", "fa7", "fs2", "fs3", "fs4", "fs5", "fs6", "fs7", "fs8", "fs9",
    "fs10", "fs11", "ft8", "ft9", "ft10", "ft11"
  };

  /** strings and chars string builder */
  private StringBuilder text;

  /** RVF */
  private HashMap<String, Integer> rvf;

  /** RVI */
  private HashMap<String, Integer> rvi;

  /**
   * Creates a new cup Symbol with a predefined value obtained with the yytext() method.
   *
   * @param type the symbol type
   * @return the cup symbol
   */
  private Symbol symbol(int type) {
    return new Symbol(type, yyline + 1, yycolumn + 1, yytext());
  }

  /**
   * Creates a new cup Symbol with a given value.
   *
   * @param type the symbol type
   * @param value the symbol value
   * @return the cup symbol
   */
  private Symbol symbol(int type, Object value) {
    return new Symbol(type, yyline + 1, yycolumn + 1, value);
  }

  /**
   * Returns RVI register symbol.
   *
   * @return RVI register symbol or error if the register name is invalid
   */
  private Symbol getX() {
    String reg = yytext().toLowerCase();
    if (rvi.containsKey(reg)) {
      return symbol(Token.XREG, rvi.get(reg));
    }
    return symbol(Token.ERROR, "invalid register: " + yytext());
  }

  /**
   * Returns RVF register symbol.
   *
   * @return RVF register symbol or error if the register name is invalid
   */
  private Symbol getF() {
    String reg = yytext().toLowerCase();
    if (rvf.containsKey(reg)) {
      return symbol(Token.FREG, rvf.get(reg));
    }
    return symbol(Token.ERROR, "invalid register: " + yytext());
  }

%}

%init{
  text = new StringBuilder(0);
  // init rvi
  rvi = new HashMap<>();
  for (int i = 0; i < RVI.length; i++) {
    rvi.put(RVI[i], i);
    rvi.put(String.format("x%d", i), i);
    if (RVI[i].equals("s0"))
      rvi.put("fp", i);
  }
  // init rvf
  rvf = new HashMap<>();
  for (int i = 0; i < RVF.length; i++) {
    rvf.put(RVF[i], i);
    rvf.put(String.format("f%d", i), i);
  }
%init}

%eofval{
  switch (yystate()) {
    case STRING:
      return symbol(Token.ERROR, "EOF in string constant");
    case CHARACTER:
      return symbol(Token.ERROR, "EOF in char constant");
    case SBACKSLASH:
      return symbol(Token.ERROR, "EOF in string constant");
    case CBACKSLASH:
      return symbol(Token.ERROR, "EOF in char constant");
    default:
      return symbol(Token.EOF);
  }
%eofval}


%public
%final
%class Lexer
%cup
%line
%column
%unicode

%state COMMENT
%state STRING
%state SBACKSLASH
%state CHARACTER
%state CBACKSLASH

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
ID = [a-zA-Z_]([a-zA-Z0-9_]*("."[a-zA-Z0-9_]+)?)

// labels
LABEL = {ID}:

// directives
D_FILE = ".file"
D_ZERO = (".zero"|".space")
D_ASCIIZ = (".asciiz"|".asciz"|".string")
D_ASCII = ".ascii"
D_BYTE = ".byte"
D_HALF = (".half"|".short"|".2byte")
D_WORD = (".word"|".long"|".4byte")
D_FLOAT = ".float"
D_ALIGN = (".align"|".p2align")
D_BALIGN = ".balign"
D_GLOBL = (".globl"|".global")
D_SECTION = ".section"
D_DATA = ".data"
D_TEXT = ".text"
D_RODATA = ".rodata"
D_BSS = ".bss"
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

<YYINITIAL> {

  // other language elements
  {COMMA}  {
    return symbol(Token.COMMA);
  }

  {LPAREN} {
    return symbol(Token.LPAREN);
  }

  {RPAREN} {
    return symbol(Token.RPAREN);
  }

  // strings
  {STRSTART} {
    text.setLength(0);
    yybegin(STRING);
  }

  // chars
  {CHARSTART} {
    text.setLength(0);
    yybegin(CHARACTER);
  }

  // commnets
  {COMMENTSTART} {
    yybegin(COMMENT);
  }

  // labels
  {LABEL} {
    return symbol(Token.LABEL);
  }

  // directives
  {D_FILE} {
    return symbol(Token.D_FILE);
  }

  {D_ZERO} {
    return symbol(Token.D_ZERO);
  }

  {D_ASCIIZ} {
    return symbol(Token.D_ASCIIZ);
  }

  {D_ASCII} {
    return symbol(Token.D_ASCII);
  }

  {D_BYTE} {
    return symbol(Token.D_BYTE);
  }

  {D_HALF} {
    return symbol(Token.D_HALF);
  }

  {D_WORD} {
    return symbol(Token.D_WORD);
  }

  {D_FLOAT} {
    return symbol(Token.D_FLOAT);
  }

  {D_ALIGN} {
    return symbol(Token.D_ALIGN);
  }

  {D_BALIGN} {
    return symbol(Token.D_BALIGN);
  }

  {D_GLOBL} {
    return symbol(Token.D_GLOBL);
  }

  {D_SECTION} {
    return symbol(Token.D_SECTION);
  }

  {D_DATA} {
    return symbol(Token.D_DATA);
  }

  {D_TEXT} {
    return symbol(Token.D_TEXT);
  }

  {D_RODATA} {
    return symbol(Token.D_RODATA);
  }

  {D_BSS} {
    return symbol(Token.D_BSS);
  }

  {D_DIRECTIVE} {
    return symbol(Token.D_DIRECTIVE);
  }

  // relocation functions
  {HI} {
    return symbol(Token.HI);
  }

  {LO} {
    return symbol(Token.LO);
  }

  {PCREL_HI} {
    return symbol(Token.PCREL_HI);
  }

  {PCREL_LO} {
    return symbol(Token.PCREL_LO);
  }

  // keywords
  {I_LUI} {
    return symbol(Token.I_LUI);
  }

  {I_AUIPC} {
    return symbol(Token.I_AUIPC);
  }

  {I_JAL} {
    return symbol(Token.I_JAL);
  }

  {I_JALR} {
    return symbol(Token.I_JALR);
  }

  {I_BEQ} {
    return symbol(Token.I_BEQ);
  }

  {I_BNE} {
    return symbol(Token.I_BNE);
  }

  {I_BLT} {
    return symbol(Token.I_BLT);
  }

  {I_BGE} {
    return symbol(Token.I_BGE);
  }

  {I_BLTU} {
    return symbol(Token.I_BLTU);
  }

  {I_BGEU} {
    return symbol(Token.I_BGEU);
  }

  {I_LB} {
    return symbol(Token.I_LB);
  }

  {I_LH} {
    return symbol(Token.I_LH);
  }

  {I_LW} {
    return symbol(Token.I_LW);
  }

  {I_LBU} {
    return symbol(Token.I_LBU);
  }

  {I_LHU} {
    return symbol(Token.I_LHU);
  }

  {I_SB} {
    return symbol(Token.I_SB);
  }

  {I_SH} {
    return symbol(Token.I_SH);
  }

  {I_SW} {
    return symbol(Token.I_SW);
  }

  {I_ADDI} {
    return symbol(Token.I_ADDI);
  }

  {I_SLTI} {
    return symbol(Token.I_SLTI);
  }

  {I_SLTIU} {
    return symbol(Token.I_SLTIU);
  }

  {I_XORI} {
    return symbol(Token.I_XORI);
  }

  {I_ORI} {
    return symbol(Token.I_ORI);
  }

  {I_ANDI} {
    return symbol(Token.I_ANDI);
  }

  {I_SLLI} {
    return symbol(Token.I_SLLI);
  }

  {I_SRLI} {
    return symbol(Token.I_SRLI);
  }

  {I_SRAI} {
    return symbol(Token.I_SRAI);
  }

  {I_ADD} {
    return symbol(Token.I_ADD);
  }

  {I_SUB} {
    return symbol(Token.I_SUB);
  }

  {I_SLL} {
    return symbol(Token.I_SLL);
  }

  {I_SLT} {
    return symbol(Token.I_SLT);
  }

  {I_SLTU} {
    return symbol(Token.I_SLTU);
  }

  {I_XOR} {
    return symbol(Token.I_XOR);
  }

  {I_SRL} {
    return symbol(Token.I_SRL);
  }

  {I_SRA} {
    return symbol(Token.I_SRA);
  }

  {I_OR} {
    return symbol(Token.I_OR);
  }

  {I_AND} {
    return symbol(Token.I_AND);
  }

  {I_FENCE} {
    return symbol(Token.I_FENCE);
  }

  {I_ECALL} {
    return symbol(Token.I_ECALL);
  }

  {I_EBREAK} {
    return symbol(Token.I_EBREAK);
  }

  {I_CSRRW} {
    return symbol(Token.I_CSRRW);
  }

  {I_CSRRS} {
    return symbol(Token.I_CSRRS);
  }

  {I_CSRRC} {
    return symbol(Token.I_CSRRC);
  }

  {I_CSRRWI} {
    return symbol(Token.I_CSRRWI);
  }

  {I_CSRRSI} {
    return symbol(Token.I_CSRRSI);
  }

  {I_CSRRCI} {
    return symbol(Token.I_CSRRCI);
  }

  {I_MUL} {
    return symbol(Token.I_MUL);
  }

  {I_MULH} {
    return symbol(Token.I_MULH);
  }

  {I_MULHSU} {
    return symbol(Token.I_MULHSU);
  }

  {I_MULHU} {
    return symbol(Token.I_MULHU);
  }

  {I_DIV} {
    return symbol(Token.I_DIV);
  }

  {I_DIVU} {
    return symbol(Token.I_DIVU);
  }

  {I_REM} {
    return symbol(Token.I_REM);
  }

  {I_REMU} {
    return symbol(Token.I_REMU);
  }

  {I_FLW} {
    return symbol(Token.I_FLW);

  }

  {I_FSW} {
    return symbol(Token.I_FSW);
  }

  {I_FMADDS} {
    return symbol(Token.I_FMADDS);
  }

  {I_FMSUBS} {
    return symbol(Token.I_FMSUBS);
  }

  {I_FNMSUBS} {
    return symbol(Token.I_FNMSUBS);
  }

  {I_FNMADDS} {
    return symbol(Token.I_FNMADDS);
  }

  {I_FADDS} {
    return symbol(Token.I_FADDS);
  }

  {I_FSUBS} {
    return symbol(Token.I_FSUBS);
  }

  {I_FMULS} {
    return symbol(Token.I_FMULS);
  }

  {I_FDIVS} {
    return symbol(Token.I_FDIVS);
  }

  {I_FSQRTS} {
    return symbol(Token.I_FSQRTS);
  }

  {I_FSGNJS} {
    return symbol(Token.I_FSGNJS);
  }

  {I_FSGNJNS} {
    return symbol(Token.I_FSGNJNS);
  }

  {I_FSGNJXS} {
    return symbol(Token.I_FSGNJXS);
  }

  {I_FMINS} {
    return symbol(Token.I_FMINS);
  }

  {I_FMAXS} {
    return symbol(Token.I_FMAXS);
  }

  {I_FCVTWS} {
    return symbol(Token.I_FCVTWS);
  }

  {I_FCVTWUS} {
    return symbol(Token.I_FCVTWUS);
  }

  {I_FMVXW} {
    return symbol(Token.I_FMVXW);
  }

  {I_FEQS} {
    return symbol(Token.I_FEQS);
  }

  {I_FLTS} {
    return symbol(Token.I_FLTS);
  }

  {I_FLES} {
    return symbol(Token.I_FLES);
  }

  {I_FCLASSS} {
    return symbol(Token.I_FCLASSS);
  }

  {I_FCVTSW} {
    return symbol(Token.I_FCVTSW);
  }

  {I_FCVTSWU} {
    return symbol(Token.I_FCVTSWU);
  }

  {I_FMVWX} {
    return symbol(Token.I_FMVWX);
  }

  {I_LA} {
    return symbol(Token.I_LA);
  }

  {I_NOP} {
    return symbol(Token.I_NOP);
  }

  {I_LI} {
    return symbol(Token.I_LI);
  }

  {I_MV} {
    return symbol(Token.I_MV);
  }

  {I_NOT} {
    return symbol(Token.I_NOT);
  }

  {I_NEG} {
    return symbol(Token.I_NEG);
  }

  {I_SEQZ} {
    return symbol(Token.I_SEQZ);
  }

  {I_SNEZ} {
    return symbol(Token.I_SNEZ);
  }

  {I_SLTZ} {
    return symbol(Token.I_SLTZ);
  }

  {I_SGTZ} {
    return symbol(Token.I_SGTZ);
  }

  {I_FMVS} {
    return symbol(Token.I_FMVS);
  }

  {I_FABSS} {
    return symbol(Token.I_FABSS);
  }

  {I_FNEGS} {
    return symbol(Token.I_FNEGS);
  }

  {I_BEQZ} {
    return symbol(Token.I_BEQZ);
  }

  {I_BNEZ} {
    return symbol(Token.I_BNEZ);
  }

  {I_BLEZ} {
    return symbol(Token.I_BLEZ);
  }

  {I_BGEZ} {
    return symbol(Token.I_BGEZ);
  }

  {I_BLTZ} {
    return symbol(Token.I_BLTZ);
  }

  {I_BGTZ} {
    return symbol(Token.I_BGTZ);
  }

  {I_BGT} {
    return symbol(Token.I_BGT);
  }

  {I_BLE} {
    return symbol(Token.I_BLE);
  }

  {I_BGTU} {
    return symbol(Token.I_BGTU);
  }

  {I_BLEU} {
    return symbol(Token.I_BLEU);
  }

  {I_J} {
    return symbol(Token.I_J);
  }


  {I_JR} {
    return symbol(Token.I_JR);
  }

  {I_RET} {
    return symbol(Token.I_RET);
  }

  {I_CALL} {
    return symbol(Token.I_CALL);
  }

  {I_TAIL} {
    return symbol(Token.I_TAIL);
  }

  {I_CSRR} {
    return symbol(Token.I_CSRR);
  }

  {I_CSRW} {
    return symbol(Token.I_CSRW);
  }

  {I_CSRS} {
    return symbol(Token.I_CSRS);
  }

  {I_CSRC} {
    return symbol(Token.I_CSRC);
  }

  {I_CSRWI} {
    return symbol(Token.I_CSRWI);
  }

  {I_CSRSI} {
    return symbol(Token.I_CSRSI);
  }

  {I_CSRCI} {
    return symbol(Token.I_CSRCI);
  }

  {I_FRCSR} {
    return symbol(Token.I_FRCSR);
  }

  {I_FSCSR} {
    return symbol(Token.I_FSCSR);
  }

  {I_FRRM} {
    return symbol(Token.I_FRRM);
  }

  {I_FSRM} {
    return symbol(Token.I_FSRM);
  }

  {I_FRFLAGS} {
    return symbol(Token.I_FRFLAGS);
  }

  {I_FSFLAGS} {
    return symbol(Token.I_FSFLAGS);
  }

  // registers
  {XREG} {
    return getX();
  }

  {FREG} {
    return getF();
  }

  // identifiers
  {ID} {
    return symbol(Token.ID);
  }

  // numbers
  {INT} {
    try {
      return symbol(Token.INT,Integer.parseInt(yytext()));
    } catch (Exception e) {
      return symbol(Token.ERROR, "invalid number constant: '" + yytext() + "' (32 bits only)");
    }
  }

  {HEX} {
    try {
      return symbol(Token.HEX, Integer.parseUnsignedInt(yytext().substring(2), 16));
    } catch (Exception e) {
      return symbol(Token.ERROR, "invalid hexadecimal constant: '" + yytext() + "' (32 bits only) ");
    }
  }

  {BIN} {
    try {
      return symbol(Token.BIN,Integer.parseUnsignedInt(yytext().substring(2), 2));
    } catch (Exception e) {
      return symbol(Token.ERROR, "invalid binary constant: '" + yytext() + "' (32 bits only)");
    }
  }

  // floats
  {FLOAT} {
    try {
      return symbol(Token.FLOAT, Float.parseFloat(yytext()));
    } catch (Exception e) {
      return symbol(Token.ERROR, "invalid floating-point constant: '" + yytext() + "'");
    }
  }

  // newlines
  {NEWLINE}+ {
    return symbol(Token.NEWLINE);
  }

  // ignore whitespace
  {WHITESPACE} { /* do nothing */ }

  // report error
  {ERROR}  {
    return symbol(Token.ERROR, "invalid language element: '" + yytext() + "'");
  }

}

<STRING> {

  {STRSTART} {
    yybegin(YYINITIAL);
    String str = text.toString();
    if (str.length() > 1024) {
      return symbol(Token.ERROR, "string constant too long");
    }
    return symbol(Token.STRING, text.toString());
  }

  {BACKSLASH} {
    yybegin(SBACKSLASH);
  }

  {CHAR} {
    text.append(yytext());
  }

  {NEWLINE} {
    yypushback(yylength());
    yybegin(YYINITIAL);
    return symbol(Token.ERROR, "unterminated string constant");
  }

}

<CHARACTER> {

  {CHARSTART} {
    yybegin(YYINITIAL);
    String ch = text.toString();
    if (ch.length() == 1) {
      return symbol(Token.CHARACTER, ch.charAt(0));
    } else {
      return symbol(Token.ERROR, "invalid char: '" + ch + "'");
    }
  }

  {BACKSLASH} {
    yybegin(CBACKSLASH);
  }

  {CHAR} {
    text.append(yytext());
  }

  {NEWLINE} {
    yypushback(yylength());
    yybegin(YYINITIAL);
    return symbol(Token.ERROR, "unterminated char constant");
  }

}

<SBACKSLASH> {

  "0" {
    text.append('\0');
    yybegin(STRING);
  }

  "n" {
    text.append(System.getProperty("line.separator"));
    yybegin(STRING);
  }

  "t" {
    text.append('\t');
    yybegin(STRING);
  }

  "f" {
    text.append('\f');
    yybegin(STRING);
  }

  "b" {
    text.append('\b');
    yybegin(STRING);
  }

  "v" {
    text.append((char)11);
    yybegin(STRING);
  }

  "r" {
    text.append('\r');
    yybegin(STRING);
  }

  "\"" {
    text.append("\"");
    yybegin(STRING);
  }

  "'" {
    text.append("'");
    yybegin(STRING);
  }

  "\\" {
    text.append("\\");
    yybegin(STRING);
  }

  {CHAR} {
    text.append(yytext());
    yybegin(STRING);
  }

  {NEWLINE} {
    text.append(yytext());
    yybegin(STRING);
  }

}

<CBACKSLASH> {

  "0" {
    text.append('\0');
    yybegin(CHARACTER);
  }

  "n" {
    text.append('\n');
    yybegin(CHARACTER);
  }

  "t" {
    text.append('\t');
    yybegin(CHARACTER);
  }

  "f" {
    text.append('\f');
    yybegin(CHARACTER);
  }

  "b" {
    text.append('\b');
    yybegin(CHARACTER);
  }

  "v" {
    text.append((char)11);
    yybegin(CHARACTER);
  }

  "r" {
    text.append((char)'\r');
    yybegin(CHARACTER);
  }

  "\"" {
    text.append("\"");
    yybegin(CHARACTER);
  }

  "\'" {
    text.append("\'");
    yybegin(CHARACTER);
  }

  "\\" {
    text.append("\\");
    yybegin(CHARACTER);
  }

  {CHAR} {
    text.append(yytext());
    yybegin(CHARACTER);
  }

  {NEWLINE} {
    yypushback(yylength());
    yybegin(YYINITIAL);
    return symbol(Token.ERROR, "unterminated char constant");
  }

}

<COMMENT> {

  // consume everything
  {CHAR} { /* consume everything */ }

  {NEWLINE} {
    yypushback(yylength());
    yybegin(YYINITIAL);
  }

}
