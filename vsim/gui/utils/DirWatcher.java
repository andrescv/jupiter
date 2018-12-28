package vsim.gui.utils;

import java.io.File;
import vsim.Settings;
import java.util.HashMap;
import java.nio.file.Path;
import java.nio.file.Files;
import java.io.IOException;
import javafx.concurrent.Task;
import java.nio.file.WatchKey;
import java.nio.file.WatchEvent;
import java.nio.file.LinkOption;
import java.nio.file.FileSystems;
import java.nio.file.WatchService;
import javafx.application.Platform;
import vsim.gui.components.TreePath;
import javafx.scene.control.TreeView;
import vsim.gui.controllers.EditorController;
import static java.nio.file.StandardWatchEventKinds.*;


/**
 * Utility class that recursively watches a directory.
 */
public final class DirWatcher extends Task<Void> {

  /** current dir watcher task */
  private static DirWatcher current;
  /** Expanded tab lookup */
  private static final HashMap<File, Boolean> expanded = new HashMap<File, Boolean>();

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
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public Void call() throws Exception {
    // set the new tree root
    Platform.runLater(() -> this.controller.cleanTree());
    TreePath root = new TreePath(Settings.DIR, true, DirWatcher.expanded);
    Platform.runLater(() -> this.controller.setRoot(root));
    // register new keys
    this.registerAll(Settings.DIR);
    // run loop
    this.loop();
    // meh
    return null;
  }

  /**
   * Forever loop that continuously watches directory changes.
   */
  private void loop() {
    // forever ...
    while (!this.isCancelled()) {
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
        if (kind == ENTRY_CREATE || kind == ENTRY_DELETE) {
          // set the new tree root
          TreePath root = new TreePath(Settings.DIR, true, DirWatcher.expanded);
          Platform.runLater(() -> this.controller.setRoot(root));
        }
        // clean expanded
        if (kind == ENTRY_DELETE) {
          // check external deletions
          Platform.runLater(() -> this.controller.checkExternalDelete());
          Object[] fileKeys = DirWatcher.expanded.keySet().toArray();
          for (int i = 0; i < fileKeys.length; i++) {
            File k = (File)fileKeys[i];
            if (!k.exists())
              DirWatcher.expanded.remove(k);
          }
        }
        // check external modifications
        if (kind == ENTRY_MODIFY)
          Platform.runLater(() -> this.controller.checkExternalModify());
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
        File[] files = root.listFiles();
        if (files != null) {
          // walk directory tree
          for (File f: files)
            this.registerAll(f);
        }
      } catch (IOException e) {
        // TODO what to do if a I/O error occurs
      }
    }
  }

  /**
   * Opens a path setting its expanded info to true.
   *
   * @param path path to open
   */
  public static void open(File path) {
    DirWatcher.expanded.put(path, true);
  }

  /**
   * Removes path directory if if it is in expanded lookup.
   *
   * @param path path to remove
   */
  public static void close(File path) {
    DirWatcher.expanded.remove(path);
  }

  /**
   * Sets newPath with oldPath expanded info if oldPath is in expanded lookup.
   *
   * @param oldPath old path
   * @param newPath new path
   */
  public static void rename(File oldPath, File newPath) {
    if (DirWatcher.expanded.get(oldPath) != null)
      DirWatcher.expanded.put(newPath, DirWatcher.expanded.get(oldPath));
  }

  /**
   * Starts a new dir watcher task and cancel any previous task if any.
   *
   * @param tree tree view to update
   */
  public static void start(EditorController controller) {
    // stop previous task
    if (DirWatcher.current != null)
      DirWatcher.current.cancel();
    // run new watcher task
    DirWatcher.expanded.clear();
    DirWatcher.current = new DirWatcher(controller);
    Thread t = new Thread(DirWatcher.current);
    t.setDaemon(true);
    t.start();
  }

}
