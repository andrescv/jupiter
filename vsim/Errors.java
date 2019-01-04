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

package vsim;

import java.util.ArrayList;
import vsim.assembler.DebugInfo;
import vsim.utils.IO;
import vsim.utils.Message;


/**
 * The class Errors contains useful methods to create and report errors.
 */
public final class Errors {

  /** V-Sim assembler, linker and runtime error list */
  private static final ArrayList<String> errors = new ArrayList<String>();

  /**
   * This method adds a raw error message to the error list {@link vsim.Errors#errors}.
   *
   * @param msg an error message
   */
  public static void add(String msg) {
    if (!Errors.errors.contains(msg))
      Errors.errors.add(msg);
  }

  /**
   * This method adds an error message with useful debug information to the error list {@link vsim.Errors#errors}.
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
    Errors.add(filename + ":" + phase + ":" + lineno + ": " + msg + newline + "    |" + newline
        + "    └─ (source line) " + source);
  }

  /**
   * This method adds an error message with some useful debug information to the error list {@link vsim.Errors#errors}.
   *
   * @param lineno source line number
   * @param filename source filename
   * @param phase the phase where the error ocurred (e.g assembler, linker)
   * @param msg an error message
   */
  public static void add(int lineno, String filename, String phase, String msg) {
    Errors.add(filename + ":" + phase + ":" + lineno + ":" + msg);
  }

  /**
   * This method reports all the current errors and exits if there are any errors in the error list
   * {@link vsim.Errors#errors}.
   *
   * @return true if there are errors, false otherwise.
   */
  public static boolean report() {
    if (Errors.errors.size() > 0) {
      // print every error message
      for (String msg : Errors.errors)
        Message.error(msg + System.getProperty("line.separator"));
      // report how many errors ocurred
      IO.stderr.println(Errors.errors.size() + " errors(s)");
      IO.stderr.flush();
      // clear messages
      Errors.errors.clear();
      // exit only in CLI mode
      if (!Settings.GUI)
        System.exit(1);
      return true;
    }
    return false;
  }

  /**
   * This method clears all the errors in the error list {@link vsim.Errors#errors}.
   */
  public static void clear() {
    Errors.errors.clear();
  }

}
