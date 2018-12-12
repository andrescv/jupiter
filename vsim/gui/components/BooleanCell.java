package vsim.gui.components;

import javafx.css.PseudoClass;
import vsim.linker.InfoStatement;
import javafx.scene.control.TableRow;
import javafx.scene.control.cell.CheckBoxTableCell;


/**
 * This class represents an editable checkbox table cell.
 */
public final class BooleanCell extends CheckBoxTableCell<InfoStatement, Boolean> {

  /**
   * Creates a boolean editable cell.
   */
  public BooleanCell() {
    super();
    PseudoClass nonEditable = PseudoClass.getPseudoClass("non-editable");
    this.itemProperty().addListener((e, oldVal, newVal) -> {
      if (this.getTableRow() == null) {
        this.setEditable(false);
        this.pseudoClassStateChanged(nonEditable, true);
      } else {
        InfoStatement stmt = (InfoStatement)this.getTableRow().getItem();
        if (stmt == null) {
          this.setEditable(false);
          this.pseudoClassStateChanged(nonEditable, true);
        } else if (stmt.isEbreak()) {
          this.setEditable(false);
          this.pseudoClassStateChanged(nonEditable, true);
        } else
          this.setEditable(true);
      }
    });
  }

}
