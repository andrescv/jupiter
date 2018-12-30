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
    if (empty || item == null) {
      this.setText("");
      this.setGraphic(null);
    } else {
      this.setText(item);
      this.setGraphic(null);
      this.setTooltip(new Tooltip(this.getText()));
    }
  }

}
