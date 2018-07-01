/*
Copyright (C) 2018 Andres Castellanos

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

package vsim;

import vsim.utils.IO;
import vsim.utils.Message;
import java.util.ArrayList;
import vsim.assembler.DebugInfo;


/**
 * The class Errors contains useful methods to create and report errors.
 */
public final class Errors {

  /** V-Sim assembler, linker and runtime error list */
  public static final ArrayList<String> errors = new ArrayList<String>();

  /**
   * This method adds a raw error message to the error list
   * {@link vsim.Errors#errors}.
   *
   * @param msg an error message
   */
  public static void add(String msg) {
    Errors.errors.add(msg);
  }

  /**
   * This method adds an error message with useful debug information to the
   * error list {@link vsim.Errors#errors}.
   *
   * @param debug debug information
   * @param phase the phase where the error ocurred (e.g assembler, linker)
   * @param msg an error message
   */
  public static void add(DebugInfo debug, String phase, String msg) {
    String filename = debug.getFilename();
    String newline = System.getProperty("line.separator");
    String source = debug.getSource();
    int lineno = debug.getLineNumber();
    Errors.add(
      filename + ":" + phase + ":" + lineno + ": " + msg +
      newline + "    |" +
      newline + "    └─> (source line) " + source
    );
  }

  /**
   * This method reports all the current errors and exits if there are
   * any errors in the error list {@link vsim.Errors#errors}.
   */
  public static void report() {
    if (Errors.errors.size() > 0) {
      // print every error message
      for (String msg: Errors.errors)
        Message.error(msg + System.getProperty("line.separator"));
      // report how many errors ocurred
      IO.stderr.println(Errors.errors.size() + " errors(s)");
      IO.stderr.flush();
      System.exit(1);
    }
  }

  /**
   * This method clears all the errors in the error list
   * {@link vsim.Errors#errors}.
   */
  public static void clear() {
    Errors.errors.clear();
  }

}
