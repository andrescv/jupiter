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
import javafx.scene.control.TextArea;


public final class ConsoleOutput {

  public static final String NL = System.getProperty("line.separator");
  private final TextArea area;

  public ConsoleOutput(TextArea area) {
    this.area = area;
  }

  public void postRunMessage(String msg) {
    Platform.runLater(() -> this.area.appendText(msg));
  }

  public void postMessage(String msg) {
    this.area.appendText(msg);
  }

}
