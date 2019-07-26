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

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import jupiter.utils.Data;


/** jupiter.utils.io.CLIConsoleInput tests. */
public class CLIConsoleInputTest {

  public static final String NUMBER = "10" + Data.EOL;

  private void test(String data, String expected) {
    ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
    CLIConsoleInput cli = new CLIConsoleInput(in);
    assertEquals(expected, cli.readLine());
  }

  private void test(String data, int expected) {
    ByteArrayInputStream in = new ByteArrayInputStream(data.getBytes());
    CLIConsoleInput cli = new CLIConsoleInput(in);
    assertEquals(expected, cli.read());
  }

  @Test
  void testCLIConsoleInput() throws IOException {
    test("10" + Data.EOL, "10");
    test(Data.EOL, (int) Data.EOL.charAt(0));
    test("", "");
    test("", 0);
    test("10" + Data.EOL, (int) "1".charAt(0));
  }

}
