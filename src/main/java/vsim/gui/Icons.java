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

package vsim.gui;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


/** V-Sim icons management. */
public final class Icons {

  /** icon default width */
  public static double DEFAULT_WIDTH = 24.0;
  /** icon default height */
  public static double DEFAULT_HEIGHT = 24.0;

  /**
   * Creates an image icon from favicon image.
   *
   * @return V-Sim favicon image.
   */
  public static Image favicon() {
    return new Image(Icons.class.getResourceAsStream("/vsim/img/favicon.png"));
  }

  /** Creates an image view from about image. */
  public static ImageView about() {
    ImageView img = new ImageView();
    img.setFitHeight(274);
    img.setFitWidth(500);
    img.setImage(new Image(Icons.class.getResourceAsStream("/vsim/img/about.png")));
    return img;
  }

  /**
   * Creates an image view from an image icon with the given width and height.
   *
   * @param name image name
   * @param width image view fit width
   * @param height image view fit height
   * @return image view
   */
  public static ImageView get(String name, double width, double height) {
    ImageView img = new ImageView();
    img.setFitWidth(width);
    img.setFitHeight(height);
    img.setImage(new Image(Icons.class.getResourceAsStream(String.format("/vsim/img/icons/%s.png", name))));
    return img;
  }

  /**
   * Creates an image view from a button image icon with the given width and height.
   *
   * @param name image name
   * @param width image view fit width
   * @param height image view fit height
   * @return image view
   */
  public static ImageView btn(String name, double width, double height) {
    ImageView img = new ImageView();
    img.setFitWidth(width);
    img.setFitHeight(height);
    img.setImage(new Image(Icons.class.getResourceAsStream(String.format("/vsim/img/icons/btns/%s.png", name))));
    return img;
  }

  /**
   * Creates an image view from an image icon with the default width and height.
   *
   * @param name image name
   */
  public static ImageView get(String name) {
    return get(name, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

  /**
   * Creates an image view from a button image icon with the default width and height.
   *
   * @param name image name
   */
  public static ImageView btn(String name) {
    return btn(name, DEFAULT_WIDTH, DEFAULT_HEIGHT);
  }

}
