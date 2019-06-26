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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


/** CLI input. */
public final class CmdInput implements Input {

  /** BufferedReader instance */
  private final BufferedReader in;

  /**
   * Creates a new command line input from an input stream.
   *
   * @param is InputStream (e.g System.in)
   */
  public CmdInput(InputStream is) {
    in = new BufferedReader(new InputStreamReader(is));
  }

  /** {@inheritDoc} */
  @Override
  public int read() {
    try {
      String data = in.readLine();
      if (data == null) {
        System.out.println();
        return 0;
      }
      if (data.equals(""))
        return 0;
      return (int) data.charAt(0);
    } catch (IOException e) {
      return 0;
    }
  }

  /** {@inheritDoc} */
  @Override
  public String readLine() {
    try {
      String data = in.readLine();
      if (data == null) {
        System.out.println();
        return "";
      }
      return data;
    } catch (IOException e) {
      return "";
    }
  }

}
