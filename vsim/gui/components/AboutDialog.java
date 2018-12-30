package vsim.gui.components;

import vsim.Settings;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import vsim.gui.utils.Icons;
import javafx.stage.Modality;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import com.jfoenix.controls.JFXDecorator;


/**
 * Simple class representing an about dialog. This class reuse Preloader fxml file.
 */
public final class AboutDialog {

  /** Dialog stage */
  private Stage stage;
  /** version label */
  @FXML private Label version;
  /** copyright label */
  @FXML private Label copyright;

  /**
   * Creates a new about dialog.
   */
  public AboutDialog() {
    try {
      this.stage = new Stage();
      this.stage.setTitle("V-Sim About");
      this.stage.initModality(Modality.APPLICATION_MODAL);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Preloader.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
      decorator.setGraphic(Icons.getImage("logo"));
      this.stage.setResizable(false);
      this.stage.setScene(new Scene(decorator, 504, 312));
      this.version.setText(Settings.VERSION);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  /**
   * Shows about dialog.
   */
  public void show() {
    this.stage.show();
  }

}
