package vsim.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableView;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TableColumn;


/**
 * Simulator controller class.
 */
public class SimulatorController {

  /** Simulator go button */
  @FXML protected JFXButton goBtn;
  /** Simulator step button */
  @FXML protected JFXButton stepBtn;
  /** Simulator backstep button */
  @FXML protected JFXButton backstepBtn;
  /** Simulator reset button */
  @FXML protected JFXButton resetBtn;
  /** Simulator dump button */
  @FXML protected JFXButton dumpBtn;

  /** Simulator text segment table view */
  @FXML protected TableView<?> textTable;
  /** Simulator text segment table view memory address column */
  @FXML protected TableColumn<?, ?> txtAddrCol;
  /** Simulator text segment table view machine code column */
  @FXML protected TableColumn<?, ?> txtMachineCode;
  /** Simulator text segment table view source code column */
  @FXML protected TableColumn<?, ?> txtSourceCode;
  /** Simulator text segment table view basic code column */
  @FXML protected TableColumn<?, ?> txtBasicCode;

  /** Hardware tab pane */
  @FXML protected JFXTabPane hardware;

  /** RISC-V RVI register file tab */
  @FXML protected Tab rviTab;
  /** RISC-V RVI register file table view */
  @FXML protected TableView<?> rviTable;
  /** RISC-V RVI register file table view register mnemonic column */
  @FXML protected TableColumn<?, ?> rviMnemonic;
  /** RISC-V RVI register file table view register number column */
  @FXML protected TableColumn<?, ?> rviNumber;
  /** RISC-V RVI register file table view register value column */
  @FXML protected TableColumn<?, ?> rviValue;

  /** RISC-V RVF register file tab */
  @FXML protected Tab rvfTab;
  /** RISC-V RVF register file table view */
  @FXML protected TableView<?> rvfTable;
  /** RISC-V RVF register file table view register mnemonic column */
  @FXML protected TableColumn<?, ?> rvfMnemonic;
  /** RISC-V RVF register file table view register number column */
  @FXML protected TableColumn<?, ?> rvfNumber;
  /** RISC-V RVF register file table view register value column */
  @FXML protected TableColumn<?, ?> rvfValue;

  /** RISC-V memory tab */
  @FXML protected Tab memTab;
  /** RISC-V memory table view */
  @FXML protected TableView<?> memTable;
  /** RISC-V memory table view address column */
  @FXML protected TableColumn<?, ?> memAddress;
  /** RISC-V memory table view offset 0 column */
  @FXML protected TableColumn<?, ?> memOffset0;
  /** RISC-V memory table view offset 1 column */
  @FXML protected TableColumn<?, ?> memOffset1;
  /** RISC-V memory table view offset 2 column */
  @FXML protected TableColumn<?, ?> memOffset2;
  /** RISC-V memory table view offset 3 column */
  @FXML protected TableColumn<?, ?> memOffset3;

  /** RISC-V memory segment combo box */
  @FXML protected JFXComboBox<?> memCombo;
  /** RISC-V memory up button */
  @FXML protected JFXButton upBtn;
  /** RISC-V memory down button */
  @FXML protected JFXButton downBtn;

  /** Reference to main controller */
  private MainController mainController;

  /**
   * Initialize simulator controller.
   *
   * @param controller main controller
   */
  protected void initialize(MainController controller) {
    this.mainController = controller;
  }

}
