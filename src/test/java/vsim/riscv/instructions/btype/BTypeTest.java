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

package vsim.riscv.instructions.btype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.btype tests. */
public class BTypeTest {

  @Test
  void testBType() {
    assertEquals("bne x5, x10, -4", Globals.iset.get("bne").disassemble(new MachineCode(0xFEA29EE3)));
    assertEquals("beq x5, x10, -20", Globals.iset.get("beq").disassemble(new MachineCode(0xFEA286E3)));
    assertEquals("bge x6, x10, -12", Globals.iset.get("bge").disassemble(new MachineCode(0xFEA35AE3)));
    assertEquals("bgeu x30, x17, -28", Globals.iset.get("bgeu").disassemble(new MachineCode(0xFF1F72E3)));
    assertEquals("blt x28, x11, -20", Globals.iset.get("blt").disassemble(new MachineCode(0xFEBE46E3)));
    assertEquals("bltu x6, x16, -36", Globals.iset.get("bltu").disassemble(new MachineCode(0xFD036EE3)));
  }

}
