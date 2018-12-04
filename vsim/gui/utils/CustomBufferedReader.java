package vsim.gui.utils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import vsim.gui.components.InputDialog;


/**
 * This class represents a custom and hacky buffered reader.
 */
public final class CustomBufferedReader extends BufferedReader {

  /** input dialog */
  private InputDialog input;

  public CustomBufferedReader() {
    // just because is necessary :S
    super(new InputStreamReader(System.in));
    this.input = new InputDialog();
  }

  /**
   * {@inheritDoc}
   */
  public int read() throws IOException {
    String data = this.input.showAndWait();
    if (data.length() == 0)
      data = System.getProperty("line.separator");
    return data.charAt(0);
  }

  /**
   * {@inheritDoc}
   */
  public String readLine() throws IOException {
    return this.input.showAndWait();
  }

}
