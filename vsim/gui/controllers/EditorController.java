package vsim.gui.controllers;

import java.io.File;
import vsim.Settings;
import javafx.fxml.FXML;
import java.util.HashMap;
import vsim.utils.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import javafx.scene.image.Image;
import vsim.gui.utils.DirWatcher;
import javafx.scene.image.ImageView;
import vsim.gui.components.TreePath;
import javafx.scene.control.TreeItem;
import javafx.stage.DirectoryChooser;
import vsim.gui.components.EditorTab;
import javafx.scene.control.TreeView;
import javafx.scene.control.MenuItem;
import javafx.scene.input.MouseButton;
import vsim.gui.components.SaveDialog;
import vsim.gui.components.CloseDialog;
import com.jfoenix.controls.JFXTabPane;
import vsim.gui.components.InputDialog;
import vsim.gui.components.DeleteDialog;
import javafx.scene.control.ContextMenu;
import javafx.stage.FileChooser.ExtensionFilter;


/**
 * Editor controller class.
 */
public class EditorController {

  /** Expanded tab lookup */
  private HashMap<File, Boolean> expanded;

  /** Current directory tree view */
  @FXML protected TreeView<String> tree;
  /** Editor tab pane */
  @FXML protected JFXTabPane editor;

  /** directory watcher */
  private DirWatcher watcher;

  /** tree view directory context menu */
  private ContextMenu dirContext;
  /** tree view file context menu */
  private ContextMenu fileContext;

  /** Reference to main controller */
  protected MainController mainController;

  /**
   * Initialize editor controller.
   *
   * @param controller main controller
   */
  protected void initialize(MainController controller) {
    this.mainController = controller;
    // add a new untitled tab
    this.addNewUntitledTab();
    // update tree root and start directory watcher
    this.expanded = new HashMap<File, Boolean>();
    this.watcher = new DirWatcher(this);
    this.updateTree();
    this.watcher.start();
    // create tree context menu
    MenuItem newFile = new MenuItem("New File");
    newFile.setOnAction(e -> this.addNewFile());
    MenuItem newFolder = new MenuItem("New Folder");
    newFolder.setOnAction(e -> this.addNewFolder());
    MenuItem renameDir = new MenuItem("Rename");
    renameDir.setOnAction(e -> this.renameDir());
    MenuItem deleteDir = new MenuItem("Delete");
    deleteDir.setOnAction(e -> this.deleteDir());
    this.setGraphic(newFile, "/resources/img/icons/new_file.png");
    this.setGraphic(newFolder, "/resources/img/icons/new_folder.png");
    this.setGraphic(renameDir, "/resources/img/icons/edit.png");
    this.setGraphic(deleteDir, "/resources/img/icons/trash.png");
    this.dirContext = new ContextMenu();
    this.dirContext.getItems().addAll(newFile, newFolder, renameDir, deleteDir);
    MenuItem renameFile = new MenuItem("Rename");
    renameFile.setOnAction(e -> this.renameFile());
    MenuItem deleteFile = new MenuItem("Delete");
    deleteFile.setOnAction(e -> this.deleteFile());
    this.setGraphic(renameFile, "/resources/img/icons/edit.png");
    this.setGraphic(deleteFile, "/resources/img/icons/trash.png");
    this.fileContext = new ContextMenu();
    this.fileContext.getItems().addAll(renameFile, deleteFile);
    this.tree.setOnMouseClicked(e -> {
      this.dirContext.hide();
      this.fileContext.hide();
      TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
      if (item != null) {
        if (e.getButton() == MouseButton.PRIMARY && item.getPath().isFile())
          this.addTitledTab(item.getPath());
        else if (e.getButton() == MouseButton.SECONDARY)
          this.openContextMenu(item, e.getScreenX(), e.getScreenY());
      }
    });
  }

