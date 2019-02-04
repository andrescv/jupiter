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

import java.awt.Desktop;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.URI;
import javafx.application.Platform;
import javafx.scene.Cursor;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import vsim.gui.utils.Icons;
import vsim.utils.Message;


/** This class represents a generic exception dialog. */
public final class ExceptionDialog extends JFXDialog {

  /** Dialog stacktrace text area */
  private final TextArea textarea;

  /** Dialog title */
  private final Text title;

  /** Dialog message label */
  private final Label label;

  /** current exception */
  private String loc;

  /**
   * Creates a new exception dialog.
   *
   * @param root dialog container
   */
  public ExceptionDialog(StackPane root) {
    super();
    JFXDialogLayout layout = new JFXDialogLayout();
    title = new Text("");
    layout.setHeading(title);
    textarea = new TextArea();
    textarea.setEditable(false);
    VBox box = new VBox();
    box.setSpacing(10.0);
    VBox.setVgrow(textarea, Priority.ALWAYS);
    Label st = new Label("Stacktrace:");
    st.setId("stacktrace");
    Label msg = new Label(
        "You can go to the following link (https://git.io/fhSKB) to see if your problem has already been solved or create a new one by clicking on the button below");
    msg.setId("exceptionhint");
    box.getChildren().addAll(st, textarea, msg);
    VBox body = new VBox();
    label = new Label("");
    label.setId("exceptionmsg");
    VBox.setVgrow(box, Priority.ALWAYS);
    body.getChildren().addAll(label, box);
    body.setSpacing(20.0);
    layout.setBody(body);
    this.setDialogContainer(root);
    this.setContent(layout);
    this.setTransitionType(JFXDialog.DialogTransition.TOP);
    JFXButton send = new JFXButton("Create a GitHub Issue");
    send.setOnAction(e -> {
      Thread t = new Thread(() -> {
        String url = "https://github.com/andrescv/V-Sim/issues/new?";
        String query = "title=Unexpected%20Exception%20in%20" + loc + "&labels=bug";
        String newline = System.getProperty("line.separator");
        String text = textarea.getText().replace(newline, "%0A").replace("\t", "%09").replace(" ", "%20");
        query += "&body=%2A%2AStacktrace%2A%2A%3A%0A%60%60%60java%0A" + text + "%60%60%60";
        String link = url + query;
        try {
          Desktop.getDesktop().browse(new URI(link));
        } catch (Exception ex) {
          Message.runError("could not open online docs, try again later or go to: ");
        } finally {
          Platform.runLater(() -> this.close());
        }
      });
      t.setDaemon(true);
      t.start();
    });
    send.setGraphic(Icons.getImage("issueBtn"));
    send.setCursor(Cursor.HAND);
    send.setFont(Font.font("Roboto Bold", 14.0));
    send.setTextFill(Color.WHITE);
    send.setStyle("-fx-background-color: #0277bd;");
    JFXButton cancel = new JFXButton("Cancel");
    cancel.setGraphic(Icons.getImage("cancelBtn"));
    cancel.setCursor(Cursor.HAND);
    cancel.setFont(Font.font("Roboto Bold", 14.0));
    cancel.setTextFill(Color.WHITE);
    cancel.setStyle("-fx-background-color: #d32f2f;");
    cancel.setOnAction(e -> this.close());
    HBox actions = new HBox();
    actions.setSpacing(10.0);
    actions.getChildren().addAll(send, cancel);
    layout.setActions(actions);
  }

  /**
   * Shows dialog and reports exception with the stack trace.
   *
   * @param msg dialog message
   * @param e exception to show
   */
  public void show(String msg, Exception e) {
    StringWriter errors = new StringWriter();
    e.printStackTrace(new PrintWriter(errors));
    textarea.setText(errors.toString());
    label.setText(msg);
    Throwable rootCause = e;
    while (rootCause.getCause() != null && rootCause.getCause() != rootCause)
      rootCause = rootCause.getCause();
    loc = rootCause.getStackTrace()[0].getClassName() + "/" + rootCause.getStackTrace()[0].getMethodName();
    title.setText("Exception in " + loc);
    this.show();
  }

}
