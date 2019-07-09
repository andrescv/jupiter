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
import java.nio.file.Path;
import java.util.ArrayList;

import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TreeView;
import javafx.scene.layout.VBox;

import com.jfoenix.controls.JFXTabPane;

import vsim.Logger;
import vsim.gui.Settings;
import vsim.gui.components.EditorTab;
import vsim.gui.dialogs.*;
import vsim.utils.FS;


/** V-Sim GUI editor controller. */
public final class Editor {

  /** main controller */
  private Main mainController;
  /** find and replace dialog */
  private FindReplaceDialog findReplaceDialog;
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
   * Initializes V-Sim's GUI editor controller.
   *
   * @param mainController main controller
   */
  protected void initialize(Main mainController) {
    this.mainController = mainController;
    mainController.stage.setOnCloseRequest(e -> {
      e.consume();
      quit();
    });
    newFile();
    updateStatusBar(getSelectedTab());
    Settings.USER_DIR.addListener((e, o, n) -> updateStatusBar(getSelectedTab()));
    editorTabPane.getSelectionModel().selectedItemProperty().addListener((e, o, n) -> updateStatusBar((EditorTab) n));
    findReplaceDialog = new FindReplaceDialog(this);
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
   * @return all files to assemble.
   */
  protected ArrayList<File> getFiles() {
    ArrayList<File> files = new ArrayList<>();
    if (Settings.ASSEMBLE_ONLY_OPEN.get()) {
      for (Tab openTab : editorTabPane.getTabs()) {
        files.add(((EditorTab) openTab).getFile());
      }
    } else if (Settings.ASSEMBLE_ALL.get()) {
      try {
        files = FS.ls(FS.toFile(Settings.USER_DIR.get()));
      } catch (IOException e) {
        Logger.warning("could not get files in dir: " + Settings.USER_DIR.get());
      }
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

  /** Opnes a file. */
  protected void openFile() {
    mainController.editor();
    FileDialog dialog = mainController.fileDialog();
    File file = dialog.open("Open RISC-V File");
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

  /** Saves current selected tab. */
  protected void save() {
    saveTab(getSelectedTab(), false);
  }

  /** Saves as current selected tab. */
  protected void saveAs() {
    EditorTab tab = getSelectedTab();
    FileDialog chooser = mainController.fileDialog();
    File file = chooser.save("Save RISC-V File As...", tab.getName());
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

  /** Quits V-Sim GUI application. */
  protected void quit() {
    mainController.editor();
    boolean save = false;
    for (Tab openTab : editorTabPane.getTabs()) {
      save |= ((EditorTab) openTab).modified();
    }
    if (save) {
      CloseDialog dialog = mainController.closeDialog();
      switch (dialog.get()) {
        case 0:
          (new ArrayList<Tab>(editorTabPane.getTabs())).forEach(e -> closeTab((EditorTab) e));
          mainController.stage.close();
        case 1:
          ArrayList<Tab> tabs = new ArrayList<>(editorTabPane.getTabs());
          for (Tab openTab : tabs) {
            EditorTab tab = (EditorTab) openTab;
            saveTab(tab, true);
            if (!tab.closed()) {
              return;
            }
          }
          mainController.stage.close();
        default:
          break;
      }
    } else {
      mainController.stage.close();
    }
  }

  /** Opens find/replace dialog */
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
          FileDialog dialog = mainController.fileDialog();
          File file = dialog.save("Save RISC-V File", tab.getName());
          if (file != null) {
            tab.setPath(file);
          } else {
            return;
          }
        }
        tab.save();
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
        SaveDialog dialog = mainController.saveDialog();
        switch (dialog.get(tab.getName())) {
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
    } else if (tab == null && editorTabPane.getTabs().size() == 0) {
      mainController.stage.close();
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

}
