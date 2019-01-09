/*
Copyright (C) 2018-2019 Andres Castellanos

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

package vsim.assembler;

/** CUP generated class containing symbol constants. */
public class Token {

  /* terminals */
  public static final int I_SLTIU = 76;
  public static final int I_XORI = 79;
  public static final int I_BLT = 89;
  public static final int FLOAT = 126;
  public static final int FREGISTER = 130;
  public static final int F_FSGNJXS = 57;
  public static final int I_SEQZ = 101;
  public static final int I_MULH = 28;
  public static final int I_BLE = 112;
  public static final int I_CALL = 118;
  public static final int I_LW = 72;
  public static final int F_FMVS = 120;
  public static final int RPAREN = 5;
  public static final int I_LI = 97;
  public static final int I_LH = 70;
  public static final int I_AUIPC = 93;
  public static final int I_BGTZ = 110;
  public static final int I_BGTU = 113;
  public static final int I_LB = 68;
  public static final int TIMES = 8;
  public static final int I_LA = 95;
  public static final int F_FMINS = 58;
  public static final int D_SECTION = 138;
  public static final int I_REMU = 31;
  public static final int UMINUS = 20;
  public static final int F_FMULS = 48;
  public static final int REGISTER = 129;
  public static final int MOD = 10;
  public static final int I_ADD = 22;
  public static final int I_LUI = 92;
  public static final int I_JR = 116;
  public static final int I_DIV = 25;
  public static final int I_SLTZ = 103;
  public static final int I_LHU = 71;
  public static final int F_FSW = 85;
  public static final int I_SLTU = 33;
  public static final int LABEL = 6;
  public static final int NEWLINE = 7;
  public static final int EOF = 0;
  public static final int MINUS = 12;
  public static final int F_FSUBS = 47;
  public static final int I_SLTI = 75;
  public static final int F_FSQRTS = 50;
  public static final int D_ZERO = 134;
  public static final int F_FNMSUBS = 53;
  public static final int I_BGEZ = 108;
  public static final int I_BGEU = 88;
  public static final int D_BALIGN = 144;
  public static final int F_FMSUBS = 52;
  public static final int I_SLT = 35;
  public static final int I_BEQZ = 105;
  public static final int I_DIVU = 24;
  public static final int OR = 17;
  public static final int SLL = 13;
  public static final int NUMBER = 123;
  public static final int D_ASCII = 133;
  public static final int I_SLL = 34;
  public static final int F_FMAXS = 59;
  public static final int I_BREAK = 80;
  public static final int AND = 16;
  public static final int BINARY = 125;
  public static final int I_AND = 23;
  public static final int HEXNUM = 124;
  public static final int I_BGT = 111;
  public static final int I_RET = 117;
  public static final int I_SNEZ = 102;
  public static final int I_ECALL = 66;
  public static final int D_DATA = 140;
  public static final int I_REM = 32;
  public static final int I_BGE = 87;
  public static final int F_FCVTSWU = 43;
  public static final int I_SW = 84;
  public static final int F_FCLASSS = 63;
  public static final int NEG = 19;
  public static final int I_NEG = 100;
  public static final int D_TEXT = 139;
  public static final int F_FDIVS = 49;
  public static final int PLUS = 11;
  public static final int I_SH = 83;
  public static final int LPAREN = 4;
  public static final int I_SB = 82;
  public static final int I_BLTZ = 109;
  public static final int I_JALR = 67;
  public static final int F_FEQS = 60;
  public static final int UPLUS = 21;
  public static final int I_MULHSU = 26;
  public static final int I_BLTU = 90;
  public static final int XOR = 18;
  public static final int I_XOR = 39;
  public static final int I_BEQ = 86;
  public static final int D_WORD = 137;
  public static final int F_FCVTWUS = 45;
  public static final int COMMA = 3;
  public static final int D_GLOBL = 145;
  public static final int F_FCVTWS = 44;
  public static final int IDENTIFIER = 131;
  public static final int I_ANDI = 65;
  public static final int I_NOT = 99;
  public static final int I_NOP = 96;
  public static final int I_SUB = 38;
  public static final int F_FABSS = 121;
  public static final int F_FMVXW = 41;
  public static final int I_MUL = 29;
  public static final int I_LBU = 69;
  public static final int CHARACTER = 128;
  public static final int DOT = 2;
  public static final int I_ADDI = 64;
  public static final int F_FLTS = 61;
  public static final int I_BNEZ = 106;
  public static final int F_FMVWX = 40;
  public static final int DIVIDE = 9;
  public static final int I_SRAI = 77;
  public static final int I_BLEZ = 107;
  public static final int STRING = 127;
  public static final int I_BLEU = 114;
  public static final int D_BYTE = 135;
  public static final int I_SGTZ = 104;
  public static final int I_MULHU = 27;
  public static final int F_FLW = 81;
  public static final int I_OR = 30;
  public static final int F_FSGNJNS = 56;
  public static final int D_RODATA = 141;
  public static final int I_JAL = 94;
  public static final int D_ASCIIZ = 132;
  public static final int D_BSS = 142;
  public static final int I_SRLI = 78;
  public static final int F_FADDS = 46;
  public static final int error = 1;
  public static final int SRL = 14;
  public static final int I_BNE = 91;
  public static final int I_SRL = 37;
  public static final int F_FNMADDS = 54;
  public static final int SRA = 15;
  public static final int I_SRA = 36;
  public static final int I_SLLI = 74;
  public static final int I_J = 115;
  public static final int I_ORI = 73;
  public static final int ERROR = 147;
  public static final int F_FNEGS = 122;
  public static final int F_FCVTSW = 42;
  public static final int F_FMADDS = 51;
  public static final int D_HALF = 136;
  public static final int D_ALIGN = 143;
  public static final int D_FLOAT = 146;
  public static final int F_FLES = 62;
  public static final int F_FSGNJS = 55;
  public static final int I_MV = 98;
  public static final int I_TAIL = 119;
  public static final String[] terminalNames = new String[] { "EOF", "error", "DOT", "COMMA", "LPAREN", "RPAREN",
      "LABEL", "NEWLINE", "TIMES", "DIVIDE", "MOD", "PLUS", "MINUS", "SLL", "SRL", "SRA", "AND", "OR", "XOR", "NEG",
      "UMINUS", "UPLUS", "I_ADD", "I_AND", "I_DIVU", "I_DIV", "I_MULHSU", "I_MULHU", "I_MULH", "I_MUL", "I_OR",
      "I_REMU", "I_REM", "I_SLTU", "I_SLL", "I_SLT", "I_SRA", "I_SRL", "I_SUB", "I_XOR", "F_FMVWX", "F_FMVXW",
      "F_FCVTSW", "F_FCVTSWU", "F_FCVTWS", "F_FCVTWUS", "F_FADDS", "F_FSUBS", "F_FMULS", "F_FDIVS", "F_FSQRTS",
      "F_FMADDS", "F_FMSUBS", "F_FNMSUBS", "F_FNMADDS", "F_FSGNJS", "F_FSGNJNS", "F_FSGNJXS", "F_FMINS", "F_FMAXS",
      "F_FEQS", "F_FLTS", "F_FLES", "F_FCLASSS", "I_ADDI", "I_ANDI", "I_ECALL", "I_JALR", "I_LB", "I_LBU", "I_LH",
      "I_LHU", "I_LW", "I_ORI", "I_SLLI", "I_SLTI", "I_SLTIU", "I_SRAI", "I_SRLI", "I_XORI", "I_BREAK", "F_FLW", "I_SB",
      "I_SH", "I_SW", "F_FSW", "I_BEQ", "I_BGE", "I_BGEU", "I_BLT", "I_BLTU", "I_BNE", "I_LUI", "I_AUIPC", "I_JAL",
      "I_LA", "I_NOP", "I_LI", "I_MV", "I_NOT", "I_NEG", "I_SEQZ", "I_SNEZ", "I_SLTZ", "I_SGTZ", "I_BEQZ", "I_BNEZ",
      "I_BLEZ", "I_BGEZ", "I_BLTZ", "I_BGTZ", "I_BGT", "I_BLE", "I_BGTU", "I_BLEU", "I_J", "I_JR", "I_RET", "I_CALL",
      "I_TAIL", "F_FMVS", "F_FABSS", "F_FNEGS", "NUMBER", "HEXNUM", "BINARY", "FLOAT", "STRING", "CHARACTER",
      "REGISTER", "FREGISTER", "IDENTIFIER", "D_ASCIIZ", "D_ASCII", "D_ZERO", "D_BYTE", "D_HALF", "D_WORD", "D_SECTION",
      "D_TEXT", "D_DATA", "D_RODATA", "D_BSS", "D_ALIGN", "D_BALIGN", "D_GLOBL", "D_FLOAT", "ERROR" };
}
