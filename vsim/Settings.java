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
import java.util.prefs.Preferences;


/** The Settings class contains the V-Sim simulator settings. */
public final class Settings {

  /** Preferences object */
  private static final Preferences prefs = Preferences.userRoot().node("vsim");

// GUI AND CLI SETTINGS

  /** installation path */
  public static File ROOT = null;

  /** trap handler path */
  public static File TRAP = null;

  /** user directory */
  public static File DIR = new File(System.getProperty("user.dir"));

  /** history length (backstep) */
  public static int HIST_SIZE = 2000;

  /** global start label, set with -start flag */
  public static String START = "main";

  /** bare machine mode (no pseudos), set with -bare flag */
  public static boolean BARE = false;

  /** extrict mode assembler warnings are consider errors */
  public static boolean EXTRICT = false;

  /** self-modifying code */
  public static boolean SELF_MODIFYING = false;

// CLI ONLY SETTINGS

  /** debugging mode, set with -debug flag or ebreak instruction */
  public static boolean DEBUG = false;

  /** show title and copyright notice, disable with -notitle */
  public static boolean TITLE = true;

  /** dump machine code to a file, set with -dump flag */
  public static String DUMP = null;

// GUI ONLY SETTINGS

  /** if running in GUI mode */
  public static boolean GUI = false;

  /** display mode of RVI register */
  public static int DISP_RVI_REG = 0;

  /** display mode of RVF register */
  public static int DISP_RVF_REG = 0;

  /** display mode of memory cell */
  public static int DISP_MEM_CELL = 0;

  /** if popup an input dialog for input ecalls */
  public static boolean POPUP_ECALL_INPUT = false;

  /** show labels (only in gui mode) */
  public static boolean SHOW_LABELS = false;

  /** assemble all files currently open (only in gui mode) */
  public static boolean ASSEMBLE_ONLY_OPEN = false;

  /** code area current syntax theme */
  public static String CODE_AREA_SYNTAX_THEME = "material";

  /** code area tab size */
  public static int CODE_AREA_TAB_SIZE = 4;

  /** code area auto indent */
  public static boolean CODE_AREA_AUTO_INDENT = true;

  /** code area background color */
  public static String CODE_AREA_BG = "#212121";

  /** code area font weight */
  public static String CODE_AREA_FONT_WEIGHT = "normal";

  /** code area font style */
  public static String CODE_AREA_FONT_STYLE = "normal";

  /** code area font family */
  public static String CODE_AREA_FONT_FAMILY = "Envy Code R";

  /** code area font size */
  public static int CODE_AREA_FONT_SIZE = 14;

  /** code area selection color */
  public static String CODE_AREA_SELECTION = "#3b3b3b";

  /** code area line numbering text color */
  public static String CODE_AREA_LINENO_COLOR = "#b2ccd6";

  /** code area line numbering background color */
  public static String CODE_AREA_LINENO_BG = "#212121";

  /** code area caret color */
  public static String CODE_AREA_CARET_COLOR = "#009688";

  /** code area current caret line highlight color */
  public static String CODE_AREA_LINE_HIGHLIGHT = "#1b1b1b";

  /** code area synxtax color */
  public static String CODE_AREA_SYNTAX = "#b2ccd6";

  /** code area operators color */
  public static String CODE_AREA_OPERATOR = "#89ddf3";

  /** code area directive color */
  public static String CODE_AREA_DIRECTIVE = "#82aaff";

  /** code area keyword color */
  public static String CODE_AREA_KEYWORD = "#c792ea";

  /** code area label color */
  public static String CODE_AREA_LABEL = "#f78c6a";

  /** code area identifier color */
  public static String CODE_AREA_IDENTIFIER = "#ffcb6b";

  /** code area register color */
  public static String CODE_AREA_REGISTER = "#dddddd";

  /** code area numeric number color */
  public static String CODE_AREA_NUMBER = "#f07178";

  /** code area comment color */
  public static String CODE_AREA_COMMENT = "#545454";

  /** code area string and char color */
  public static String CODE_AREA_STRING = "#c3e88d";

  /** code area backslash in string/char color */
  public static String CODE_AREA_BACKSLASH = "#89ddf3";

  /** code area error */
  public static String CODE_AREA_ERROR = "#ef4d13";

