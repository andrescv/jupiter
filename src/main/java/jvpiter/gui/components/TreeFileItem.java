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

import java.io.File;
import java.util.Comparator;
import java.util.Hashtable;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TreeItem;

import jvpiter.gui.Icons;
import jvpiter.utils.FS;


public final class TreeFileItem extends TreeItem<String> {

  /** tree item file */
  private final File file;

  /** expanded lookup */
  private final Hashtable<String, Boolean> expanded;

  /** build children */
  private boolean build;


  /**
   * Creates a new tree path item.
   *
   * @param path file path
   * @param isRoot if the tree path item is the root element
   * @param expanded expanded lookup info
   */
  public TreeFileItem(File file, boolean isRoot, Hashtable<String, Boolean> expanded) {
    super(file.getName());
    this.file = file;
    this.expanded = expanded;
    build = true;
    // set expanded property
    String path = file.getAbsolutePath();
    Boolean expandedProp = expanded.get(path);
    if (expandedProp != null) {
      setExpanded(expandedProp);
    } else if (isRoot) {
      setExpanded(true);
      expanded.put(path, true);
    } else {
      setExpanded(false);
      expanded.put(path, false);
    }
    // set item icon
    if (file.isDirectory()) {
      String iconName = isRoot ? "root" : "folder";
      setGraphic(Icons.get(iconName, 20, 20));
      expandedProperty().addListener((e, oldVal, newVal) -> expanded.put(path, (Boolean) newVal));
    } else {
      setGraphic(Icons.get("file", 20, 20));
    }
  }

  /** {@inheritDoc} */
  @Override
  public ObservableList<TreeItem<String>> getChildren() {
    if (build) {
      build = false;
      super.getChildren().setAll(buildChildren());
    }
    return super.getChildren();
  }

  /** {@inheritDoc} */
  @Override
  public boolean isLeaf() {
    return FS.isAssemblyFile(file);
  }

  /**
   * Gets tree item path.
   *
   * @return tree item path
   */
  public File getFile() {
    return file;
  }

  /**
   * Builds tree item children.
   *
   * @return tree item children
   */
  private ObservableList<TreeFileItem> buildChildren() {
    if (file != null && file.isDirectory()) {
      File[] files = file.listFiles();
      if (files != null) {
        ObservableList<TreeFileItem> children = FXCollections.observableArrayList();
        for (File f : files) {
          if (!f.isHidden() && (f.isDirectory() || FS.isAssemblyFile(f))) {
            children.add(new TreeFileItem(f, false, expanded));
          }
        }
        // sort children
        children.sort(new Comparator<TreeFileItem>() {
          @Override
          public int compare(TreeFileItem p, TreeFileItem q) {
            String fn = p.getFile().getName().toString();
            String qn = q.getFile().getName().toString();
            boolean pd = p.getFile().isDirectory();
            boolean qd = q.getFile().isDirectory();
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

}