  /**
   * Adds a new untitled tab.
   */
  protected void addNewUntitledTab() {
    this.mainController.selectEditorTab();
    EditorTab tab = new EditorTab();
    this.editor.getTabs().add(tab);
    this.editor.getSelectionModel().select(tab);
  }

  /**
   * Adds a new titled tab by opening a file.
   */
  protected void addTitledTab() {
    this.mainController.selectEditorTab();
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Open RISC-V File");
    chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
    File file = chooser.showOpenDialog(this.mainController.stage);
    this.addTitledTab(file);
  }

  /**
   * Opens a new project directory and makes it default.
   */
  protected void openFolder() {
    this.mainController.selectEditorTab();
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Open RISC-V Project");
    chooser.setInitialDirectory(Settings.DIR);
    File dir = chooser.showDialog(this.mainController.stage);
    if (dir != null && !dir.equals(Settings.DIR)) {
      Settings.DIR = dir;
      this.expanded.clear();
      this.updateTree();
      this.watcher.start();
    }
  }

  /**
   * Saves current selected tab if necessary.
   */
  protected void saveTab() {
    EditorTab tab = this.getSelectedTab();
    this.saveTab(tab, false);
  }

  /**
   * Saves as current selected tab.
   */
  protected void saveTabAs() {
    EditorTab tab = this.getSelectedTab();
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Save RISC-V File As...");
    chooser.setInitialFileName(tab.getName());
    chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
    File file = chooser.showSaveDialog(this.mainController.stage);
    if (file != null) {
      // backup current file path
      File old = tab.getPath();
      // change path and save
      try {
        tab.setPath(file);
        tab.save();
      } catch (IOException e) {
        // revert file path if something goes wrong...
        if (old != null)
          tab.setPath(old);
        Message.error("Could not save file: " + file);
      }
    }
  }

  /**
   * Saves all tabs.
   */
  protected void saveAllTabs() {
    for (Tab openTab: this.editor.getTabs()) {
      EditorTab tab = (EditorTab)openTab;
      this.saveTab(tab, false);
    }
  }

  /**
   * Closes current selected tab.
   */
  protected void closeTab() {
    EditorTab tab = this.getSelectedTab();
    if (tab != null)
      this.closeTabSafetly(tab);
  }

  /**
   * Closes all tabs.
   */
  protected void closeAllTabs() {
    ArrayList<Tab> tabs = new ArrayList<Tab>(this.editor.getTabs());
    for (Tab openTab: tabs) {
      EditorTab tab = (EditorTab)openTab;
      this.closeTabSafetly(tab);
      if (!tab.isClosed())
        break;
    }
  }

  /**
   * Closes all tabs after saving all changes.
   */
  protected void quit() {
    this.mainController.selectEditorTab();
    boolean save = false;
    for (Tab openTab: this.editor.getTabs())
      save |= ((EditorTab) openTab).hasChanged();
    if (save) {
      CloseDialog dialog = new CloseDialog();
      switch (dialog.showAndWait()) {
        case 0:
          (new ArrayList<Tab>(this.editor.getTabs())).forEach(e -> this.closeTab((EditorTab) e));
          break;
        case 1:
          ArrayList<Tab> tabs = new ArrayList<Tab>(this.editor.getTabs());
          for (Tab openTab: tabs) {
            EditorTab tab = (EditorTab)openTab;
            this.saveTab(tab, true);
            if (!tab.isClosed())
              break;
          }
          break;
        default:
          break;
      }
    } else
      this.closeAllTabs();
  }

  /**
   * Updates tree view
   */
  public void updateTree() {
    this.tree.setRoot(this.getTree(Settings.DIR, true));
  }

  /**
   * Opens a dynamic context menu at mouse position.
   *
   * @param item selected tree item
   * @param x mouse x position
   * @param y mouse y position
   */
  private void openContextMenu(TreePath item, double x, double y) {
    if (item.getPath().isFile())
      this.fileContext.show(this.tree, x, y);
    else
      this.dirContext.show(this.tree, x, y);
  }

