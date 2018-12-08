package vsim.gui.components;

import vsim.Globals;
import vsim.riscv.MemoryCell;
import javafx.scene.input.KeyCode;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableCell;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyCodeCombination;


/**
 * This class represents an editable table cell
 *
 */
public final class MemoryEditingCell extends TableCell<MemoryCell, String> {

  /** cell text field */
  private TextField textField;

  /**
   * Creates a new memory editing cell with a Tooltip.
   */
  public MemoryEditingCell() {
    super();
    Tooltip tt = new Tooltip();
    tt.textProperty().bind(this.itemProperty().asString());
    this.setTooltip(tt);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void startEdit() {
    MemoryCell cell = (MemoryCell)this.getTableRow().getItem();
    if (!isEmpty() && Globals.memory.checkAddress(cell.getIntAddress())) {
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
      this.setGraphic(null);
    } else {
      if (this.isEditing()) {
        if (this.textField != null) {
          this.textField.setText(this.getString());
        }
        this.setText(null);
        this.setGraphic(textField);
      } else {
        this.setText(this.getString());
        this.setGraphic(null);
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
