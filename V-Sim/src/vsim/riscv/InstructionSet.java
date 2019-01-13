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

package vsim.riscv;

import java.lang.reflect.Modifier;
import java.util.HashMap;
import vsim.riscv.instructions.*;
import vsim.utils.IO;
import vsim.utils.Message;


/**
 * The class InstructionSet represents the available instruction set.
 */
public final class InstructionSet {

  /** newline */
  private static final String NL = System.getProperty("line.separator");

  /** r-type instructions package */
  private static final String RTYPE = "vsim.riscv.instructions.rtype";
  /** i-type instructions package */
  private static final String ITYPE = "vsim.riscv.instructions.itype";
  /** s-type instructions package */
  private static final String STYPE = "vsim.riscv.instructions.stype";
  /** b-type instructions package */
  private static final String BTYPE = "vsim.riscv.instructions.btype";
  /** u-type instructions package */
  private static final String UTYPE = "vsim.riscv.instructions.utype";
  /** j-type instructions package */
  private static final String JTYPE = "vsim.riscv.instructions.jtype";
  /** r4-type instructions package */
  private static final String R4TYPE = "vsim.riscv.instructions.r4type";

  /** current classes in rtype package */
  private static final String[] RClasses = { "Add", "Sub", "Sll", "Slt", "Sltu", "Xor", "Srl", "Sra", "Or", "And",
      "Div", "Divu", "Mul", "Mulh", "Mulhu", "Mulhsu", "Rem", "Remu", "Fmvwx", "Fmvxw", "Fcvtsw", "Fcvtswu", "Fcvtws",
      "Fcvtwus", "Fadds", "Fsubs", "Fmuls", "Fdivs", "Fsqrts", "Fsgnjs", "Fsgnjns", "Fsgnjxs", "Feqs", "Flts", "Fles",
      "Fclasss", "Fmins", "Fmaxs" };

  /** current classes in itype package */
  private static final String[] IClasses = { "Jalr", "Lb", "Lh", "Lw", "Lbu", "Lhu", "Addi", "Slti", "Sltiu", "Xori",
      "Ori", "Andi", "Slli", "Srli", "Srai", "Ecall", "Flw", "Ebreak" };

  /** current classes in stype package */
  private static final String[] SClasses = { "Sb", "Sh", "Sw", "Fsw" };

  /** current classes in btype package */
  private static final String[] BClasses = { "Beq", "Bge", "Bgeu", "Blt", "Bltu", "Bne" };

  /** current classes in utype package */
  private static final String[] UClasses = { "Auipc", "Lui" };

  /** current classes in jtype package */
  private static final String[] JClasses = { "Jal" };

  /** current classes in r4type package */
  private static final String[] R4Classes = { "Fmadds", "Fmsubs", "Fnmadds", "Fnmsubs" };

  /** the only available instance of the InstructionSet class */
  public static final InstructionSet insts = new InstructionSet();

  /** instructions dictionary */
  private HashMap<String, Instruction> instructions;

