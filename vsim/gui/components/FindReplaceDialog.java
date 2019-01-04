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

package vsim.gui.components;

import java.io.IOException;
import java.util.ArrayList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXTextField;
import vsim.gui.controllers.EditorController;
import vsim.gui.utils.Icons;


/** This class represents a Find and Replace dialog. */
public final class FindReplaceDialog {

  /** Dialog stage */
  private final Stage stage;
  /** Current selected tab */
  private EditorTab current;
  /** Current search indexes */
  private ArrayList<Integer> found;
  /** Editor controller */
  private final EditorController controller;
  /** Dialog find button */
  @FXML private JFXButton find;
  /** Dialog replace button */
  @FXML private JFXButton replace;
  /** Dialog replace all button */
  @FXML private JFXButton replaceAll;
  /** Dialog case sensitive switch */
  @FXML private JFXCheckBox caseSensitive;
  /** Dialog replace text field */
  @FXML private JFXTextField findText;
  /** Dialog find text field */
  @FXML private JFXTextField replaceText;

  public FindReplaceDialog(EditorController controller) {
    try {
      this.stage = new Stage();
      this.controller = controller;
      this.found = new ArrayList<Integer>();
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/FindReplaceDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 479, 198));
      // add actions
      this.find.setOnAction(e -> this.find());
      this.replace.setOnAction(e -> this.replace());
      this.replaceAll.setOnAction(e -> this.replaceAll());
      // perform a search if find text field change
      this.findText.textProperty().addListener((e, oldVal, newVal) -> this.search());
      // perform a search if case sensitive checkbox selected property changes
      this.caseSensitive.selectedProperty().addListener((e, oldVal, newVal) -> this.search());
      // and also perform a search if selected tab changes
      this.controller.getEditor().getSelectionModel().selectedItemProperty()
          .addListener((e, oldVal, newVal) -> this.search());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /** Searches all indexes in editor text that matches find text field. */
  private void search() {
    // get selected tab
    EditorTab tab = this.controller.getSelectedTab();
    if (this.findText.getText().length() > 0 && tab != null) {
      // save selected tab
      this.current = tab;
      // clear previous content
      this.found.clear();
      String find = this.caseSensitive.isSelected() ? this.findText.getText() : this.findText.getText().toLowerCase();
      String text = this.caseSensitive.isSelected() ? tab.getEditorText() : tab.getEditorText().toLowerCase();
      int lastIndex = 0;
      // search all indexes
      while (lastIndex != -1) {
        lastIndex = text.indexOf(find, lastIndex);
        if (lastIndex != -1) {
          this.found.add(lastIndex);
          lastIndex += find.length();
        }
      }
      // update stage title
      this.setSearchTitle(false, false);
    } else {
      // remove any selection in editor
      if (current != null)
        this.current.deselect();
      // cleanup all state
      this.current = null;
      this.found.clear();
      // update stage title with default title
      this.setSearchTitle(false, true);
    }
  }

  /**
   * Finds the next index to look at in found array list.
   *
   * @return next index closest to editor caret position, -1 if no matches or no tab is selected.
   */
  private int getSearchIndex() {
    if (this.current != null && this.found.size() > 0) {
      // use index 0 as default
      int index = 0;
      int minDiff = Integer.MAX_VALUE;
      int caretPos = this.current.getEditorCaretPosition();
      int counter = 0;
      for (Integer i : this.found) {
        int diff = i - caretPos;
        // use the index that is closest to editor caret position
        if (diff >= 0 && diff <= minDiff) {
          index = counter;
          minDiff = diff;
        }
        counter++;
      }
      return index;
    }
    return -1;
  }

  /** Selects the next found match. */
  private void find() {
    if (this.current != null && this.found.size() > 0) {
      int result = this.found.get(this.getSearchIndex());
      this.setSearchTitle(true, false);
      this.current.select(result, result + this.findText.getText().length());
    }
  }

  /** Replaces the current selected match. */
  private void replace() {
    if (this.current != null && this.found.size() > 0) {
      // do a find if no selected text, otherwise replace will not take effect
      if (!this.current.isEditorTextSelected())
        this.find();
      this.current.replace(this.replaceText.getText());
      // because indexes change, need to do a search again
      this.search();
      // select next match
      this.find();
    }
  }

  /** Replaces all matches. */
  private void replaceAll() {
    if (this.current != null && this.found.size() > 0) {
      // just loop until no more matches
      while (this.found.size() > 0) {
        // always get the head of the array (ascending mode)
        int index = this.found.remove(0);
        // replace match
        this.current.replace(index, index + this.findText.getText().length(), this.replaceText.getText());
        // perform a search to refresh indexes
        this.search();
      }
    }
  }

  /**
   * Sets stage title with search info.
   *
   * @param dispIndex display current search index
   */
  private void setSearchTitle(boolean dispIndex, boolean defaultTitle) {
    int size = this.found.size();
    if (size > 0 && !defaultTitle) {
      if (dispIndex)
        this.stage
            .setTitle(String.format("Find And Replace (%d results) (%d of %d)", size, this.getSearchIndex() + 1, size));
      else
        this.stage.setTitle(String.format("Find And Replace (%d results)", size));
    } else {
      if (defaultTitle)
        this.stage.setTitle("Find And Replace");
      else
        this.stage.setTitle("Find And Replace (no results)");
    }
  }

  /** Shows dialog and performs initial search if possible. */
  public void show() {
    this.search();
    this.stage.show();
    this.findText.requestFocus();
    this.stage.toFront();
  }

  /**
   * If dialog is currently showing.
   *
   * @return true if dialog is showing, false otherwise
   */
  public boolean isShowing() {
    return this.stage.isShowing();
  }
}
