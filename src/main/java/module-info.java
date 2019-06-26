/** V-Sim module */
module vsim {
  requires java.base;
  requires java.desktop;

  requires javafx.base;
  requires javafx.controls;
  requires javafx.fxml;
  requires javafx.graphics;

  requires richtextfx;
  requires com.jfoenix;
  requires java.cup.runtime;

  exports vsim;
}
