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

package jupiter;

import org.junit.jupiter.api.Test;

import jupiter.asm.Assembler;
import jupiter.linker.Linker;
import jupiter.sim.Loader;


/** Dummy Tests */
public class DummyTest {

  @Test
  public void testDummy() {
    Logger logger = new Logger();
    Globals globl = new Globals();
    Jupiter jupiter = new Jupiter();
    Flags flags = new Flags();
    Assembler asm = new Assembler();
    Linker linker = new Linker();
    Loader loader = new Loader();
  }

}
