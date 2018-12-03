package vsim.gui.components;

import java.time.Duration;
import java.io.StringReader;
import java.util.Collection;
import java.util.Collections;
import vsim.gui.syntax.Token;
import vsim.gui.syntax.Lexer;
import org.reactfx.Subscription;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.wellbehaved.event.Nodes;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.EventPattern;
import org.fxmisc.richtext.model.StyleSpansBuilder;


/**
 * A subclass of CodeArea that colorize text based on RISC-V syntax.
 */
public final class Editor extends CodeArea {

  /** Last editor text */
  private String lastText;

  /** Highlighting subscription */
  private Subscription subscription;

  /**
   * Creates a code editor with a default text.
   *
   * @param text default text
   */
  public Editor(String text) {
    super();
    // save last text
    this.lastText = text;
    this.replaceText(0, 0, text);
    this.setStyleSpans(0, this.computeHighlighting());
    // add line numbering
    this.setParagraphGraphicFactory(LineNumberFactory.get(this));
    // recompute the syntax highlighting 500 ms after user stops editing area
    this.subscription = this
      // plain changes = ignore style changes that are emitted when syntax highlighting is reapplied
      // multi plain changes = save computation by not rerunning the code multiple times
      // when making multiple changes (e.g. renaming a method at multiple parts in file)
      .multiPlainChanges()
      // do not emit an event until 50 ms have passed since the last emission of previous stream
      .successionEnds(Duration.ofMillis(50))
      // run the following code block when previous stream emits an event
      .subscribe(ignore -> this.setStyleSpans(0, computeHighlighting()));
    // replace tab with 2 spaces
    InputMap<KeyEvent> im = InputMap.consume(
      EventPattern.keyPressed(KeyCode.TAB),
      e -> this.replaceSelection("  ")
    );
    Nodes.addInputMap(this, im);
    // forget undo history
    this.getUndoManager().forgetHistory();
  }

  /**
   * Creates an empty code editor.
   */
  public Editor() {
    this("");
  }

  /**
   * Sets editor text and sets last text variable.
   *
   * @param text the new editor text
   */
  public void setEditorText(String text) {
    this.replaceText(0, this.getText().length(), text);
    this.setStyleSpans(0, this.computeHighlighting());
    this.lastText = text;
    this.getUndoManager().forgetHistory();
  }

  /**
   * Updates last text with current editor text.
   */
  public void updateLastText() {
    this.lastText = this.getText();
  }

  /**
   * Verifies if editor has changed.
   *
   * @return true if editor has changed, false otherwise
   */
  public boolean hasChanged() {
    return !this.getText().equals(this.lastText);
  }

  /**
   * Computes syntax highlighting.
   *
   * @return Style spans of syntax theme classes
   */
  private StyleSpans<Collection<String>> computeHighlighting() {
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
    } catch (Exception e) { }
    spansBuilder.add(Collections.emptyList(), this.getText().length() - current);
    return spansBuilder.create();
  }

  /**
   * Removes Highlighting subscription.
   */
  public void unsubscribe() {
    this.subscription.unsubscribe();
  }

}
