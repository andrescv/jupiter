package vsim.simulator;

import javafx.beans.property.SimpleBooleanProperty;


public final class Status {

  /** if program is assembled and ready to simulate */
  public static final SimpleBooleanProperty READY = new SimpleBooleanProperty(false);

  /** if simulation is running */
  public static final SimpleBooleanProperty RUNNING = new SimpleBooleanProperty(false);

  /** if simulation executes an exit/exit2 ecall */
  public static final SimpleBooleanProperty EXIT = new SimpleBooleanProperty(false);

  /** indicates if the history is empty */
  public static final SimpleBooleanProperty EMPTY = new SimpleBooleanProperty(true);

  /**
   * Resets all status flags.
   */
  public static void reset() {
    Status.RUNNING.set(false);
    Status.EXIT.set(false);
    Status.EMPTY.set(true);
  }

}
