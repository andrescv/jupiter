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

package vsim.gui.components;

import java.io.IOException;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ChangeListener;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXColorPicker;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDecorator;
import com.jfoenix.controls.JFXRadioButton;
import com.jfoenix.controls.JFXSlider;
import org.fxmisc.flowless.VirtualizedScrollPane;
import vsim.Settings;
import vsim.gui.utils.Icons;


/** This class is used for change and save editor settings. */
public final class EditorDialog {

  /** Newline */
  private static String NL = System.getProperty("line.separator");

  /** Sample text editor code */
  private static String SAMPLE_CODE = "; Meaningless Code Example :)" + NL + ".data" + NL
      + "%sstr: .asciiz \"hello world\\n\"" + NL + "%snum: .word 0xcafe" + NL + ".text" + NL + "main:" + NL
      + "%stail exit" + NL + "exit:" + NL + "%sli a0, 10" + NL + "%secall" + NL + "{}!; forced errors";

  /** Dialog stage */
  private Stage stage;

  /** Code area */
  private Editor editor;

  /** Font family combobox */
  @FXML private JFXComboBox<String> fontfamily;
  /** Font style combobox */
  @FXML private JFXComboBox<String> fontstyle;
  /** Font size slider */
  @FXML private JFXSlider fontsize;
  /** Tab size slider */
  @FXML private JFXSlider tabsize;
  /** Eclipse radio button option */
  @FXML private JFXRadioButton eclipse;
  /** Monokai radio button option */
  @FXML private JFXRadioButton monokai;
  /** Material radio button option */
  @FXML private JFXRadioButton material;
  /** Onelight radio button option */
  @FXML private JFXRadioButton onelight;
  /** Custom radio button option */
  @FXML private JFXRadioButton custom;
  /** Anchor pane for putting a code area */
  @FXML private AnchorPane anchor;
  /** Syntax color picker */
  @FXML private JFXColorPicker syntax;
  /** Directive color picker */
  @FXML private JFXColorPicker directive;
  /** keyword color picker */
  @FXML private JFXColorPicker keyword;
  /** Label color picker */
  @FXML private JFXColorPicker label;
  /** Identififer color picker */
  @FXML private JFXColorPicker identifier;
  /** Register color picker */
  @FXML private JFXColorPicker register;
  /** Number color picker */
  @FXML private JFXColorPicker number;
  /** Comment color picker */
  @FXML private JFXColorPicker comment;
  /** String color picker */
  @FXML private JFXColorPicker string;
  /** Backslash color picker */
  @FXML private JFXColorPicker backslash;
  /** Error color picker */
  @FXML private JFXColorPicker error;
  /** Background color picker */
  @FXML private JFXColorPicker background;
  /** Selection color picker */
  @FXML private JFXColorPicker selection;
  /** Lineno color picker */
  @FXML private JFXColorPicker lineno;
  /** Lineno background color picker */
  @FXML private JFXColorPicker linenobg;
  /** Caret color picker */
  @FXML private JFXColorPicker caret;
  /** Current line highlight color picker */
  @FXML private JFXColorPicker highlight;
  /** Auto-Indent check box */
  @FXML private JFXCheckBox autoIndent;

  /** Creates a new dialog for changing editor settings. */
  public EditorDialog() throws IOException {
    this.stage = new Stage();
    this.stage.setTitle("Editor Settings");
    this.stage.initModality(Modality.APPLICATION_MODAL);
    this.stage.setResizable(false);
    this.stage.getIcons().add(Icons.getFavicon());
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/EditorDialog.fxml"));
    loader.setController(this);
    Parent root = loader.load();
    this.editor = new Editor();
    this.editor.setEditable(false);
    VirtualizedScrollPane<Editor> scroll = new VirtualizedScrollPane<>(this.editor);
    AnchorPane.setBottomAnchor(scroll, 0.0);
    AnchorPane.setTopAnchor(scroll, 0.0);
    AnchorPane.setLeftAnchor(scroll, 0.0);
    AnchorPane.setRightAnchor(scroll, 0.0);
    this.anchor.getChildren().add(scroll);
    JFXDecorator decorator = new JFXDecorator(stage, root, false, false, false);
    decorator.setGraphic(Icons.getImage("logo"));
    Scene scene = new Scene(decorator, 704, 638);
    scene.getStylesheets().addAll(getClass().getResource("/css/jfoenix-fonts.css").toExternalForm(),
        getClass().getResource("/css/vsim-fonts.css").toExternalForm(),
        getClass().getResource("/css/vsim.css").toExternalForm());
    this.stage.setScene(scene);
    // enable color pickers only if custom option is selected
    this.syntax.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.directive.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.keyword.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.label.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.identifier.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.register.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.number.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.comment.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.string.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.backslash.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.error.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.background.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.selection.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.lineno.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.linenobg.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.caret.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    this.highlight.disableProperty().bind(Bindings.not(this.custom.selectedProperty()));
    // populate combo boxes
    this.fontstyle.setItems(FXCollections.observableArrayList("Bold", "Bold Italic", "Italic", "Regular"));
    this.fontfamily.setItems(FXCollections.observableArrayList(Font.getFamilies()));
  }

