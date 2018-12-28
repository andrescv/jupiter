package vsim.gui.components;

import java.io.File;
import java.util.HashSet;
import javafx.stage.Stage;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileInputStream;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
   * Returns tab name.
   *
   * @return tab name
   */
  public String getName() {
    return this.name;
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
    this.setText(this.name);
    this.updateIcon();
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
   * Uses the editor undo manager to make an undo.
   */
  public void undo() {
    if (this.editor.isUndoAvailable())
      this.editor.undo();
  }

  /**
   * Uses the editor undo manager to make a redo.
   */
  public void redo() {
    if (this.editor.isRedoAvailable())
      this.editor.redo();
  }

  /**
   * Uses the editor clipboard actions to make a cut of selected text.
   */
  public void cut() {
    this.editor.cut();
  }

  /**
   * Uses the editor clipboard actions to make a copy of selected text.
   */
  public void copy() {
    this.editor.copy();
  }

  /**
   * Uses the editor clipboard actions to make a paste.
   */
  public void paste() {
    this.editor.paste();
  }

  /**
   * Uses the editor navigation actions to select all text content.
   */
  public void selectAll() {
    this.editor.selectAll();
  }

  /**
   * This method selects a text in editor if possible.
   *
   * @param start start index of selection range
   * @param end end index of selection range
   */
  public void select(int start, int end) {
    if (start >= 0 && end <= this.editor.getLength()) {
      this.editor.moveTo(start);
      this.editor.requestFollowCaret();
      this.editor.selectRange(start, end);
    }
  }

  /**
   * This method replaces a selected text in editor if possible.
   *
   * @param text replacement text
   */
  public void replace(String text) {
    if (this.isEditorTextSelected())
      this.editor.replaceSelection(text);
  }

  /**
   * This method selects and replaces a text in editor if possible.
   *
   * @param text replacement text
   */
  public void replace(int start, int end, String text) {
    this.select(start, end);
    if (this.isEditorTextSelected())
      this.editor.replaceSelection(text);
  }

  /**
   * Gets editor current text.
   *
   * @return editor text
   */
  public String getEditorText() {
    return this.editor.getText();
  }

  /**
   * Gets editor current caret position.
   *
   * @return editor caret position
   */
  public int getEditorCaretPosition() {
    return this.editor.getCaretPosition();
  }

  /**
   * if there is selected text in editor.
   *
   * @return true if editor has selected text, false otherwise
   */
  public boolean isEditorTextSelected() {
    return this.editor.getSelectedText().length() > 0;
  }

  /**
   * Deselects editor selected text (if any).
   */
  public void deselect() {
    this.editor.deselect();
  }

  /**
   * Handles editor changes.
   */
  private void handleChange() {
    this.hasChanged = this.editor.hasChanged();
    this.updateIcon();
  }

  /**
   * Updates editor graphic icon.
   */
  private void updateIcon() {
    if (this.hasChanged && this.getGraphic() == null) {
      Image img = new Image(getClass().getResource("/resources/img/icons/dot.png").toExternalForm());
      ImageView icon = new ImageView();
      icon.setFitWidth(12);
      icon.setFitHeight(12);
      icon.setImage(img);
      this.setGraphic(icon);
    } else if (!this.hasChanged)
      this.setGraphic(null);
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
