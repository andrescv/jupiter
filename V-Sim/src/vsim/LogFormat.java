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

package vsim.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;


/** Logger format */
public final class LogFormat extends Formatter {

  /**
   * {@inheritDoc}
   */
  @Override
  public String format(LogRecord record) {
    StringBuilder sb = new StringBuilder();
    SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    fmt.setTimeZone(TimeZone.getTimeZone("UTC"));
    sb.append("[").append(fmt.format(new Date())).append("] ").append("(").append(record.getLevel().getLocalizedName())
        .append("): ").append(record.getMessage()).append(System.getProperty("line.separator"));
    return sb.toString();
  }

}
