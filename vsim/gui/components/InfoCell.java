package vsim.gui.components;

import vsim.linker.InfoStatement;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TableCell;


/**
 * This class represents an info cell for text table.
 */
public final class InfoCell extends TableCell<InfoStatement, String> {

  /**
   * Creates a new memory editing cell with a Tooltip.
   */
  public InfoCell() {
    super();
    Tooltip tt = new Tooltip();
    tt.textProperty().bind(this.itemProperty().asString());
    this.setTooltip(tt);
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
      this.setText(this.getItem() == null ? "" : this.getItem().toString());
      this.setGraphic(null);
    }
  }

}
