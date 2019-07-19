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

package jvpiter.asm;

/** CUP generated class containing symbol constants. */
public class Token {
  /* terminals */
  public static final int PCREL_LO = 151;
  public static final int I_SLTIU = 27;
  public static final int I_XORI = 28;
  public static final int FLOAT = 155;
  public static final int I_BLT = 13;
  public static final int I_FSCSR = 123;
  public static final int I_SEQZ = 93;
  public static final int I_MULH = 54;
  public static final int I_BLE = 107;
  public static final int I_FSRM = 125;
  public static final int I_FSGNJXS = 74;
  public static final int I_CALL = 113;
  public static final int I_LW = 19;
  public static final int I_FMINS = 75;
  public static final int I_CSRRCI = 52;
  public static final int RPAREN = 4;
  public static final int I_LI = 89;
  public static final int I_LH = 18;
  public static final int I_AUIPC = 8;
  public static final int I_BGTZ = 105;
  public static final int I_BGTU = 108;
  public static final int I_LB = 17;
  public static final int I_LA = 87;
  public static final int I_FMULS = 69;
  public static final int D_SECTION = 142;
  public static final int I_REMU = 60;
  public static final int I_EBREAK = 46;
  public static final int I_ADD = 34;
  public static final int I_LUI = 7;
  public static final int I_JR = 111;
  public static final int I_DIV = 57;
  public static final int I_FSUBS = 68;
  public static final int I_FSQRTS = 71;
  public static final int I_SLTZ = 95;
  public static final int I_LHU = 21;
  public static final int I_FEQS = 80;
  public static final int I_SLTU = 38;
  public static final int LABEL = 5;
  public static final int I_CSRSI = 120;
  public static final int BIN = 154;
  public static final int NEWLINE = 6;
  public static final int EOF = 0;
  public static final int I_SLTI = 26;
  public static final int INT = 152;
  public static final int I_FMSUBS = 64;
  public static final int I_CSRRW = 47;
  public static final int D_ZERO = 132;
  public static final int I_BGEZ = 103;
  public static final int I_CSRRS = 48;
  public static final int I_BGEU = 16;
  public static final int PCREL_HI = 150;
  public static final int I_FNMSUBS = 65;
  public static final int D_BALIGN = 140;
  public static final int I_FMAXS = 76;
  public static final int I_CSRRC = 49;
  public static final int I_SLT = 37;
  public static final int I_BEQZ = 100;
  public static final int I_DIVU = 58;
  public static final int I_CSRW = 116;
  public static final int D_ASCII = 134;
  public static final int I_SLL = 36;
  public static final int I_CSRS = 117;
  public static final int I_CSRR = 115;
  public static final int I_AND = 43;
  public static final int I_FSW = 62;
  public static final int I_BGT = 106;
  public static final int I_CSRC = 118;
  public static final int I_RET = 112;
  public static final int I_SNEZ = 94;
  public static final int I_FLTS = 81;
  public static final int I_ECALL = 45;
  public static final int D_FILE = 131;
  public static final int D_DATA = 143;
  public static final int I_REM = 59;
  public static final int I_BGE = 14;
  public static final int I_CSRRWI = 50;
  public static final int I_FDIVS = 70;
  public static final int I_FCVTSWU = 85;
  public static final int I_SW = 24;
  public static final int D_TEXT = 144;
  public static final int I_NEG = 92;
  public static final int HEX = 153;
  public static final int I_FCLASSS = 83;
  public static final int I_SH = 23;
  public static final int LPAREN = 3;
  public static final int XREG = 128;
  public static final int I_SB = 22;
  public static final int I_BLTZ = 104;
  public static final int I_CSRCI = 121;
  public static final int I_JALR = 10;
  public static final int I_FRRM = 124;
  public static final int I_MULHSU = 55;
  public static final int I_BLTU = 15;
  public static final int I_FCVTWS = 77;
  public static final int I_XOR = 39;
  public static final int I_BEQ = 11;
  public static final int D_WORD = 137;
  public static final int I_FRFLAGS = 126;
  public static final int COMMA = 2;
  public static final int I_FCVTWUS = 78;
  public static final int D_GLOBL = 141;
  public static final int LO = 149;
  public static final int I_FABSS = 98;
  public static final int FREG = 129;
  public static final int I_FMVXW = 79;
  public static final int I_FLES = 82;
  public static final int I_ANDI = 30;
  public static final int I_NOT = 91;
  public static final int I_NOP = 88;
  public static final int I_SUB = 35;
  public static final int I_MUL = 53;
  public static final int I_FMVWX = 86;
  public static final int D_DIRECTIVE = 147;
  public static final int I_FENCE = 44;
  public static final int I_LBU = 20;
  public static final int CHARACTER = 157;
  public static final int I_ADDI = 25;
  public static final int I_BNEZ = 101;
  public static final int I_SRAI = 33;
  public static final int I_FMVS = 97;
  public static final int I_BLEZ = 102;
  public static final int STRING = 156;
  public static final int I_BLEU = 109;
  public static final int D_BYTE = 135;
  public static final int I_SGTZ = 96;
  public static final int I_MULHU = 56;
  public static final int I_CSRRSI = 51;
  public static final int I_FRCSR = 122;
  public static final int I_FADDS = 67;
  public static final int I_OR = 42;
  public static final int D_RODATA = 145;
  public static final int I_JAL = 9;
  public static final int D_ASCIIZ = 133;
  public static final int D_BSS = 146;
  public static final int I_FSFLAGS = 127;
  public static final int I_FSGNJNS = 73;
  public static final int I_SRLI = 32;
  public static final int error = 1;
  public static final int I_FMADDS = 63;
  public static final int I_FCVTSW = 84;
  public static final int I_BNE = 12;
  public static final int I_FNEGS = 99;
  public static final int I_SRL = 40;
  public static final int ID = 130;
  public static final int I_FSGNJS = 72;
  public static final int I_SRA = 41;
  public static final int I_FNMADDS = 66;
  public static final int I_SLLI = 31;
  public static final int I_J = 110;
  public static final int I_CSRWI = 119;
  public static final int I_ORI = 29;
  public static final int ERROR = 158;
  public static final int D_HALF = 136;
  public static final int D_ALIGN = 139;
  public static final int D_FLOAT = 138;
  public static final int HI = 148;
  public static final int I_FLW = 61;
  public static final int I_MV = 90;
  public static final int I_TAIL = 114;
  public static final String[] terminalNames = new String[] {
  "EOF",
  "error",
  "COMMA",
  "LPAREN",
  "RPAREN",
  "LABEL",
  "NEWLINE",
  "I_LUI",
  "I_AUIPC",
  "I_JAL",
  "I_JALR",
  "I_BEQ",
  "I_BNE",
  "I_BLT",
  "I_BGE",
  "I_BLTU",
  "I_BGEU",
  "I_LB",
  "I_LH",
  "I_LW",
  "I_LBU",
  "I_LHU",
  "I_SB",
  "I_SH",
  "I_SW",
  "I_ADDI",
  "I_SLTI",
  "I_SLTIU",
  "I_XORI",
  "I_ORI",
  "I_ANDI",
  "I_SLLI",
  "I_SRLI",
  "I_SRAI",
  "I_ADD",
  "I_SUB",
  "I_SLL",
  "I_SLT",
  "I_SLTU",
  "I_XOR",
  "I_SRL",
  "I_SRA",
  "I_OR",
  "I_AND",
  "I_FENCE",
  "I_ECALL",
  "I_EBREAK",
  "I_CSRRW",
  "I_CSRRS",
  "I_CSRRC",
  "I_CSRRWI",
  "I_CSRRSI",
  "I_CSRRCI",
  "I_MUL",
  "I_MULH",
  "I_MULHSU",
  "I_MULHU",
  "I_DIV",
  "I_DIVU",
  "I_REM",
  "I_REMU",
  "I_FLW",
  "I_FSW",
  "I_FMADDS",
  "I_FMSUBS",
  "I_FNMSUBS",
  "I_FNMADDS",
  "I_FADDS",
  "I_FSUBS",
  "I_FMULS",
  "I_FDIVS",
  "I_FSQRTS",
  "I_FSGNJS",
  "I_FSGNJNS",
  "I_FSGNJXS",
  "I_FMINS",
  "I_FMAXS",
  "I_FCVTWS",
  "I_FCVTWUS",
  "I_FMVXW",
  "I_FEQS",
  "I_FLTS",
  "I_FLES",
  "I_FCLASSS",
  "I_FCVTSW",
  "I_FCVTSWU",
  "I_FMVWX",
  "I_LA",
  "I_NOP",
  "I_LI",
  "I_MV",
  "I_NOT",
  "I_NEG",
  "I_SEQZ",
  "I_SNEZ",
  "I_SLTZ",
  "I_SGTZ",
  "I_FMVS",
  "I_FABSS",
  "I_FNEGS",
  "I_BEQZ",
  "I_BNEZ",
  "I_BLEZ",
  "I_BGEZ",
  "I_BLTZ",
  "I_BGTZ",
  "I_BGT",
  "I_BLE",
  "I_BGTU",
  "I_BLEU",
  "I_J",
  "I_JR",
  "I_RET",
  "I_CALL",
  "I_TAIL",
  "I_CSRR",
  "I_CSRW",
  "I_CSRS",
  "I_CSRC",
  "I_CSRWI",
  "I_CSRSI",
  "I_CSRCI",
  "I_FRCSR",
  "I_FSCSR",
  "I_FRRM",
  "I_FSRM",
  "I_FRFLAGS",
  "I_FSFLAGS",
  "XREG",
  "FREG",
  "ID",
  "D_FILE",
  "D_ZERO",
  "D_ASCIIZ",
  "D_ASCII",
  "D_BYTE",
  "D_HALF",
  "D_WORD",
  "D_FLOAT",
  "D_ALIGN",
  "D_BALIGN",
  "D_GLOBL",
  "D_SECTION",
  "D_DATA",
  "D_TEXT",
  "D_RODATA",
  "D_BSS",
  "D_DIRECTIVE",
  "HI",
  "LO",
  "PCREL_HI",
  "PCREL_LO",
  "INT",
  "HEX",
  "BIN",
  "FLOAT",
  "STRING",
  "CHARACTER",
  "ERROR"
  };
}