  /**
   * Returns a directory tree
   */
  private TreePath getTree(File directory, boolean isRoot) {
    TreePath root = new TreePath(directory);
    // expand tree item if its the root item
    Boolean expandedProp = this.expanded.get(directory);
    if (expandedProp != null)
      root.setExpanded(expandedProp);
    else if (isRoot) {
      root.setExpanded(true);
      this.expanded.put(root.getPath(), true);
    } else
      this.expanded.put(root.getPath(), false);
    // set item icon
    String path = isRoot ? "/resources/img/icons/root.png" : "/resources/img/icons/folder.png";
    Image image = new Image(getClass().getResource(path).toExternalForm());
    ImageView icon = new ImageView();
    icon.setImage(image);
    icon.setFitHeight(20);
    icon.setFitWidth(20);
    root.setGraphic(icon);
    root.expandedProperty().addListener((e, oldVal, newVal) -> {
      this.expanded.put(directory, (Boolean)newVal);
    });
    // walk directory tree
    for (File f: directory.listFiles()) {
      if (f.isDirectory() && !f.isHidden())
        root.getChildren().add(this.getTree(f, false));
      else if (!f.isHidden()) {
        String filename = f.getName();
        if (filename.endsWith(".s") || filename.endsWith(".asm")) {
          image = new Image(getClass().getResource("/resources/img/icons/file.png").toExternalForm());
          icon = new ImageView();
          icon.setImage(image);
          icon.setFitHeight(18);
          icon.setFitWidth(18);
          TreePath child = new TreePath(f);
          child.setGraphic(icon);
          root.getChildren().add(child);
        }
      }
    }
    // sort root children
    root.getChildren().sort(new Comparator<TreeItem<String>>() {
      @Override
      public int compare(TreeItem<String> tp, TreeItem<String> tq) {
        TreePath p = (TreePath)tp;
        TreePath q = (TreePath)tq;
        String fn = p.getPath().getName().toString();
        String qn = q.getPath().getName().toString();
        boolean pd = p.getPath().isDirectory();
        boolean qd = q.getPath().isDirectory();
        if (pd && !qd)
          return -1;
        else if (!pd && qd)
          return 1;
        else
          return fn.compareToIgnoreCase(qn);
      }
    });
    return root;
  }

  /**
   * Adds a new titled if necessary or reuse a not changed untitled tab.
   *
   * @param file file to open
   */
  private void addTitledTab(File file) {
    if (file != null) {
      try {
        EditorTab tab = null;
        EditorTab reuse = null;
        // search in all current tabs
        for (Tab openTab: this.editor.getTabs()) {
          EditorTab t = (EditorTab)openTab;
          // a tab pointing to this file already exits
          if (t.getPath() != null && t.getPath().equals(file)) {
            tab = t;
            break;
          }
          // reuse some untitled tab with no changes
          else if (t.isUntitled() && !t.hasChanged() && reuse == null)
            reuse = t;
        }
        // if tab was not already open
        if (tab == null) {
          // if a untitled tab can be reused
          if (reuse != null) {
            tab = reuse;
            tab.setPathAndOpen(file);
          } else {
            tab = new EditorTab(file);
            this.editor.getTabs().add(tab);
          }
        }
        this.editor.getSelectionModel().select(tab);
      } catch (IOException e) {
        Message.error("Could not open file: " + file);
      }
    }
  }

  /**
   * Saves a tab.
   *
   * @param tab tab to save
   * @param close after save, close the tab also if true
   */
  private void saveTab(EditorTab tab, boolean close) {
    try {
      // save dialog if is a untitled tab
      if (tab.isUntitled()) {
        FileChooser chooser = new FileChooser();
        chooser.setTitle("Save RISC-V File");
        chooser.setInitialFileName(tab.getName());
        chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
        File newFile = chooser.showSaveDialog(this.mainController.stage);
        // set new file path
        if (newFile != null)
          tab.setPath(newFile);
        // dont continue if cancel button was clicked
        else
          return;
      }
      // save tab
      tab.save();
      // close tab ?
      if (close)
        this.closeTab(tab);
    } catch (IOException e) {
      Message.error("Could not save file: " + tab.getPath());
    }
  }