  /** pseudo instructions */
  private static final String[][] pseudos = {
      { "la", "la rd, symbol",
          "auipc rd, delta[31:12] + delta[11]" + NL + "addi rd, rd, delta[11:0]" + NL + NL
              + "where delta = symbol − pc" },
      { "lb", "lb rd, symbol",
          "auipc rd, delta[31:12] + delta[11]" + NL + "lb rd, delta[11:0](rd)" + NL + NL
              + "where delta = symbol − pc" },
      { "lh", "lh rd, symbol",
          "auipc rd, delta[31:12] + delta[11]" + NL + "lb rd, delta[11:0](rd)" + NL + NL
              + "where delta = symbol − pc" },
      { "lw", "lw rd, symbol",
          "auipc rd, delta[31:12] + delta[11]" + NL + "lh rd, delta[11:0](rd)" + NL + NL
              + "where delta = symbol − pc" },
      { "sb", "sb rd, symbol, rt",
          "auipc rd, delta[31:12] + delta[11]" + NL + "sb rd, delta[11:0](rt)" + NL + NL
              + "where delta = symbol − pc" },
      { "sh", "sh rd, symbol, rt",
          "auipc rd, delta[31:12] + delta[11]" + NL + "sh rd, delta[11:0](rt)" + NL + NL
              + "where delta = symbol − pc" },
      { "sw", "sw rd, symbol, rt",
          "auipc rd, delta[31:12] + delta[11]" + NL + "sw rd, delta[11:0](rt)" + NL + NL
              + "where delta = symbol − pc" },
      { "flw", "flw rd, symbol, rt",
          "auipc rd, delta[31:12] + delta[11]" + NL + "flw rd, delta[11:0](rt)" + NL + NL
              + "where delta = symbol − pc" },
      { "fsw", "fsw rd, symbol, rt",
          "auipc rd, delta[31:12] + delta[11]" + NL + "fsw rd, delta[11:0](rt)" + NL + NL
              + "where delta = symbol − pc" },
      { "nop", "nop", "addi x0, x0, 0" }, { "li", "li rd, imm", "Myriad sequences" },
      { "mv", "mv rd, rs", "addi rd, rs, 0" }, { "not", "not rd, rs", "xori rd, rs, -1" },
      { "neg", "neg rd, rs", "sub rd, x0, rs" }, { "seqz", "seqz rd, rs", "sltiu rd, rs, 1" },
      { "snez", "snez rd, rs", "sltu rd, x0, rs" }, { "sltz", "sltz rd, rs", "slt rd, rs, x0" },
      { "sgtz", "sgtz rd, rs", "slt rd, x0, rs" }, { "fmv.s", "fmv.s rd, rs", "fsgnj.s rd, rs, rs" },
      { "fabs.s", "fabs.s rd, rs", "fsgnjx.s rd, rs, rs" }, { "fneg.s", "fneg.s rd, rs", "fsgnjn.s rd, rs, rs" },
      { "beqz", "beqz rs, offset", "beq rs, x0, offset" }, { "bnez", "bnez rs, offset", "bne rs, x0, offset" },
      { "blez", "blez rs, offset", "bge x0, rs, offset" }, { "bgez", "bgez rs, offset", "bge rs, x0, offset" },
      { "bltz", "bltz rs, offset", "blt rs, x0, offset" }, { "bgtz", "bgtz rs, offset", "blt x0, rs, offset" },
      { "bgt", "bgt rs, rt, offset", "blt rt, rs, offset" }, { "ble", "ble rs, rt, offset", "bge rt, rs, offset" },
      { "bgtu", "bgtu rs, rt, offset", "bltu rt, rs, offset" },
      { "bleu", "bleu rs, rt, offset", "bgeu rt, rs, offset" }, { "j", "j offset", "jal x0, offset" },
      { "jal", "jal offset", "jal x1, offset" }, { "jr", "jr rs", "jalr x0, 0(rs)" },
      { "jalr", "jalr rs", "jalr x1, 0(rs)" }, { "ret", "ret", "jalr x0, 0(x1)" },
      { "call", "call offset", "auipc x1, offset[31:12] + offset[11]" + NL + "jalr x1, offset[11:0](x1)" },
      { "tail", "tail offset", "auipc x6, offset[31:12] + offset[11]" + NL + "jalr x0, offset[11:0](x6" } };

  /**
   * Unique constructor that initializes a newly InstructionSet object.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  private InstructionSet() {
    this.instructions = new HashMap<String, Instruction>();
    this.populate();
  }

  /**
   * This method adds all the instruction classes in the given package.
   *
   * @param classes the classes to add
   * @param pkg the package of the classes
   * @see vsim.riscv.instructions.Instruction
   */
  private void add(String[] classes, String pkg) {
    for (String className : classes) {
      String classPath = pkg + "." + className;
      try {
        Class<?> cls = Class.forName(classPath);
        // only final classes
        if (!Instruction.class.isAssignableFrom(cls) || Modifier.isAbstract(cls.getModifiers())
            || Modifier.isInterface(cls.getModifiers()))
          continue;
        // add this new instruction to isa
        Instruction inst = (Instruction) (cls.getConstructor().newInstance());
        String mnemonic = inst.getMnemonic();
        if (this.instructions.containsKey(mnemonic))
          Message.warning("duplicated instruction name: '" + mnemonic + "', skip this");
        else
          this.instructions.put(mnemonic, inst);
      } catch (Exception e) {
        Message.panic("class: '" + classPath + "' could not be loaded");
      }
    }
  }

  /**
   * This method populates the instruction set instance with all the instructions available.
   *
   * @see vsim.riscv.instructions.Instruction
   */
  private void populate() {
    this.add(RClasses, RTYPE);
    this.add(IClasses, ITYPE);
    this.add(SClasses, STYPE);
    this.add(BClasses, BTYPE);
    this.add(UClasses, UTYPE);
    this.add(JClasses, JTYPE);
    this.add(R4Classes, R4TYPE);
  }

