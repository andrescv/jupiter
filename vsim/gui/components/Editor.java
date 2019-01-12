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

package vsim.gui.components;

import java.io.StringReader;
import java.time.Duration;
import java.util.Collection;
import java.util.Collections;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;
import vsim.Settings;
import vsim.gui.utils.Icons;
import vsim.gui.utils.Lexer;
import vsim.gui.utils.Token;


/** A subclass of CodeArea that colorize text based on RISC-V syntax. */
public final class Editor extends CodeArea {

  /** Newline */
  private static final String NL = System.getProperty("line.separator");

  /** Color lookups and Font styles */
  public static final String STYLE = "-fx-syntax: %s;" + NL + "-fx-directive: %s;" + NL + "-fx-keyword: %s;" + NL
      + "-fx-label: %s;" + NL + "-fx-identifier: %s;" + NL + "-fx-register: %s;" + NL + "-fx-number: %s;" + NL
      + "-fx-comment: %s;" + NL + "-fx-string: %s;" + NL + "-fx-stringb: %s;" + NL + "-fx-error: %s;" + NL
      + "-fx-code-area-bg: %s;" + NL + "-fx-selection: %s;" + NL + "-fx-lineno-text: %s;" + NL + "-fx-lineno-bg: %s;"
      + NL + "-fx-caret: %s;" + NL + "-fx-caret-bg: %s;" + NL + "-fx-font-weight: %s;" + NL + "-fx-font-style: %s;" + NL
      + "-fx-font-family: '%s';" + NL + "-fx-font-size: %dpt;";

  /** Last editor text */
  private String lastText;

  /** Editor tab size */
  private String tabSize;

  /** Auto indent option */
  private boolean autoIndent;

  /** Highlighting subscription */
  private Subscription subscription;

  /**
   * Creates a code editor with a default text.
   *
   * @param text default text
   */
  public Editor(String text) {
    super();
    // update styles
    this.updateStyle();
    // auto-indent
    this.setAutoIndent();
    // tab size
    this.setTabSize();
    // save last text
    this.lastText = text;
    this.replaceText(0, 0, text);
    this.setStyleSpans(0, this.computeHighlighting());
    // add line numbering
    this.setParagraphGraphicFactory(LineNumberFactory.get(this));
    // recompute the syntax highlighting 500 ms after user stops editing area
    this.subscription = this
        // plain changes = ignore style changes that are emitted when syntax highlighting is
        // reapplied
        // multi plain changes = save computation by not rerunning the code multiple times
        // when making multiple changes (e.g. renaming a method at multiple parts in file)
        .multiPlainChanges()
        // do not emit an event until 50 ms have passed since the last emission of previous
        // stream
        .successionEnds(Duration.ofMillis(50))
        // run the following code block when previous stream emits an event
        .subscribe(ignore -> this.setStyleSpans(0, computeHighlighting()));
    // add some input maps
    Nodes.addInputMap(this, InputMap.sequence(
        // navigation
        InputMap.consumeWhen(EventPattern.keyPressed(KeyCode.LEFT), () -> this.beginningTab(false),
            e -> this.moveTo(this.getCaretPosition() - this.tabSize.length())),
        InputMap.consumeWhen(EventPattern.keyPressed(KeyCode.KP_LEFT), () -> this.beginningTab(false),
            e -> this.moveTo(this.getCaretPosition() - this.tabSize.length())),
        InputMap.consumeWhen(EventPattern.keyPressed(KeyCode.RIGHT), () -> this.beginningTab(true),
            e -> this.moveTo(this.getCaretPosition() + this.tabSize.length())),
        InputMap.consumeWhen(EventPattern.keyPressed(KeyCode.KP_RIGHT), () -> this.beginningTab(true),
            e -> this.moveTo(this.getCaretPosition() + this.tabSize.length())),
        // enter for auto-indent
        InputMap.consume(EventPattern.keyPressed(KeyCode.ENTER), e -> {
          this.insertText(this.getCurrentParagraph(), this.getCaretColumn(), System.getProperty("line.separator"));
          if (this.autoIndent) {
            String line = this.getParagraph(this.getCurrentParagraph() - 1).getText();
            String spaces = "";
            for (int i = 0; i < line.length(); i++) {
              if (line.charAt(i) == ' ')
                spaces += " ";
              else
                break;
            }
            this.insertText(this.getCurrentParagraph(), this.getCaretColumn(), spaces);
          }
        }),
        // backspace
        InputMap.consumeWhen(EventPattern.keyPressed(KeyCode.BACK_SPACE), () -> this.beginningTab(false), e -> {
          int pos = this.getCaretPosition();
          this.replaceText(pos - this.tabSize.length(), pos, "");
        }),
        // tab for tabsize
        InputMap.consume(EventPattern.keyPressed(KeyCode.TAB), e -> this.replaceSelection(this.tabSize))));
    // forget undo history
    this.getUndoManager().forgetHistory();
    // undo option
    MenuItem undo = new MenuItem("Undo");
    undo.setOnAction(e -> {
      if (this.isUndoAvailable())
        this.undo();
    });
    undo.setGraphic(Icons.getImage("undo"));
    // redo option
    MenuItem redo = new MenuItem("Redo");
    redo.setOnAction(e -> {
      if (this.isRedoAvailable())
        this.redo();
    });
    redo.setGraphic(Icons.getImage("redo"));
    // cut
    MenuItem cut = new MenuItem("Cut");
    cut.setOnAction(e -> this.cut());
    cut.setGraphic(Icons.getImage("cut"));
    // copy
    MenuItem copy = new MenuItem("Copy");
    copy.setOnAction(e -> this.copy());
    copy.setGraphic(Icons.getImage("copy"));
    // paste
    MenuItem paste = new MenuItem("Paste");
    paste.setOnAction(e -> this.paste());
    paste.setGraphic(Icons.getImage("paste"));
    // select all
    MenuItem selectAll = new MenuItem("Select All");
    selectAll.setOnAction(e -> this.selectAll());
    selectAll.setGraphic(Icons.getImage("select"));
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(undo, redo, cut, copy, paste, selectAll);
    this.setContextMenu(menu);
  }