  /**
   * Closes a tab safetly and removes it from current tabs.
   *
   * @param tab tab to close
   */
  private void closeTabSafetly(EditorTab tab) {
    if (tab.hasChanged()) {
      SaveDialog dialog = new SaveDialog(tab.getName());
      int result = dialog.showAndWait();
      switch (result) {
        // dont save
        case 0:
          this.closeTab(tab);
          break;
        // save
        case 1:
          // save as ?
          this.saveTab(tab, true);
          break;
        // cancel
        default:
          break;
      }
    } else
      this.closeTab(tab);
  }

  /**
   * Closes a tab and removes it from current tabs.
   *
   * @param tab tab to close
   */
  private void closeTab(EditorTab tab) {
    tab.close();
    this.editor.getTabs().remove(tab);
  }

  /**
   * Gets current selected tab.
   *
   * @return selected tab
   */
  private EditorTab getSelectedTab() {
    return (EditorTab)this.editor.getSelectionModel().getSelectedItem();
  }

  /**
   * Sets a graphic to a menu item.
   *
   * @param item menu item to add graphic
   * @param path menu item graphic path
   */
  private void setGraphic(MenuItem item, String path) {
    ImageView img = new ImageView();
    img.setFitWidth(20.0);
    img.setFitHeight(20.0);
    img.setImage(new Image(getClass().getResourceAsStream(path)));
    item.setGraphic(img);
  }

  /**
   * Recursively deletes a directory and remove opened tabs if necessary.
   *
   * @param dir directory to delete
   */
  private void deleteDir(File dir) {
    // if its a directory first delete all sub-directories and files
    if (dir.isDirectory()) {
      for (File f : dir.listFiles())
        deleteDir(f);
      // only if all sub-directories and files were deleted
      // we can delete the parent directory
      if (dir.listFiles().length == 0)
        dir.delete();
    }
    // if its a file just delete the file and close the opened
    // tab if there is a opened tab with the same path
    else if (dir.isFile()) {
      EditorTab tab = null;
      for (Tab openTab: this.editor.getTabs()) {
        EditorTab t = (EditorTab)openTab;
        if (t.getPath() != null && t.getPath().equals(dir)) {
          tab = t;
          break;
        }
      }
      dir.delete();
      // close the opened tab only if we actually delete the file
      if (tab != null && !dir.exists())
        this.closeTab(tab);
    }
  }

  private void renameOpenedTabsWith(File oldPath, File newPath) {
    for (Tab openTab: this.editor.getTabs()) {
      EditorTab tab = (EditorTab)openTab;
      if (tab.getPath() != null) {
        String absPath = tab.getPath().getAbsolutePath();
        if (absPath.startsWith(oldPath.getAbsolutePath())) {
          String base = absPath.split(oldPath.getAbsolutePath())[1];
          tab.setPath(new File(newPath + File.separator + base));
        }
      }
    }
  }

  /**
   * Adds a new titled tab by creating a file.
   */
  private void addNewFile() {
    // get selected tree item
    TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog("Enter the path for the new file");
    String filename = dialog.showAndWait();
    // if user actually pressed enter
    if (filename.length() > 0) {
      // only accept assembler files
      if (filename.endsWith(".s") || filename.endsWith(".asm")) {
        // create a new path
        File path = new File(item.getPath() + File.separator + filename);
        if (path.exists())
          Message.warning(String.format("file %s already exists", filename));
        else {
          try {
            // create all necessary folders
            (new File(path.getParent())).mkdirs();
            // create file
            path.createNewFile();
            // add titled tab with the new file
            this.addTitledTab(path);
          } catch (IOException e) {
            Message.error("Could not create file: " + path);
          }
        }
      } else
        Message.warning("invalid file name: " + filename);
    }
  }


