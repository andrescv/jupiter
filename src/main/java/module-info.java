/** V-Sim module */
module vsim {
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

  exports vsim;
  opens vsim.gui.models to javafx.base;
  opens vsim.gui.controllers to javafx.fxml;
}
