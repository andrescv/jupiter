package vsim.gui.utils;

import java.nio.ByteBuffer;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.io.PipedInputStream;
import java.io.PipedOutputStream;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.Clipboard;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.control.TextInputControl;


/**
 * A subclass of InputStream to read user input from a TextInputControl.
 */
public final class CustomInputStream extends InputStream {

  /** Enter key combination */
  private static final KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);

  /** if control is in reading mode */
  private boolean reading;
  /* last caret position */
  private int lastIndex;
  /** Input control */
  private final TextInputControl control;
  /** piped output stream */
  private final PipedInputStream output;
  /** piped input stream */
  private final PipedOutputStream input;

  /**
   * Creates a CustomInputStream given a TextInputControl.
   *
   * @param control TextInputControl e.g TextArea
   */
  public CustomInputStream(TextInputControl control) {
    this.control = control;
    this.lastIndex = -1;
    this.reading = false;
    this.control.setOnKeyTyped(e -> this.handleKeyTyped(e));
    this.control.setOnKeyPressed(e -> this.handleKeyPressed(e));
    this.input = new PipedOutputStream();
    try {
      this.output = new PipedInputStream(this.input);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * {@inheritDoc}
   */
  public int read() throws IOException {
    this.reading = true;
    this.lastIndex = this.control.getCaretPosition();
    try {
      return this.output.read();
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(final byte[] b, final int offset, final int length) throws IOException {
    this.reading = true;
    this.lastIndex = this.control.getCaretPosition();
    try {
      return this.output.read(b, offset, length);
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int read(final byte[] b) throws IOException {
    this.reading = true;
    this.lastIndex = this.control.getCaretPosition();
    try {
      return this.output.read(b);
    } catch (IOException e) {
      return -1;
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public int available() throws IOException {
    return this.output.available();
  }

  /**
   * Handles key typed event over text input control.
   *
   * @param e KeyEvent
   */
  private synchronized void handleKeyTyped(KeyEvent e) {
    if (!this.reading)
      e.consume();
  }

  /**
   * Handles key pressed event over text input control.
   *
   * @param e KeyEvent
   */
  private synchronized void handleKeyPressed(KeyEvent e) {
    // fix caret position if needed
    int pos = this.control.getCaretPosition();
    if (pos < this.lastIndex)
      this.control.positionCaret(this.control.getLength());
    // only if reading
    if (this.reading) {
      if (ENTER.match(e)) {
        try {
          this.control.positionCaret(this.control.getLength());
          String value = this.control.getText(this.lastIndex, this.control.getLength());
          String newline = System.getProperty("line.separator");
          value = value.replaceAll("[\r\n]+", "");
          ByteBuffer buf = Charset.defaultCharset().encode(value + newline);
          this.input.write(buf.array(), 0, buf.remaining());
          this.input.flush();
          this.reading = false;
        } catch (IOException ex) {
          if ("Read end dead".equals(ex.getMessage()))
            return;
          throw new RuntimeException(ex);
        }
      }
    } else
      e.consume();
  }

}