  /**
   * Adds a new folder
   */
  private void addNewFolder() {
    // get selected tree item
    TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog("Enter the path for the new folder");
    String dirname = dialog.showAndWait();
    // if user actually pressed enter
    if (dirname.length() > 0) {
      // create new path
      File path = new File(item.getPath() + File.separator + dirname);
      if (path.exists())
        Message.warning(String.format("directory %s already exists", dirname));
      else {
        // create all necessary folders
        path.mkdirs();
        this.expanded.put(path, true);
      }
    }
  }

  /**
   * Renames a directory.
   */
  private void renameDir() {
    TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
    InputDialog dialog = new InputDialog("Enter the path for the new folder");
    String dirname = dialog.showAndWait();
    if (dirname.length() > 0) {
      File newPath = new File(item.getPath().getParent() + File.separator + dirname);
      File oldPath = item.getPath();
      // rename file and find a opened tab
      if (!oldPath.equals(newPath) && !newPath.exists()) {
        if (this.expanded.get(oldPath) != null) {
          this.expanded.put(newPath, this.expanded.get(oldPath));
          this.expanded.remove(oldPath);
        }
        newPath.mkdirs();
        this.renameOpenedTabsWith(oldPath, newPath);
        item.getPath().renameTo(newPath);
      }
      // dont do anything if the user uses the same path
      else if (oldPath.equals(newPath))
        return;
      else if (newPath.exists())
        Message.warning(String.format("directory %s already exists", newPath.getName()));
    }
  }

  /**
   * Recursively deletes a selected directory.
   */
  private void deleteDir() {
    // only if user really wants to delete the directory
    DeleteDialog dialog = new DeleteDialog();
    boolean delete = dialog.showAndWait();
    if (delete) {
      TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
      this.expanded.remove(item.getPath());
      this.deleteDir(item.getPath());
    }
  }

  /**
   * Renames a file and renames opened tab if there is an
   * opened tab with the same old path.
   */
  private void renameFile() {
    // get selected tree item
    TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog("Enter the path for the new file");
    String filename = dialog.showAndWait();
    // if user actually pressed enter
    if (filename.length() > 0) {
      // create new path
      File newPath = new File(item.getPath().getParent() + File.separator + filename);
      // get old file path
      File oldPath = item.getPath();
      // rename file and find a opened tab
      if (!oldPath.equals(newPath) && !newPath.exists()) {
        for (Tab openTab: this.editor.getTabs()) {
          EditorTab t = (EditorTab)openTab;
          if (t.getPath() != null && t.getPath().equals(oldPath)) {
            t.setPath(newPath);
            break;
          }
        }
        item.getPath().renameTo(newPath);
      }
      // dont do anything if the user uses the same path
      else if (oldPath.equals(newPath))
        return;
      else if (newPath.exists())
        Message.warning(String.format("file %s already exists", newPath.getName()));
    }
  }

  /**
   * Deletes a file and also closes an opened tab if there is an
   * opened tab with the same path.
   */
  private void deleteFile() {
    // only if user really wants to delete the file
    DeleteDialog dialog = new DeleteDialog();
    boolean delete = dialog.showAndWait();
    if (delete) {
      // get selected tree item
      TreePath item = (TreePath)this.tree.getSelectionModel().getSelectedItem();
      // find opened tab (if any)
      EditorTab tab = null;
      for (Tab openTab: this.editor.getTabs()) {
        EditorTab t = (EditorTab)openTab;
        if (t.getPath() != null && t.getPath().equals(item.getPath())) {
          tab = t;
          break;
        }
      }
      // delete file
      item.getPath().delete();
      // only if file really was deleted
      if (tab != null && !item.getPath().exists())
        this.closeTab(tab);
    }
  }

}
