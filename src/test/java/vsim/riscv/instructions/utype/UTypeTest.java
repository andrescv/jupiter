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

package jupiter.riscv.instructions.utype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jupiter.Globals;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.MachineCode;


/** jupiter.riscv.instructions.utype tests. */
public class UTypeTest {

  @Test
  void testDisassemble() {
    assertEquals("auipc x6, 0", Globals.iset.get("auipc").disassemble(new MachineCode(0x00000317)));
    assertEquals("lui x1, 524288", Globals.iset.get("lui").disassemble(new MachineCode(0x800000B7)));
    assertEquals("lui x0, 524288", Globals.iset.get("lui").disassemble(new MachineCode(0x80000037)));
    assertEquals("auipc x10, 65536", Globals.iset.get("auipc").disassemble(new MachineCode(0x10000517)));
  }

  @Test
  void testFields() {
    fieldsTest("lui", 0b0110111);
    fieldsTest("auipc", 0b0010111);
  }

  private void fieldsTest(String mnemonic, int opcode) {
    assertEquals(Format.U, Globals.iset.get(mnemonic).getFormat());
    assertEquals(opcode, Globals.iset.get(mnemonic).getOpCode());
    assertEquals(0, Globals.iset.get(mnemonic).getFunct3());
    assertEquals(0, Globals.iset.get(mnemonic).getFunct7());
  }

}
