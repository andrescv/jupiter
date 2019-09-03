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

package jupiter.gui;

import java.io.File;
import java.util.prefs.Preferences;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;

import jupiter.Flags;
import jupiter.utils.FS;


/** Jupiter GUI settings. */
public final class Settings {

  /** preferences object */
  private static final Preferences prefs = Preferences.userRoot().node("Jupiter");

  /** user directory */
  public static final SimpleStringProperty USER_DIR = new SimpleStringProperty(System.getProperty("user.dir"));

  /** show symbol table setting */
  public static final SimpleBooleanProperty SHOW_ST = new SimpleBooleanProperty(false);

  /** enables cache setting */
  public static final SimpleBooleanProperty CACHE_ENABLED = new SimpleBooleanProperty(true);

  /** assemble only open files setting */
  public static final SimpleBooleanProperty ASSEMBLE_ONLY_SELECTED = new SimpleBooleanProperty(true);

  /** assemble only open files setting */
  public static final SimpleBooleanProperty ASSEMBLE_ONLY_OPEN = new SimpleBooleanProperty(false);

  /** assemble all files in current directory setting */
  public static final SimpleBooleanProperty ASSEMBLE_ALL = new SimpleBooleanProperty(false);

  /** assembler warnings are consider errors setting */
  public static final SimpleBooleanProperty ASSEMBLER_WARNINGS = new SimpleBooleanProperty(false);

  /** permit pseudo instructions setting */
  public static final SimpleBooleanProperty PERMIT_PSEUDOS = new SimpleBooleanProperty(true);

  /** self-modifying code setting */
  public static final SimpleBooleanProperty SELF_MODIFYING = new SimpleBooleanProperty(false);

  /** global start label setting */
  public static final SimpleStringProperty START = new SimpleStringProperty("__start");

  /** editor auto indent setting */
  public static final SimpleBooleanProperty AUTO_INDENT = new SimpleBooleanProperty(true);

  /** dark mode setting */
  public static final SimpleBooleanProperty DARK_MODE = new SimpleBooleanProperty(true);

  /** editor tab size */
  public static final SimpleIntegerProperty TAB_SIZE = new SimpleIntegerProperty(2);

  /** text editor font size */
  public static final SimpleIntegerProperty CODE_FONT_SIZE = new SimpleIntegerProperty(20);

  /** console font size */
  public static final SimpleIntegerProperty CONSOLE_FONT_SIZE = new SimpleIntegerProperty(16);


  /** Loads Jupiter GUI settings. */
  public static void load() {
    USER_DIR.set(prefs.get("USER_DIR", USER_DIR.get()));
    SHOW_ST.set(prefs.getBoolean("SHOW_ST", SHOW_ST.get()));
    CACHE_ENABLED.set(prefs.getBoolean("CACHE_ENABLED", CACHE_ENABLED.get()));
    ASSEMBLE_ONLY_SELECTED.set(prefs.getBoolean("ASSEMBLE_ONLY_SELECTED", ASSEMBLE_ONLY_SELECTED.get()));
    ASSEMBLE_ONLY_OPEN.set(prefs.getBoolean("ASSEMBLE_ONLY_OPEN", ASSEMBLE_ONLY_OPEN.get()));
    ASSEMBLE_ALL.set(prefs.getBoolean("ASSEMBLE_ALL", ASSEMBLE_ALL.get()));
    ASSEMBLER_WARNINGS.set(prefs.getBoolean("ASSEMBLER_WARNINGS", ASSEMBLER_WARNINGS.get()));
    PERMIT_PSEUDOS.set(prefs.getBoolean("PERMIT_PSEUDOS", PERMIT_PSEUDOS.get()));
    SELF_MODIFYING.set(prefs.getBoolean("SELF_MODIFYING", SELF_MODIFYING.get()));
    START.set(prefs.get("START", START.get()));
    AUTO_INDENT.set(prefs.getBoolean("AUTO_INDENT", AUTO_INDENT.get()));
    DARK_MODE.set(prefs.getBoolean("DARK_MODE", DARK_MODE.get()));
    TAB_SIZE.set(prefs.getInt("TAB_SIZE", TAB_SIZE.get()));
    CODE_FONT_SIZE.set(prefs.getInt("CODE_FONT_SIZE", CODE_FONT_SIZE.get()));
    CONSOLE_FONT_SIZE.set(prefs.getInt("CONSOLE_FONT_SIZE", CONSOLE_FONT_SIZE.get()));
    // set flags
    Flags.BARE = !PERMIT_PSEUDOS.get();
    Flags.EXTRICT = ASSEMBLER_WARNINGS.get();
    Flags.SELF_MODIFYING = SELF_MODIFYING.get();
    Flags.START = START.get();
    Flags.CACHE_ENABLED = CACHE_ENABLED.get();
    // ensure USER_DIR exists
    if (!FS.toFile(USER_DIR.get()).exists()) {
      USER_DIR.set(System.getProperty("user.dir"));
      prefs.put("USER_DIR", USER_DIR.get());
    }
  }

  /** Toggles and saves show symbol table setting. */
  public static void toggleShowSymbolTable() {
    SHOW_ST.set(!SHOW_ST.get());
    prefs.putBoolean("SHOW_ST", SHOW_ST.get());
  }

