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


/**
 * The Settings class contains the V-Sim simulator settings.
 */
public final class Settings {

  /** current version */
  public static final String VERSION = "vsim-v1.0.0-beta";

  /** global start label, set with -start flag */
  public static String START = "main";

  /** bare machine mode (no pseudos), set with -bare flag */
  public static boolean BARE = false;

  /** do not show warnings, set with -quiet flag */
  public static boolean QUIET = false;

  /** debugging mode, set with -debug flag or ebreak instruction */
  public static boolean DEBUG = false;

  /** show title and copyright notice, disable with -notitle */
  public static boolean TITLE = true;

  /** dump machine code to a file, set with -dump flag*/
  public static String DUMP = null;

  /** colorize output, disable with -nocolor flag */
  public static boolean COLORIZE = true;

}
