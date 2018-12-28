package vsim.gui.components;

import java.io.File;
import java.util.HashMap;
import java.util.Comparator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.TreeItem;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;


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
      String imgPath = isRoot ? "/resources/img/icons/root.png" : "/resources/img/icons/folder.png";
      Image image = new Image(getClass().getResource(imgPath).toExternalForm());
      ImageView icon = new ImageView();
      icon.setImage(image);
      icon.setFitHeight(20);
      icon.setFitWidth(20);
      this.setGraphic(icon);
      this.expandedProperty().addListener((e, oldVal, newVal) -> {
        this.expanded.put(path, (Boolean)newVal);
      });
    } else {
      Image image = new Image(getClass().getResource("/resources/img/icons/file.png").toExternalForm());
      ImageView icon = new ImageView();
      icon.setImage(image);
      icon.setFitHeight(18);
      icon.setFitWidth(18);
      this.setGraphic(icon);
    }
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public ObservableList<TreeItem<String>> getChildren() {
    if (this.build) {
      this.build = false;
      super.getChildren().setAll(this.buildChildren(this));
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
   * Returning a collection of type ObservableList containing TreeItems, which
   * represent all children available in handed TreeItem.
   * (Ref: Alexander Bolte www.consulting-bolte.de)
   *
   * @param TreeItem
   *            the root node from which children a collection of TreeItem
   *            should be created.
   * @return an ObservableList<TreeItem<String>> containing TreeItems, which
   *         represent all children available in handed TreeItem. If the
   *         handed TreeItem is a leaf, an empty list is returned.
   */
  private ObservableList<TreeItem<String>> buildChildren(TreePath item) {
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
            TreePath p = (TreePath)tp;
            TreePath q = (TreePath)tq;
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
