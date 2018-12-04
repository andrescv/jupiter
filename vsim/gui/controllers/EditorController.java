package vsim.gui.controllers;

import java.io.File;
import javafx.fxml.FXML;
import vsim.utils.Message;
import java.io.IOException;
import java.util.ArrayList;
import javafx.scene.control.Tab;
import javafx.stage.FileChooser;
import vsim.gui.components.EditorTab;
import javafx.scene.control.TreeView;
import vsim.gui.components.SaveDialog;
import vsim.gui.components.CloseDialog;
import com.jfoenix.controls.JFXTabPane;
import javafx.stage.FileChooser.ExtensionFilter;


/**
 * Editor controller class.
 */
public class EditorController {

  /** Current directory tree view */
  @FXML protected TreeView<String> tree;
  /** Editor tab pane */
  @FXML protected JFXTabPane editor;

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

}
