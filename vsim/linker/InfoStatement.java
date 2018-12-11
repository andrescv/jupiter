package vsim.linker;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;
import vsim.assembler.statements.Statement;
import javafx.beans.property.SimpleStringProperty;


/**
 * This class represents the info of a statement, only useful for GUI application.
 */
public final class InfoStatement {

  /** address where the statement was placed in memory */
  private final SimpleStringProperty address;
  /** machine code of the statement */
  private final SimpleStringProperty machineCode;
  /** line source code */
  private final SimpleStringProperty sourceCode;
  /** basic code */
  private final SimpleStringProperty basicCode;

  /**
   * Creates a info statement.
   *
   * @param address memory address where statement was placed in memory
   * @param stmt source statement
   */
  public InfoStatement(int address, Statement stmt) {
    MachineCode result = stmt.result();
    this.address = new SimpleStringProperty(String.format("0x%08x", address));
    this.machineCode = new SimpleStringProperty(result.toString());
    this.sourceCode = new SimpleStringProperty(stmt.getDebugInfo().getSource());
    this.basicCode = new SimpleStringProperty(Globals.iset.get(stmt.getMnemonic()).disassemble(result));
  }

  /**
   * Gets the memory address.
   *
   * @return memory address of the statement
   */
  public String getAddress() {
    return this.address.get();
  }

  /**
   * Gets the generated machine code.
   *
   * @return machine code of the statement
   */
  public String getMachineCode() {
    return this.machineCode.get();
  }

  /**
   * Gets the source code.
   *
   * @return source code of the statement
   */
  public String getSourceCode() {
    return this.sourceCode.get();
  }

  /**
   * Gets the basic code.
   *
   * @return basic (TAL) code
   */
  public String getBasicCode() {
    return this.basicCode.get();
  }

}
