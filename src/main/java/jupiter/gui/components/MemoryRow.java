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

package jupiter.gui.components;

import javafx.css.PseudoClass;
import javafx.scene.control.TreeTableRow;

import jupiter.gui.Status;
import jupiter.gui.models.MemoryItem;


/** Custom tree table row for memory table. */
public final class MemoryRow extends TreeTableRow<MemoryItem> {

  /** {@inheritDoc} */
  @Override
  public void updateItem(MemoryItem item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null || Status.RUNNING.get()) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("updated"), false);
    } else if (item != null && item.updated()) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("updated"), true);
    } else {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("updated"), false);
    }
  }

}
