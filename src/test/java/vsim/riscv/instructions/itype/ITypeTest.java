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

package vsim.riscv.instructions.itype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.itype tests. */
public class ITypeTest {

  @Test
  void testDisassemble() {
    assertEquals("ebreak", Globals.iset.get("ebreak").disassemble(new MachineCode(0x00100073)));
    assertEquals("ecall", Globals.iset.get("ecall").disassemble(new MachineCode(0x00000073)));
    assertEquals("srai x10, x5, 1", Globals.iset.get("srai").disassemble(new MachineCode(0x4012D513)));
    assertEquals("jalr x1, x1, 0", Globals.iset.get("jalr").disassemble(new MachineCode(0x000080E7)));
    assertEquals("sltiu x7, x6, 20", Globals.iset.get("sltiu").disassemble(new MachineCode(0x01433393)));
    assertEquals("slti x7, x6, 40", Globals.iset.get("slti").disassemble(new MachineCode(0x02832393)));
    assertEquals("lb x29, x10, 2", Globals.iset.get("lb").disassemble(new MachineCode(0x00250E83)));
    assertEquals("lbu x30, x0, 3", Globals.iset.get("lbu").disassemble(new MachineCode(0x00304F03)));
    assertEquals("lh x1, x2, -12", Globals.iset.get("lh").disassemble(new MachineCode(0xFF411083)));
    assertEquals("lw x8, x2, 4", Globals.iset.get("lw").disassemble(new MachineCode(0x00412403)));
    assertEquals("slli x10, x10, 4", Globals.iset.get("slli").disassemble(new MachineCode(0x00451513)));
    assertEquals("srli x5, x5, 25", Globals.iset.get("srli").disassemble(new MachineCode(0x0192D293)));
    assertEquals("ori x10, x10, 1", Globals.iset.get("ori").disassemble(new MachineCode(0x00156513)));
    assertEquals("andi x10, x5, 255", Globals.iset.get("andi").disassemble(new MachineCode(0x0FF2F513)));
    assertEquals("xori x8, x9, 10", Globals.iset.get("xori").disassemble(new MachineCode(0x00A4C413)));
    assertEquals("addi x6, x0, 10", Globals.iset.get("addi").disassemble(new MachineCode(0x00A00313)));
    assertEquals("flw f5, x0, 5", Globals.iset.get("flw").disassemble(new MachineCode(0x00502287)));
    assertEquals("lhu x10, x2, 0", Globals.iset.get("lhu").disassemble(new MachineCode(0x00015503)));
    assertEquals("fence", Globals.iset.get("fence").disassemble(new MachineCode(0x0000000F)));
    assertEquals("csrrw x5, 10, x2", Globals.iset.get("csrrw").disassemble(new MachineCode(0x00A112F3)));
  }

  @Test
  void testFields() {
    fieldsTest("jalr", 0b1100111, 0b000, 0b0000000);
    fieldsTest("lb", 0b0000011, 0b000, 0b0000000);
    fieldsTest("lh", 0b0000011, 0b001, 0b0000000);
    fieldsTest("lw", 0b0000011, 0b010, 0b0000000);
    fieldsTest("lbu", 0b0000011, 0b100, 0b0000000);
    fieldsTest("lhu", 0b0000011, 0b101, 0b0000000);
    fieldsTest("addi", 0b0010011, 0b000, 0b0000000);
    fieldsTest("slti", 0b0010011, 0b010, 0b0000000);
    fieldsTest("sltiu", 0b0010011, 0b011, 0b0000000);
    fieldsTest("xori", 0b0010011, 0b100, 0b0000000);
    fieldsTest("ori", 0b0010011, 0b110, 0b0000000);
    fieldsTest("andi", 0b0010011, 0b111, 0b0000000);
    fieldsTest("slli", 0b0010011, 0b001, 0b0000000);
    fieldsTest("srli", 0b0010011, 0b101, 0b0000000);
    fieldsTest("srai", 0b0010011, 0b101, 0b0100000);
    fieldsTest("fence", 0b0001111, 0b000, 0b0000000);
    fieldsTest("ecall", 0b1110011, 0b000, 0b0000000);
    fieldsTest("ebreak", 0b1110011, 0b000, 0b0000000);
    fieldsTest("csrrw", 0b1110011, 0b001, 0b0000000);
    fieldsTest("csrrs", 0b1110011, 0b010, 0b0000000);
    fieldsTest("csrrc", 0b1110011, 0b011, 0b0000000);
    fieldsTest("csrrwi", 0b1110011, 0b101, 0b0000000);
    fieldsTest("csrrsi", 0b1110011, 0b110, 0b0000000);
    fieldsTest("csrrci", 0b1110011, 0b111, 0b0000000);
    fieldsTest("flw", 0b0000111, 0b010, 0b0000000);
  }

  private void fieldsTest(String mnemonic, int opcode, int funct3, int funct7) {
    assertEquals(Format.I, Globals.iset.get(mnemonic).getFormat());
    assertEquals(opcode, Globals.iset.get(mnemonic).getOpCode());
    assertEquals(funct3, Globals.iset.get(mnemonic).getFunct3());
    assertEquals(funct7, Globals.iset.get(mnemonic).getFunct7());
  }

}
