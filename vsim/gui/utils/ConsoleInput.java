package vsim.gui.utils;

import java.io.InputStream;
import vsim.simulator.Status;
import javafx.event.EventType;
import javafx.event.EventHandler;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseButton;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import java.util.concurrent.ArrayBlockingQueue;


/**
 * This class represents a custom input stream to use with a text area.
 */
public class ConsoleInput {

  private int maxLength;
  /** inidicates the initial position to get the user response from text area */
  private int initialPos;
  /** user response */
  private String inputText;
  /** text input control */
  private final TextArea area;
  /** key event filter */
  private final EventHandler<KeyEvent> listener;
  /** mouse event filter */
  private final EventHandler<MouseEvent> mouseListener;
  /** stop listener */
  private final ChangeListener<Boolean> stopListener;
  /** blocking queue */
  private final ArrayBlockingQueue<String> queue;

  /**
   * Creates a custom input stream instance with the given text area.
   */
  public ConsoleInput(TextArea area) {
    this.area = area;
    this.initialPos = -1;
    this.inputText = null;
    this.maxLength = -1;
    this.area.setEditable(false);
    this.queue = new ArrayBlockingQueue<String>(1);
    this.listener = new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        switch (e.getCode()) {
          case UP:
          case KP_UP:
          case PAGE_UP:
            area.positionCaret(initialPos);
            e.consume();
            break;
          case DOWN:
          case KP_DOWN:
          case PAGE_DOWN:
            area.positionCaret(area.getLength());
            e.consume();
            break;
          // ensure always that caret position is >= initialPos
          case LEFT:
          case KP_LEFT:
          case BACK_SPACE:
            if (area.getCaretPosition() == initialPos)
              e.consume();
            break;
          // return user response
          case ENTER:
            // after user release enter key return response
            if (e.getEventType() == KeyEvent.KEY_RELEASED)
              returnResponse();
            break;
          // nothing
          default:
            if (e.getEventType() == KeyEvent.KEY_RELEASED) {
              if (area.getText(initialPos, area.getLength()).length() == maxLength) {
                returnResponse();
                area.appendText(System.getProperty("line.separator"));
              }
            }
            break;
        }
      }
    };
    this.mouseListener = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        EventType<? extends MouseEvent> type = e.getEventType();
        // disable mouse selection
        if (type == MouseEvent.DRAG_DETECTED || type == MouseEvent.MOUSE_DRAGGED)
          e.consume();
        // control caret position
        if (type == MouseEvent.MOUSE_CLICKED || type == MouseEvent.MOUSE_RELEASED || type == MouseEvent.MOUSE_PRESSED) {
          if (e.getClickCount() == 1 && e.getButton() == MouseButton.PRIMARY) {
            if (area.getCaretPosition() < initialPos)
              area.positionCaret(initialPos);
          } else
            e.consume();
        }
      }
    };
    this.stopListener = new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue obs, Boolean oldValue, Boolean newValue) {
        if (newValue == true)
          returnResponse();
      }
    };
  }

  /**
   * Prepares text area adding listeners and set editable to true.
   */
  private void prepare() {
    Platform.runLater(() -> {
      this.initialPos = this.area.getLength();
      this.area.positionCaret(this.initialPos);
      this.area.addEventFilter(KeyEvent.ANY, this.listener);
      this.area.addEventFilter(MouseEvent.ANY, this.mouseListener);
      Status.STOPPED.addListener(this.stopListener);
      this.area.getContextMenu().getItems().forEach(e -> e.setDisable(true));
      this.area.setEditable(true);
      this.area.requestFocus();
    });
  }

  /**
   * Cleans up text area removing listeners and set editable to false.
   */
  private void cleanup() {
    Platform.runLater(() -> {
      this.area.setEditable(false);
      this.area.removeEventFilter(KeyEvent.ANY, this.listener);
      this.area.removeEventFilter(MouseEvent.ANY, this.mouseListener);
      Status.STOPPED.removeListener(this.stopListener);
      this.area.getContextMenu().getItems().forEach(e -> e.setDisable(false));
    });
  }

  /**
   * Sets the inputText variable with user response.
   */
  private void returnResponse() {
    this.queue.offer(this.area.getText(Math.min(this.initialPos, this.area.getLength()), this.area.getLength()));
  }

  /**
   * Waits for user input.
   * This method will block current thread until a user input is enqueue.
   */
  private void waitForResponse() {
    // this is called once
    if (this.inputText == null) {
      try {
        this.prepare();
        this.inputText = this.queue.take();
      } catch (InterruptedException e) {
        this.inputText = "";
      } finally {
        this.cleanup();
      }
      this.initialPos = 0;
    }
  }

  /**
   * Reads a string of the given length.
   *
   * @param maxLength string max length
   * @return user input
   */
  public String readString(int maxLength) {
    this.maxLength = (maxLength < 0) ? Integer.MAX_VALUE : maxLength;
    if (this.maxLength > 0) {
      this.waitForResponse();
      String str = this.inputText;
      this.inputText = null;
      this.initialPos = -1;
      this.queue.clear();
      return str;
    }
    return "";
  }

}
