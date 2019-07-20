/** Jupiter module */
module jupiter {
  requires java.base;
  requires java.prefs;
  requires java.desktop;

  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;

  requires reactfx;
  requires richtextfx;
  requires com.jfoenix;
  requires java.cup.runtime;

  exports jupiter;
  exports jupiter.gui to javafx.graphics;

  opens jupiter.gui to javafx.fxml;
  opens jupiter.gui.models to javafx.base;
  opens jupiter.gui.controllers to javafx.fxml;
}
