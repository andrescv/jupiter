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

import java.io.IOException;
import java.nio.file.Path;

import javafx.scene.control.Tab;

import org.fxmisc.flowless.VirtualizedScrollPane;

import vsim.gui.Icons;
import vsim.utils.FS;


/** V-Sim editor tab. */
public final class EditorTab extends Tab {

  /** untitled tabs set */
  private static int UNTITLED = 1;

  /** file path */
  private Path file;
  /** tab name */
  private String name;
  /** untitled number */
  private int untitled;
  /** tab content */
  private TextEditor textEditor;
  /** if is closed */
  private boolean closed;

  /**
   * Creates a new titled tab from the given file path.
   *
   * @throws IOException if an I/O error occurs
   */
  public EditorTab(Path file) throws IOException {
    super();
    this.file = file;
    closed = false;
    untitled = -1;
    name = file.toFile().getName();
    setText(name);
    // create new text editor
    textEditor = new TextEditor(FS.read(file));
    // set tab content
    setContent(new VirtualizedScrollPane<>(textEditor));
    // add listeners
    textEditor.textProperty().addListener((e, o, n) -> handleChanges());
  }

  /** Creates a new untitled tab. */
  public EditorTab() {
    super();
    closed = false;
    file = null;
    untitled = UNTITLED++;
    name = String.format("riscv%d.s", untitled);
    setText(name);
    textEditor = new TextEditor();
    setContent(new VirtualizedScrollPane<>(textEditor));
    // add listeners
    textEditor.textProperty().addListener((e, o, n) -> handleChanges());
  }

  /**
   * Sets tab file path.
   *
   * @param file new file path
   */
  public void setPath(Path file) {
    this.file = file;
    untitled = -1;
    name = file.toFile().getName();
    setText(name);
    updateIcon();
  }

  /**
   * Opens file and reads its content.
   *
   * @param file file to open
   * @throws IOException if an I/O error occurs
   */
  public void open(Path file) throws IOException {
    this.file = file;
    untitled = -1;
    name = file.toFile().getName();
    setText(name);
    textEditor.load(FS.read(file));
  }

  /**
   * Saves text editor's content to a file.
   *
   * @throws IOException if an I/O error occurs
   */
  public void save() throws IOException {
    if (file != null) {
      FS.write(file, textEditor.getText());
      textEditor.save();
      setText(name);
      updateIcon();
    }
  }

  /** Closes file. */
  public void close() {
    untitled = -1;
    textEditor.close();
    closed = true;
  }

  /**
   * Returns text editor.
   *
   * @return text editor
   */
  public TextEditor getTextEditor() {
    return textEditor;
  }

  /**
   * Returns file path.
   *
   * @return file path
   */
  public Path getPath() {
    return file;
  }

  /**
   * Verifies if the tab is an untitled tab.
   *
   * @return {@code true} if the tab is an untitled tab, false if not
   */
  public boolean untitled() {
    return untitled != -1;
  }


  /**
   * Verifies if the tab has been modified.
   *
   * @return {@code true} if the tab has been modified, false if not
   */
  public boolean modified() {
    return textEditor.modified();
  }

  /**
   * Verifies if the tab has been closed.
   *
   * @return {@code true} if the tab has been closed, false if not
   */
  public boolean closed() {
    return closed;
  }

  /**
   * Returns tab name.
   *
   * @return tab name
   */
  public String getName() {
    return name;
  }

  /** Handles text editor changes. */
  private void handleChanges() {
    updateIcon();
  }

  /** Updates tab graphic. */
  private void updateIcon() {
    boolean modified = textEditor.modified();
    if (modified && getGraphic() == null) {
      setGraphic(Icons.get("dot", 12, 12));
    } else if (!modified && getGraphic() != null) {
      setGraphic(null);
    }
  }

}
