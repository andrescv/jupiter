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

package vsim.gui.utils;

import javafx.application.Platform;
import org.fxmisc.richtext.InlineCssTextArea;


public final class ConsoleOutput {

  private final InlineCssTextArea area;

  public ConsoleOutput(InlineCssTextArea area) {
    this.area = area;
  }

  private void appendText(String msg, String color) {
    this.area.moveTo(this.area.getLength());
    int pos = this.area.getLength();
    this.area.appendText(msg);
    this.area.setStyle(pos, this.area.getLength(), String.format("-fx-fill: %s ;", color));
  }

  public void postRunMessage(String msg, String color) {
    Platform.runLater(() -> appendText(msg, color));
  }

  public void postMessage(String msg, String color) {
    appendText(msg, color);
  }

  public void postRunMessage(String msg) {
    Platform.runLater(() -> appendText(msg, "#1c1c1c"));
  }

  public void postMessage(String msg) {
    appendText(msg, "#1c1c1c");
  }

  public void postRunWarning(String msg) {
    Platform.runLater(() -> appendText(msg, "#f57f17"));
  }

  public void postWarning(String msg) {
    appendText(msg, "#f57f17");
  }

  public void postRunError(String msg) {
    Platform.runLater(() -> appendText(msg, "#b71c1c"));
  }

  public void postError(String msg) {
    appendText(msg, "#b71c1c");
  }

}
