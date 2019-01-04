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

package vsim.gui.controllers;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import com.jfoenix.controls.JFXTabPane;
import vsim.Settings;
import vsim.gui.components.CloseDialog;
import vsim.gui.components.DeleteDialog;
import vsim.gui.components.EditorTab;
import vsim.gui.components.FindReplaceDialog;
import vsim.gui.components.InputDialog;
import vsim.gui.components.SaveDialog;
import vsim.gui.components.TreePath;
import vsim.gui.utils.DirWatcher;
import vsim.gui.utils.Icons;
import vsim.utils.Message;


/** Editor controller class. */
public class EditorController {

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

  /** Find/Replace in buffer dialog */
  private FindReplaceDialog findReplaceDialog;

  /** Reference to main controller */
  protected MainController mainController;

  /**
   * Initialize editor controller.
   *
   * @param controller main controller
   */
  protected void initialize(MainController controller) {
    this.mainController = controller;
    // create a new find replace dialog
    this.findReplaceDialog = new FindReplaceDialog(this);
    // init tree
    this.initTree();
    // start directory watcher
    DirWatcher.start(this);
  }

  /*-------------------------------------------------------*
  |                    public actions                     |
  *-------------------------------------------------------*/

  /** Cleans tree root. */
  public void cleanTree() {
    this.tree.setRoot(null);
  }

  /** Sets tree root. */
  public void setRoot(TreePath root) {
    this.tree.setRoot(root);
  }

  /** Checks if a external delete is performed to an open tab. */
  public void checkExternalDelete() {
    for (Tab tab : this.editor.getTabs()) {
      ((EditorTab) tab).checkExternalDelete();
    }
  }

  /** Checks if a external modify is performed to an open tab. */
  public void checkExternalModify() {
    for (Tab tab : this.editor.getTabs()) {
      ((EditorTab) tab).checkExternalModify();
    }
  }

  /**
   * Gets current selected tab.
   *
   * @return selected tab
   */
  public EditorTab getSelectedTab() {
    return (EditorTab) this.editor.getSelectionModel().getSelectedItem();
  }

  /**
   * Gets editor tab pane.
   *
   * @return editor tab pane
   */
  public JFXTabPane getEditor() {
    return this.editor;
  }

  /*-------------------------------------------------------*
  |                  protected actions                    |
  *-------------------------------------------------------*/

  /** Adds a new untitled tab. */
  protected void addNewUntitledTab() {
    this.mainController.selectEditorTab();
    final EditorTab tab = new EditorTab();
    tab.setOnCloseRequest(e -> {
      e.consume();
      this.closeTabSafetly(tab);
    });
    this.editor.getTabs().add(tab);
    this.editor.getSelectionModel().select(tab);
  }

  /** Adds a new titled tab by opening a file. */
  protected void addTitledTab() {
    this.mainController.selectEditorTab();
    FileChooser chooser = new FileChooser();
    chooser.setTitle("Open RISC-V File");
    chooser.getExtensionFilters().add(new ExtensionFilter("RISC-V Files", "*.s", "*.asm"));
    File file = chooser.showOpenDialog(this.mainController.stage);
    this.addTitledTab(file);
  }

  /** Opens a new project directory and makes it default. */
  protected void openFolder() {
    this.mainController.selectEditorTab();
    DirectoryChooser chooser = new DirectoryChooser();
    chooser.setTitle("Open RISC-V Project");
    chooser.setInitialDirectory(Settings.DIR);
    File dir = chooser.showDialog(this.mainController.stage);
    if (dir != null && !dir.equals(Settings.DIR)) {
      Settings.DIR = dir;
      // start directory watcher
      DirWatcher.start(this);
    }
  }

  /** Saves current selected tab if necessary. */
  protected void saveTab() {
    EditorTab tab = this.getSelectedTab();
    this.saveTab(tab, false);
  }

