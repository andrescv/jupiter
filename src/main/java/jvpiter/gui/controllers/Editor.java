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

package jvpiter.gui.controllers;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Hashtable;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXTabPane;

import jvpiter.Logger;
import jvpiter.gui.DirWatcher;
import jvpiter.gui.Icons;
import jvpiter.gui.Settings;
import jvpiter.gui.components.EditorTab;
import jvpiter.gui.components.TreeFileItem;
import jvpiter.gui.dialogs.FindReplaceDialog;
import jvpiter.utils.FS;


/** Jvpiter GUI editor controller. */
public final class Editor implements PropertyChangeListener {

  /** Expanded tab lookup */
  private static final Hashtable<String, Boolean> EXPANDED = new Hashtable<String, Boolean>();

  /** main controller */
  private Main mainController;
  /** Directory watcher */
  private DirWatcher watcher;
  /** find and replace dialog */
  private FindReplaceDialog findReplaceDialog;

  /** file context menu */
  private ContextMenu fileContext;
  /** directory context menu */
  private ContextMenu dirContext;

  /** editor tab pane */
  @FXML private JFXTabPane editorTabPane;
  /** directory label */
  @FXML private Label file;
  /** line and col label */
  @FXML private Label lineAndCol;
  /** editor vbox */
  @FXML private VBox editorVBox;
  /** editor tree view */
  @FXML private TreeView<String> tree;

