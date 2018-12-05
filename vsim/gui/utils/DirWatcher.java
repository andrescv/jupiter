package vsim.gui.utils;

import java.io.File;
import vsim.Settings;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.LinkOption;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import javafx.application.Platform;
import javafx.scene.control.TreeView;
import vsim.gui.controllers.EditorController;
import static java.nio.file.StandardWatchEventKinds.*;


/**
 * Utility class that recursively watches a directory.
 */
public final class DirWatcher {

  /** Thread watcher */
  private Thread thread;
  /** Reference to editor controller */
  private EditorController controller;
  /** Java watch service utility */
  private final WatchService watcher;
  /** Map of watched keys */
  private final HashMap<WatchKey,Path> keys;

  /**
   * Creates a new directory watcher that will refresh a TreeView object.
   *
   * @param controller editor controller used to refresh tree view
   */
  public DirWatcher(EditorController controller) {
    this.controller = controller;
    try {
      this.watcher = FileSystems.getDefault().newWatchService();
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
    this.keys = new HashMap<WatchKey, Path>();
    this.registerAll(Settings.DIR);
  }

  /**
   * Re-starts the thread watcher using {@link Settings.DIR} as root.
   */
  public void start() {
    // stop previous thread
    if (this.thread != null) {
      this.thread.interrupt();
      this.thread = null;
    }
    // clear old keys
    this.keys.clear();
    // register new keys and run thread
    this.registerAll(Settings.DIR);
    this.run();
  }

  /**
   * Forever loop that continuously watches directory changes.
   */
  private void loop() {
    // forever ...
    while (true) {
      // wait for key to be signalled
      WatchKey key;
      try {
        key = watcher.take();
      } catch (InterruptedException e) {
        return;
      }
      // unregistered key
      Path dir = keys.get(key);
      if (dir == null)
        continue;
      // get all events
      for (WatchEvent<?> event: key.pollEvents()) {
        WatchEvent.Kind kind = event.kind();
        // how OVERFLOW event is handled
        if (kind == OVERFLOW)
          continue;
        // Context for directory entry event is the file name of entry
        @SuppressWarnings("unchecked")
        WatchEvent<Path> ev = (WatchEvent<Path>)event;
        Path name = ev.context();
        Path child = dir.resolve(name);
        // update tree view
        Platform.runLater(() -> this.controller.updateTree());
        // if directory is created, then register it and its sub-directories
        if (kind == ENTRY_CREATE && Files.isDirectory(child, LinkOption.NOFOLLOW_LINKS))
          registerAll(child.toFile());
      }
      // reset key and remove from set if directory no longer accessible
      boolean valid = key.reset();
      // if it's a invalid key remove it from key set
      if (!valid) {
        this.keys.remove(key);
        // all directories are inaccessible (nothing to watch), stop loop
        if (this.keys.isEmpty())
          break;
      }
    }
  }

  /**
   * Creates a new thread watcher and starts it.
   */
  private void run() {
    this.thread = new Thread(this::loop, "DirWatcher");
    this.thread.setDaemon(true);
    this.thread.start();
  }

  /**
   * Register all keys recursively.
   *
   * @param root starting root directory
   */
  private void registerAll(File root) {
    // only for unhidden directories
    if (root.isDirectory() && !root.isHidden()) {
      try {
        // create a new watch key for the directory
        Path rootPath = root.toPath();
        WatchKey key = rootPath.register(this.watcher, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY);
        this.keys.put(key, rootPath);
        // walk directory tree
        for (File f: root.listFiles())
          this.registerAll(f);
      } catch (IOException e) {
        // TODO what to do if a I/O error occurs
      }
    }
  }

}
