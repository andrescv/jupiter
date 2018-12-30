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

import java.io.File;
import java.util.prefs.Preferences;


/**
 * The Settings class contains the V-Sim simulator settings.
 */
public final class Settings {

  /** current version */
  public static final String VERSION = "v1.1.0";

  /** if running in GUI mode */
  public static boolean GUI = false;

  /** display mode of RVI register */
  public static int DISP_RVI_REG = 0;

  /** display mode of RVF register */
  public static int DISP_RVF_REG= 0;

  /** display mode of memory cell */
  public static int DISP_MEM_CELL = 0;

  /** history length (backstep) */
  public static int HIST_SIZE = 2000;

  /** if popup an input dialog for input ecalls */
  public static boolean POPUP_ECALL_INPUT = false;

  /** user directory */
  public static File DIR = new File(System.getProperty("user.dir"));

  /** installation path */
  public static File ROOT = null;

  /** trap handler path */
  public static File TRAP = null;

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
  public static boolean COLORIZE = false;

  /** show labels (only in gui mode) */
  public static boolean SHOW_LABELS = false;

  /** assemble all files currently open (only in gui mode) */
  public static boolean ASSEMBLE_ONLY_OPEN = false;

  /** extrict mode assembler warnings are consider errors */
  public static boolean EXTRICT = false;

  /**
   * Loads all saved preferences in GUI mode.
   */
  public static void load() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    Settings.START = prefs.get("START", "main");
    Settings.BARE = prefs.getBoolean("BARE", false);
    Settings.EXTRICT = prefs.getBoolean("EXTRICT", false);
    Settings.SHOW_LABELS = prefs.getBoolean("SHOW_LABELS", false);
    Settings.POPUP_ECALL_INPUT = prefs.getBoolean("POPUP_ECALL_INPUT", false);
    Settings.ASSEMBLE_ONLY_OPEN = prefs.getBoolean("ASSEMBLE_ONLY_OPEN", false);
  }

  /**
   * Sets global start label.
   *
   * @param start global label name
   * @return true if success, false if not
   */
  public static boolean setStart(String start) {
    if (start != null && start.length() > 0 && start.trim().matches("[a-zA-Z_]([a-zA-Z0-9_]*(\".\"[a-zA-Z0-9_]+)?)")) {
      Preferences prefs = Preferences.userRoot().node("vsim");
      prefs.put("START", start.trim());
      Settings.START = start.trim();
      return true;
    }
    return false;
  }

  /**
   * Toggles bare setting.
   *
   * @return bare setting value after toggle
   */
  public static boolean toggleBare() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    prefs.putBoolean("BARE", !Settings.BARE);
    Settings.BARE = !Settings.BARE;
    return Settings.BARE;
  }

  /**
   * Toggles extrict setting.
   *
   * @return extrict setting value after toggle
   */
  public static boolean toggleExtrict() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    prefs.putBoolean("EXTRICT", !Settings.EXTRICT);
    Settings.EXTRICT = !Settings.EXTRICT;
    return Settings.EXTRICT;
  }

  /**
   * Toggles show labels setting.
   *
   * @return show labels setting value after toggle
   */
  public static boolean toggleShowLabels() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    prefs.putBoolean("SHOW_LABELS", !Settings.SHOW_LABELS);
    Settings.SHOW_LABELS = !Settings.SHOW_LABELS;
    return Settings.SHOW_LABELS;
  }

  /**
   * Toggles popup setting.
   *
   * @return popup setting value after toggle
   */
  public static boolean togglePopup() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    prefs.putBoolean("POPUP_ECALL_INPUT", !Settings.POPUP_ECALL_INPUT);
    Settings.POPUP_ECALL_INPUT = !Settings.POPUP_ECALL_INPUT;
    return Settings.POPUP_ECALL_INPUT;
  }

  /**
   * Toggles only open setting.
   *
   * @return only open setting value after toggle
   */
  public static boolean toggleAssembleOnlyOpen() {
    Preferences prefs = Preferences.userRoot().node("vsim");
    prefs.putBoolean("ASSEMBLE_ONLY_OPEN", !Settings.ASSEMBLE_ONLY_OPEN);
    Settings.ASSEMBLE_ONLY_OPEN = !Settings.ASSEMBLE_ONLY_OPEN;
    return Settings.ASSEMBLE_ONLY_OPEN;
  }

}
