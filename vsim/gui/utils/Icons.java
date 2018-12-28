package vsim.gui.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/**
 * This class contains utilities for icon management.
 */
public final class Icons {

  /** Icon default width */
  public static double DEFAULT_WIDTH = 20.0;
  /** Icon default height */
  public static double DEFAULT_HEIGHT = 20.0;

  /**
   * Gets V-Sim favicon
   *
   * @return V-Sim favicon
   */
  public static Image getFavicon() {
    return new Image(Icons.class.getResourceAsStream("/resources/img/favicon.png"));
  }

  /**
   * Loads a raw image given a path.
   *
   * @param name icon name
   * @return raw image
   */
  public static Image getRawImage(String name) {
    return new Image(Icons.class.getResourceAsStream(String.format("/resources/img/icons/%s.png", name)));
  }

  /**
   * Creates an image view from an image path, width and height.
   *
   * @param name image name
   * @param width image view fit width
   * @param height image view fit height
   * @return image view
   */
  public static ImageView getImage(String name, double width, double height) {
    ImageView img = new ImageView();
    img.setFitWidth(width);
    img.setFitHeight(height);
    img.setImage(Icons.getRawImage(name));
    return img;
  }

  /**
   * Creates an image view from an image path and default width and height.
   *
   * @param name image name
   */
  public static ImageView getImage(String name) {
    return Icons.getImage(name, Icons.DEFAULT_WIDTH, Icons.DEFAULT_HEIGHT);
  }

}