  /** Toggles and saves enable cache simulation setting. */
  public static void toggleCacheEnabled() {
    CACHE_ENABLED.set(!CACHE_ENABLED.get());
    Flags.CACHE_ENABLED = CACHE_ENABLED.get();
    prefs.putBoolean("CACHE_ENABLED", CACHE_ENABLED.get());
  }

  /** Toggles and saves assemble only selected file setting. */
  public static void toggleAssembleOnlySelected() {
    ASSEMBLE_ONLY_SELECTED.set(!ASSEMBLE_ONLY_SELECTED.get());
    if (ASSEMBLE_ONLY_SELECTED.get()) {
      ASSEMBLE_ONLY_OPEN.set(false);
      ASSEMBLE_ALL.set(false);
      prefs.putBoolean("ASSEMBLE_ONLY_OPEN", false);
      prefs.putBoolean("ASSEMBLE_ALL", false);
    }
    prefs.putBoolean("ASSEMBLE_ONLY_SELECTED", ASSEMBLE_ONLY_SELECTED.get());
  }

  /** Toggles and saves assemble only open files setting. */
  public static void toggleAssembleOnlyOpen() {
    ASSEMBLE_ONLY_OPEN.set(!ASSEMBLE_ONLY_OPEN.get());
    if (ASSEMBLE_ONLY_OPEN.get()) {
      ASSEMBLE_ALL.set(false);
      ASSEMBLE_ONLY_SELECTED.set(false);
      prefs.putBoolean("ASSEMBLE_ONLY_SELECTED", false);
      prefs.putBoolean("ASSEMBLE_ALL", false);
    }
    prefs.putBoolean("ASSEMBLE_ONLY_OPEN", ASSEMBLE_ONLY_OPEN.get());
  }

  /** Toggles and saves assemble all setting. */
  public static void toggleAssembleAll() {
    ASSEMBLE_ALL.set(!ASSEMBLE_ALL.get());
    if (ASSEMBLE_ALL.get()) {
      ASSEMBLE_ONLY_SELECTED.set(false);
      ASSEMBLE_ONLY_OPEN.set(false);
      prefs.putBoolean("ASSEMBLE_ONLY_SELECTED", false);
      prefs.putBoolean("ASSEMBLE_ONLY_OPEN", false);
    }
    prefs.putBoolean("ASSEMBLE_ALL", ASSEMBLE_ALL.get());
  }

  /** Toggles and saves assembler warnings setting. */
  public static void toggleAssemblerWarnings() {
    ASSEMBLER_WARNINGS.set(!ASSEMBLER_WARNINGS.get());
    Flags.EXTRICT = ASSEMBLER_WARNINGS.get();
    prefs.putBoolean("ASSEMBLER_WARNINGS", ASSEMBLER_WARNINGS.get());
  }

  /** Toggles and saves permit pseudos setting. */
  public static void togglePermitPseudos() {
    PERMIT_PSEUDOS.set(!PERMIT_PSEUDOS.get());
    Flags.BARE = !PERMIT_PSEUDOS.get();
    prefs.putBoolean("PERMIT_PSEUDOS", PERMIT_PSEUDOS.get());
  }

  /** Toggles and saves self-modifying code setting. */
  public static void toggleSelfModifyingCode() {
    SELF_MODIFYING.set(!SELF_MODIFYING.get());
    Flags.SELF_MODIFYING = SELF_MODIFYING.get();
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

  /** Increments code font size. */
  public static void incCodeFontSize() {
    CODE_FONT_SIZE.set(Math.min(CODE_FONT_SIZE.get() + 1, 72));
    prefs.putInt("CODE_FONT_SIZE", CODE_FONT_SIZE.get());
  }

  /** Decrements code font size. */
  public static void decCodeFontSize() {
    CODE_FONT_SIZE.set(Math.max(CODE_FONT_SIZE.get() - 1, 10));
    prefs.putInt("CODE_FONT_SIZE", CODE_FONT_SIZE.get());
  }

  /** Increments console font size. */
  public static void incConsoleFontSize() {
    CONSOLE_FONT_SIZE.set(Math.min(CONSOLE_FONT_SIZE.get() + 1, 72));
    prefs.putInt("CONSOLE_FONT_SIZE", CONSOLE_FONT_SIZE.get());
  }

  /** Decrements console font size. */
  public static void decConsoleFontSize() {
    CONSOLE_FONT_SIZE.set(Math.max(CONSOLE_FONT_SIZE.get() - 1, 10));
    prefs.putInt("CONSOLE_FONT_SIZE", CONSOLE_FONT_SIZE.get());
  }

  /**
   * Sets global start label setting.
   *
   * @param label new start label
   */
  public static void setStartLabel(String label) {
    START.set(label);
    Flags.START = label;
    prefs.put("START", START.get());
  }

  /**
   * Sets and saves user directory setting.
   *
   * @param dir new user directory
   */
  public static void setUserDir(File dir) {
    if (dir != null) {
      USER_DIR.set(dir.getAbsolutePath());
      prefs.put("USER_DIR", USER_DIR.get());
    }
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