  /**
   * Action fired when a color picker changes.
   *
   * @param e action event
   */
  @FXML
  private void changeColor(ActionEvent e) {
    this.update();
  }

  /**
   * Action fired when save button is clicked.
   *
   * @param e action event
   */
  @FXML
  private void save(ActionEvent e) {
    // save syntax theme setting
    if (this.eclipse.isSelected())
      Settings.setCodeAreaSyntaxTheme("eclipse");
    if (this.material.isSelected())
      Settings.setCodeAreaSyntaxTheme("material");
    if (this.monokai.isSelected())
      Settings.setCodeAreaSyntaxTheme("monokai");
    if (this.onelight.isSelected())
      Settings.setCodeAreaSyntaxTheme("onelight");
    if (this.custom.isSelected())
      Settings.setCodeAreaSyntaxTheme("custom");
    String weight = "normal";
    String style = "normal";
    // update font weight and style
    switch ((String) this.fontstyle.getSelectionModel().getSelectedItem()) {
      case "Bold":
        weight = "bold";
        break;
      case "Bold Italic":
        weight = "bold";
        style = "italic";
        break;
      case "Italic":
        style = "italic";
        break;
      default:
        break;
    }
    Settings.setCodeAreaTabSize((int) Math.round(this.tabsize.getValue()));
    Settings.setCodeAreaAutoIndent(this.autoIndent.isSelected());
    Settings.setCodeAreaBG(this.toRgba(this.background.getValue()));
    Settings.setCodeAreaFontWeight(weight);
    Settings.setCodeAreaFontStyle(style);
    Settings.setCodeAreaFontFamily((String) this.fontfamily.getSelectionModel().getSelectedItem());
    Settings.setCodeAreaFontSize((int) Math.round(this.fontsize.getValue()));
    Settings.setCodeAreaSelection(this.toRgba(this.selection.getValue()));
    Settings.setCodeAreaLinenoColor(this.toRgba(this.lineno.getValue()));
    Settings.setCodeAreaLinenoBG(this.toRgba(this.linenobg.getValue()));
    Settings.setCodeAreaCaretColor(this.toRgba(this.caret.getValue()));
    Settings.setCodeAreaLineHighlight(this.toRgba(this.highlight.getValue()));
    Settings.setCodeAreaSyntaxColor(this.toRgba(this.syntax.getValue()));
    Settings.setCodeAreaDirectiveColor(this.toRgba(this.directive.getValue()));
    Settings.setCodeAreaKeywordColor(this.toRgba(this.keyword.getValue()));
    Settings.setCodeAreaLabelColor(this.toRgba(this.label.getValue()));
    Settings.setCodeAreaIdentifierColor(this.toRgba(this.identifier.getValue()));
    Settings.setCodeAreaRegisterColor(this.toRgba(this.register.getValue()));
    Settings.setCodeAreaNumberColor(this.toRgba(this.number.getValue()));
    Settings.setCodeAreaCommentColor(this.toRgba(this.comment.getValue()));
    Settings.setCodeAreaStringColor(this.toRgba(this.string.getValue()));
    Settings.setCodeAreaBackslashColor(this.toRgba(this.backslash.getValue()));
    Settings.setCodeAreaErrorColor(this.toRgba(this.error.getValue()));
  }

  /**
   * Action fired when save and close button is clicked.
   *
   * @param e action event
   */
  @FXML
  private void saveAndClose(ActionEvent e) {
    this.save(e);
    this.stage.hide();
  }

