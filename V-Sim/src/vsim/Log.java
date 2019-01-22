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

package vsim;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.logging.ConsoleHandler;
import java.util.logging.FileHandler;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import vsim.utils.Message;


/** Logger wrapper class */
public final class Log {

  /** App logger instance */
  private static Logger logger = null;

  /**
   * Emits a config log message.
   *
   * @param msg log message
   */
  public static void config(String msg) {
    getLogger().config(msg);
  }

  /**
   * Emits an info log message.
   *
   * @param msg log message
   */
  public static void info(String msg) {
    getLogger().info(msg);
  }

  /**
   * Emits a warning log message.
   *
   * @param msg log message
   */
  public static void warning(String msg) {
    getLogger().warning(msg);
  }

  /**
   * Emits a warning log message for an exception.
   *
   * @param e exception
   */
  public static void warning(Exception e) {
    getLogger().severe("exception: ");
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    getLogger().warning(writer.toString());
  }

  /**
   * Emits a severe log message.
   *
   * @param msg log message
   */
  public static void severe(String msg) {
    getLogger().severe(msg);
  }

  /**
   * Emits a severe log message for an exception.
   *
   * @param e exception
   */
  public static void severe(Exception e) {
    getLogger().severe("exception: ");
    StringWriter writer = new StringWriter();
    e.printStackTrace(new PrintWriter(writer));
    getLogger().severe(writer.toString());
  }

  /**
   * Gets default V-Sim logger.
   *
   * @return application logger
   */
  private static Logger getLogger() {
    if (logger == null) {
      // load properties
      try {
        LogManager.getLogManager().readConfiguration(Log.class.getResourceAsStream("/log.properties"));
      } catch (IOException e) {
        Message.log("could not load logger properties");
      }
      Log.logger = Logger.getLogger("V-Sim");
      // add file handler
      try {
        String tmpDir = System.getProperty("java.io.tmpdir");
        File vsimDir = new File(tmpDir + File.separator + "V-Sim");
        // create V-Sim temp dir if necessary
        if (!vsimDir.exists())
          vsimDir.mkdir();
        logger.addHandler(new FileHandler(vsimDir + File.separator + "V-Sim.log"));
        // add console handler if GUI application is running
        if (Settings.GUI)
          logger.addHandler(new ConsoleHandler());
      } catch (Exception e) {
        logger.severe("could not create log file");
      }
    }
    return logger;
  }

}
