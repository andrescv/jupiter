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

package jupiter.utils.io;

import java.util.concurrent.ArrayBlockingQueue;

import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import org.fxmisc.richtext.InlineCssTextArea;

import jupiter.gui.Status;
import jupiter.utils.Data;
import jupiter.utils.IO;


/** GUI input. */
public final class GUIConsoleInput implements Input {

  /** inidicates the initial position to get the user response from text area */
  private int initialPos;
  /** user response */
  private String inputText;
  /** text input control */
  private final InlineCssTextArea area;
  /** key event filter */
  private final EventHandler<KeyEvent> listener;
  /** mouse event filter */
  private final EventHandler<MouseEvent> mouseListener;
  /** stop listener */
  private final ChangeListener<Boolean> stopListener;
  /** blocking queue */
  private final ArrayBlockingQueue<String> queue;

  /**
   * Creates a new GUI console input.
   *
   * @param area GUI text area
   */
  public GUIConsoleInput(InlineCssTextArea area) {
    this.area = area;
    initialPos = -1;
    inputText = null;
    area.setEditable(false);
    queue = new ArrayBlockingQueue<>(1);
    listener = new EventHandler<KeyEvent>() {
      @Override
      public void handle(KeyEvent e) {
        switch (e.getCode()) {
          case UP:
          case KP_UP:
          case PAGE_UP:
            area.moveTo(initialPos);
            e.consume();
            break;
          case DOWN:
          case KP_DOWN:
          case PAGE_DOWN:
            area.moveTo(area.getLength());
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
            // change area input color
            if (initialPos < area.getLength()) {
              area.setStyle(initialPos, area.getLength(), "-fx-fill: #4a148c;");
            }
            break;
        }
      }
    };
    mouseListener = new EventHandler<MouseEvent>() {
      @Override
      public void handle(MouseEvent e) {
        EventType<? extends MouseEvent> type = e.getEventType();
        // disable mouse selection
        if (type == MouseEvent.DRAG_DETECTED || type == MouseEvent.MOUSE_DRAGGED)
          e.consume();
        // control caret position
        if (type == MouseEvent.MOUSE_CLICKED || type == MouseEvent.MOUSE_RELEASED || type == MouseEvent.MOUSE_PRESSED) {
          if (e.getButton() == MouseButton.PRIMARY) {
            if (area.getCaretPosition() < initialPos) {
              area.setDisable(true);
              area.moveTo(initialPos);
              area.setDisable(false);
            }
          } else {
            e.consume();
          }
        }
      }
    };
    stopListener = new ChangeListener<Boolean>() {
      @Override
      public void changed(ObservableValue obs, Boolean oldValue, Boolean newValue) {
        if (!newValue) {
          returnResponse();
        }
      }
    };
  }

  /** {@inheritDoc} */
  @Override
  public int read() {
    waitForResponse();
    if (inputText == null) {
      IO.stdout().println();
      return 0;
    } else if (inputText.equals("")) {
      return (int) Data.EOL.charAt(0);
    } else {
      return (int) inputText.charAt(0);
    }
  }

  /** {@inheritDoc} */
  @Override
  public String readLine() {
    waitForResponse();
    if (inputText == null) {
      IO.stdout().println();
      return "";
    } else {
      return inputText;
    }
  }

  /** Prepares text area adding listeners and set editable to true. */
  private void prepare() {
    Status.RUNNING.addListener(stopListener);
    Platform.runLater(() -> {
      initialPos = area.getLength();
      area.moveTo(initialPos);
      area.addEventFilter(KeyEvent.ANY, listener);
      area.addEventFilter(MouseEvent.ANY, mouseListener);
      area.getContextMenu().getItems().forEach(e -> e.setDisable(true));
      area.setEditable(true);
      area.requestFocus();
    });
  }

  /** Cleans up text area removing listeners and set editable to {@code false}. */
  private void cleanup() {
    Status.RUNNING.removeListener(stopListener);
    Platform.runLater(() -> {
      initialPos = -1;
      area.setEditable(false);
      area.removeEventFilter(KeyEvent.ANY, listener);
      area.removeEventFilter(MouseEvent.ANY, mouseListener);
      area.getContextMenu().getItems().forEach(e -> e.setDisable(false));
    });
  }

  /** Sets the inputText variable with user response. */
  private void returnResponse() {
    if (initialPos >= 0) {
      queue.offer(area.getText(Math.min(initialPos, area.getLength()), area.getLength()));
    }
  }

  /** Waits for user input. This method will block current thread until a user input is enqueue. */
  private void waitForResponse() {
    try {
      prepare();
      inputText = queue.take();
    } catch (InterruptedException e) {
      inputText = null;
    } finally {
      cleanup();
      queue.clear();
    }
  }

}
