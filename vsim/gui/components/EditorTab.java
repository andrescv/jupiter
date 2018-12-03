package vsim.gui.components;

import java.io.File;
import java.util.HashSet;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import javafx.scene.control.Tab;
import java.nio.file.StandardOpenOption;
import static java.nio.file.StandardOpenOption.*;
import org.fxmisc.flowless.VirtualizedScrollPane;


/**
 * This class is used as a editor tab for the editor tab pane.
 */
public final class EditorTab extends Tab {

  /** untitled tabs set */
  private static HashSet<Integer> UNTITLED = new HashSet<Integer>();

  /** File path */
  private File path;
  /** Tab name */
  private String name;
  /** Untitled number */
  private int untitled;
  /** Code area */
  private Editor editor;
  /** If code area has changed */
  private boolean hasChanged;
  /** if is closed */
  private boolean closed;

  /**
   * Creates a new titled tab.
   *
   * @param path file to open
   * @throws IOException if the file cannot be read
   */
  public EditorTab(File path) throws IOException {
    super();
    this.closed = false;
    this.untitled = -1;
    this.name = path.getName();
    this.path = path;
    this.setText(this.name);
    this.hasChanged = false;
    // read file
    FileInputStream fis = new FileInputStream(path);
    byte[] data = new byte[(int) path.length()];
    fis.read(data);
    fis.close();
    // create a new editor with default text
    this.editor = new Editor(new String(data));
    // create a new virtualized scroll pane
    this.setContent(new VirtualizedScrollPane<>(this.editor));
    // handle editor key typed event
    this.editor.textProperty().addListener(e -> this.handleChange());
  }

  /**
   * Creates a new untitled tab.
   */
  public EditorTab() {
    super();
    this.closed = false;
    this.untitled = EditorTab.next();
    this.name = String.format("riscv%d.s", this.untitled);
    this.path = null;
    this.editor = new Editor();
    this.hasChanged = false;
    this.setText(this.name);
    this.setContent(new VirtualizedScrollPane<>(this.editor));
    this.editor.textProperty().addListener(e -> this.handleChange());
  }

  /**
   * Returns tab change state.
   *
   * @return true if editor has changed, false otherwise
   */
  public boolean hasChanged() {
    return this.hasChanged;
  }

  /**
   * Returns tab file path.
   *
   * @return file path
   */
  public File getPath() {
    return this.path;
  }

  /**
   * Tab close state.
   *
   * @return true if tab is closed, false otherwise
   */
  public boolean isClosed() {
    return this.closed;
  }

  /**
   * Returns if the tab is an untitled tab.
   *
   * @return true if tab is untitled, false otherwise.
   */
  public boolean isUntitled() {
    return this.untitled != -1;
  }

  /**
   * Sets tab file path.
   *
   * @param path file path
   */
  public void setPath(File path) {
    // remove untitled number
    if (this.untitled != -1)
      EditorTab.UNTITLED.remove(this.untitled);
    // update tab state
    this.untitled = -1;
    this.path = path;
    this.name = path.getName();
    this.setText(name + " *");
  }

  /**
   * Sets tab file path and sets editor default text.
   *
   * @param path file path
   * @throws IOException if the file cannot be read
   */
  public void setPathAndOpen(File path) throws IOException {
    // remove untitled number
    if (this.untitled != -1)
      EditorTab.UNTITLED.remove(this.untitled);
    // update tab state
    this.untitled = -1;
    this.path = path;
    this.name = path.getName();
    this.setText(name);
    this.hasChanged = false;
    // read file
    FileInputStream fis = new FileInputStream(path);
    byte[] data = new byte[(int) path.length()];
    fis.read(data);
    fis.close();
    // set editor default text
    this.editor.setEditorText(new String(data));
  }

  /**
   * Saves editor text to file if path != null.
   * @throws IOException if the file cannot be saved
   */
  public void save() throws IOException {
    if (this.path != null) {
      // create new file if necessary
      if (!this.path.exists())
        this.path.createNewFile();
      // truncate existing file
      StandardOpenOption[] opts = new StandardOpenOption[]{WRITE, TRUNCATE_EXISTING};
      Files.write(this.path.toPath(), this.editor.getText().getBytes(), opts);
      // update editor state
      this.hasChanged = false;
      this.editor.updateLastText();
      this.setText(this.name);
    }
  }

  /**
   * Close tab and unsubscribe syntax highlighting.
   */
  public void close() {
    // remove untitled number
    if (this.untitled != -1)
      EditorTab.UNTITLED.remove(this.untitled);
    this.untitled = -1;
    this.editor.unsubscribe();
    this.closed = true;
  }

  /**
   * Handles editor changes.
   */
  private void handleChange() {
    this.hasChanged = this.editor.hasChanged();
    // set * in tab text
    if (this.hasChanged && !this.getText().endsWith("*"))
      this.setText(this.name + " *");
    // if no changes set default name
    else if (!this.hasChanged)
      this.setText(this.name);
  }

  /**
   * Gets next available untitled number.
   *
   * @return next available number
   */
  private static int next() {
    int count = 1;
    // find next available number
    while (true) {
      if (!EditorTab.UNTITLED.contains(count)) {
        break;
      }
      count++;
    }
    // mark as used
    EditorTab.UNTITLED.add(count);
    return count;
  }

}
