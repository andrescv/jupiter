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

import jvpiter.utils.Data;


/** jvpiter.riscv.hardware.RVIRegisterFile tests. */
public class RVIRegisterFileTest {

  @Test
  void testProgramCounter() {
    RVIRegisterFile rf = new RVIRegisterFile();
    assertEquals(Data.TEXT, rf.getProgramCounter());
    rf.incProgramCounter();
    assertEquals(Data.TEXT + Data.WORD_LENGTH, rf.getProgramCounter());
    rf.setProgramCounter(Data.TEXT + Data.WORD_LENGTH * 10);
    assertEquals(Data.TEXT + Data.WORD_LENGTH * 10, rf.getProgramCounter());
    rf.reset();
      assertEquals(Data.TEXT, rf.getProgramCounter());
  }

  @Test
  void testPrint() {
    RVIRegisterFile rf = new RVIRegisterFile();
    rf.print("a0");
    rf.print("pc");
    rf.print();
    assertThrows(IllegalArgumentException.class, () -> { rf.print("x60"); });
  }

}
