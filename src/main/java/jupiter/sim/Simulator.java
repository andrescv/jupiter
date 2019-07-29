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

package jupiter.sim;

import jupiter.Globals;
import jupiter.Jupiter;
import jupiter.Logger;
import jupiter.asm.stmts.Statement;
import jupiter.exc.*;
import jupiter.linker.LinkedProgram;
import jupiter.utils.IO;


/** Jupiter simulator. */
public final class Simulator {

  /** program history */
  private final History history;
  /** linked program */
  private final LinkedProgram program;

  /**
   * Creates a new Jupiter simulator.
   *
   * @param program linked program
   */
  public Simulator(LinkedProgram program) {
    this.program = program;
    program.load();
    history = new History();
  }

  /** Simulates a RISC-V program. */
  public void simulate() {
    State state = program.getState();
    while (true) {
      try {
        // get next statement
        Statement stmt = program.next();
        // save PC and heap pointer
        history.savePCAndHeap(state);
        // execute
        Globals.iset.get(stmt.mnemonic()).execute(stmt.code(), state);
        // save memory and register files
        history.saveMemAndRegs(state);
      } catch (BreakpointException e) {
        (new Debugger(program, history)).debug();
        break;
      } catch (HaltException e) {
        if (Globals.PRINT) {
          IO.stdout().println();
          IO.stdout().println();
        };
        state.memory().cache().stats();
        IO.stdout().println();
        Logger.info(String.format("exit(%d)", e.getCode()));
        Jupiter.exit(e.getCode());
      } catch (SimulationException e) {
        if (Globals.PRINT) {
          IO.stdout().println();
          IO.stdout().println();
        };
        Logger.error(e.getMessage());
        IO.stdout().println();
        Logger.info(String.format("exit(%d)", -1));
        Jupiter.exit(1);
      }
    }
  }

}
