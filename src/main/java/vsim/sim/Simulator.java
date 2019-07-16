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

import vsim.Globals;
import vsim.Logger;
import vsim.State;
import vsim.VSim;
import vsim.asm.stmts.Statement;
import vsim.exc.*;
import vsim.linker.LinkedProgram;


/** V-Sim simulator. */
public final class Simulator {

  /** linked program */
  private final LinkedProgram program;

  /**
   * Creates a new V-Sim simulator.
   *
   * @param program linked program
   */
  public Simulator(LinkedProgram program) {
    this.program = program;
    program.load();
  }

  /** Simulates a RISC-V program. */
  public void simulate() {
    State state = program.getState();
    while (true) {
      try {
        Statement stmt = program.next();
        Globals.iset.get(stmt.mnemonic()).execute(stmt.code(), state);
      } catch (BreakpointException e) {
        (new Debugger(program, false)).debug();
        break;
      } catch (HaltException e) {
        VSim.exit(e.getCode());
      } catch (SimulationException e) {
        Logger.error(e.getMessage());
        VSim.exit(1);
      }
    }
  }

}
