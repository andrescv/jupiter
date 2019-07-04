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

import javafx.scene.control.cell.CheckBoxTreeTableCell;

import vsim.gui.models.StatementItem;


/**  Custom tree table cell for breakpoints. */
public final class TextBooleanCell extends CheckBoxTreeTableCell<StatementItem, Boolean> {

  /** {@inheritDoc} */
  @Override
  public void updateItem(Boolean item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null) {
      setEditable(false);
    } else {
      StatementItem stmt = (StatementItem) getTreeTableRow().getItem();
      if (stmt == null) {
        setEditable(false);
      } else if (stmt.isEbreak()) {
        setEditable(false);
      } else {
        setEditable(true);
      }
    }
  }

}
