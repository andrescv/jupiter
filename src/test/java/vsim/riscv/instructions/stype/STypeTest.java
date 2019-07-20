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

package jupiter.riscv.instructions.stype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import jupiter.Globals;
import jupiter.riscv.instructions.Format;
import jupiter.riscv.instructions.MachineCode;


/** jupiter.riscv.instructions.stype tests. */
public class STypeTest {

  @Test
  void testDisassemble() {
    assertEquals("sw x2, x10, 0", Globals.iset.get("sw").disassemble(new MachineCode(0x00A12023)));
    assertEquals("sh x10, x5, -12", Globals.iset.get("sh").disassemble(new MachineCode(0xFE551A23)));
    assertEquals("sb x5, x6, 4", Globals.iset.get("sb").disassemble(new MachineCode(0x00628223)));
    assertEquals("fsw x2, f0, 0", Globals.iset.get("fsw").disassemble(new MachineCode(0x00012027)));
    assertEquals("fsw x2, f8, 13", Globals.iset.get("fsw").disassemble(new MachineCode(0x008126A7)));
    assertEquals("fsw x10, f6, -2", Globals.iset.get("fsw").disassemble(new MachineCode(0xFE652F27)));
  }

  @Test
  void testFields() {
    fieldsTest("sb", 0b0100011, 0b000);
    fieldsTest("sh", 0b0100011, 0b001);
    fieldsTest("sw", 0b0100011, 0b010);
    fieldsTest("fsw", 0b0100111, 0b010);
  }

  private void fieldsTest(String mnemonic, int opcode, int funct3) {
    assertEquals(Format.S, Globals.iset.get(mnemonic).getFormat());
    assertEquals(opcode, Globals.iset.get(mnemonic).getOpCode());
    assertEquals(funct3, Globals.iset.get(mnemonic).getFunct3());
    assertEquals(0, Globals.iset.get(mnemonic).getFunct7());
  }

}
