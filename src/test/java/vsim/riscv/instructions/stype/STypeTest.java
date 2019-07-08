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

package vsim.riscv.instructions.stype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.stype tests. */
public class STypeTest {

  @Test
  void testSType() {
    assertEquals("sw x2, x10, 0", Globals.iset.get("sw").disassemble(new MachineCode(0x00A12023)));
    assertEquals("sh x10, x5, -12", Globals.iset.get("sh").disassemble(new MachineCode(0xFE551A23)));
    assertEquals("sb x5, x6, 4", Globals.iset.get("sb").disassemble(new MachineCode(0x00628223)));
    assertEquals("fsw x2, f0, 0", Globals.iset.get("fsw").disassemble(new MachineCode(0x00012027)));
    assertEquals("fsw x2, f8, 13", Globals.iset.get("fsw").disassemble(new MachineCode(0x008126A7)));
    assertEquals("fsw x10, f6, -2", Globals.iset.get("fsw").disassemble(new MachineCode(0xFE652F27)));
  }

}
