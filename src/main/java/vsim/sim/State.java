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

package vsim.sim;

import vsim.riscv.hardware.Memory;
import vsim.riscv.hardware.RVFRegisterFile;
import vsim.riscv.hardware.RVIRegisterFile;


/** Simulator state. */
public final class State {

  /** RISC-V main memory */
  private final Memory memory;

  /** RVI register file */
  private final RVIRegisterFile xregfile;

  /** RVF register file */
  private final RVFRegisterFile fregfile;

  /** Creates a new simulator state. */
  public State() {
    memory = new Memory();
    xregfile = new RVIRegisterFile();
    fregfile = new RVFRegisterFile();
  }

  /**
   * Returns main memory.
   *
   * @return main memory
   */
  public Memory memory() {
    return memory;
  }

  /**
   * Returns RVI register file.
   *
   * @return RVI register file
   */
  public RVIRegisterFile xregfile() {
    return xregfile;
  }

  /**
   * Returns RVF register file.
   *
   * @return RVF register file
   */
  public RVFRegisterFile fregfile() {
    return fregfile;
  }

  /** Resets simulator state. */
  public void reset() {
    memory.reset();
    xregfile.reset();
    fregfile.reset();
  }

}
