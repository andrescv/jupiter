package vsim.assembler.statements;

import vsim.assembler.DebugInfo;
import vsim.riscv.instructions.MachineCode;


/**
 * The class Statement is used to represent a RISC-V program statement.
 */
public abstract class Statement {

  /** statement mnemonic, e.g add */
  protected String mnemonic;
  /** statement attached debug info */
  protected DebugInfo debug;
  /** statement attached machine code */
  protected MachineCode code;

  /**
   * Unique constructor that initializes a newly Statement object.
   *
   * @param mnemonic statement mnemonic
   * @param debug statement debug information
   */
  public Statement(String mnemonic, DebugInfo debug) {
    this.debug = debug;
    this.mnemonic = mnemonic;
    this.code = new MachineCode();
  }

  /**
   * This method tries to resolve all the relocation expansions (if any).
   *
   * @see vsim.linker.Relocation
   */
  public abstract void resolve();

  /**
   * This method tries to build the machine code that represents this statement.
   *
   * @param pc current program counter value
   * @see vsim.riscv.instructions.MachineCode
   */
  public abstract void build(int pc);

  /**
   * This method returns the machine code that represents the statement.
   * This method should be called after build().
   *
   * @return the machine code that represents the statement
   */
  public MachineCode result() {
    return this.code;
  }

  /**
   * This method returns the mnemonic of the statement.
   *
   * @return the mnemonic of the statement
   */
  public String getMnemonic() {
    return this.mnemonic;
  }

  /**
   * This method returns the debug information of the statement.
   *
   * @return the debug information of the statement
   */
  public DebugInfo getDebugInfo() {
    return this.debug;
  }

}
