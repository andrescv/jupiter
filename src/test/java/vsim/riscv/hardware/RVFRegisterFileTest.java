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

package jvpiter.riscv.hardware;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;


/** jvpiter.riscv.hardware.RVFRegisterFile tests. */
public class RVFRegisterFileTest {

  @Test
  void testGetRegisterFloat() {
    RVFRegisterFile rf = new RVFRegisterFile();
    assertThrows(IllegalArgumentException.class, () -> { rf.getRegisterFloat(65); });
    assertThrows(IllegalArgumentException.class, () -> { rf.getRegisterFloat("f65"); });
    assertEquals(0.0f, rf.getRegisterFloat("f0"));
    assertEquals(0.0f, rf.getRegisterFloat(0));
  }

  @Test
  void testSetRegisterFloat() {
    RVFRegisterFile rf = new RVFRegisterFile();
    rf.setRegister("f0", 1.25f);
    assertEquals(1.25f, rf.getRegisterFloat("f0"));
    rf.setRegister(2, 3.1416f);
    assertEquals(3.1416f, rf.getRegisterFloat(2));
    assertThrows(IllegalArgumentException.class, () -> { rf.setRegister(65, 12.5f); });
    assertThrows(IllegalArgumentException.class, () -> { rf.setRegister("f65", 12.5f); });
  }

  @Test
  void testPrint() {
    RVFRegisterFile rf = new RVFRegisterFile();
    rf.print("f0");
    rf.print();
    assertThrows(IllegalArgumentException.class, () -> { rf.print("f60"); });
  }

}
