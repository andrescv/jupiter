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

import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import vsim.Globals;
import vsim.riscv.hardware.MemoryCell;


/**
 * This class represents an editable table cell
 *
 */
public final class MemoryEditingCell extends TableCell<MemoryCell, String> {

  /** cell text field */
  private TextField textField;

  /**
   * {@inheritDoc}
   */
  @Override
  public void startEdit() {
    MemoryCell cell = (MemoryCell) this.getTableRow().getItem();
    if (!this.isEmpty() && Globals.memory.checkAddress(cell.getIntAddress())) {
      super.startEdit();
      this.createTextField();
      this.setText(null);
      this.setGraphic(textField);
      this.textField.selectAll();
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void cancelEdit() {
    super.cancelEdit();
    this.setText(this.getItem().toString());
    this.setGraphic(null);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      this.setText(null);
      this.setTooltip(null);
      this.setGraphic(null);
    } else {
      if (this.isEditing()) {
        if (this.textField != null) {
          this.textField.setText(this.getString());
        }
        this.setText(null);
        this.setTooltip(null);
        this.setGraphic(textField);
      } else {
        this.setText(this.getString());
        this.setGraphic(null);
        this.setTooltip(new Tooltip(this.getString()));
      }
    }
  }

  /**
   * Creates a new text field for the table cell.
   */
  private void createTextField() {
    this.textField = new TextField(getString());
    this.textField.setMinWidth(this.getWidth() - this.getGraphicTextGap() * 2);
    this.textField.setOnKeyPressed(e -> {
      KeyCodeCombination ENTER = new KeyCodeCombination(KeyCode.ENTER);
      if (ENTER.match(e))
        this.commitEdit(textField.getText());
    });
  }

  /**
   * Gets the cell text value.
   *
   * @return cell text value
   */
  private String getString() {
    return this.getItem() == null ? "" : this.getItem().toString();
  }

}