  /** Creates an empty code editor. */
  public Editor() {
    this("");
  }

  /**
   * Sets editor text and sets last text variable.
   *
   * @param text the new editor text
   */
  protected void setEditorText(String text) {
    this.replaceText(0, this.getText().length(), text);
    this.setStyleSpans(0, this.computeHighlighting());
    this.lastText = text;
    this.getUndoManager().forgetHistory();
  }

  /** Updates last text with current editor text. */
  protected void updateLastText() {
    this.lastText = this.getText();
  }

  /**
   * Verifies if editor has changed.
   *
   * @return true if editor has changed, false otherwise
   */
  protected boolean hasChanged() {
    return !this.getText().equals(this.lastText);
  }

  /**
   * Computes syntax highlighting.
   *
   * @return Style spans of syntax theme classes
   */
  protected StyleSpans<Collection<String>> computeHighlighting() {
    Token token;
    int current = 0;
    String text = this.getText();
    Lexer lexer = new Lexer(new StringReader(text));
    StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
    try {
      while (!(token = lexer.yylex()).getStyle().equals("eof")) {
        current += token.getLength();
        spansBuilder.add(Collections.singleton(token.getStyle()), token.getLength());
      }
    } catch (Exception e) {
    }
    spansBuilder.add(Collections.emptyList(), this.getText().length() - current);
    return spansBuilder.create();
  }

  private void updateStyle() {
    String style = String.format(Editor.STYLE, Settings.CODE_AREA_SYNTAX, Settings.CODE_AREA_DIRECTIVE,
        Settings.CODE_AREA_KEYWORD, Settings.CODE_AREA_LABEL, Settings.CODE_AREA_IDENTIFIER,
        Settings.CODE_AREA_REGISTER, Settings.CODE_AREA_NUMBER, Settings.CODE_AREA_COMMENT, Settings.CODE_AREA_STRING,
        Settings.CODE_AREA_BACKSLASH, Settings.CODE_AREA_ERROR, Settings.CODE_AREA_BG, Settings.CODE_AREA_SELECTION,
        Settings.CODE_AREA_LINENO_COLOR, Settings.CODE_AREA_LINENO_BG, Settings.CODE_AREA_CARET_COLOR,
        Settings.CODE_AREA_LINE_HIGHLIGHT, Settings.CODE_AREA_FONT_WEIGHT, Settings.CODE_AREA_FONT_STYLE,
        Settings.CODE_AREA_FONT_FAMILY, Settings.CODE_AREA_FONT_SIZE);
    this.setStyle(style);
  }

  /** Removes Highlighting subscription. */
  protected void unsubscribe() {
    this.subscription.unsubscribe();
  }

  protected void updateSettings() {
    this.setTabSize();
    this.setAutoIndent();
    this.updateStyle();
  }

  protected void setTabSize(int size) {
    this.tabSize = "";
    for (int i = 0; i < size; i++)
      this.tabSize += " ";
  }

  private void setTabSize() {
    this.setTabSize(Settings.CODE_AREA_TAB_SIZE);
  }

  private void setAutoIndent() {
    this.autoIndent = Settings.CODE_AREA_AUTO_INDENT;
  }

  protected void setAutoIndent(boolean autoIndent) {
    this.autoIndent = autoIndent;
  }

  private boolean beginningTab(boolean exclude) {
    int col = this.getCaretColumn();
    String line = this.getParagraph(this.getCurrentParagraph()).getText();
    int pos = 0;
    for (pos = 0; pos < line.length(); pos++) {
      if (line.charAt(pos) != ' ')
        break;
    }
    if (((!exclude && col > 0) || exclude)
        && ((exclude && col <= (pos - this.tabSize.length())) || (!exclude && col <= pos))
        && (col % this.tabSize.length() == 0))
      return true;
    return false;
  }
}
