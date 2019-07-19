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

package jvpiter.gui.dialogs;

import java.util.ArrayList;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;

import jvpiter.gui.components.EditorTab;
import jvpiter.gui.controllers.Editor;


/** Jvpiter GUI find/replace dialog. */
public final class FindReplaceDialog extends AnchorPane {

  /** find label */
  private final Label label;
  /** find text field */
  private final JFXTextField findText;
  /** replace text field */
  private final JFXTextField replaceText;
  /** case sensitive checkbox */
  private final JFXCheckBox caseSensitive;
  /** current editor tab */
  private EditorTab current;
  /** found indexes */
  private ArrayList<Integer> found;
  /** editor controller */
  private final Editor controller;

  /**
   * Creates a new find and replace dialog.
   *
   * @param controller editor controller
   */
  public FindReplaceDialog(Editor controller) {
    super();
    //
    this.controller = controller;
    // found indexes
    found = new ArrayList<Integer>();
    // create anchor pane
    setMinHeight(130);
    setMaxHeight(130);
    setPrefHeight(130);
    getStyleClass().add("findreplace-anchor-pane");
    // create horizontal box
    HBox box = new HBox();
    box.setAlignment(Pos.CENTER);
    box.setSpacing(5.0);
    setRightAnchor(box, 5.0);
    setTopAnchor(box, 5.0);
    // create buttons
    JFXButton find = new JFXButton("Find");
    find.getStyleClass().add("findreplace");
    JFXButton replace = new JFXButton("Replace");
    replace.getStyleClass().add("findreplace");
    JFXButton replaceAll = new JFXButton("Replace All");
    replaceAll.getStyleClass().add("findreplace");
    JFXButton close = new JFXButton("x");
    close.getStyleClass().add("close");
    caseSensitive = new JFXCheckBox("Aa");
    caseSensitive.getStyleClass().add("findreplace");
    box.getChildren().addAll(find, replaceAll, caseSensitive, close);
    // create label
    label = new Label("Find And Replace");
    label.getStyleClass().add("findreplace");
    setLeftAnchor(label, 5.0);
    setTopAnchor(label, 5.0);
    // create text fields
    findText = new JFXTextField();
    findText.setPromptText("Find in current buffer");
    findText.getStyleClass().add("findreplace");
    setRightAnchor(findText, 10.0);
    setLeftAnchor(findText, 10.0);
    setTopAnchor(findText, 45.0);
    replaceText = new JFXTextField();
    replaceText.setPromptText("Replace in current buffer");
    replaceText.getStyleClass().add("findreplace");
    setRightAnchor(replaceText, 10.0);
    setLeftAnchor(replaceText, 10.0);
    setTopAnchor(replaceText, 90.0);
    getChildren().addAll(label, findText, replaceText, box);
    // bindings
    find.setOnAction(e -> find());
    replace.setOnAction(e -> replace());
    replaceAll.setOnAction(e -> replaceAll());
    close.setOnAction(e -> controller.removeFindReplaceDialog());
    // perform a search if find text field change
    findText.textProperty().addListener((e, o, n) -> search());
    // perform a search if case sensitive checkbox selected property changes
    caseSensitive.selectedProperty().addListener((e, o, n) -> search());
    // perform a search if the current tab changes
    controller.getEditorTabPane().getSelectionModel().selectedItemProperty().addListener((e, o, n) -> search());
  }

  /** Focus find text field. */
  public void focus() {
    findText.requestFocus();
  }

  /**
   * Sets find text field value.
   *
   * @param text text field new value
   */
  public void setFindText(String text) {
    findText.setText(text);
  }

  /** Searches match indexes. */
  private synchronized void search() {
    EditorTab tab = controller.getSelectedTab();
    // get find text
    if (tab != null && findText.getText().length() > 0) {
      // save current tab
      current = tab;
      // clear indexes
      found.clear();
      // get editor and find text
      String find = findText.getText();
      String text = tab.getTextEditor().getText();
      // fix for case insensitive
      if (!caseSensitive.isSelected()) {
        find = find.toLowerCase();
        text = text.toLowerCase();
      }
      // search al indexes
      int lastIndex = 0;
      while (lastIndex != -1) {
        lastIndex = text.indexOf(find, lastIndex);
        if (lastIndex != -1) {
          found.add(lastIndex);
          lastIndex += find.length();
        }
      }
      // update results
      setSearchLabel(false, false);
    } else {
      // remove selection
      if (current != null) {
        current.getTextEditor().deselect();
      }
      // clear indexes
      current = null;
      found.clear();
      // update results
      setSearchLabel(false, true);
    }
  }

  /**
   * Returns closest match index.
   *
   * @return closest match index
   */
  private int getSearchIndex() {
    if (current != null && found.size() > 0) {
      // use index 0 as default
      int index = 0;
      int minDiff = Integer.MAX_VALUE;
      int caretPos = current.getTextEditor().getCaretPosition();
      int counter = 0;
      for (Integer i : found) {
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

  /**
   * Sets search label text.
   *
   * @param index display index if {@code true}
   * @param title display default title if {@code true}
   */
  private void setSearchLabel(boolean index, boolean title) {
    int size = found.size();
    if (size > 0 && !title) {
      if (index) {
        String fmt = "Find And Replace (%d results) (%d of %d)";
        label.setText(String.format(fmt, size, getSearchIndex() + 1, size));
      } else {
        String fmt = "Find And Replace (%d results)";
        label.setText(String.format(fmt, size));
      }
    } else if (title) {
      label.setText("Find And Replace");
    } else {
      label.setText("Find And Replace (no results)");
    }
  }

  /** Selects the next found match. */
  private synchronized void find() {
    if (current != null && found.size() > 0) {
      setSearchLabel(true, false);
      int start = found.get(getSearchIndex());
      int end = start + findText.getText().length();
      if (start >= 0 && end < current.getTextEditor().getText().length()) {
        current.getTextEditor().moveTo(start);
        current.getTextEditor().requestFollowCaret();
        current.getTextEditor().selectRange(start, end);
      }
    }
  }

  /** Replaces the current selected match. */
  private synchronized void replace() {
    if (current != null && found.size() > 0) {
      // find if no selected text
      if (!(current.getTextEditor().getSelectedText().length() > 0)) {
        find();
      }
      // replace selected text
      current.getTextEditor().replaceSelection(replaceText.getText());
      // because indexes change
      search();
      // select next match
      find();
    }
  }

  /** Replaces all matches. */
  private synchronized void replaceAll() {
    if (current != null && found.size() > 0) {
      while (found.size() > 0) {
        // always get the head of the array
        int start = found.remove(0);
        int end = start + findText.getText().length();
        // replace selected text
        current.getTextEditor().moveTo(start);
        current.getTextEditor().requestFollowCaret();
        current.getTextEditor().selectRange(start, end);
        current.getTextEditor().replaceSelection(replaceText.getText());
        // refresh indexes
        search();
      }
    }
  }

}