  /**
   * This method returns the intruction that represent the given mnemonic.
   *
   * @param mnemonic the instruction mnemonic
   * @see vsim.riscv.instructions.Instruction
   * @return the instruction or null if the mnemonic is invalid
   */
  public Instruction get(String mnemonic) {
    return this.instructions.get(mnemonic);
  }

  /**
   * Decodes a machine code to a instruction mnemonic.
   *
   * @param code machine code to decode
   * @return instruction mnemonic
   */
  public String decode(MachineCode code) {
    switch (code.get(InstructionField.OPCODE)) {
      case 0b0110111: // lui
        return "lui";
      case 0b0010111: // auipc
        return "auipc";
      case 0b1101111: // jal
        return "jal";
      case 0b1100111: // jalr
        return "jalr";
      case 0b1100011: // branches
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // beq
            return "beq";
          case 0b001: // bne
            return "bne";
          case 0b100: // blt
            return "blt";
          case 0b101: // bge
            return "bge";
          case 0b110: // bltu
            return "bltu";
          case 0b111: // bgeu
            return "bgeu";
        }
        break;
      case 0b0000011: // loads
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // lb
            return "lb";
          case 0b001: // lh
            return "lh";
          case 0b010: // lw
            return "lw";
          case 0b100: // lbu
            return "lbu";
          case 0b101: // lhu
            return "lhu";
        }
        break;
      case 0b0100011: // stores
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // sb
            return "sb";
          case 0b001: // sh
            return "sh";
          case 0b010: // sw
            return "sw";
        }
        break;
      case 0b0010011: // immediates
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // addi
            return "addi";
          case 0b010: // slti
            return "slti";
          case 0b011: // sltiu
            return "sltiu";
          case 0b100: // xori
            return "xori";
          case 0b110: // ori
            return "ori";
          case 0b111: // andi
            return "andi";
          case 0b001: // slli
            return "slli";
          case 0b101:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // srli
                return "srli";
              case 0b0100000: // srai
                return "srai";
            }
            break;
        }
        break;
      case 0b0110011: // r
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // add
                return "add";
              case 0b0100000: // sub
                return "sub";
              case 0b0000001: // mul
                return "mul";
            }
            break;
          case 0b001:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // sll
                return "sll";
              case 0b0000001: // mulh
                return "mulh";
            }
            break;
          case 0b010:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // slt
                return "slt";
              case 0b0000001: // mulhsu
                return "mulhsu";
            }
            break;
          case 0b011:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // sltu
                return "sltu";
              case 0b0000001: // mulhu
                return "mulhu";
            }
            break;
          case 0b100:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // xor
                return "xor";
              case 0b0000001: // div
                return "div";
            }
            break;
          case 0b101:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // srl
                return "srl";
              case 0b0100000: // sra
                return "sra";
              case 0b0000001: // divu
                return "divu";
            }
            break;
          case 0b110:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // or
                return "or";
              case 0b0000001: // rem
                return "rem";
            }
            break;
          case 0b111:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // and
                return "and";
              case 0b0000001: // remu
                return "remu";
            }
            break;
        }
        break;
      case 0b1110011:
        switch (code.get(InstructionField.IMM_11_0)) {
          case 0b000000000000: // ecall
            return "ecall";
          case 0b000000000001: // ebreak
            return "ebreak";
        }
        break;
      // f extension
      case 0b0000111: // flw
        return "flw";
      case 0b0100111: // fsw
        return "fsw";
      // fused
      case 0b1000011:
        return "fmadd.s";
      case 0b1000111:
        return "fmsub.s";
      case 0b1001011:
        return "fnmsub.s";
      case 0b1001111:
        return "fnmadd.s";
      case 0b1010011: // f
        switch (code.get(InstructionField.FUNCT7)) {
          case 0b0000000:
            return "fadd.s";
          case 0b0000100:
            return "fsub.s";
          case 0b0001000:
            return "fmul.s";
          case 0b0001100:
            return "fdiv.s";
          case 0b0101100:
            return "fsqrt.s";
          case 0b0010000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return "fsgnj.s";
              case 0b001:
                return "fsgnjn.s";
              case 0b010:
                return "fsgnjx.s";
            }
            break;
          case 0b0010100:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return "fmin.s";
              case 0b001:
                return "fmax.s";
            }
            break;
          case 0b1100000:
            switch (code.get(InstructionField.RS2)) {
              case 0b00000:
                return "fcvt.w.s";
              case 0b00001:
                return "fcvt.wu.s";
            }
            break;
          case 0b1110000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return "fmv.x.w";
              case 0b001:
                return "fclass.s";
            }
            break;
          case 0b1010000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b010:
                return "feq.s";
              case 0b001:
                return "flt.s";
              case 0b000:
                return "fle.s";
            }
            break;
          case 0b1101000:
            switch (code.get(InstructionField.RS2)) {
              case 0b00000:
                return "fcvt.s.w";
              case 0b00001:
                return "fcvt.s.wu";
            }
            break;
          case 0b1111000:
            return "fmv.w.x";
        }
        break;
    }
    return null;
  }

  /**
   * This method pretty prints the usage and description of an instruction.
   *
   * @param mnemonic instruction mnemonic to print
   */
  public void print(String mnemonic) {
    mnemonic = mnemonic.toLowerCase();
    Instruction inst = this.instructions.get(mnemonic);
    if (inst != null) {
      IO.stdout.println("Instruction:");
      IO.stdout.println();
      IO.stdout.println(String.format("[%s] (%s) example: %s", inst.getFormat().toString(), mnemonic, inst.getUsage()));
      IO.stdout.println();
      IO.stdout.println("Description:");
      IO.stdout.println();
      IO.stdout.println(inst.getDescription());
      for (int i = 0; i < InstructionSet.pseudos.length; i++) {
        if (pseudos[i][0].equals(mnemonic)) {
          IO.stdout.println();
          IO.stdout.println("Pseudo Instruction:");
          IO.stdout.println();
          IO.stdout.println(String.format("(%s) example: %s", mnemonic, pseudos[i][1]));
          IO.stdout.println();
          IO.stdout.println("Base Instruction(s):");
          IO.stdout.println();
          IO.stdout.println(pseudos[i][2]);
        }
      }
      return;
    } else {
      boolean enter = false;
      for (int i = 0; i < InstructionSet.pseudos.length; i++) {
        if (pseudos[i][0].equals(mnemonic)) {
          if (enter)
            IO.stdout.println();
          IO.stdout.println("Pseudo Instruction:");
          IO.stdout.println();
          IO.stdout.println(String.format("(%s) example: %s", mnemonic, pseudos[i][1]));
          IO.stdout.println();
          IO.stdout.println("Base Instruction(s):");
          IO.stdout.println();
          IO.stdout.println(pseudos[i][2]);
          enter = true;
        }
      }
      if (enter)
        return;
    }
    Message.warning("Invalid instruction mnemonic: '" + mnemonic + "'");
  }

  /**
   * This method pretty prints the instruction set.
   */
  public void print() {
    // number of instructions
    IO.stdout.println(String.format("Number of Instructions: %s", String.format("%03d", this.instructions.size())));
    IO.stdout.println();
    IO.stdout.println("FORMAT   MNEMONIC                      USAGE");
    IO.stdout.println();
    int maxLength = -1;
    // get mnemonic max instruction length for pretty printer
    for (String mnemonic : this.instructions.keySet())
      maxLength = Math.max(maxLength, mnemonic.length());
    // get all formats
    Format[] formats = Format.values();
    // overhead here but necessary to print instructions in order
    for (int i = 0; i < formats.length; i++) {
      for (String mnemonic : this.instructions.keySet()) {
        Instruction inst = this.instructions.get(mnemonic);
        Format format = inst.getFormat();
        String usage = inst.getUsage();
        String space = "";
        for (int j = 0; j < (maxLength - mnemonic.length()); j++)
          space += " ";
        if (format == formats[i]) {
          IO.stdout.println(String.format(" [%s]%s  (%s)%s example: %s", format.toString(),
              (format.toString().length() == 2) ? " " : "  ", mnemonic, space, usage));
        }
      }
    }
    // print pseudos
    IO.stdout.println();
    IO.stdout.println(String.format("Number of Pseudo Instructions: %s", String.format("%03d", pseudos.length)));
    IO.stdout.println();
    IO.stdout.println("MNEMONIC              USAGE");
    IO.stdout.println();
    maxLength = -1;
    // get mnemonic max instruction length for pretty printer
    for (int i = 0; i < pseudos.length; i++)
      maxLength = Math.max(maxLength, pseudos[i][0].length());
    for (int i = 0; i < pseudos.length; i++) {
      String usage = pseudos[i][1];
      String space = "";
      String mnemonic = pseudos[i][0];
      for (int j = 0; j < (maxLength - mnemonic.length()); j++)
        space += " ";
      IO.stdout.println(String.format("(%s)%s example: %s", mnemonic, space, usage));
    }
  }

}
