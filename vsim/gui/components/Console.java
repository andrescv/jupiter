package vsim.gui.components;

import java.io.PrintStream;
import javafx.scene.control.TextArea;
import vsim.gui.utils.CustomInputStream;
import vsim.gui.utils.CustomOutputStream;


/**
 * A subclass of TextArea that uses CustomInputStream and CustomOutputStream.
 */
public final class Console extends TextArea {

  /** Standard input */
  private CustomInputStream stdin;
  /** Standard output */
  private PrintStream stdout;
  /** Standard error */
  private PrintStream stderr;

  /**
   * Creates a Console text input control.
   */
  public Console() {
    super();
    this.stdin = new CustomInputStream(this);
    this.stdout = new PrintStream(new CustomOutputStream(this));
    this.stderr = this.stdout;
    this.getStyleClass().add("console");
  }

  /**
   * Gets standard input of control.
   *
   * @return standard input
   */
  public CustomInputStream getStdin() {
    return this.stdin;
  }

  /**
   * Gets standard output of control.
   *
   * @return standard output
   */
  public PrintStream getStdout() {
    return this.stdout;
  }

  /**
   * Gets standard error of control.
   *
   * @return standard error
   */
  public PrintStream getStderr() {
    return this.stderr;
  }

}
