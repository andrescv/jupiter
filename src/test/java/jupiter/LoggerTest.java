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

package jupiter;

import org.junit.jupiter.api.Test;


/** jupiter.Logger Tests */
public class LoggerTest {

  @Test
  public void testLogger() {
    Logger.info("hello world");
    Logger.warning("hello world");
    Logger.error("hello world");
  }

}
