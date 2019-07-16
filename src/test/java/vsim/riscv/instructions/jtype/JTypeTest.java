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

package vsim.riscv.instructions.jtype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.jtype tests. */
public class JTypeTest {

  @Test
  void testJType() {
    assertEquals("jal x4, 16", Globals.iset.get("jal").disassemble(new MachineCode(0x0100026F)));
    assertEquals("jal x0, 24", Globals.iset.get("jal").disassemble(new MachineCode(0x0180006F)));
  }

  @Test
  void testFields() {
    fieldsTest("jal", 0b1101111);
  }

  private void fieldsTest(String mnemonic, int opcode) {
    assertEquals(Format.J, Globals.iset.get(mnemonic).getFormat());
    assertEquals(opcode, Globals.iset.get(mnemonic).getOpCode());
    assertEquals(0, Globals.iset.get(mnemonic).getFunct3());
    assertEquals(0, Globals.iset.get(mnemonic).getFunct7());
  }

}
