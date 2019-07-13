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

package vsim.gui;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.HashMap;

import javafx.concurrent.Task;

import vsim.Logger;
import vsim.utils.FS;


/** Directory watcher. */
public final class DirWatcher extends Task<Void> {

  /** current dir watcher */
  private static DirWatcher CURRENT = null;

  /** property change support */
  private final PropertyChangeSupport pcs;
  /** java watch service utility */
  private final WatchService watcher;
  /** map of watched keys */
  private final HashMap<WatchKey, Path> keys;

  /**
   * Creates a new directory watcher.
   *
   * @param observer watcher observer
   * @throws IOException if an I/O error occurs
   */
  private DirWatcher(PropertyChangeListener observer) throws IOException {
    watcher = FileSystems.getDefault().newWatchService();
    keys = new HashMap<>();
    pcs = new PropertyChangeSupport(this);
    pcs.addPropertyChangeListener(observer);
  }

  /** {@inheritDoc} */
  @Override
  public Void call() {
    registerAll(FS.toPath(Settings.USER_DIR.get()));
    while(!isCancelled()) {
      // wait for key to be signalled
      WatchKey key;
      try {
        key = watcher.take();
      } catch (InterruptedException e) {
        break;
      }
      // get path
      Path dir = keys.get(key);
      // ignore unregistered keys
      if (dir == null) {
        continue;
      }
      // get events
      for (WatchEvent<?> event : key.pollEvents()) {
        WatchEvent.Kind kind = event.kind();
        // ignore overflow events
        if (kind == StandardWatchEventKinds.OVERFLOW) {
          continue;
        }
        @SuppressWarnings("unchecked")
        File file = dir.resolve(((WatchEvent<Path>) event).context()).toFile();
        // handle event
        if (kind == StandardWatchEventKinds.ENTRY_CREATE && file.isDirectory()) {
          pcs.firePropertyChange("create_dir", "create", file);
        } else if (kind == StandardWatchEventKinds.ENTRY_CREATE) {
          pcs.firePropertyChange("create_file", "create", file);
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE && file.isDirectory()) {
          pcs.firePropertyChange("delete_dir", "delete", file);
        } else if (kind == StandardWatchEventKinds.ENTRY_DELETE) {
          pcs.firePropertyChange("delete_file", "delete", file);
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY && file.isDirectory()) {
          pcs.firePropertyChange("modify_dir", "modify", file);
        } else if (kind == StandardWatchEventKinds.ENTRY_MODIFY) {
          pcs.firePropertyChange("modify_file", "modify", file);
        }
      }
      boolean valid = key.reset();
      if (!valid) {
        keys.remove(key);
        if (keys.isEmpty()) {
          break;
        }
      }
    }
    return null;
  }

  /** Closes watch service. */
  private void close() {
    try {
      watcher.close();
      keys.clear();
      for (PropertyChangeListener observer : pcs.getPropertyChangeListeners()) {
        pcs.removePropertyChangeListener(observer);
      }
    } catch (IOException e) {
      // nothing here :]
    }
  }

  /**
   * Register all keys.
   *
   * @param directory directory to register
   */
  private void registerAll(Path directory) {
    File file = directory.toFile();
    // only for unhidden directories
    if (file.isDirectory() && !file.isHidden()) {
      try {
        // create a new watch key for the directory
        WatchKey key = directory.register(
          watcher,
          StandardWatchEventKinds.ENTRY_CREATE,
          StandardWatchEventKinds.ENTRY_DELETE,
          StandardWatchEventKinds.ENTRY_MODIFY
        );
        keys.put(key, directory);
        File[] files = file.listFiles();
        if (files != null) {
          // walk directory tree
          for (File f : files) {
            registerAll(f.toPath());
          }
        }
      } catch (IOException e) {
        // skip directory and subtree :]
      }
    }
  }

  /**
   * Starts a new directory watcher.
   *
   * @param observer watcher observer
   */
  public static void start(PropertyChangeListener observer) {
    // cancel previous watcher
    if (CURRENT != null) {
      CURRENT.cancel();
      CURRENT.close();
    }
    // create and start watcher
    try {
      CURRENT = new DirWatcher(observer);
      Thread th = new Thread(CURRENT);
      th.setDaemon(true);
      th.start();
    } catch (IOException e) {
      Logger.warning("could not load watch service...");
    }
  }

}
