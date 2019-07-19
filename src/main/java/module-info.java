/** Jvpiter module */
module jvpiter {
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

  exports jvpiter;
  exports jvpiter.gui to javafx.graphics;

  opens jvpiter.gui to javafx.fxml;
  opens jvpiter.gui.models to javafx.base;
  opens jvpiter.gui.controllers to javafx.fxml;
}
