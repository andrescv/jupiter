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

package vsim.utils.io;

import javafx.application.Platform;

import org.fxmisc.richtext.InlineCssTextArea;

import vsim.utils.Data;


/** GUI console output. */
public final class GUIConsoleOutput implements Output {

  /** GUI text area */
  private final InlineCssTextArea area;

  /**
   * Creates a new GUI console output.
   *
   * @param area GUI text area
   */
  public GUIConsoleOutput(InlineCssTextArea area) {
    this.area = area;
  }

  /** {@inheritDoc} */
  @Override
  public void print(String txt) {
    appendText(txt);
  }

  /** {@inheritDoc} */
  @Override
  public void println(String txt) {
    appendText(txt + Data.EOL);
  }

  /** {@inheritDoc} */
  @Override
  public void println() {
    appendText(Data.EOL);
  }

  /**
   * Appends text to text area.
   *
   * @param txt text to append
   */
  private void appendText(String txt) {
    Platform.runLater(() -> {
      area.moveTo(area.getLength());
      int pos = area.getLength();
      area.appendText(txt);
      area.setStyle(pos, area.getLength(), "-fx-fill: #222222;");
    });
  }

}
