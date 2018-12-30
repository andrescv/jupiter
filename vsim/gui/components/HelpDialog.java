package vsim.gui.components;

import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import vsim.gui.utils.Icons;
import javafx.fxml.FXMLLoader;
import javafx.scene.web.WebView;
import com.jfoenix.controls.JFXDecorator;


public final class HelpDialog {

  /** Dialog stage */
  private Stage stage;
  /** Dialog save button */
  @FXML private WebView browser;

  public HelpDialog() {
    try {
      this.stage = new Stage();
      this.stage.setTitle("V-Sim Help");
      this.stage.setMinWidth(800);
      this.stage.setMinHeight(480);
      this.stage.getIcons().add(Icons.getFavicon());
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/HelpDialog.fxml"));
      loader.setController(this);
      Parent root = loader.load();
      JFXDecorator decorator = new JFXDecorator(stage, root, false, true, true);
      decorator.setCustomMaximize(true);
      this.stage.setScene(new Scene(decorator, 1024, 800));
      this.browser.getEngine().load(getClass().getResource("/resources/docs/index.html").toExternalForm());
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  public void show() {
    this.stage.show();
  }

}