  /** Loads all saved preferences in GUI mode. */
  public static void load() {
    Settings.START = Settings.prefs.get("START", "main");
    Settings.BARE = Settings.prefs.getBoolean("BARE", false);
    Settings.EXTRICT = Settings.prefs.getBoolean("EXTRICT", false);
    Settings.SELF_MODIFYING = Settings.prefs.getBoolean("SELF_MODIFYING", false);
    Settings.SHOW_LABELS = Settings.prefs.getBoolean("SHOW_LABELS", false);
    Settings.POPUP_ECALL_INPUT = Settings.prefs.getBoolean("POPUP_ECALL_INPUT", false);
    Settings.ASSEMBLE_ONLY_OPEN = Settings.prefs.getBoolean("ASSEMBLE_ONLY_OPEN", false);
    Settings.CODE_AREA_TAB_SIZE = Settings.prefs.getInt("CODE_AREA_TAB_SIZE", 4);
    Settings.CODE_AREA_AUTO_INDENT = Settings.prefs.getBoolean("CODE_AREA_AUTO_INDENT", true);
    Settings.CODE_AREA_SYNTAX_THEME = Settings.prefs.get("CODE_AREA_SYNTAX_THEME", "material");
    Settings.CODE_AREA_BG = Settings.prefs.get("CODE_AREA_BG", "#212121");
    Settings.CODE_AREA_FONT_WEIGHT = Settings.prefs.get("CODE_AREA_FONT_WEIGHT", "normal");
    Settings.CODE_AREA_FONT_STYLE = Settings.prefs.get("CODE_AREA_FONT_STYLE", "normal");
    Settings.CODE_AREA_FONT_FAMILY = Settings.prefs.get("CODE_AREA_FONT_FAMILY", "Envy Code R");
    Settings.CODE_AREA_FONT_SIZE = Settings.prefs.getInt("CODE_AREA_FONT_SIZE", 14);
    Settings.CODE_AREA_SELECTION = Settings.prefs.get("CODE_AREA_SELECTION", "#3b3b3b");
    Settings.CODE_AREA_LINENO_COLOR = Settings.prefs.get("CODE_AREA_LINENO_COLOR", "#b2ccd6");
    Settings.CODE_AREA_LINENO_BG = Settings.prefs.get("CODE_AREA_LINENO_BG", "#212121");
    Settings.CODE_AREA_CARET_COLOR = Settings.prefs.get("CODE_AREA_CARET_COLOR", "#009688");
    Settings.CODE_AREA_LINE_HIGHLIGHT = Settings.prefs.get("CODE_AREA_LINE_HIGHLIGHT", "#1b1b1b");
    Settings.CODE_AREA_SYNTAX = Settings.prefs.get("CODE_AREA_SYNTAX", "#b2ccd6");
    Settings.CODE_AREA_OPERATOR = Settings.prefs.get("CODE_AREA_OPERATOR", "#89ddf3");
    Settings.CODE_AREA_DIRECTIVE = Settings.prefs.get("CODE_AREA_DIRECTIVE", "#82aaff");
    Settings.CODE_AREA_KEYWORD = Settings.prefs.get("CODE_AREA_KEYWORD", "#c792eA");
    Settings.CODE_AREA_LABEL = Settings.prefs.get("CODE_AREA_LABEL", "#f78c6a");
    Settings.CODE_AREA_IDENTIFIER = Settings.prefs.get("CODE_AREA_IDENTIFIER", "#ffcb6b");
    Settings.CODE_AREA_REGISTER = Settings.prefs.get("CODE_AREA_REGISTER", "#dddddd");
    Settings.CODE_AREA_NUMBER = Settings.prefs.get("CODE_AREA_NUMBER", "#f07178");
    Settings.CODE_AREA_COMMENT = Settings.prefs.get("CODE_AREA_COMMENT", "#545454");
    Settings.CODE_AREA_STRING = Settings.prefs.get("CODE_AREA_STRING", "#c3e88d");
    Settings.CODE_AREA_BACKSLASH = Settings.prefs.get("CODE_AREA_BACKSLASH", "#89ddf3");
    Settings.CODE_AREA_ERROR = Settings.prefs.get("CODE_AREA_ERROR", "#ef4d13");
  }

  /** This method sets the root path, i.e V-Sim source code path. */
  public static void setRootPath() {
    try {
      File f = new File(VSim.class.getProtectionDomain().getCodeSource().getLocation().toURI());
      Settings.ROOT = new File(f.getParent());
    } catch (Exception e) {
      /* NOTHING */
    }
  }

