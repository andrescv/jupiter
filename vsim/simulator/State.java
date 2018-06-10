package vsim.simulator;

import vsim.riscv.Memory;
import vsim.riscv.RegisterFile;


public final class State {

  // RAM
  public static final Memory memory = new Memory();

  // RV register file
  public static final RegisterFile regfile = new RegisterFile();

  // reset state
  public static void reset() {
    // reset memory
    State.memory.reset();
    // reset registers
    State.regfile.reset();
  }

}
