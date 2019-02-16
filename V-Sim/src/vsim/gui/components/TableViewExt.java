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

import java.util.LinkedHashSet;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.util.Callback;


/**
 * Extra calls for TableView which would have been nice to see in JavaFx TableView
 */
public class TableViewExt<T> {

  /** table view to extend */
  private final TableView<T> tableView;
  /** table view rows */
  private LinkedHashSet<TableRow<T>> rows = new LinkedHashSet<>();
  /** table view first index */
  private int firstIndex;
  /** table view last index */
  private int lastIndex;

  /**
   * Initializes a new table view extension.
   *
   * @param tableView table view to extend
   */
  public TableViewExt(TableView<T> tableView) {
    this.tableView = tableView;
    // Callback to monitor row creation and to identify visible screen rows
    final Callback<TableView<T>, TableRow<T>> rf = tableView.getRowFactory();
    // modify row factory
    final Callback<TableView<T>, TableRow<T>> modifiedRowFactory = param -> {
      TableRow<T> r = rf != null ? rf.call(param) : new TableRow<T>();
      // Save row, this implementation relies on JaxaFX re-using TableRow efficiently
      rows.add(r);
      return r;
    };
    tableView.setRowFactory(modifiedRowFactory);
  }

  /** recomputes table view first visible and last visible cells indexes */
  private void recomputeVisibleIndexes() {
    firstIndex = -1;
    lastIndex = -1;
    // Work out which of the rows are visible
    double tblViewHeight = tableView.getHeight();
    double headerHeight = tableView.lookup(".column-header-background").getBoundsInLocal().getHeight();
    double viewPortHeight = tblViewHeight - headerHeight;
    for (TableRow<T> r : rows) {
      double minY = r.getBoundsInParent().getMinY();
      double maxY = r.getBoundsInParent().getMaxY();
      boolean hidden = (maxY < 0) || (minY > viewPortHeight);
      if (!hidden) {
        if (firstIndex < 0 || r.getIndex() < firstIndex)
          firstIndex = r.getIndex();
        if (lastIndex < 0 || r.getIndex() > lastIndex)
          lastIndex = r.getIndex();
      }
    }
  }

  /**
   * Find the first row in the table view which is visible on the display.
   *
   * @return -1 if none visible or the index of the first visible row (wholly or fully)
   */
  public int getFirstVisibleIndex() {
    recomputeVisibleIndexes();
    return firstIndex;
  }

  /**
   * Find the last row in the table view which is visible on the display.
   *
   * @return -1 if none visible or the index of the last visible row (wholly or fully)
   */
  public int getLastVisibleIndex() {
    recomputeVisibleIndexes();
    return lastIndex;
  }

}
