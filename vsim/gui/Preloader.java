package vsim.gui;

import vsim.Settings;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.scene.Parent;
import java.io.IOException;
import vsim.gui.utils.Icons;
import javafx.fxml.FXMLLoader;
import javafx.stage.StageStyle;
import javafx.scene.control.Label;
import javafx.geometry.Rectangle2D;
import javafx.application.Platform;


/**
 * This class represents V-Sim's GUI preloader.
 */
public final class Preloader extends javafx.application.Preloader {

  /** preloader width */
  private static final double WIDTH = 500.0;
  /** preloader height */
  private static final double HEIGHT = 274.0;

  /** version label */
  @FXML private Label version;

  /** preloader main stage */
  private Stage stage;

  @Override
  public void start(Stage stage) throws IOException {
    // remove main frame and buttons (close, minimize, maximize...)
    stage.initStyle(StageStyle.UNDECORATED);
    stage.getIcons().add(Icons.getFavicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Preloader.fxml"));
    loader.setController(this);
    Parent root = loader.load();
    this.version.setText(Settings.VERSION);
    // save primary stage
    this.stage = stage;
    stage.setResizable(false);
    Scene scene = new Scene(root, WIDTH, HEIGHT);
    stage.setScene(scene);
    // center stage
    Rectangle2D bounds = Screen.getPrimary().getVisualBounds();
    stage.setX((bounds.getWidth() - WIDTH) / 2.0);
    stage.setY((bounds.getHeight() - HEIGHT) / 2.0);
    stage.show();
    // center stage
    stage.setX((bounds.getWidth() - stage.getWidth()) / 2);
    stage.setY((bounds.getHeight() - stage.getHeight()) / 2);
  }

  @Override
  public void handleStateChangeNotification(StateChangeNotification info) {
    StateChangeNotification.Type type = info.getType();
    switch (type) {
      case BEFORE_START:
        this.stage.hide();
        break;
    }
  }

}