  /** Saves as current selected tab. */
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
        Message.warning("Could not save file: " + file);
      }
    }
  }

  /** Saves all tabs. */
  protected void saveAllTabs() {
    for (Tab openTab : this.editor.getTabs()) {
      EditorTab tab = (EditorTab) openTab;
      this.saveTab(tab, false);
    }
  }

  /** Closes current selected tab. */
  protected void closeTab() {
    EditorTab tab = this.getSelectedTab();
    if (tab != null)
      this.closeTabSafetly(tab);
  }

  /** Closes all tabs. */
  protected void closeAllTabs() {
    ArrayList<Tab> tabs = new ArrayList<Tab>(this.editor.getTabs());
    for (Tab openTab : tabs) {
      EditorTab tab = (EditorTab) openTab;
      this.closeTabSafetly(tab);
      if (!tab.isClosed())
        break;
    }
  }

  /** Closes all tabs after saving all changes. */
  protected void quit() {
    this.mainController.selectEditorTab();
    boolean save = false;
    for (Tab openTab : this.editor.getTabs())
      save |= ((EditorTab) openTab).hasChanged();
    if (save) {
      CloseDialog dialog = new CloseDialog();
      switch (dialog.showAndWait()) {
        case 0:
          (new ArrayList<Tab>(this.editor.getTabs())).forEach(e -> this.closeTab((EditorTab) e));
          break;
        case 1:
          ArrayList<Tab> tabs = new ArrayList<Tab>(this.editor.getTabs());
          for (Tab openTab : tabs) {
            EditorTab tab = (EditorTab) openTab;
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

  /** Undo editor action. */
  protected void undo() {
    this.getSelectedTab().undo();
  }

  /** Redo editor action. */
  protected void redo() {
    this.getSelectedTab().redo();
  }

  /** Cut editor action. */
  protected void cut() {
    this.getSelectedTab().cut();
  }

  /** Copy editor action. */
  protected void copy() {
    this.getSelectedTab().copy();
  }

  /** Paste editor action. */
  protected void paste() {
    this.getSelectedTab().paste();
  }

  /** Select editor action. */
  protected void selectAll() {
    this.getSelectedTab().selectAll();
  }

  /** Shows find/replace in buffer dialog. */
  protected void findReplaceInBuffer() {
    if (!this.findReplaceDialog.isShowing())
      this.findReplaceDialog.show();
  }

  /** Updates all tabs with saved settings. */
  protected void updateSettings() {
    for (Tab tab : this.editor.getTabs())
      ((EditorTab) tab).updateSettings();
  }

  /**
   * Determines if all open tabs are saved.
   *
   * @return true if all tabs are saved, false otherwise
   */
  protected boolean allSaved() {
    for (Tab tab : this.editor.getTabs()) {
      EditorTab t = (EditorTab) tab;
      if (t.isUntitled() || t.hasChanged() || !t.getPath().exists())
        return false;
    }
    return true;
  }

  /**
   * Gets all open and saved paths.
   *
   * @return array list of open and saved paths
   */
  protected ArrayList<File> getSavedPaths() {
    ArrayList<File> files = new ArrayList<File>();
    for (Tab tab : this.editor.getTabs()) {
      EditorTab t = (EditorTab) tab;
      if (!t.isUntitled() && !t.hasChanged() && t.getPath().exists())
        files.add(t.getPath());
    }
    files.trimToSize();
    return files;
  }

  /*-------------------------------------------------------*
  |                   private actions                     |
  *-------------------------------------------------------*/

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
        for (Tab openTab : this.editor.getTabs()) {
          EditorTab t = (EditorTab) openTab;
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
            final EditorTab newTab = new EditorTab(file);
            tab = newTab;
            tab.setOnCloseRequest(e -> {
              e.consume();
              this.closeTabSafetly(newTab);
            });
            this.editor.getTabs().add(tab);
          }
        }
        this.editor.getSelectionModel().select(tab);
      } catch (IOException e) {
        Message.warning("Could not open file: " + file);
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
      Message.warning("Could not save file: " + tab.getPath());
    }
  }

  /**
   * Closes a tab safetly and removes it from current tabs.
   *
   * @param tab tab to close
   */
  private void closeTabSafetly(EditorTab tab) {
    if (tab.hasChanged()) {
      SaveDialog dialog = new SaveDialog();
      switch (dialog.showAndWait(tab.getName())) {
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
   * Recursively deletes a directory and remove open tabs if necessary.
   *
   * @param dir directory to delete
   * @return true if directory was deleted, false if not
   */
  private boolean deleteDir(File dir) {
    // if its a directory first delete all sub-directories and files
    if (dir.isDirectory()) {
      boolean deleted = true;
      File[] files = dir.listFiles();
      if (files != null) {
        for (File f : files)
          deleted &= this.deleteDir(f);
      }
      // only if all sub-directories and files were deleted
      // we can delete the parent directory
      if (deleted && dir.delete()) {
        DirWatcher.close(dir);
        return true;
      }
      return false;
    }
    // if its a file just delete the file and close the open
    // tab if there is an open tab with the same path
    else {
      EditorTab tab = null;
      for (Tab openTab : this.editor.getTabs()) {
        EditorTab t = (EditorTab) openTab;
        if (t.getPath() != null && t.getPath().equals(dir)) {
          tab = t;
          break;
        }
      }
      boolean deleted = dir.delete();
      // close the open tab only if we actually delete the file
      if (tab != null && deleted)
        this.closeTab(tab);
      return deleted;
    }
  }

  /**
   * Renames old open tabs that have an absolute path with a prefix equal to the old path passed as argument, and
   * prepends the new path prefix.
   *
   * @param oldPath old path prefix
   * @param newPath new path prefix
   */
  private void renameOpenedTabsWith(File oldPath, File newPath) {
    for (Tab openTab : this.editor.getTabs()) {
      EditorTab tab = (EditorTab) openTab;
      if (tab.getPath() != null) {
        String absPath = tab.getPath().getAbsolutePath();
        if (absPath.startsWith(oldPath.getAbsolutePath())) {
          String basename = absPath.split(oldPath.getAbsolutePath())[1];
          tab.setPath(new File(newPath + File.separator + basename));
        }
      }
    }
  }

  /** Adds a new titled tab by creating a file. */
  private void addNewFile() {
    // get selected tree item
    TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog();
    String filename = dialog.showAndWait("Enter the path for the new file");
    // if user actually enters a filename
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
            if (path.createNewFile())
              // add titled tab with the new file
              this.addTitledTab(path);
            else
              Message.warning("could not create file: " + path);
          } catch (IOException e) {
            Message.warning("Could not create file: " + path);
          }
        }
      } else
        Message.warning("invalid file name: " + filename);
    }
  }

  /** Adds a new folder */
  private void addNewFolder() {
    // get selected tree item
    TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog();
    String dirname = dialog.showAndWait("Enter the path for the new folder");
    // if user actually enters a dir name
    if (dirname.length() > 0) {
      // create new path
      File path = new File(item.getPath() + File.separator + dirname);
      if (path.exists())
        Message.warning(String.format("directory %s already exists", dirname));
      else {
        // create all necessary folders
        if (path.mkdirs())
          DirWatcher.open(path);
        else
          Message.warning("could not create folder: " + path);
      }
    }
  }

  /** Renames a directory. */
  private void renameDir() {
    TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
    InputDialog dialog = new InputDialog();
    String dirname = dialog.showAndWait("Enter the path for the new folder");
    // if user actually enters a new name
    if (dirname.length() > 0) {
      File newPath = new File(item.getPath().getParent() + File.separator + dirname);
      File oldPath = item.getPath();
      // rename file and find a open tab
      if (!oldPath.equals(newPath) && !newPath.exists()) {
        newPath.mkdirs();
        if (item.getPath().renameTo(newPath)) {
          this.renameOpenedTabsWith(oldPath, newPath);
          DirWatcher.rename(oldPath, newPath);
        } else
          Message.warning(String.format("could not rename directory %s to %s", oldPath.toString(), newPath.toString()));
      }
      // dont do anything if the user uses the same path
      else if (oldPath.equals(newPath))
        return;
      else if (newPath.exists())
        Message.warning(String.format("directory %s already exists", newPath.getName()));
    }
  }

  /** Recursively deletes a selected directory. */
  private void deleteDir() {
    // only if user really wants to delete the directory
    DeleteDialog dialog = new DeleteDialog();
    if (dialog.showAndWait()) {
      TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
      if (!this.deleteDir(item.getPath()))
        Message.warning("could not delete directory " + item.getPath());
    }
  }

  /** Renames a file and renames open tab if there is an open tab with the same old path. */
  private void renameFile() {
    // get selected tree item
    TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
    // get user new path
    InputDialog dialog = new InputDialog();
    String filename = dialog.showAndWait("Enter the path for the new file");
    // if user actually enters a new name
    if (filename.length() > 0) {
      // create new path
      File newPath = new File(item.getPath().getParent() + File.separator + filename);
      // get old file path
      File oldPath = item.getPath();
      // rename file and find a open tab
      if (!oldPath.equals(newPath) && !newPath.exists()) {
        EditorTab tab = null;
        for (Tab openTab : this.editor.getTabs()) {
          EditorTab t = (EditorTab) openTab;
          if (t.getPath() != null && t.getPath().equals(oldPath)) {
            tab = t;
            break;
          }
        }
        // try to rename path
        if (item.getPath().renameTo(newPath))
          tab.setPath(newPath);
        else
          Message
              .warning(String.format("could not rename file %s to %s", tab.getPath().toString(), newPath.toString()));
      }
      // dont do anything if the user uses the same path
      else if (oldPath.equals(newPath))
        return;
      else if (newPath.exists())
        Message.warning(String.format("file %s already exists", newPath.getName()));
    }
  }

  /** Deletes a file and also closes an open tab if there is an open tab with the same path. */
  private void deleteFile() {
    // only if user really wants to delete the file
    DeleteDialog dialog = new DeleteDialog();
    boolean delete = dialog.showAndWait();
    if (delete) {
      // get selected tree item
      TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
      // find open tab (if any)
      EditorTab tab = null;
      for (Tab openTab : this.editor.getTabs()) {
        EditorTab t = (EditorTab) openTab;
        if (t.getPath() != null && t.getPath().equals(item.getPath())) {
          tab = t;
          break;
        }
      }
      // delete file
      boolean deleted = item.getPath().delete();
      if (!deleted)
        Message.warning("could not delete file: " + item.getPath());
      // only if file really was deleted
      if (tab != null && deleted)
        this.closeTab(tab);
    }
  }

  /*-------------------------------------------------------*
  |                       Inits                           |
  *-------------------------------------------------------*/

  /** Initializes editor tree view and context menus. */
  private void initTree() {
    // create tree directory context menu
    MenuItem newFile = new MenuItem("New File");
    newFile.setOnAction(e -> this.addNewFile());
    newFile.setGraphic(Icons.getImage("new_file"));
    MenuItem newFolder = new MenuItem("New Folder");
    newFolder.setOnAction(e -> this.addNewFolder());
    newFolder.setGraphic(Icons.getImage("new_folder"));
    MenuItem renameDir = new MenuItem("Rename");
    renameDir.setOnAction(e -> this.renameDir());
    renameDir.setGraphic(Icons.getImage("edit"));
    MenuItem deleteDir = new MenuItem("Delete");
    deleteDir.setOnAction(e -> this.deleteDir());
    deleteDir.setGraphic(Icons.getImage("trash"));
    this.dirContext = new ContextMenu();
    this.dirContext.getItems().addAll(newFile, newFolder, renameDir, deleteDir);
    // create tree file context menu
    MenuItem renameFile = new MenuItem("Rename");
    renameFile.setOnAction(e -> this.renameFile());
    renameFile.setGraphic(Icons.getImage("edit"));
    MenuItem deleteFile = new MenuItem("Delete");
    deleteFile.setOnAction(e -> this.deleteFile());
    deleteFile.setGraphic(Icons.getImage("trash"));
    this.fileContext = new ContextMenu();
    this.fileContext.getItems().addAll(renameFile, deleteFile);
    // add click action to tree
    this.tree.setOnMouseClicked(e -> {
      this.dirContext.hide();
      this.fileContext.hide();
      TreePath item = (TreePath) this.tree.getSelectionModel().getSelectedItem();
      if (item != null) {
        if (e.getButton() == MouseButton.PRIMARY && item.getPath().isFile())
          this.addTitledTab(item.getPath());
        else if (e.getButton() == MouseButton.SECONDARY)
          this.openContextMenu(item, e.getScreenX(), e.getScreenY());
      }
    });
  }
}
