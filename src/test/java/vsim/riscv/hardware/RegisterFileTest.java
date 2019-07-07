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
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.HashMap;

import org.junit.jupiter.api.Test;

import vsim.utils.Data;


/** vsim.riscv.hardware.RegisterFile tests. */
public class RegisterFileTest {

  @Test
  void testCreateRegFile() {
    RegisterFile rf = new RVIRegisterFile();
    rf.setRegister("a0", 10);
    assertEquals(10, rf.getRegister("a0"));
    assertEquals(rf.getRegister("a0"), rf.getRegister(10));
  }

  @Test
  void testObserver() {
    Observer observer = new Observer();
    RegisterFile rf = new RVIRegisterFile();
    rf.addObserver(observer);
    rf.setRegister(1, 10);
    assertEquals("here", observer.message);
    rf.removeObserver(observer);
    rf.setRegister(1, 20);
    assertEquals("here", observer.message);
  }

  @Test
  void testReset() {
    RegisterFile rf = new RVIRegisterFile();
    assertEquals(0, rf.getRegister("a0"));
    rf.setRegister("a0", 10);
    assertEquals(10, rf.getRegister("a0"));
    rf.reset();
    assertEquals(0, rf.getRegister("a0"));
  }

  @Test
  void testSetRegister() {
    RegisterFile rf = new RVIRegisterFile();
    rf.setRegister("a0", 10);
    assertEquals(10, rf.getRegister("a0"));
    rf.setRegister(1, 0xcafe);
    assertEquals(0xcafe, rf.getRegister(1));
    assertThrows(IllegalArgumentException.class, () -> { rf.setRegister("x65", 10); });
    assertThrows(IllegalArgumentException.class, () -> { rf.setRegister(33, 10); });
  }

  @Test
  void testGetRegister() {
    RegisterFile rf = new RVIRegisterFile();
    assertEquals(Data.STACK_POINTER, rf.getRegister("sp"));
    assertEquals(0, rf.getRegister(0));
    assertThrows(IllegalArgumentException.class, () -> { rf.getRegister(65); });
    assertThrows(IllegalArgumentException.class, () -> { rf.getRegister("x65"); });
  }

  @Test
  void testHistory() {
    RegisterFile rf = new RVIRegisterFile();
    assertEquals(0, rf.getRegister("a0"));
    rf.setRegister("a0", 10);
    assertEquals(10, rf.getRegister("a0"));
    HashMap<String, Integer> diff = rf.getDiff();
    assertEquals(true, diff.size() != 0);
    rf.restore(diff);
    assertEquals(0, rf.getRegister("a0"));
  }

  @Test
  void testGetRF() {
    RegisterFile rf = new RVIRegisterFile();
    assertEquals(rf.getRF().size(), 32);
  }

}