  /**
   * Action fired when cancel button is clicked.
   *
   * @param e action event
   */
  @FXML
  private void cancel(ActionEvent e) {
    this.stage.hide();
  }

  /** Updates settings and refreshes preview. */
  private void update() {
    String weight = "normal";
    String style = "normal";
    // update font weight and style
    switch ((String) this.fontstyle.getSelectionModel().getSelectedItem()) {
      case "Bold":
        weight = "bold";
        break;
      case "Bold Italic":
        weight = "bold";
        style = "italic";
        break;
      case "Italic":
        style = "italic";
        break;
      default:
        break;
    }
    // update editor text
    String tab = this.getTab();
    this.editor.setEditorText(String.format(EditorDialog.SAMPLE_CODE, tab, tab, tab, tab, tab));
    // update editor colors
    this.editor.setStyle(String.format(Editor.STYLE, this.toRgba(this.syntax.getValue()),
        this.toRgba(this.directive.getValue()), this.toRgba(this.keyword.getValue()),
        this.toRgba(this.label.getValue()), this.toRgba(this.identifier.getValue()),
        this.toRgba(this.register.getValue()), this.toRgba(this.number.getValue()),
        this.toRgba(this.comment.getValue()), this.toRgba(this.string.getValue()),
        this.toRgba(this.backslash.getValue()), this.toRgba(this.error.getValue()),
        this.toRgba(this.background.getValue()), this.toRgba(this.selection.getValue()),
        this.toRgba(this.lineno.getValue()), this.toRgba(this.linenobg.getValue()), this.toRgba(this.caret.getValue()),
        this.toRgba(this.highlight.getValue()), weight, style, this.fontfamily.getValue(),
        Math.round(this.fontsize.getValue())));
    // update auto-indent
    this.editor.setAutoIndent(this.autoIndent.isSelected());
    // update tab size
    this.editor.setTabSize((int) Math.round(this.tabsize.getValue()));
  }

  /**
   * Converts a color to RGBA web string.
   *
   * @param color color to convert
   * @return rgba web string
   */
  private String toRgba(Color color) {
    return String.format("rgba(%d, %d, %d, %.2f)", (int) (color.getRed() * 255), (int) (color.getGreen() * 255),
        (int) (color.getBlue() * 255), color.getOpacity());
  }

  /** Sets eclipse syntax theme. */
  private void loadEclipse() {
    this.syntax.setValue(Color.web("#000000"));
    this.directive.setValue(Color.web("#000099"));
    this.keyword.setValue(Color.web("#7f0055"));
    this.label.setValue(Color.web("#7f0055"));
    this.identifier.setValue(Color.web("#000000"));
    this.register.setValue(Color.web("#6a3e3e"));
    this.number.setValue(Color.web("#000000"));
    this.comment.setValue(Color.web("#407e60"));
    this.string.setValue(Color.web("#162cf6"));
    this.backslash.setValue(Color.web("#8abeb7"));
    this.error.setValue(Color.web("#ff0000"));
    this.background.setValue(Color.web("#ffffff"));
    this.selection.setValue(Color.web("#b6d6fd"));
    this.lineno.setValue(Color.web("#646464"));
    this.linenobg.setValue(Color.web("#ffffff"));
    this.caret.setValue(Color.web("#000000"));
    this.highlight.setValue(Color.web("#e8f2fe"));
    this.update();
  }

  /** Sets material syntax theme. */
  private void loadMaterial() {
    this.syntax.setValue(Color.web("#B2CCD6"));
    this.directive.setValue(Color.web("#82AAFF"));
    this.keyword.setValue(Color.web("#C792EA"));
    this.label.setValue(Color.web("#F78C6A"));
    this.identifier.setValue(Color.web("#FFCB6B"));
    this.register.setValue(Color.web("#DDDDDD"));
    this.number.setValue(Color.web("#F07178"));
    this.comment.setValue(Color.web("#545454"));
    this.string.setValue(Color.web("#C3E88D"));
    this.backslash.setValue(Color.web("#89DDF3"));
    this.error.setValue(Color.web("#EF4D13"));
    this.background.setValue(Color.web("#212121"));
    this.selection.setValue(Color.web("#3b3b3b"));
    this.lineno.setValue(Color.web("#B2CCD6"));
    this.linenobg.setValue(Color.web("#212121"));
    this.caret.setValue(Color.web("#009688"));
    this.highlight.setValue(Color.web("#1b1b1b"));
    this.update();
  }