  /**
   * Initializes Jvpiter's GUI editor controller.
   *
   * @param mainController main controller
   */
  protected void initialize(Main mainController) {
    this.mainController = mainController;
    newFile();
    updateStatusBar(getSelectedTab());
    Settings.USER_DIR.addListener((e, o, n) -> updateStatusBar(getSelectedTab()));
    editorTabPane.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> updateStatusBar((EditorTab) n));
    findReplaceDialog = new FindReplaceDialog(this);
    initTree();
  }

  /** Removes find and replace dialog. */
  public void removeFindReplaceDialog() {
    if (editorVBox.getChildren().contains(findReplaceDialog)) {
      editorVBox.getChildren().remove(1);
    }
  }

  /**
   * Returns editor current selected tab.
   *
   * @return editor current selected tab
   */
  public EditorTab getSelectedTab() {
    return (EditorTab) editorTabPane.getSelectionModel().getSelectedItem();
  }

  /**
   * Returns editor tab pane.
   *
   * @return editor tab pane
   */
  public JFXTabPane getEditorTabPane() {
    return editorTabPane;
  }

  /**
   * Determines if all open tabs are saved.
   *
   * @return {@code true} if all tabs are saved, {@code false} otherwise
   */
  protected boolean allSaved() {
    if (Settings.ASSEMBLE_ONLY_OPEN.get()) {
      for (Tab openTab : editorTabPane.getTabs()) {
        EditorTab tab = (EditorTab) openTab;
        if (tab.untitled() || tab.modified() || !tab.getFile().exists()) {
          return false;
        }
      }
      return true;
    } else if (Settings.ASSEMBLE_ALL.get()) {
      for (Tab openTab : editorTabPane.getTabs()) {
        EditorTab tab = (EditorTab) openTab;
        if (!tab.untitled() && tab.getFile().exists()) {
          String p = tab.getFile().getAbsolutePath();
          String d = Settings.USER_DIR.get();
          if (p.startsWith(d) && tab.modified()) {
            return false;
          }
        }
      }
      return true;
    } else {
      EditorTab tab = getSelectedTab();
      if (tab == null) {
        return true;
      } if (tab.untitled() || tab.modified() || !tab.getFile().exists()) {
        return false;
      } else {
        return true;
      }
    }
  }

  /**
   * Returns all files to assemble.
   *
   * @return all files to assemble
   */
  protected ArrayList<File> getFiles() {
    ArrayList<File> files = new ArrayList<>();
    if (Settings.ASSEMBLE_ONLY_OPEN.get()) {
      for (Tab openTab : editorTabPane.getTabs()) {
        files.add(((EditorTab) openTab).getFile());
      }
    } else if (Settings.ASSEMBLE_ALL.get()) {
      files = FS.ls(FS.toFile(Settings.USER_DIR.get()));
    } else {
      EditorTab tab = getSelectedTab();
      if (tab != null) {
        files.add(tab.getFile());
      }
    }
    files.trimToSize();
    return files;
  }

  /** Adds a new untitled file. */
  protected void newFile() {
    mainController.editor();
    EditorTab tab = new EditorTab();
    tab.setOnCloseRequest(e -> {
      e.consume();
      closeTabSafetly(tab);
    });
    editorTabPane.getTabs().add(tab);
    editorTabPane.getSelectionModel().select(tab);
  }

  /** Opens a file. */
  protected void openFile() {
    mainController.editor();
    File file = mainController.fileDialog().open("Open RISC-V File");
    open(file);
  }

  /** Changes project folder */
  protected void changeProjectFolder() {
    mainController.editor();
    File directory = mainController.directoryDialog().open("Change Project Folder");
    if (directory != null) {
      Settings.setUserDir(directory);
      EXPANDED.clear();
      Platform.runLater(() -> updateTree());
      DirWatcher.start(this);
    }
  }

  /** Saves current selected tab. */
  protected void save() {
    saveTab(getSelectedTab(), false);
  }

  /** Saves as current selected tab. */
  protected void saveAs() {
    EditorTab tab = getSelectedTab();
    File file = mainController.fileDialog().save("Save RISC-V File As...", tab.getName());
    if (file != null) {
      File old = tab.getFile();
      try {
        tab.setPath(file);
        tab.save();
      } catch (IOException e) {
        tab.setPath(old);
        Logger.warning("could not save file: " + file);
      }
    }
  }

  /** Saves all tabs. */
  protected void saveAll() {
    for (Tab openTab : editorTabPane.getTabs()) {
      EditorTab tab = (EditorTab) openTab;
      saveTab(tab, false);
    }
  }

  /** Closes current selected tab. */
  protected void close() {
    closeTabSafetly(getSelectedTab());
  }

  /** Closes all tabs. */
  protected void closeAll() {
    ArrayList<Tab> tabs = new ArrayList<>(editorTabPane.getTabs());
    for (Tab openTab : tabs) {
      EditorTab tab = (EditorTab) openTab;
      closeTabSafetly(tab);
      if (!tab.closed()) {
        return;
      }
    }
  }

  /** Quits Jvpiter GUI application. */
  protected void quit() {
    mainController.editor();
    boolean save = false;
    for (Tab openTab : editorTabPane.getTabs()) {
      save |= ((EditorTab) openTab).modified();
    }
    if (save) {
      switch (mainController.closeDialog().get()) {
        case 0:
          (new ArrayList<Tab>(editorTabPane.getTabs())).forEach(e -> closeTab((EditorTab) e));
          mainController.closeStage();
        case 1:
          ArrayList<Tab> tabs = new ArrayList<>(editorTabPane.getTabs());
          for (Tab openTab : tabs) {
            EditorTab tab = (EditorTab) openTab;
            saveTab(tab, true);
            if (!tab.closed()) {
              return;
            }
          }
          mainController.closeStage();
        default:
          break;
      }
    } else {
      mainController.closeStage();
    }
  }

  /** Opens find/replace dialog. */
  protected void findReplace() {
    if (!editorVBox.getChildren().contains(findReplaceDialog)) {
      editorVBox.getChildren().add(1, findReplaceDialog);
    }
    findReplaceDialog.focus();
    EditorTab tab = getSelectedTab();
    if (tab != null && tab.getTextEditor().getSelectedText().length() > 0) {
      findReplaceDialog.setFindText(tab.getTextEditor().getSelectedText());
    }
  }

  /** Undo editor action. */
  protected void undo() {
    getSelectedTab().getTextEditor().undo();
  }

  /** Redo editor action. */
  protected void redo() {
    getSelectedTab().getTextEditor().redo();
  }

  /** Cut editor action. */
  protected void cut() {
    getSelectedTab().getTextEditor().cut();
  }

  /** Copy editor action. */
  protected void copy() {
    getSelectedTab().getTextEditor().copy();
  }

  /** Paste editor action. */
  protected void paste() {
    getSelectedTab().getTextEditor().paste();
  }

  /** Select all editor action. */
  protected void selectAll() {
    getSelectedTab().getTextEditor().selectAll();
  }

  /**
   * Opens a file.
   *
   * @param file file to open
   */
  private void open(File file) {
    if (file != null) {
      try {
        EditorTab tab = null;
        EditorTab reuse = null;
        // search in all current tabs
        for (Tab openTab : editorTabPane.getTabs()) {
          EditorTab t = (EditorTab) openTab;
          // a tab pointing to this file already exits
          if (t.getFile() != null && FS.equals(t.getFile(), file)) {
            tab = t;
            break;
          }
          // reuse some untitled tab with no changes
          else if (t.untitled() && !t.modified() && reuse == null)
            reuse = t;
        }
        // if tab was not already open
        if (tab == null) {
          // if a untitled tab can be reused
          if (reuse != null) {
            tab = reuse;
            tab.open(file);
          } else {
            EditorTab newTab = new EditorTab(file);
            tab = newTab;
            tab.setOnCloseRequest(e -> {
              e.consume();
              closeTabSafetly(newTab);
            });
            editorTabPane.getTabs().add(tab);
          }
        }
        editorTabPane.getSelectionModel().select(tab);
      } catch (IOException e) {
        Logger.warning("could not open file: " + file);
      }
    }
  }

  /**
   * Closes a tab and removes it from current tabs.
   *
   * @param tab tab to close
   */
  private void closeTab(EditorTab tab) {
    tab.close();
    editorTabPane.getTabs().remove(tab);
  }

  /**
   * Saves a tab.
   *
   * @param tab tab to save
   * @param close after save, close the tab also if {@code true}
   */
  private void saveTab(EditorTab tab, boolean close) {
    if (tab != null) {
      try {
        if (tab.untitled()) {
          File file = mainController.fileDialog().save("Save RISC-V File", tab.getName());
          if (file != null) {
            tab.save(file);
          } else {
            return;
          }
        } else {
          tab.save();
        }
        if (close) {
          closeTab(tab);
        }
      } catch (IOException e) {
        Logger.warning("could not save file: " + tab.getFile());
      }
    }
  }

  /**
   * Closes a tab safetly and removes it from current tabs.
   *
   * @param tab tab to close
   */
  private void closeTabSafetly(EditorTab tab) {
    if (tab != null) {
      if (tab.modified()) {
        switch (mainController.saveDialog().get(tab.getName())) {
          // don't save
          case 0:
            closeTab(tab);
            break;
          // save
          case 1:
            saveTab(tab, true);
            break;
          // cancel
          default:
            break;
        }
      } else {
        closeTab(tab);
      }
    }
  }

  /**
   * Updates status bar.
   *
   * @param tab current selected tab
   */
  private void updateStatusBar(EditorTab tab) {
    if (tab != null) {
      file.textProperty().bind(Bindings.createStringBinding(
        () -> {
          Path dir = FS.toPath(Settings.USER_DIR.get());
          File file = tab.getFile();
          if (file != null) {
            return dir.relativize(file.toPath()).toString();
          }
          return tab.getName();
        },
        tab.textProperty()
      ));
      lineAndCol.textProperty().bind(Bindings.createStringBinding(
        () -> {
          int l = tab.getTextEditor().getCurrentParagraph() + 1;
          int c = tab.getTextEditor().getCaretColumn() + 1;
          return String.format("%d:%d", l, c);
        },
        tab.getTextEditor().caretPositionProperty()
      ));
      file.setVisible(true);
      lineAndCol.setVisible(true);
    } else {
      file.setVisible(false);
      lineAndCol.setVisible(false);
      file.textProperty().unbind();
      lineAndCol.textProperty().unbind();
    }
  }

  /** Adds a new file from tree view. */
  private void addFile() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    File file = mainController.pathDialog().get("Enter the path for the new file", item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        if (file != null) {
          String name = file.getName();
          if (name.endsWith(".s") || name.endsWith(".asm")) {
            if (file.exists()) {
              Logger.warning("file " + file + " already exists");
            } else {
              try {
                FS.createDirectory(FS.toFile(file.getParent()));
                FS.createFile(file);
              } catch (IOException e) {
                Logger.warning("could not create file: " + file);
              }
            }
          } else {
            Logger.warning("invalid file: " + file);
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Adds a new directory from tree view. */
  private void addFolder() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    File directory = mainController.pathDialog().get("Enter the path for the new folder", item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        if (directory != null) {
          if (directory.exists()) {
            Logger.warning("folder " + file + " already exists");
          } else {
            try {
              FS.createDirectory(directory);
            } catch (IOException e) {
              Logger.warning("could not create folder: " + file);
            }
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Renames a file from tree view. */
  private void renameFile() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    File file = mainController.pathDialog().get("Enter the path for the new file", item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        if (file != null) {
          File oldFile = item.getFile();
          if (!FS.equals(file, oldFile) && !file.exists()) {
            EditorTab tab = null;
            for (Tab openTab : editorTabPane.getTabs()) {
              EditorTab t = (EditorTab) openTab;
              if (!t.untitled() && FS.equals(t.getFile(), oldFile)) {
                tab = t;
                break;
              }
            }
            final EditorTab t = tab;
            try {
              if (t != null) {
                Platform.runLater(() -> t.setPath(file));
              }
              FS.moveFile(item.getFile(), file);
            } catch (IOException e) {
              if (t != null) {
                Platform.runLater(() -> t.setPath(oldFile));
              }
              Logger.warning("could not rename file " + oldFile + " to " + file);
            }
          } else if (FS.equals(file, oldFile)) {
            return null;
          } else if (file.exists()) {
            Logger.warning("file " + file + " already exists");
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Renames a folder from tree view. */
  private void renameFolder() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    File file = mainController.pathDialog().get("Enter the path for the new file", item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        if (file != null) {
          ArrayList<File> files = FS.ls(item.getFile());
          final ArrayList<File> oldFiles = new ArrayList<>();
          final ArrayList<EditorTab> tabs = new ArrayList<>();
          for (Tab openTab : editorTabPane.getTabs()) {
            final EditorTab t = (EditorTab) openTab;
            if (!t.untitled() && FS.contains(t.getFile(), files)) {
              oldFiles.add(t.getFile());
              tabs.add(t);
              String abs = t.getFile().getAbsolutePath().substring(item.getFile().getAbsolutePath().length());
              Platform.runLater(() -> t.setPath(Paths.get(file.getAbsolutePath(), abs).toFile()));
            }
          }
          try {
            FS.moveDirectory(item.getFile(), file);
          } catch (IOException e) {
            for (int i = 0; i < tabs.size(); i++) {
              final int index = i;
              Platform.runLater(() -> tabs.get(index).setPath(oldFiles.get(index)));
            }
            Logger.warning("could not rename directory "  + item.getFile() + " to " + file);
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Deletes a file from tree view. */
  private void deleteFile() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    boolean delete = mainController.deleteDialog().get(item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        // only if user really wants to delete the file
        if (delete) {
          try {
            FS.deleteFile(item.getFile());
          } catch (IOException e) {
            Logger.warning("could not delete file: " + item.getFile());
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Deletes a directory from tree view. */
  private void deleteFolder() {
    TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
    boolean delete = mainController.deleteDialog().get(item.getFile());
    Thread th = new Thread(new Task<Void>() {
      /** {@inheritDoc} */
      public Void call() {
        // only if user really wants to delete the file
        if (delete) {
          try {
            FS.deleteDirectory(item.getFile());
          } catch (IOException e) {
            Logger.warning("could not delete folder: " + item.getFile());
          }
        }
        return null;
      }
    });
    th.setDaemon(true);
    th.start();
  }

  /** Initializes editor tree view and context menus. */
  private void initTree() {
    // create tree directory context menu
    MenuItem newFile = new MenuItem("New File");
    newFile.setGraphic(Icons.get("new_file"));
    newFile.setOnAction(e -> addFile());
    MenuItem newFolder = new MenuItem("New Folder");
    newFolder.setGraphic(Icons.get("new_folder"));
    newFolder.setOnAction(e -> addFolder());
    MenuItem renameDir = new MenuItem("Rename");
    renameDir.setGraphic(Icons.get("edit"));
    renameDir.setOnAction(e -> renameFolder());
    MenuItem deleteDir = new MenuItem("Delete");
    deleteDir.setGraphic(Icons.get("trash"));
    deleteDir.setOnAction(e -> deleteFolder());
    dirContext = new ContextMenu();
    dirContext.getItems().addAll(newFile, newFolder, renameDir, deleteDir);
    // create tree file context menu
    MenuItem renameFile = new MenuItem("Rename");
    renameFile.setGraphic(Icons.get("edit"));
    renameFile.setOnAction(e -> renameFile());
    MenuItem deleteFile = new MenuItem("Delete");
    deleteFile.setGraphic(Icons.get("trash"));
    deleteFile.setOnAction(e -> deleteFile());
    fileContext = new ContextMenu();
    fileContext.getItems().addAll(renameFile, deleteFile);
    // add click action to tree
    tree.setOnMouseClicked(e -> {
      dirContext.hide();
      fileContext.hide();
      TreeFileItem item = (TreeFileItem) tree.getSelectionModel().getSelectedItem();
      if (item != null && e.getButton() == MouseButton.PRIMARY && item.getFile().isFile()) {
        tree.getSelectionModel().clearSelection();
        open(item.getFile());
      } else if (item != null && e.getButton() == MouseButton.SECONDARY) {
        File file = item.getFile();
        if (file.isDirectory()) {
          dirContext.show(tree, e.getScreenX(), e.getScreenY());
        } else {
          fileContext.show(tree, e.getScreenX(), e.getScreenY());
        }
      }
    });
    Platform.runLater(() -> updateTree());
    DirWatcher.start(this);
  }

  /** Updates tree root. */
  private void updateTree() {
    Platform.runLater(() -> {
      tree.setRoot(new TreeFileItem(FS.toFile(Settings.USER_DIR.get()), true, EXPANDED));
    });
  }

  private void onDirectoryCreate(File file) {
    updateTree();
  }

  private void onDirectoryDelete(File file) {
    updateTree();
    EXPANDED.remove(file.getAbsolutePath());
  }

  private void onFileChange(File file) {
    // code for processing change event
    updateTree();
    for (Tab openTab : editorTabPane.getTabs()) {
      EditorTab tab = (EditorTab) openTab;
      if (!tab.untitled() && FS.equals(file, tab.getFile())) {
        Platform.runLater(() -> tab.externalModify());
        return;
      }
    }
  }

  private void onFileCreate(File file) {
    updateTree();
  }

  private void onFileDelete(File file) {
    updateTree();
    for (Tab openTab : editorTabPane.getTabs()) {
      EditorTab tab = (EditorTab) openTab;
      if (!tab.untitled() && FS.equals(file, tab.getFile())) {
        Platform.runLater(() -> tab.externalDelete());
        return;
      }
    }
  }

  /** {@inheritDoc} */
  @Override
  public void propertyChange(PropertyChangeEvent e) {
    switch (e.getPropertyName()) {
      case "create_dir":
        onDirectoryCreate((File) e.getNewValue());
        break;
      case "create_file":
        onFileCreate((File) e.getNewValue());
        break;
      case "delete_dir":
        onDirectoryDelete((File) e.getNewValue());
        break;
      case "delete_file":
        onFileDelete((File) e.getNewValue());
        break;
      case "modify_file":
        onFileChange((File) e.getNewValue());
        break;
      default:
        updateTree();
        break;
    }
  }

}
