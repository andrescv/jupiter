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

import java.io.IOException;
import java.io.OutputStream;
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
    this.write(new byte[] { (byte) b }, 0, 1);
  }

}
