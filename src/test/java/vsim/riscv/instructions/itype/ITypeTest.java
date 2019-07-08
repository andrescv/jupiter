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
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.itype tests. */
public class ITypeTest {

  @Test
  void testIType() {
    assertEquals("ebreak x0, x0, 1", Globals.iset.get("ebreak").disassemble(new MachineCode(0x00100073)));
    assertEquals("ecall x0, x0, 0", Globals.iset.get("ecall").disassemble(new MachineCode(0x00000073)));
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
  }

}
