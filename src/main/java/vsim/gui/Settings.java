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

package vsim.gui;

import java.util.prefs.Preferences;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;


/** V-Sim GUI settings. */
public final class Settings {

  /** preferences object */
  private static final Preferences prefs = Preferences.userRoot().node("V-Sim");

  /** user directory */
  public static final SimpleStringProperty USER_DIR = new SimpleStringProperty(System.getProperty("user.dir"));

  /** show symbol table setting */
  public static final SimpleBooleanProperty SHOW_ST = new SimpleBooleanProperty(false);

  /** popup dialog for input ecalls setting */
  public static final SimpleBooleanProperty POPUP_DIALOG = new SimpleBooleanProperty(false);

  /** assemble all files in current directory setting */
  public static final SimpleBooleanProperty ASSEMBLE_ALL = new SimpleBooleanProperty(false);

  /** assembler warnings are consider errors setting */
  public static final SimpleBooleanProperty ASSEMBLER_WARNINGS = new SimpleBooleanProperty(false);

  /** permit pseudo instructions setting */
  public static final SimpleBooleanProperty PERMIT_PSEUDOS = new SimpleBooleanProperty(true);

  /** self-modifying code setting */
  public static final SimpleBooleanProperty SELF_MODIFYING = new SimpleBooleanProperty(false);

  /** editor auto indent setting */
  public static final SimpleBooleanProperty AUTO_INDENT = new SimpleBooleanProperty(true);

  /** dark mode setting */
  public static final SimpleBooleanProperty DARK_MODE = new SimpleBooleanProperty(true);

  /** editor tab size */
  public static final SimpleIntegerProperty TAB_SIZE = new SimpleIntegerProperty(2);


  /** Loads V-Sim GUI settings. */
  public static void load() {
    USER_DIR.set(prefs.get("USER_DIR", USER_DIR.get()));
    SHOW_ST.set(prefs.getBoolean("SHOW_ST", SHOW_ST.get()));
    POPUP_DIALOG.set(prefs.getBoolean("POPUP_DIALOG", POPUP_DIALOG.get()));
    ASSEMBLE_ALL.set(prefs.getBoolean("ASSEMBLE_ALL", ASSEMBLE_ALL.get()));
    ASSEMBLER_WARNINGS.set(prefs.getBoolean("ASSEMBLER_WARNINGS", ASSEMBLER_WARNINGS.get()));
    PERMIT_PSEUDOS.set(prefs.getBoolean("PERMIT_PSEUDOS", PERMIT_PSEUDOS.get()));
    SELF_MODIFYING.set(prefs.getBoolean("SELF_MODIFYING", SELF_MODIFYING.get()));
    AUTO_INDENT.set(prefs.getBoolean("AUTO_INDENT", AUTO_INDENT.get()));
    DARK_MODE.set(prefs.getBoolean("DARK_MODE", DARK_MODE.get()));
    TAB_SIZE.set(prefs.getInt("TAB_SIZE", TAB_SIZE.get()));
  }

  /** Toggles and saves show symbol table setting. */
  public static void toggleShowSymbolTable() {
    SHOW_ST.set(!SHOW_ST.get());
    prefs.putBoolean("SHOW_ST", SHOW_ST.get());
  }

  /** Toggles and saves popup dialog setting. */
  public static void togglePopupDialog() {
    POPUP_DIALOG.set(!POPUP_DIALOG.get());
    prefs.putBoolean("POPUP_DIALOG", POPUP_DIALOG.get());
  }

  /** Toggles and saves assemble all setting. */
  public static void toggleAssembleAll() {
    ASSEMBLE_ALL.set(!ASSEMBLE_ALL.get());
    prefs.putBoolean("ASSEMBLE_ALL", ASSEMBLE_ALL.get());
  }

  /** Toggles and saves assembler warnings setting. */
  public static void toggleAssemblerWarnings() {
    ASSEMBLER_WARNINGS.set(!ASSEMBLER_WARNINGS.get());
    prefs.putBoolean("ASSEMBLER_WARNINGS", ASSEMBLER_WARNINGS.get());
  }

  /** Toggles and saves permit pseudos setting. */
  public static void togglePermitPseudos() {
    PERMIT_PSEUDOS.set(!PERMIT_PSEUDOS.get());
    prefs.putBoolean("PERMIT_PSEUDOS", PERMIT_PSEUDOS.get());
  }

  /** Toggles and saves self-modifying code setting. */
  public static void toggleSelfModifyingCode() {
    SELF_MODIFYING.set(!SELF_MODIFYING.get());
    prefs.putBoolean("SELF_MODIFYING", SELF_MODIFYING.get());
  }

  /** Toggles and saves auto indent setting. */
  public static void toggleAutoIndent() {
    AUTO_INDENT.set(!AUTO_INDENT.get());
    prefs.putBoolean("AUTO_INDENT", AUTO_INDENT.get());
  }

  /** Toggles and saves dark mode setting. */
  public static void toggleDarkMode() {
    DARK_MODE.set(!DARK_MODE.get());
    prefs.putBoolean("DARK_MODE", DARK_MODE.get());
  }

  /**
   * Sets and saves user directory setting.
   *
   * @param dir new user directory
   */
  public static void setUserDir(String dir) {
    USER_DIR.set(dir);
    prefs.put("USER_DIR", USER_DIR.get());
  }

  /**
   * Sets and saves tab size setting.
   *
   * @param size new tab size
   */
  public static void setTabSize(int size) {
    TAB_SIZE.set(size);
    prefs.putInt("TAB_SIZE", size);
  }

}
