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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import org.junit.jupiter.api.Test;

import jupiter.utils.Data;


/** jupiter.utils.io.CLIConsoleOutput tests. */
public class CLIConsoleOutputTest {

  public static final String TEST = "Jupiter" + Data.EOL + Data.EOL + "Please enter a number: ";

  @Test
  void testCLIConsoleOutput() throws IOException {
    final ByteArrayOutputStream baos = new ByteArrayOutputStream();
    PrintStream ps = new PrintStream(baos, true, "utf-8");
    CLIConsoleOutput cli = new CLIConsoleOutput(ps);
    cli.println("Jupiter");
    cli.println();
    cli.print("Please enter a number: ");
    assertEquals(TEST, new String(baos.toByteArray(), StandardCharsets.UTF_8));
  }

}
