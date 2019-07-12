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

import java.io.File;

import org.apache.commons.io.monitor.FileAlterationListenerAdaptor;
import org.apache.commons.io.monitor.FileAlterationMonitor;
import org.apache.commons.io.monitor.FileAlterationObserver;

import vsim.Logger;


/** Directory watcher. */
public final class DirWatcher {

  /** file monitor */
  private final FileAlterationMonitor monitor;


  /**
   * Creates a new directory watcher.
   *
   * @param interval the amount of time in milliseconds to wait between checks of the file system
   */
  public DirWatcher(long interval) {
    monitor = new FileAlterationMonitor(interval);
    monitor.setThreadFactory((r) -> {
      Thread th = new Thread(r);
      th.setDaemon(true);
      return th;
    });
  }

  /**
   * Watches a directory for changes.
   *
   * @param directory directory to watch
   */
  public void watch(File directory, FileAlterationListenerAdaptor listener) {
    FileAlterationObserver observer = new FileAlterationObserver(directory);
    observer.addListener(listener);
    monitor.addObserver(observer);
  }

  /** Starts monitor. */
  public void start() {
    try {
      monitor.start();
    } catch (Exception e) {
      Logger.warning("could not start directory watcher...");
    }
  }

  /** Stops monitor. */
  public void stop() {
    try {
      monitor.stop();
    } catch (Exception e) {
      Logger.warning("could not stop directory watcher...");
    }
  }

}
