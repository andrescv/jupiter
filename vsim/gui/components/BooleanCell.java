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

import javafx.css.PseudoClass;
import javafx.scene.control.cell.CheckBoxTableCell;
import vsim.linker.InfoStatement;


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
        InfoStatement stmt = (InfoStatement) this.getTableRow().getItem();
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
