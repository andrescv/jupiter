package vsim.gui.components;

import java.io.File;
import javafx.scene.control.TreeItem;


/**
 * A subclass of Java FX TreeItem that represents a tree path.
 */
public final class TreePath extends TreeItem<String> {

  /** Tree item path */
  private File path;

  /**
   * Creates a TreePath given a path.
   *
   * @param path file path
   */
  public TreePath(File path) {
    super(path.getName());
    this.path = path;
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
