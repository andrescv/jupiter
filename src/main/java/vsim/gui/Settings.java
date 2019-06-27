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


/** V-Sim GUI settings. */
public final class Settings {

  /** preferences object */
  private static final Preferences prefs = Preferences.userRoot().node("V-Sim");

  /** show symbol table setting */
  public static final SimpleBooleanProperty SHOW_ST = new SimpleBooleanProperty(false);

  /** popup dialog for input ecalls setting */
  public static final SimpleBooleanProperty POPUP_DIALOG = new SimpleBooleanProperty(false);

  /** assemble all files in current directory setting */
  public static final SimpleBooleanProperty ASSEMBLE_ALL = new SimpleBooleanProperty(false);

  /** assembler warnings are consider errors setting */
  public static final SimpleBooleanProperty EXTRICT = new SimpleBooleanProperty(false);

  /** permit pseudo instructions setting */
  public static final SimpleBooleanProperty PSEUDOS = new SimpleBooleanProperty(true);

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
    SHOW_ST.set(prefs.getBoolean("SHOW_ST", SHOW_ST.get()));
    POPUP_DIALOG.set(prefs.getBoolean("POPUP_DIALOG", POPUP_DIALOG.get()));
    ASSEMBLE_ALL.set(prefs.getBoolean("ASSEMBLE_ALL", ASSEMBLE_ALL.get()));
    EXTRICT.set(prefs.getBoolean("EXTRICT", EXTRICT.get()));
    PSEUDOS.set(prefs.getBoolean("PSEUDOS", PSEUDOS.get()));
    SELF_MODIFYING.set(prefs.getBoolean("SELF_MODIFYING", SELF_MODIFYING.get()));
    AUTO_INDENT.set(prefs.getBoolean("AUTO_INDENT", AUTO_INDENT.get()));
    DARK_MODE.set(prefs.getBoolean("DARK_MODE", DARK_MODE.get()));
    TAB_SIZE.set(prefs.getInt("TAB_SIZE", TAB_SIZE.get()));
  }

}
