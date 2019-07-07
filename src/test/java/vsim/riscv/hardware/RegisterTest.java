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

package vsim.riscv.hardware;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/** vsim.riscv.hardware.Register tests. */
public class RegisterTest {

  @Test
  void testEditableRegister() {
    Register reg = new Register(1, "ra", 0, true);
    assertEquals(1, reg.getNumber());
    assertEquals("ra", reg.getMnemonic());
    assertEquals(0, reg.getValue());
    assertEquals(true, reg.isEditable());
    reg.setValue(100);
    assertEquals(100, reg.getValue());
    reg.reset();
    assertEquals(0, reg.getValue());
  }

  @Test
  void testNonEditableRegister() {
    Register reg = new Register(0, "zero", 0, false);
    assertEquals(0, reg.getNumber());
    assertEquals("zero", reg.getMnemonic());
    assertEquals(0, reg.getValue());
    assertEquals(false, reg.isEditable());
    reg.setValue(100);
    assertEquals(0, reg.getValue());
    reg.reset();
    assertEquals(0, reg.getValue());
  }

}
