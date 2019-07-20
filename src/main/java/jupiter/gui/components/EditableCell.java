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

import javafx.scene.control.Tooltip;

import com.jfoenix.controls.cells.editors.base.GenericEditableTreeTableCell;

import jupiter.gui.models.MemoryItem;
import jupiter.sim.State;


/** Custom editable tree table cell. */
public final class EditableCell<S, T> extends GenericEditableTreeTableCell<S, T> {

  /** program state */
  private State state;

  /**
   * Creates a new editable cell.
   *
   * @param state program state
   */
  public EditableCell(State state) {
    super();
    this.state = state;
  }

  /** {@inheritDoc} */
  @Override
  public void startEdit() {
    S item = (S) getTreeTableRow().getItem();
    if (item instanceof MemoryItem) {
      MemoryItem mitem = (MemoryItem) item;
      if (!isEmpty() && state.memory().check(mitem.getIntAddress(), false)) {
        super.startEdit();
      }
    } else {
      super.startEdit();
    }
  }

  /** {@inheritDoc} */
  @Override
  public void updateItem(T item, boolean empty) {
    super.updateItem(item, empty);
    if (item != null) {
      setText(item.toString());
      setTooltip(new Tooltip(item.toString()));
    }
  }

}
