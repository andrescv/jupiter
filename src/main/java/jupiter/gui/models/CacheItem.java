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

package jupiter.gui.models;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javafx.beans.property.SimpleStringProperty;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


/** Cache item for cache table. */
public final class CacheItem extends RecursiveTreeObject<CacheItem> implements PropertyChangeListener {

  /** block index */
  private final int index;
  /** block state */
  private final SimpleStringProperty state;

  /**
   * Creates a new cache item.
   *
   * @param index block index
   */
  public CacheItem(int index) {
    this.index = index;
    state = new SimpleStringProperty(String.format("(%d) EMPTY", index));
  }

  /**
   * Returns the byte property shown in the +3 column.
   *
   * @return byte3 property
   */
  public SimpleStringProperty stateProperty() {
    return state;
  }

  /**
   * Returns the byte shown in the +0 column.
   *
   * @return byte0
   */
  public String getState() {
    return state.get();
  }

  /** {@inheritDoc} */
  @Override
  public void propertyChange(PropertyChangeEvent e) {
    int i = (int) e.getNewValue();
    if (i == index) {
      String op = e.getPropertyName();
      if (op.equals("hit")) {
        state.set(String.format("(%d) HIT", index));
      } else if (op.equals("miss")) {
        state.set(String.format("(%d) MISS", index));
      } else {
        state.set(String.format("(%d) EMPTY", index));
      }
    }
  }

}
