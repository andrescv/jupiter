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

package jvpiter;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import jvpiter.sim.Loader;
import jvpiter.utils.FS;


/** RISC-V Tests */
public class RISCVTests {

  /**
   * Runs a single RISC-V assembly file.
   *
   * @param file assembly file to simulate
   */
  private void run(File file) {
    ArrayList<File> files = new ArrayList<>(1);
    files.add(file);
    assertEquals(files.get(0).exists(), true);
    try {
      // user runtime exception for exit
      Flags.EXIT = false;
      // reset globals
      Globals.globl.reset();
      Globals.local.clear();
      // simulate
      Loader.load(files);
    } catch (RuntimeException e) {
      assertEquals("Jvpiter(0)", e.getMessage(), "failed: " + file.toString());
    }
  }

  @Test
  public void testSimulator() throws IOException {
    for (File file : FS.ls(FS.toFile("./src/test/riscv-tests"))) {
      run(file);
    }
  }

}
