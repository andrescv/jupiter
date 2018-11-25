package vsim.gui.controllers;

import java.net.URL;
import javafx.fxml.FXML;
import javafx.stage.Stage;
import java.util.ResourceBundle;
import javafx.scene.control.Tab;
import javafx.fxml.Initializable;
import vsim.gui.components.Console;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TreeView;
import javafx.scene.control.TableView;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTabPane;
import com.jfoenix.controls.JFXComboBox;
import javafx.scene.control.TableColumn;


public class MainController implements Initializable {

  private Stage stage;

  @FXML private MenuItem newFile;
  @FXML private MenuItem openFile;
  @FXML private MenuItem openFolder;
  @FXML private MenuItem save;
  @FXML private MenuItem saveAs;
  @FXML private MenuItem saveAll;
  @FXML private MenuItem closeTab;
  @FXML private MenuItem closeAll;
  @FXML private MenuItem quit;
  @FXML private MenuItem assemble;
  @FXML private MenuItem go;
  @FXML private MenuItem step;
  @FXML private MenuItem backstep;
  @FXML private MenuItem reset;
  @FXML private MenuItem clearBreakpoints;
  @FXML private MenuItem undo;
  @FXML private MenuItem redo;
  @FXML private MenuItem cut;
  @FXML private MenuItem copy;
  @FXML private MenuItem paste;
  @FXML private MenuItem selectAll;
  @FXML private MenuItem preferences;
  @FXML private MenuItem findInBuffer;
  @FXML private MenuItem replaceInBuffer;
  @FXML private MenuItem help;
  @FXML private MenuItem about;

  @FXML private JFXTabPane main;
  @FXML private JFXTabPane editor;
  @FXML private JFXTabPane console;
  @FXML private JFXTabPane hardware;

  @FXML private Tab editorTab;
  @FXML private Tab simTab;
  @FXML private Tab rviTab;
  @FXML private Tab rvfTab;
  @FXML private Tab memTab;
  @FXML private Tab logTab;
  @FXML private Tab ioTab;

  @FXML private TreeView<?> tree;

  @FXML private JFXButton goBtn;
  @FXML private JFXButton stepBtn;
  @FXML private JFXButton backstepBtn;
  @FXML private JFXButton resetBtn;
  @FXML private JFXButton dumpBtn;
  @FXML private JFXButton upBtn;
  @FXML private JFXButton downBtn;

  @FXML private TableView<?> textTable;
  @FXML private TableColumn<?, ?> txtAddrCol;
  @FXML private TableColumn<?, ?> txtMachineCode;
  @FXML private TableColumn<?, ?> txtSourceCode;
  @FXML private TableColumn<?, ?> txtBasicCode;

  @FXML private TableView<?> rviTable;
  @FXML private TableColumn<?, ?> rviMnemonic;
  @FXML private TableColumn<?, ?> rviNumber;
  @FXML private TableColumn<?, ?> rviValue;

  @FXML private TableView<?> rvfTable;
  @FXML private TableColumn<?, ?> rvfMnemonic;
  @FXML private TableColumn<?, ?> rvfNumber;
  @FXML private TableColumn<?, ?> rvfValue;

  @FXML private TableView<?> memTable;
  @FXML private TableColumn<?, ?> memAddress;
  @FXML private TableColumn<?, ?> memOffset0;
  @FXML private TableColumn<?, ?> memOffset1;
  @FXML private TableColumn<?, ?> memOffset2;
  @FXML private TableColumn<?, ?> memOffset3;

  @FXML private JFXComboBox<?> memCombo;

  @FXML private Console vsimLog;
  @FXML private Console vsimIO;

  @Override
  public void initialize(URL location, ResourceBundle resources) {
    this.newFile.setOnAction(e -> this.newFile());
    this.openFile.setOnAction(e -> this.openFile());
    this.openFolder.setOnAction(e -> this.openFolder());
    this.save.setOnAction(e -> this.save());
    this.saveAll.setOnAction(e -> this.saveAll());
    this.closeTab.setOnAction(e -> this.closeTab());
    this.closeAll.setOnAction(e -> this.closeAll());
    this.quit.setOnAction(e -> this.quit());
  }

  public void setStage(Stage stage) {
    this.stage = stage;
  }

  public void newFile() {

  }

  public void openFile() {

  }

  public void openFolder() {

  }

  public void save() {

  }

  public void saveAll() {

  }

  public void closeTab() {

  }

  public void closeAll() {

  }

  public void quit() {
    this.stage.close();
  }

}
