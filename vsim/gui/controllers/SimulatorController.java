package vsim.gui.controllers;

import vsim.Globals;
import vsim.Settings;
import javafx.fxml.FXML;
import vsim.utils.Message;
import vsim.riscv.Register;
import javafx.util.Callback;
import vsim.riscv.MemoryCell;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableCell;
import com.jfoenix.controls.JFXButton;
import javafx.scene.control.TableView;
import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.ContextMenu;
import javafx.collections.ListChangeListener;
import vsim.gui.components.MemoryEditingCell;
import javafx.scene.control.SeparatorMenuItem;
import vsim.gui.components.RegisterEditingCell;
import javafx.collections.ListChangeListener.Change;
import javafx.scene.control.TableColumn.CellEditEvent;
import javafx.scene.control.cell.PropertyValueFactory;


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
  @FXML protected TableView<Register> rviTable;
  /** RISC-V RVI register file table view register mnemonic column */
  @FXML protected TableColumn<Register, String> rviMnemonic;
  /** RISC-V RVI register file table view register number column */
  @FXML protected TableColumn<Register, Integer> rviNumber;
  /** RISC-V RVI register file table view register value column */
  @FXML protected TableColumn<Register, String> rviValue;

  /** RISC-V RVF register file tab */
  @FXML protected Tab rvfTab;
  /** RISC-V RVF register file table view */
  @FXML protected TableView<Register> rvfTable;
  /** RISC-V RVF register file table view register mnemonic column */
  @FXML protected TableColumn<Register, String> rvfMnemonic;
  /** RISC-V RVF register file table view register number column */
  @FXML protected TableColumn<Register, Integer> rvfNumber;
  /** RISC-V RVF register file table view register value column */
  @FXML protected TableColumn<Register, String> rvfValue;

  /** RISC-V memory tab */
  @FXML protected Tab memTab;
  /** RISC-V memory table view */
  @FXML protected TableView<MemoryCell> memTable;
  /** RISC-V memory table view address column */
  @FXML protected TableColumn<MemoryCell, String> memAddress;
  /** RISC-V memory table view offset 0 column */
  @FXML protected TableColumn<MemoryCell, String> memOffset0;
  /** RISC-V memory table view offset 1 column */
  @FXML protected TableColumn<MemoryCell, String> memOffset1;
  /** RISC-V memory table view offset 2 column */
  @FXML protected TableColumn<MemoryCell, String> memOffset2;
  /** RISC-V memory table view offset 3 column */
  @FXML protected TableColumn<MemoryCell, String> memOffset3;

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
    this.initRegFiles();
    this.initMemory();
  }

  /**
   * Assembles all files in directory.
   */
  protected void assemble() {
    // TODO
  }

  /**
   * Go step control, runs all the program.
   */
  protected void go() {
    // TODO
  }

  /**
   * Step flow control, advances the simulator by 1 step.
   */
  protected void step() {
    // TODO
  }

  /**
   * Backstep flow control, goes back to the previous step.
   */
  protected void backstep() {
    // TODO
  }

  /**
   * Resets all the simulator state and starts again.
   */
  protected void reset() {
    // TODO
  }

  /**
   * Clear all breakpoints that were set.
   */
  protected void clearAllBreakpoints() {
    // TODO
  }

  /**
   * Dumps generated machine code to a file.
   */
  protected void dump() {
    // TODO
  }

  /**
   * This method initializes simulator control buttons.
   */
  private void initButtons() {
    this.goBtn.setOnAction(e -> this.go());
    this.stepBtn.setOnAction(e -> this.step());
    this.backstepBtn.setOnAction(e -> this.backstep());
    this.resetBtn.setOnAction(e -> this.reset());
    this.dumpBtn.setOnAction(e -> this.dump());
  }

  /**
   * This method initializes the memory table.
   */
  @SuppressWarnings("unchecked")
  private void initMemory() {
    // cell factory
    Callback<TableColumn<MemoryCell, String>,
      TableCell<MemoryCell, String>> cellFactory
          = (TableColumn<MemoryCell, String> p) -> new MemoryEditingCell();
    // set memory columns
    this.memAddress.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("address"));
    this.memAddress.getStyleClass().add("editable-cell");
    this.memOffset0.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("offset0"));
    this.memOffset0.setCellFactory(cellFactory);
    this.memOffset0.getStyleClass().add("editable-cell");
    this.memOffset0.setOnEditCommit(e -> this.updateMemoryCell(e));
    this.memOffset1.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("offset1"));
    this.memOffset1.setCellFactory(cellFactory);
    this.memOffset1.getStyleClass().add("editable-cell");
    this.memOffset1.setOnEditCommit(e -> this.updateMemoryCell(e));
    this.memOffset2.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("offset2"));
    this.memOffset2.setCellFactory(cellFactory);
    this.memOffset2.getStyleClass().add("editable-cell");
    this.memOffset2.setOnEditCommit(e -> this.updateMemoryCell(e));
    this.memOffset3.setCellValueFactory(new PropertyValueFactory<MemoryCell, String>("offset3"));
    this.memOffset3.setCellFactory(cellFactory);
    this.memOffset3.getStyleClass().add("editable-cell");
    this.memOffset3.setOnEditCommit(e -> this.updateMemoryCell(e));
    this.memTable.setItems(Globals.memory.getCells());
    // Memory Segment Actions
    this.upBtn.setOnAction(e -> Globals.memory.up());
    this.downBtn.setOnAction(e -> Globals.memory.down());
    // Set memory table context menu
    MenuItem text = new MenuItem("Jump To Text Segment");
    text.setOnAction(e -> Globals.memory.text());
    this.setGraphic(text, "/resources/img/icons/jump.png");
    MenuItem data = new MenuItem("Jump To Data Segment");
    data.setOnAction(e -> Globals.memory.data());
    this.setGraphic(data, "/resources/img/icons/jump.png");
    MenuItem heap = new MenuItem("Jump To Heap Segment");
    heap.setOnAction(e -> Globals.memory.heap());
    this.setGraphic(heap, "/resources/img/icons/jump.png");
    MenuItem stack = new MenuItem("Jump To Stack Segment");
    stack.setOnAction(e -> Globals.memory.stack());
    this.setGraphic(stack, "/resources/img/icons/jump.png");
    MenuItem ascii = new MenuItem("ASCII Display Mode");
    ascii.setOnAction(e -> this.memoryDisplayAscii());
    this.setGraphic(ascii, "/resources/img/icons/ascii.png");
    MenuItem hex = new MenuItem("Hex Display Mode");
    hex.setOnAction(e -> this.memoryDisplayHex());
    this.setGraphic(hex, "/resources/img/icons/hex.png");
    MenuItem decimal = new MenuItem("Decimal Display Mode");
    decimal.setOnAction(e -> this.memoryDisplayDecimal());
    this.setGraphic(decimal, "/resources/img/icons/decimal.png");
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(text, data, heap, stack, new SeparatorMenuItem(), hex, decimal, ascii);
    this.memTable.setContextMenu(menu);
    /*
      Align table columns

      Ref:
      https://stackoverflow.com/questions/37423748/javafx-tablecolumns-headers-not-aligned-with-cells-due-to-vertical-scrollbar
    */
    Platform.runLater(() -> this.memTable.refresh());
    // disable table columns reordering (hacky and ugly)
    this.memTable.getColumns().addListener(new ListChangeListener() {
      @Override
      public void onChanged(Change change) {
        change.next();
        if (change.wasReplaced()) {
          memTable.getColumns().clear();
          memTable.getColumns().addAll(memAddress, memOffset0, memOffset1, memOffset2, memOffset3);
        }
      }
    });
  }

  /**
   * This method initializes RVI and RVF tables.
   */
  @SuppressWarnings("unchecked")
  private void initRegFiles() {
    // cell factory
    Callback<TableColumn<Register, String>,
      TableCell<Register, String>> cellFactory
          = (TableColumn<Register, String> p) -> new RegisterEditingCell();
    // RVI table
    this.rviMnemonic.setCellValueFactory(new PropertyValueFactory<Register, String>("mnemonic"));
    this.rviMnemonic.getStyleClass().add("editable-cell");
    this.rviNumber.setCellValueFactory(new PropertyValueFactory<Register, Integer>("number"));
    this.rviNumber.getStyleClass().add("editable-cell");
    this.rviValue.setCellValueFactory(new PropertyValueFactory<Register, String>("strValue"));
    this.rviValue.getStyleClass().add("editable-cell");
    this.rviValue.setCellFactory(cellFactory);
    this.rviValue.setOnEditCommit(e -> this.updateRVIRegister(e));
    this.rviTable.setItems(Globals.regfile.getRVI());
    // RVF table
    this.rvfMnemonic.setCellValueFactory(new PropertyValueFactory<Register, String>("mnemonic"));
    this.rvfMnemonic.getStyleClass().add("editable-cell");
    this.rvfNumber.setCellValueFactory(new PropertyValueFactory<Register, Integer>("number"));
    this.rvfNumber.getStyleClass().add("editable-cell");
    this.rvfValue.setCellValueFactory(new PropertyValueFactory<Register, String>("strValue"));
    this.rvfValue.getStyleClass().add("editable-cell");
    this.rvfValue.setCellFactory(cellFactory);
    this.rvfValue.setOnEditCommit(e -> this.updateRVFRegister(e));
    this.rvfTable.setItems(Globals.fregfile.getRVF());
    // set rvi table context menu
    MenuItem hex = new MenuItem("Hex Display Mode");
    hex.setOnAction(e -> this.rviDisplayHex());
    this.setGraphic(hex, "/resources/img/icons/hex.png");
    MenuItem decimal = new MenuItem("Decimal Display Mode");
    decimal.setOnAction(e -> this.rviDisplayDecimal());
    this.setGraphic(decimal, "/resources/img/icons/decimal.png");
    MenuItem unsigned = new MenuItem("Unsigned Display Mode");
    unsigned.setOnAction(e -> this.rviDisplayUnsigned());
    this.setGraphic(unsigned, "/resources/img/icons/unsigned.png");
    ContextMenu menu = new ContextMenu();
    menu.getItems().addAll(hex, decimal, unsigned);
    this.rviTable.setContextMenu(menu);
    // set rvf table context menu
    hex = new MenuItem("Hex Display Mode");
    hex.setOnAction(e -> this.rvfDisplayHex());
    this.setGraphic(hex, "/resources/img/icons/hex.png");
    MenuItem dfloat = new MenuItem("Float Display Mode");
    dfloat.setOnAction(e -> this.rvfDisplayFloat());
    this.setGraphic(dfloat, "/resources/img/icons/float.png");
    menu = new ContextMenu();
    menu.getItems().addAll(hex, dfloat);
    this.rvfTable.setContextMenu(menu);
    /*
      Align table columns

      Ref:
      https://stackoverflow.com/questions/37423748/javafx-tablecolumns-headers-not-aligned-with-cells-due-to-vertical-scrollbar
    */
    Platform.runLater(() -> {
      this.rviTable.refresh();
      this.rvfTable.refresh();
    });
    // disable table columns reordering (hacky and ugly)
    this.rviTable.getColumns().addListener(new ListChangeListener() {
      @Override
      public void onChanged(Change change) {
        change.next();
        if (change.wasReplaced()) {
          rviTable.getColumns().clear();
          rviTable.getColumns().addAll(rviMnemonic, rviNumber, rviValue);
        }
      }
    });
    this.rvfTable.getColumns().addListener(new ListChangeListener() {
      @Override
      public void onChanged(Change change) {
        change.next();
        if (change.wasReplaced()) {
          rvfTable.getColumns().clear();
          rvfTable.getColumns().addAll(rvfMnemonic, rvfNumber, rvfValue);
        }
      }
    });
  }

  /**
   * Changes memory display setting to hexadecimal.
   */
  private void memoryDisplayHex() {
    Settings.DISP_MEM_CELL = 0;
    for (MemoryCell cell: this.memTable.getItems())
      cell.update();
  }

  /**
   * Changes memory display setting to ascii.
   */
  private void memoryDisplayAscii() {
    Settings.DISP_MEM_CELL = 1;
    for (MemoryCell cell: this.memTable.getItems())
      cell.update();
  }

  /**
   * Changes memory display setting to decimal.
   */
  private void memoryDisplayDecimal() {
    Settings.DISP_MEM_CELL = 2;
    for (MemoryCell cell: this.memTable.getItems())
      cell.update();
  }

  /**
   * Changes rvi register display setting to decimal.
   */
  private void rviDisplayDecimal() {
    Settings.DISP_RVI_REG = 2;
    for (Register reg: this.rviTable.getItems())
      reg.update();
  }

  /**
   * Changes rvi register display setting to unsigned.
   */
  private void rviDisplayUnsigned() {
    Settings.DISP_RVI_REG = 1;
    for (Register reg: this.rviTable.getItems())
      reg.update();
  }

  /**
   * Changes rvi register display setting to hexadecimal.
   */
  private void rviDisplayHex() {
    Settings.DISP_RVI_REG = 0;
    for (Register reg: this.rviTable.getItems())
      reg.update();
  }

  /**
   * Changes rvf register display setting to hexadecimal.
   */
  private void rvfDisplayHex() {
    Settings.DISP_RVF_REG = 0;
    for (Register reg: this.rvfTable.getItems())
      reg.update();
  }

  /**
   * Changes rvf register display setting to float.
   */
  private void rvfDisplayFloat() {
    Settings.DISP_RVF_REG = 1;
    for (Register reg: this.rvfTable.getItems())
      reg.update();
  }

  /**
   * Updates an editable memory cell offset column.
   *
   * @param t cell edit event
   */
  private void updateMemoryCell(CellEditEvent<MemoryCell, String> t) {
    // get offset
    int offset = t.getTablePosition().getColumn() - 1;
    // get memory cell to get integer address
    MemoryCell cell = (MemoryCell)t.getTableView().getItems().get(t.getTablePosition().getRow());
    String newValue = t.getNewValue().trim();
    // convert input to an integer value
    try {
      // user enters a hex
      if (newValue.matches("^0[xX][0-9a-fA-F]+$"))
        Globals.memory.storeByte(cell.getIntAddress() + offset, Integer.parseInt(newValue.substring(2), 16));
      // user enters a decimal
      else
        Globals.memory.storeByte(cell.getIntAddress() + offset, Integer.parseInt(newValue));
    } catch (Exception e) {
      Message.warning("invalid memory cell value: " + newValue);
    }
    // always refresh table
    this.memTable.refresh();
  }

  /**
   * Updates an editable rvi register cell column.
   *
   * @param t cell edit event
   */
  private void updateRVIRegister(CellEditEvent<Register, String> t) {
    // get register to set value
    Register reg = (Register)t.getTableView().getItems().get(t.getTablePosition().getRow());
    // get user input
    String newValue = t.getNewValue().trim();
    try {
      // user enters a hex
      if (newValue.matches("^0[xX][0-9a-fA-F]+$"))
        reg.setValue(Integer.parseInt(newValue.substring(2), 16));
      // user enters a binary
      else if (newValue.matches("^0[bB][01]+$"))
        reg.setValue(Integer.parseInt(newValue.substring(2), 2));
      // user enters a decimal
      else
        reg.setValue(Integer.parseInt(newValue));
    } catch (Exception e) {
      Message.warning("invalid register value: " + newValue);
    }
    // always refresh table
    this.rviTable.refresh();
  }

  /**
   * Updates an editable rvf register cell column.
   *
   * @param t cell edit event
   */
  private void updateRVFRegister(CellEditEvent<Register, String> t) {
    // get register to set value
    Register reg = (Register)t.getTableView().getItems().get(t.getTablePosition().getRow());
    // get user input
    String newValue = t.getNewValue().trim();
    try {
      // user enters a hex value
      if (newValue.matches("^0[xX][0-9a-fA-F]+$"))
        reg.setValue(Integer.parseInt(newValue));
      // user enters a float
      else
        reg.setValue(Float.floatToIntBits(Float.parseFloat(newValue)));
    } catch (Exception e) {
      Message.warning("invalid register value: " + newValue);
    }
    this.rvfTable.refresh();
  }

  /**
   * Sets a graphic to a menu item.
   *
   * @param item menu item to add graphic
   * @param path menu item graphic path
   */
  private void setGraphic(MenuItem item, String path) {
    ImageView img = new ImageView();
    img.setFitWidth(20.0);
    img.setFitHeight(20.0);
    img.setImage(new Image(getClass().getResourceAsStream(path)));
    item.setGraphic(img);
  }

}
