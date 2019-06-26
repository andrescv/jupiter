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

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;

import vsim.Logger;
import vsim.asm.stmts.*;
import vsim.riscv.instructions.*;
import vsim.utils.Data;
import vsim.utils.IO;


/** Represents the available instruction set. */
public final class InstructionSet {

  /** instructions dictionary */
  private final HashMap<String, Instruction> instructions;

  /** pseudo-instructions */
  private final ArrayList<String> pseudos;

  /** Creates a new instruction set. */
  public InstructionSet() {
    instructions = new HashMap<String, Instruction>();
    pseudos = new ArrayList<>();
    populate();
  }

  /** Populates the instruction set instance with all the instructions available. */
  private void populate() {
    try {
      BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/iset/all.txt")));
      String line = "";
      String pkg = "";
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (line.startsWith(":")) {
          pkg = line.substring(1);
        } else if (!line.equals("")) {
          Class<?> cls = Class.forName(pkg + "." + line);
          // only final classes
          if (!Instruction.class.isAssignableFrom(cls) || Modifier.isAbstract(cls.getModifiers())
              || Modifier.isInterface(cls.getModifiers())) {
            continue;
          }
          // add this new instruction to isa
          Instruction inst = (Instruction) (cls.getConstructor().newInstance());
          String mnemonic = inst.getMnemonic();
          if (instructions.containsKey(mnemonic)) {
            IO.stderr().println("duplicated instruction name: '" + mnemonic + "', skip this");
          } else {
            instructions.put(mnemonic, inst);
          }
        }
      }
      br.close();
      // add pseudos
      br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/iset/pseudos.txt")));
      line = "";
      while ((line = br.readLine()) != null) {
        line = line.trim();
        if (!line.equals("")) {
          pseudos.add(line);
        }
      }
    } catch (Exception e) {
      Logger.error("could not load RISC-V instruction set");
      System.exit(1);
    }
  }

  /**
   * Prints instruction info.
   *
   * @param mnemonic instruction mnemonic.
   */
  public void print(String mnemonic) {
    if (contains(mnemonic)) {
      try {
        String path = String.format("/iset/%s.txt", mnemonic.toLowerCase());
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(path)));
        while (true) {
          String line = br.readLine();
          if (line != null) {
            IO.stdout().println(line);
          } else {
            break;
          }
        }
      } catch (Exception e) {
        Logger.error("could not display info for " + mnemonic + " instruction");
        System.exit(1);
      }
    } else {
      Logger.error("unknown instruction: " + mnemonic);
      System.exit(1);
    }
  }

  /** Prints available instruction set. */
  public void print() {
    String out = "[RV32IMF]" + Data.EOL;
    // TAL
    for (String i : instructions.keySet()) {
      System.out.println(i);
      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/iset/" + i + ".txt")));
        String line = br.readLine().trim();
        out += " * " + line + Data.EOL;
        br.close();
      } catch (Exception e) {
        Logger.error("could not display instruction set");
        System.exit(1);
      }
    }
    // Pseudos
    out += Data.EOL + "[PSEUDOS]" + Data.EOL;
    for (String i : pseudos) {
      try {
        BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream("/iset/" + i + ".txt")));
        String line = br.readLine().trim();
        out += " * " + line + Data.EOL;
        br.close();
      } catch (Exception e) {
        Logger.error("could not display instruction set");
        System.exit(1);
      }
    }
    IO.stdout().println(out.trim());
  }

  /**
   * Returns the intruction that represent the given mnemonic.
   *
   * @param mnemonic instruction mnemonic
   * @return the instruction or null if the mnemonic is invalid
   */
  public Instruction get(String mnemonic) {
    return instructions.get(mnemonic.toLowerCase().trim());
  }

  /**
   * Decodes a machine code to a RISC-V statement.
   *
   * @param code machine code to decode
   * @return RISC-V statement or {@code null} if code is invalid or not implemented
   */
  public Statement decode(MachineCode code) {
    switch (code.get(InstructionField.OPCODE)) {
      case 0b0110111: // lui
        return new UType("lui", code);
      case 0b0010111: // auipc
        return new UType("auipc", code);
      case 0b1101111: // jal
        return new JType("jal", code);
      case 0b1100111: // jalr
        return new IType("jalr", code);
      case 0b1100011: // branches
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // beq
            return new BType("beq", code);
          case 0b001: // bne
            return new BType("bne", code);
          case 0b100: // blt
            return new BType("blt", code);
          case 0b101: // bge
            return new BType("bge", code);
          case 0b110: // bltu
            return new BType("bltu", code);
          case 0b111: // bgeu
            return new BType("bgeu", code);
          default:
            return null;
        }
      case 0b0000011: // loads
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // lb
            return new IType("lb", code);
          case 0b001: // lh
            return new IType("lh", code);
          case 0b010: // lw
            return new IType("lw", code);
          case 0b100: // lbu
            return new IType("lbu", code);
          case 0b101: // lhu
            return new IType("lhu", code);
          default:
            return null;
        }
      case 0b0100011: // stores
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // sb
            return new SType("sb", code);
          case 0b001: // sh
            return new SType("sh", code);
          case 0b010: // sw
            return new SType("sw", code);
          default:
            return null;
        }
      case 0b0010011: // immediates
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000: // addi
            return new IType("addi", code);
          case 0b010: // slti
            return new IType("slti", code);
          case 0b011: // sltiu
            return new IType("sltiu", code);
          case 0b100: // xori
            return new IType("xori", code);
          case 0b110: // ori
            return new IType("ori", code);
          case 0b111: // andi
            return new IType("andi", code);
          case 0b001: // slli
            return new IType("slli", code);
          case 0b101:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // srli
                return new IType("srli", code);
              case 0b0100000: // srai
                return new IType("srai", code);
              default:
                return null;
            }
          default:
            return null;
        }
      case 0b0110011: // r
        switch (code.get(InstructionField.FUNCT3)) {
          case 0b000:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // add
                return new RType("add", code);
              case 0b0100000: // sub
                return new RType("sub", code);
              case 0b0000001: // mul
                return new RType("mul", code);
              default:
                return null;
            }
          case 0b001:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // sll
                return new RType("sll", code);
              case 0b0000001: // mulh
                return new RType("mulh", code);
              default:
                return null;
            }
          case 0b010:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // slt
                return new RType("slt", code);
              case 0b0000001: // mulhsu
                return new RType("mulhsu", code);
              default:
                return null;
            }
          case 0b011:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // sltu
                return new RType("sltu", code);
              case 0b0000001: // mulhu
                return new RType("mulhu", code);
              default:
                return null;
            }
          case 0b100:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // xor
                return new RType("xor", code);
              case 0b0000001: // div
                return new RType("div", code);
              default:
                return null;
            }
          case 0b101:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // srl
                return new RType("srl", code);
              case 0b0100000: // sra
                return new RType("sra", code);
              case 0b0000001: // divu
                return new RType("divu", code);
              default:
                return null;
            }
          case 0b110:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // or
                return new RType("or", code);
              case 0b0000001: // rem
                return new RType("rem", code);
              default:
                return null;
            }
          case 0b111:
            switch (code.get(InstructionField.FUNCT7)) {
              case 0b0000000: // and
                return new RType("and", code);
              case 0b0000001: // remu
                return new RType("remu", code);
              default:
                return null;
            }
          default:
            return null;
        }
      case 0b1110011:
        switch (code.get(InstructionField.IMM_11_0)) {
          case 0b000000000000: // ecall
            return new IType("ecall", code);
          case 0b000000000001: // ebreak
            return new IType("ebreak", code);
          default:
            return null;
        }
      // f extension
      case 0b0000111: // flw
        return new IType("flw", code);
      case 0b0100111: // fsw
        return new SType("fsw", code);
      // fused
      case 0b1000011:
        return new R4Type("fmadd.s", code);
      case 0b1000111:
        return new R4Type("fmsub.s", code);
      case 0b1001011:
        return new R4Type("fnmsub.s", code);
      case 0b1001111:
        return new R4Type("fnmadd.s", code);
      case 0b1010011: // f
        switch (code.get(InstructionField.FUNCT7)) {
          case 0b0000000:
            return new RType("fadd.s", code);
          case 0b0000100:
            return new RType("fsub.s", code);
          case 0b0001000:
            return new RType("fmul.s", code);
          case 0b0001100:
            return new RType("fdiv.s", code);
          case 0b0101100:
            return new RType("fsqrt.s", code);
          case 0b0010000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return new RType("fsgnj.s", code);
              case 0b001:
                return new RType("fsgnjn.s", code);
              case 0b010:
                return new RType("fsgnjx.s", code);
              default:
                return null;
            }
          case 0b0010100:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return new RType("fmin.s", code);
              case 0b001:
                return new RType("fmax.s", code);
              default:
                return null;
            }
          case 0b1100000:
            switch (code.get(InstructionField.RS2)) {
              case 0b00000:
                return new RType("fcvt.w.s", code);
              case 0b00001:
                return new RType("fcvt.wu.s", code);
              default:
                return null;
            }
          case 0b1110000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b000:
                return new RType("fmv.x.w", code);
              case 0b001:
                return new RType("fclass.s", code);
              default:
                return null;
            }
          case 0b1010000:
            switch (code.get(InstructionField.FUNCT3)) {
              case 0b010:
                return new RType("feq.s", code);
              case 0b001:
                return new RType("flt.s", code);
              case 0b000:
                return new RType("fle.s", code);
              default:
                return null;
            }
          case 0b1101000:
            switch (code.get(InstructionField.RS2)) {
              case 0b00000:
                return new RType("fcvt.s.w", code);
              case 0b00001:
                return new RType("fcvt.s.wu", code);
              default:
                return null;
            }
          case 0b1111000:
            return new RType("fmv.w.x", code);
          default:
            return null;
        }
      default:
        return null;
    }
  }

  /**
   * Returns if the instruction set contains the given instruction mnemonic.
   *
   * @param mnemonic instruction mnemonic
   * @return true if the instruction mnemonic is in the instruction set, false if not
   */
  public boolean contains(String mnemonic) {
    String m = mnemonic.toLowerCase();
    return instructions.containsKey(m) || pseudos.contains(m);
  }

}
