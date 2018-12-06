package vsim.gui;

import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.Parent;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.application.Application;
import com.jfoenix.controls.JFXDecorator;
import vsim.gui.controllers.MainController;


public final class Gui extends Application {

  /** Monokai syntax theme */
  public static final String MONOKAI = Gui.class.getResource("/resources/css/themes/monokai.css").toExternalForm();
  /** One Light syntax theme */
  public static final String ONELIGHT = Gui.class.getResource("/resources/css/themes/onelight.css").toExternalForm();
  /** Eclipse syntax theme */
  public static final String ECLIPSE = Gui.class.getResource("/resources/css/themes/eclipse.css").toExternalForm();
  /** Material syntax theme */
  public static final String MATERIAL = Gui.class.getResource("/resources/css/themes/material.css").toExternalForm();

  @Override
  public void init() {
    // wait 2 seconds before start :)
    try {
      Thread.sleep(2000);
    } catch (Exception e) { }
  }

  @Override
  public void start(Stage stage) throws IOException {
    stage.setTitle("V-Sim");
    stage.getIcons().add(new Image(getClass().getResourceAsStream("/resources/img/favicon.png")));
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/Main.fxml"));
    Parent root = loader.load();
    MainController controller = (MainController)loader.getController();
    controller.initialize(stage);
    JFXDecorator decorator = new JFXDecorator(stage, root, true, true, true);
    decorator.setCustomMaximize(true);
    // create scene
    Scene scene = new Scene(decorator, 800, 600);
    // add styles
    scene.getStylesheets().add(getClass().getResource("/resources/css/style.css").toExternalForm());
    scene.getStylesheets().add(Gui.MATERIAL);
    stage.setScene(scene);
    stage.show();
  }

}
