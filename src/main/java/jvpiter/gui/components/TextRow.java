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

package jvpiter.gui.components;

import javafx.css.PseudoClass;
import javafx.scene.control.TreeTableRow;

import jvpiter.gui.Status;
import jvpiter.gui.models.StatementItem;
import jvpiter.sim.State;
import jvpiter.utils.Data;


/** Custom tree table row for text table. */
public final class TextRow extends TreeTableRow<StatementItem> {

  /** program state */
  private final State state;

  /**
   * Creates a new tree table row.
   *
   * @param state program state
   */
  public TextRow(State state) {
    this.state = state;
  }

  /** {@inheritDoc} */
  @Override
  public void updateItem(StatementItem item, boolean empty) {
    super.updateItem(item, empty);
    if (empty || item == null || Status.RUNNING.get()) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("pc"), false);
    } else if (item != null && (Data.atoi(item.addressProperty().get()) == state.xregfile().getProgramCounter())) {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("pc"), true);
    } else {
      pseudoClassStateChanged(PseudoClass.getPseudoClass("pc"), false);
    }
  }

}
