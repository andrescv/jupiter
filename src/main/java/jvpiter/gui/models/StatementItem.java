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

package jvpiter.gui.models;

import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;


/** Statement item for text table. */
public final class StatementItem extends RecursiveTreeObject<StatementItem> {

  /** statement breakpoint state */
  private final SimpleBooleanProperty bkpt;
  /** statement address */
  private final SimpleStringProperty address;
  /** statement machine code */
  private final SimpleStringProperty code;
  /** statement basic code */
  private final SimpleStringProperty basic;
  /** statement source code */
  private final SimpleStringProperty source;

  /**
   * Creates a new statement item.
   *
   * @param bkpt statement breakpoint state
   * @param address statement address
   * @param code statement machine code
   * @param basic statement basic code
   * @param source statement source code
   */
  public StatementItem(boolean bkpt, int address, int code, String basic, String source) {
    this.bkpt = new SimpleBooleanProperty(bkpt);
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
    this.code = new SimpleStringProperty(String.format("0x%08x", code));
    this.basic = new SimpleStringProperty(basic);
    this.source = new SimpleStringProperty(source);
  }

  /**
   * Returns the breakpoint property shown in the Bkpt column.
   *
   * @return statement breakpoint property
   */
  public SimpleBooleanProperty bkptProperty() {
    return bkpt;
  }

  /**
   * Returns the address property shown in the Address column.
   *
   * @return statement address property
   */
  public SimpleStringProperty addressProperty() {
    return address;
  }

  /**
   * Returns the code property shown in the Machine Code column.
   *
   * @return statement code property
   */
  public SimpleStringProperty codeProperty() {
    return code;
  }

  /**
   * Returns the basic property shown in the Basic Code column.
   *
   * @return statement basic property
   */
  public SimpleStringProperty basicProperty() {
    return basic;
  }

  /**
   * Returns the source property shown in the Source Code column.
   *
   * @return statement source property
   */
  public SimpleStringProperty sourceProperty() {
    return source;
  }

  /**
   * Verifies if the statement is an ebreak statement.
   *
   * @return {@code true} if the statement is an ebreak statement, {@code false} if not
   */
  public boolean isEbreak() {
    return basic.get().indexOf("ebreak") != -1;
  }

}
