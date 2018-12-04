package vsim.gui.utils;

import java.io.IOException;
import java.io.OutputStream;
import javafx.application.Platform;
import javafx.scene.control.TextInputControl;


/**
 * A subclass of OutputStream to output text to a TextInputControl.
 */
public final class CustomOutputStream extends OutputStream {

  /** Text input cntrol */
  private final TextInputControl control;

  /**
   * Creates a CustomOutputStream given a text input control.
   *
   * @param control text input control
   */
  public CustomOutputStream(TextInputControl control) {
    this.control = control;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public synchronized void write(byte[] cbuf, int off, int len) throws IOException {
    this.control.appendText(new String(cbuf, off, len));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void write(int b) throws IOException {
    this.write(new byte[]{(byte)b}, 0, 1);
  }

}