  /** Sets monokai syntax theme. */
  private void loadMonokai() {
    this.syntax.setValue(Color.web("#abb3ba"));
    this.directive.setValue(Color.web("#66d9ef"));
    this.keyword.setValue(Color.web("#f92672"));
    this.label.setValue(Color.web("#fc9867"));
    this.identifier.setValue(Color.web("#b0ec38"));
    this.register.setValue(Color.web("#fd971f"));
    this.number.setValue(Color.web("#ae81ff"));
    this.comment.setValue(Color.web("rgba(171, 179, 186, 0.5)"));
    this.string.setValue(Color.web("#e6db74"));
    this.backslash.setValue(Color.web("#e91f24"));
    this.error.setValue(Color.web("#e62a19"));
    this.background.setValue(Color.web("#222222"));
    this.selection.setValue(Color.web("rgba(230, 42, 25, 0.3)"));
    this.lineno.setValue(Color.web("rgba(171, 179, 186, 0.5)"));
    this.linenobg.setValue(Color.web("#262626"));
    this.caret.setValue(Color.web("rgba(200, 200, 200, 0.8)"));
    this.highlight.setValue(Color.web("rgba(0, 0, 0, 0.1)"));
    this.update();
  }

  /** Sets one light syntax theme. */
  private void loadOnelight() {
    this.syntax.setValue(Color.web("#383a42"));
    this.directive.setValue(Color.web("#66d9ef"));
    this.keyword.setValue(Color.web("#a626a4"));
    this.label.setValue(Color.web("#ff6188"));
    this.identifier.setValue(Color.web("#0184bc"));
    this.register.setValue(Color.web("#e45649"));
    this.number.setValue(Color.web("#986801"));
    this.comment.setValue(Color.web("#a0a1a7"));
    this.string.setValue(Color.web("#50a14f"));
    this.backslash.setValue(Color.web("#0184bc"));
    this.error.setValue(Color.web("#ff1414"));
    this.background.setValue(Color.web("#fafafa"));
    this.selection.setValue(Color.web("#e5e5e6"));
    this.lineno.setValue(Color.web("#9d9d9f"));
    this.linenobg.setValue(Color.web("#e5e5e6"));
    this.caret.setValue(Color.web("#526fff"));
    this.highlight.setValue(Color.web("rgba(56, 58, 66, 0.05)"));
    this.update();
  }

  /**
   * Gets current tab represented by {@code Settings.CODE_AREA_TAB_SIZE} spaces.
   *
   * @return current tab of {@code Settings.CODE_AREA_TAB_SIZE} spaces
   */
  private String getTab() {
    String tab = "";
    for (int i = 0; i < (int) Math.round(this.tabsize.getValue()); i++) {
      tab += " ";
    }
    return tab;
  }

