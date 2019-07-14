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

import java.io.File;


/** Command line flags. */
public final class Flags {

  /** exit with System.exit option */
  public static boolean EXIT = true;

  /** show help message option */
  public static boolean HELP = false;

  /** show version option */
  public static boolean VERSION = false;

  /** show license option */
  public static boolean LICENSE = false;

  /** bare machine mode (no pseudos) option */
  public static boolean BARE = false;

  /** debugging mode option */
  public static boolean DEBUG = false;

  /** extrict mode option, assembler warnings are consider errors */
  public static boolean EXTRICT = true;

  /** self-modifying code option */
  public static boolean SELF_MODIFYING = false;

  /** start label */
  public static String START = "__start";

  /** history size */
  public static int HIST_SIZE = 2000;

  /** dump code option file */
  public static File DUMP_CODE = null;

  /** dump static data option file */
  public static File DUMP_DATA = null;

}
