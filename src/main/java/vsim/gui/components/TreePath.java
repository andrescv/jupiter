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

import java.io.File;
import java.util.Comparator;
import java.util.HashMap;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;
import vsim.gui.utils.Icons;


/**
 * A subclass of Java FX TreeItem that represents a tree path.
 */
public final class TreePath extends TreeItem<String> {

  /** Tree item path */
  private File path;

  /** Expanded tab lookup */
  private HashMap<File, Boolean> expanded;

  /** build children */
  private boolean build = true;

  /**
   * Creates a TreePath given a path.
   *
   * @param path file path
   * @param isRoot if the tree path item is the root element
   * @param expanded expanded lookup info
   */
  public TreePath(File path, Boolean isRoot, HashMap<File, Boolean> expanded) {
    super(path.getName());
    this.path = path;
    this.expanded = expanded;
    Boolean expandedProp = this.expanded.get(path);
    if (expandedProp != null)
      this.setExpanded(expandedProp);
    else if (isRoot) {
      this.setExpanded(true);
      this.expanded.put(this.path, true);
    } else {
      this.setExpanded(false);
      this.expanded.put(this.path, false);
    }
    // set item icon
    if (this.path.isDirectory()) {
      String iconName = isRoot ? "root" : "folder";
      this.setGraphic(Icons.getImage(iconName));
      this.expandedProperty().addListener((e, oldVal, newVal) -> this.expanded.put(path, (Boolean) newVal));
    } else
      this.setGraphic(Icons.getImage("file"));
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObservableList<TreeItem<String>> getChildren() {
    if (this.build) {
      this.build = false;
      super.getChildren().setAll(this.buildChildren());
    }
    return super.getChildren();
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isLeaf() {
    return this.path.isFile();
  }

  /**
   *
   * Returning a collection of type ObservableList containing TreeItems, which represent all children available in
   * handed TreeItem. (Ref: Alexander Bolte www.consulting-bolte.de)
   *
   * @param TreeItem the root node from which children a collection of TreeItem should be created.
   * @return an ObservableList<TreeItem<String>> containing TreeItems, which represent all children available in handed
   * TreeItem. If the handed TreeItem is a leaf, an empty list is returned.
   */
  private ObservableList<TreeItem<String>> buildChildren() {
    if (this.path != null && path.isDirectory()) {
      File[] files = path.listFiles();
      if (files != null) {
        ObservableList<TreeItem<String>> children = FXCollections.observableArrayList();
        for (File f : files) {
          if ((f.isDirectory() || f.getName().endsWith(".s") || f.getName().endsWith(".asm")) && !f.isHidden())
            children.add(new TreePath(f, false, this.expanded));
        }
        // sort children
        children.sort(new Comparator<TreeItem<String>>() {

          @Override
          public int compare(TreeItem<String> tp, TreeItem<String> tq) {
            TreePath p = (TreePath) tp;
            TreePath q = (TreePath) tq;
            String fn = p.getPath().getName().toString();
            String qn = q.getPath().getName().toString();
            boolean pd = p.getPath().isDirectory();
            boolean qd = q.getPath().isDirectory();
            if (pd && !qd)
              return -1;
            else if (!pd && qd)
              return 1;
            else
              return fn.compareToIgnoreCase(qn);
          }
        });
        return children;
      }
    }
    return FXCollections.emptyObservableList();
  }

  /**
   * Gets tree item path.
   *
   * @return tree item path
   */
  public File getPath() {
    return this.path;
  }

}
