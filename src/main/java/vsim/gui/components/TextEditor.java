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

import javafx.beans.value.ChangeListener;
import javafx.scene.input.KeyCode;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;
import org.reactfx.Subscription;

import vsim.gui.Settings;
import vsim.gui.highlighting.Lexer;
import vsim.gui.highlighting.Token;
import vsim.utils.Data;


/** V-Sim RISC-V code editor. */
public final class TextEditor extends CodeArea {

  /** Last editor text */
  private String lastText;
  /** Editor tab size */
  private String tabSize;
  /** Auto indent option */
  private boolean autoIndent;
  /** Highlighting subscription */
  private Subscription subscription;
  /** tab size change listener */
  private ChangeListener<Number> tabListener;

  /**
   * Creates a new text editor with a default text content.
   *
   * @param text text content
   */
  public TextEditor(String text) {
    super();
    // set tab size
    setTabSize();
    // set text
    lastText = text;
    replaceText(0, 0, text);
    getUndoManager().forgetHistory();
    setStyleSpans(0, computeHighlighting());
    // line number
    setParagraphGraphicFactory(LineNumberFactory.get(this));
    // syntax highlighting
    subscription = multiPlainChanges().successionEnds(Duration.ofMillis(50)).subscribe(ignore -> setStyleSpans(0, computeHighlighting()));
    // set input maps
    Nodes.addInputMap(this,
      InputMap.sequence(
        // left arrow
        InputMap.consumeWhen(
          EventPattern.keyPressed(KeyCode.LEFT),
            ()  -> canMoveByTab(false),
            (e) -> moveTo(getCaretPosition() - tabSize.length())),
        // left keypad
        InputMap.consumeWhen(
          EventPattern.keyPressed(KeyCode.KP_LEFT),
            ()  -> canMoveByTab(false),
            (e) -> moveTo(getCaretPosition() - tabSize.length())),
        // right arrow
        InputMap.consumeWhen(
          EventPattern.keyPressed(KeyCode.RIGHT),
            ()  -> canMoveByTab(true),
            (e) -> moveTo(this.getCaretPosition() + tabSize.length())),
        // right keypad
        InputMap.consumeWhen(
          EventPattern.keyPressed(KeyCode.KP_RIGHT),
            ()  -> canMoveByTab(true),
            (e) -> moveTo(this.getCaretPosition() + tabSize.length())),
        // enter for auto-indent
        InputMap.consume(
          EventPattern.keyPressed(KeyCode.ENTER),
            (e) -> enter()),
        // backspace
        InputMap.consumeWhen(
          EventPattern.keyPressed(KeyCode.BACK_SPACE),
            ()  -> canMoveByTab(false),
            (e) -> backspace()),
        // tabs
        InputMap.consume(
          EventPattern.keyPressed(KeyCode.TAB),
            e -> replaceSelection(tabSize))
      )
    );
    // add bindings
    tabListener = (e, o, n) -> setTabSize();
    Settings.TAB_SIZE.addListener(tabListener);
  }

  /** Creates a new empty text editor. */
  public TextEditor() {
    this("");
  }

  /**
   * Sets editor text and forgets undo/redo history.
   *
   * @param text editor text
   */
  public void load(String text) {
    lastText = text;
    replaceText(0, getText().length(), text);
    setStyleSpans(0, computeHighlighting());
    getUndoManager().forgetHistory();
  }

  /** Mark current text as last text. */
  public void save() {
    lastText = getText();
  }

  /** Closes text editor. */
  public void close() {
    subscription.unsubscribe();
    Settings.TAB_SIZE.removeListener(tabListener);
  }

  /**
   * Verifies if the content of the text editor has changed.
   *
   * @return {@code true} if has changed, {@code false} if not
   */
  public boolean modified() {
    return !getText().equals(lastText);
  }

  /** Handles enter event. */
  private void enter() {
    insertText(getCurrentParagraph(), getCaretColumn(), Data.EOL);
    if (Settings.AUTO_INDENT.get()) {
      String line = getParagraph(getCurrentParagraph() - 1).getText();
      String spaces = "";
      for (int i = 0; i < line.length(); i++) {
        if (line.charAt(i) == ' ') {
          spaces += " ";
        } else {
          break;
        }
      }
      insertText(getCurrentParagraph(), getCaretColumn(), spaces);
    }
  }

  /** Handles backspace event. */
  private void backspace() {
    int pos = getCaretPosition();
    replaceText(pos - tabSize.length(), pos, "");
  }

  /** Sets editor tab size. */
  private void setTabSize() {
    tabSize = "";
    for (int i = 0; i < Settings.TAB_SIZE.get(); i++) {
      tabSize += " ";
    }
  }

  /**
   * Verifies if caret position can move by tab length.
   *
   * @param right if moving to the right
   * @return true if can move, false if not
   */
  private boolean canMoveByTab(boolean right) {
    int col = getCaretColumn();
    String line = getParagraph(getCurrentParagraph()).getText();
    int pos = 0;
    for (pos = 0; pos < line.length(); pos++) {
      if (line.charAt(pos) != ' ') {
        break;
      }
    }
    return (((!right && col > 0) || right)
        && ((right && col <= (pos - tabSize.length())) || (!right && col <= pos))
        && (col % tabSize.length() == 0));
  }

  /**
   * Computes syntax highlighting.
   *
   * @return style spans of syntax theme classes
   */
  private StyleSpans<Collection<String>> computeHighlighting() {
    Token token;
    int current = 0;
    String text = getText();
    Lexer lexer = new Lexer(new StringReader(text));
    StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
    try {
      while (!(token = lexer.yylex()).getStyle().equals("eof")) {
        current += token.getLength();
        spansBuilder.add(Collections.singleton(token.getStyle()), token.getLength());
      }
    } catch (Exception e) { }
    spansBuilder.add(Collections.emptyList(), this.getText().length() - current);
    return spansBuilder.create();
  }

}