  /** Loads saved settings. */
  private void loadSettings() {
    // load syntax colors
    this.syntax.setValue(Color.web(Settings.CODE_AREA_SYNTAX));
    this.directive.setValue(Color.web(Settings.CODE_AREA_DIRECTIVE));
    this.keyword.setValue(Color.web(Settings.CODE_AREA_KEYWORD));
    this.label.setValue(Color.web(Settings.CODE_AREA_LABEL));
    this.identifier.setValue(Color.web(Settings.CODE_AREA_IDENTIFIER));
    this.register.setValue(Color.web(Settings.CODE_AREA_REGISTER));
    this.number.setValue(Color.web(Settings.CODE_AREA_NUMBER));
    this.comment.setValue(Color.web(Settings.CODE_AREA_COMMENT));
    this.string.setValue(Color.web(Settings.CODE_AREA_STRING));
    this.backslash.setValue(Color.web(Settings.CODE_AREA_BACKSLASH));
    this.error.setValue(Color.web(Settings.CODE_AREA_ERROR));
    this.background.setValue(Color.web(Settings.CODE_AREA_BG));
    this.selection.setValue(Color.web(Settings.CODE_AREA_SELECTION));
    this.lineno.setValue(Color.web(Settings.CODE_AREA_LINENO_COLOR));
    this.linenobg.setValue(Color.web(Settings.CODE_AREA_LINENO_BG));
    this.caret.setValue(Color.web(Settings.CODE_AREA_CARET_COLOR));
    this.highlight.setValue(Color.web(Settings.CODE_AREA_LINE_HIGHLIGHT));
    // load syntax theme setting
    switch (Settings.CODE_AREA_SYNTAX_THEME) {
      case "eclipse":
        this.eclipse.setSelected(true);
        break;
      case "material":
        this.material.setSelected(true);
        break;
      case "monokai":
        this.monokai.setSelected(true);
        break;
      case "onelight":
        this.onelight.setSelected(true);
        break;
      case "custom":
        this.custom.setSelected(true);
        break;
    }
    // load auto-indent settings
    this.autoIndent.setSelected(Settings.CODE_AREA_AUTO_INDENT);
    // load font settings
    if (Settings.CODE_AREA_FONT_STYLE.equals("normal") && Settings.CODE_AREA_FONT_WEIGHT.equals("normal"))
      this.fontstyle.getSelectionModel().select("Regular");
    else if (Settings.CODE_AREA_FONT_STYLE.equals("italic") && Settings.CODE_AREA_FONT_WEIGHT.equals("normal"))
      this.fontstyle.getSelectionModel().select("Italic");
    else if (Settings.CODE_AREA_FONT_STYLE.equals("normal") && Settings.CODE_AREA_FONT_WEIGHT.equals("bold"))
      this.fontstyle.getSelectionModel().select("Bold");
    else
      this.fontstyle.getSelectionModel().select("Bold Italic");
    this.fontfamily.getSelectionModel().select(Settings.CODE_AREA_FONT_FAMILY);
    this.fontsize.setValue(Settings.CODE_AREA_FONT_SIZE.get());
    // load tab size
    this.tabsize.setValue(Settings.CODE_AREA_TAB_SIZE);
    // change preview editor text based on settings
    String tab = this.getTab();
    this.editor.setEditorText(String.format(EditorDialog.SAMPLE_CODE, tab, tab, tab, tab, tab));
    this.editor.setTabSize(Settings.CODE_AREA_TAB_SIZE);
    this.editor.setAutoIndent(Settings.CODE_AREA_AUTO_INDENT);
  }

  /** Shows dialog and waits. */
  public void showAndWait() {
    // load settings
    this.loadSettings();
    // add listeners
    ChangeListener<Boolean> themeChange = (obs, oldVal, newVal) -> {
      if (newVal) {
        if (obs == eclipse.selectedProperty())
          this.loadEclipse();
        else if (obs == material.selectedProperty())
          this.loadMaterial();
        else if (obs == monokai.selectedProperty())
          this.loadMonokai();
        else
          this.loadOnelight();
      }
    };
    ChangeListener<Boolean> autoIndentChange = (obs, oldVal, newVal) -> this.update();
    ChangeListener<String> comboChange = (obs, oldVal, newVal) -> this.update();
    ChangeListener<Number> fontSizeChange = (e, oldVal, newVal) -> {
      this.fontsize.setValue(Math.round(newVal.doubleValue()));
      this.update();
    };
    ChangeListener<Number> tabSizeChange = (e, oldVal, newVal) -> {
      this.tabsize.setValue(Math.round(newVal.doubleValue()));
      this.update();
    };
    this.fontsize.valueProperty().addListener(fontSizeChange);
    this.tabsize.valueProperty().addListener(tabSizeChange);
    this.fontstyle.valueProperty().addListener(comboChange);
    this.fontfamily.valueProperty().addListener(comboChange);
    this.eclipse.selectedProperty().addListener(themeChange);
    this.material.selectedProperty().addListener(themeChange);
    this.monokai.selectedProperty().addListener(themeChange);
    this.onelight.selectedProperty().addListener(themeChange);
    this.autoIndent.selectedProperty().addListener(autoIndentChange);
    this.stage.showAndWait();
    // remove listeners
    this.fontsize.valueProperty().removeListener(fontSizeChange);
    this.tabsize.valueProperty().removeListener(tabSizeChange);
    this.fontstyle.valueProperty().removeListener(comboChange);
    this.fontfamily.valueProperty().removeListener(comboChange);
    this.eclipse.selectedProperty().removeListener(themeChange);
    this.material.selectedProperty().removeListener(themeChange);
    this.monokai.selectedProperty().removeListener(themeChange);
    this.onelight.selectedProperty().removeListener(themeChange);
    this.autoIndent.selectedProperty().removeListener(autoIndentChange);
  }
}