  /**
   * Sets global start label.
   *
   * @param start global label name
   * @param save if save this setting in user prefs
   * @return true if success, false if not
   */
  public static boolean setStart(String start, boolean save) {
    if (start != null && start.length() > 0 && start.trim().matches("[a-zA-Z_]([a-zA-Z0-9_]*(\\.[a-zA-Z0-9_]+)?)")) {
      if (save)
        Settings.prefs.put("START", start);
      Settings.START = start;
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
    Settings.prefs.putBoolean("BARE", !Settings.BARE);
    Settings.BARE = !Settings.BARE;
    return Settings.BARE;
  }

  /**
   * Toggles extrict setting.
   *
   * @return extrict setting value after toggle
   */
  public static boolean toggleExtrict() {
    Settings.prefs.putBoolean("EXTRICT", !Settings.EXTRICT);
    Settings.EXTRICT = !Settings.EXTRICT;
    return Settings.EXTRICT;
  }

  /**
   * Toggles self-modifying code setting.
   *
   * @return self-modifying code setting value after toggle
   */
  public static boolean toggleSelfModifying() {
    Settings.prefs.putBoolean("SELF_MODIFYING", !Settings.SELF_MODIFYING);
    Settings.SELF_MODIFYING = !Settings.SELF_MODIFYING;
    return Settings.SELF_MODIFYING;
  }

  /**
   * Toggles show labels setting.
   *
   * @return show labels setting value after toggle
   */
  public static boolean toggleShowLabels() {
    Settings.prefs.putBoolean("SHOW_LABELS", !Settings.SHOW_LABELS);
    Settings.SHOW_LABELS = !Settings.SHOW_LABELS;
    return Settings.SHOW_LABELS;
  }

  /**
   * Toggles popup setting.
   *
   * @return popup setting value after toggle
   */
  public static boolean togglePopup() {
    Settings.prefs.putBoolean("POPUP_ECALL_INPUT", !Settings.POPUP_ECALL_INPUT);
    Settings.POPUP_ECALL_INPUT = !Settings.POPUP_ECALL_INPUT;
    return Settings.POPUP_ECALL_INPUT;
  }

  /**
   * Toggles only open setting.
   *
   * @return only open setting value after toggle
   */
  public static boolean toggleAssembleOnlyOpen() {
    Settings.prefs.putBoolean("ASSEMBLE_ONLY_OPEN", !Settings.ASSEMBLE_ONLY_OPEN);
    Settings.ASSEMBLE_ONLY_OPEN = !Settings.ASSEMBLE_ONLY_OPEN;
    return Settings.ASSEMBLE_ONLY_OPEN;
  }

  /**
   * Sets code area syntax theme.
   *
   * @param theme theme name
   */
  public static void setCodeAreaSyntaxTheme(String theme) {
    Settings.prefs.put("CODE_AREA_SYNTAX_THEME", theme);
    Settings.CODE_AREA_SYNTAX_THEME = theme;
  }

  /**
   * Sets code area tab size.
   *
   * @param size tab size
   */
  public static void setCodeAreaTabSize(int size) {
    Settings.prefs.putInt("CODE_AREA_TAB_SIZE", size);
    Settings.CODE_AREA_TAB_SIZE = size;
  }

  /**
   * Sets code area auto-indent option.
   *
   * @param autoIndent true if code area performs auto-indentation, false otherwise
   */
  public static void setCodeAreaAutoIndent(boolean autoIndent) {
    Settings.prefs.putBoolean("CODE_AREA_AUTO_INDENT", autoIndent);
    Settings.CODE_AREA_AUTO_INDENT = autoIndent;
  }

  /**
   * Changes code area background color.
   *
   * @param color background color
   */
  public static void setCodeAreaBG(String color) {
    Settings.prefs.put("CODE_AREA_BG", color);
    Settings.CODE_AREA_BG = color;
  }

  /**
   * Changes code area font weight.
   *
   * @param weight font weight
   */
  public static void setCodeAreaFontWeight(String weight) {
    Settings.prefs.put("CODE_AREA_FONT_WEIGHT", weight);
    Settings.CODE_AREA_FONT_WEIGHT = weight;
  }

  /**
   * Changes code area font style.
   *
   * @param style font style
   */
  public static void setCodeAreaFontStyle(String style) {
    Settings.prefs.put("CODE_AREA_FONT_STYLE", style);
    Settings.CODE_AREA_FONT_STYLE = style;
  }

  /**
   * Changes code area font family.
   *
   * @param family font family
   */
  public static void setCodeAreaFontFamily(String family) {
    Settings.prefs.put("CODE_AREA_FONT_FAMILY", family);
    Settings.CODE_AREA_FONT_FAMILY = family;
  }

  /**
   * Changes code area font size .
   *
   * @param size font size
   */
  public static void setCodeAreaFontSize(int size) {
    Settings.prefs.putInt("CODE_AREA_FONT_SIZE", size);
    Settings.CODE_AREA_FONT_SIZE = size;
  }

  /**
   * Changes code area selection color.
   *
   * @param color selection color
   */
  public static void setCodeAreaSelection(String color) {
    Settings.prefs.put("CODE_AREA_SELECTION", color);
    Settings.CODE_AREA_SELECTION = color;
  }

  /**
   * Changes code area line number color.
   *
   * @param color line number color
   */
  public static void setCodeAreaLinenoColor(String color) {
    Settings.prefs.put("CODE_AREA_LINENO_COLOR", color);
    Settings.CODE_AREA_LINENO_COLOR = color;
  }

  /**
   * Changes code area line number background color.
   *
   * @param color line number background color
   */
  public static void setCodeAreaLinenoBG(String color) {
    Settings.prefs.put("CODE_AREA_LINENO_BG", color);
    Settings.CODE_AREA_LINENO_BG = color;
  }

  /**
   * Changes code area caret color.
   *
   * @param color caret color
   */
  public static void setCodeAreaCaretColor(String color) {
    Settings.prefs.put("CODE_AREA_CARET_COLOR", color);
    Settings.CODE_AREA_CARET_COLOR = color;
  }

  /**
   * Changes code area current line highlight color.
   *
   * @param color current line highlight color
   */
  public static void setCodeAreaLineHighlight(String color) {
    Settings.prefs.put("CODE_AREA_LINE_HIGHLIGHT", color);
    Settings.CODE_AREA_LINE_HIGHLIGHT = color;
  }

  /**
   * Changes code area syntax color.
   *
   * @param color syntax color
   */
  public static void setCodeAreaSyntaxColor(String color) {
    Settings.prefs.put("CODE_AREA_SYNTAX", color);
    Settings.CODE_AREA_SYNTAX = color;
  }

  /**
   * Changes code area operator color.
   *
   * @param color operator color
   */
  public static void setCodeAreaOperatorColor(String color) {
    Settings.prefs.put("CODE_AREA_OPERATOR", color);
    Settings.CODE_AREA_OPERATOR = color;
  }

  /**
   * Changes code area directive color.
   *
   * @param color directive color
   */
  public static void setCodeAreaDirectiveColor(String color) {
    Settings.prefs.put("CODE_AREA_DIRECTIVE", color);
    Settings.CODE_AREA_DIRECTIVE = color;
  }

  /**
   * Changes code area keyword color.
   *
   * @param color keyword color
   */
  public static void setCodeAreaKeywordColor(String color) {
    Settings.prefs.put("CODE_AREA_KEYWORD", color);
    Settings.CODE_AREA_KEYWORD = color;
  }

  /**
   * Changes code area label color.
   *
   * @param color label color
   */
  public static void setCodeAreaLabelColor(String color) {
    Settings.prefs.put("CODE_AREA_LABEL", color);
    Settings.CODE_AREA_LABEL = color;
  }

  /**
   * Changes code area identifier color.
   *
   * @param color identifier color
   */
  public static void setCodeAreaIdentifierColor(String color) {
    Settings.prefs.put("CODE_AREA_IDENTIFIER", color);
    Settings.CODE_AREA_IDENTIFIER = color;
  }

  /**
   * Changes code area register color.
   *
   * @param color register color
   */
  public static void setCodeAreaRegisterColor(String color) {
    Settings.prefs.put("CODE_AREA_REGISTER", color);
    Settings.CODE_AREA_REGISTER = color;
  }

  /**
   * Changes code area number color.
   *
   * @param color number color
   */
  public static void setCodeAreaNumberColor(String color) {
    Settings.prefs.put("CODE_AREA_NUMBER", color);
    Settings.CODE_AREA_NUMBER = color;
  }

  /**
   * Changes code area comment color.
   *
   * @param color comment color
   */
  public static void setCodeAreaCommentColor(String color) {
    Settings.prefs.put("CODE_AREA_COMMENT", color);
    Settings.CODE_AREA_COMMENT = color;
  }

  /**
   * Changes code area string color.
   *
   * @param color string color
   */
  public static void setCodeAreaStringColor(String color) {
    Settings.prefs.put("CODE_AREA_STRING", color);
    Settings.CODE_AREA_STRING = color;
  }

  /**
   * Changes code area backslash color.
   *
   * @param color backslash color
   */
  public static void setCodeAreaBackslashColor(String color) {
    Settings.prefs.put("CODE_AREA_BACKSLASH", color);
    Settings.CODE_AREA_BACKSLASH = color;
  }

  /**
   * Changes code area error color.
   *
   * @param color error color
   */
  public static void setCodeAreaErrorColor(String color) {
    Settings.prefs.put("CODE_AREA_ERROR", color);
    Settings.CODE_AREA_ERROR = color;
  }
}
