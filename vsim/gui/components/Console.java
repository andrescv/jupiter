package vsim.gui.components;

import java.io.PrintStream;
import javafx.scene.image.Image;
import javafx.scene.input.Clipboard;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.ContextMenu;
import vsim.gui.utils.CustomOutputStream;
import javafx.scene.input.ClipboardContent;


/**
 * A subclass of TextArea that uses CustomInputStream and CustomOutputStream.
 */
public final class Console extends TextArea {

  /** Standard output */
  private PrintStream stdout;
  /** Standard error */
  private PrintStream stderr;

  /**
   * Creates a Console text input control.
   */
  public Console() {
    super();
    this.setEditable(false);
    this.stdout = new PrintStream(new CustomOutputStream(this));
    this.stderr = this.stdout;
    // clear option
    MenuItem clear = new MenuItem("clear");
    clear.setOnAction(e -> this.setText(""));
    ImageView clearImg = new ImageView();
    clearImg.setFitWidth(20.0);
    clearImg.setFitHeight(20.0);
    clearImg.setImage(new Image(getClass().getResourceAsStream("/resources/img/icons/clear.png")));
    clear.setGraphic(clearImg);
    // copy option
    MenuItem copy = new MenuItem("copy");
    copy.setOnAction(e -> {
      Clipboard clipboard = Clipboard.getSystemClipboard();
      ClipboardContent content = new ClipboardContent();
      content.putString(this.getText());
      clipboard.setContent(content);
    });
    ImageView copyImg = new ImageView();
    copyImg.setFitWidth(20.0);
    copyImg.setFitHeight(20.0);
    copyImg.setImage(new Image(getClass().getResourceAsStream("/resources/img/icons/copy.png")));
    copy.setGraphic(copyImg);
    // set context menu with this options
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(clear, copy);
    this.setContextMenu(menu);
  }

  /**
   * Gets standard output of control.
   *
   * @return standard output
   */
  public PrintStream getStdout() {
    return this.stdout;
  }

  /**
   * Gets standard error of control.
   *
   * @return standard error
   */
  public PrintStream getStderr() {
    return this.stderr;
  }

}
