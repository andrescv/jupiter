package vsim.gui;

import com.jfoenix.controls.JFXDecorator;
import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import vsim.gui.controllers.MainController;
import vsim.gui.utils.Icons;

public final class Gui extends Application {

  @Override
  public void init() {
    // wait 2 seconds before start :)
    try {
      Thread.sleep(2000);
    } catch (Exception e) {
    }
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("V-Sim");
    stage.getIcons().add(Icons.getFavicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Main.fxml"));
    Parent root = loader.load();
    MainController controller = (MainController) loader.getController();
    controller.initialize(stage);
    JFXDecorator decorator = new JFXDecorator(stage, root, true, true, true);
    decorator.setGraphic(Icons.getImage("logo"));
    decorator.setCustomMaximize(true);
    // create scene
    Scene scene = new Scene(decorator, 1024, 800);
    // add styles
    scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
    stage.setScene(scene);
    stage.show();
  }
}
