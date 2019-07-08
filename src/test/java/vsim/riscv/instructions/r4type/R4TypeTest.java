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

package vsim.riscv.instructions.r4type;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.r4type tests. */
public class R4TypeTest {

  @Test
  void testR4Type() {
    assertEquals("fmadd.s f3, f0, f1, f2", Globals.iset.get("fmadd.s").disassemble(new MachineCode(0x101001C3)));
    assertEquals("fnmadd.s f3, f0, f1, f2", Globals.iset.get("fnmadd.s").disassemble(new MachineCode(0x101001CF)));
    assertEquals("fmsub.s f3, f0, f1, f2", Globals.iset.get("fmsub.s").disassemble(new MachineCode(0x101001C7)));
    assertEquals("fnmsub.s f3, f0, f1, f2", Globals.iset.get("fnmsub.s").disassemble(new MachineCode(0x101001CB)));
  }

}
