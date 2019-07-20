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
import jupiter.gui.models.CacheItem;


/** Custom tree table row for text table. */
public final class CacheRow extends TreeTableRow<CacheItem> {

  /** {@inheritDoc} */
  @Override
  public void updateItem(CacheItem item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null || Status.RUNNING.get()) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("hit"), false);
      pseudoClassStateChanged(PseudoClass.getPseudoClass("miss"), false);
    } else if (item != null && item.getState().indexOf("EMPTY") != -1) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("hit"), false);
      pseudoClassStateChanged(PseudoClass.getPseudoClass("miss"), false);
    } else if (item != null && item.getState().indexOf("HIT") != -1) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("hit"), true);
      pseudoClassStateChanged(PseudoClass.getPseudoClass("miss"), false);
    } else {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("hit"), false);
      pseudoClassStateChanged(PseudoClass.getPseudoClass("miss"), true);
    }
  }

}
