package vsim.gui.components;

import vsim.linker.InfoStatement;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableCell;


/**
 * This class represents an info cell for text table.
 */
public final class InfoCell extends TableCell<InfoStatement, String> {

  /**
   * {@inheritDoc}
   */
  @Override
  public void updateItem(String item, boolean empty) {
    super.updateItem(item, empty);
    if (empty) {
      this.setText("");
      this.setGraphic(null);
    } else {
      this.setText(this.getItem() == null ? "" : this.getItem().toString());
      this.setGraphic(null);
      if (this.getTooltip() == null) {
        Tooltip tt = new Tooltip(this.getText());
        this.setTooltip(tt);
      }
    }
  }

}
