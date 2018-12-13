package vsim.simulator;

import vsim.Globals;
import java.util.Stack;
import java.util.HashMap;


/**
 * This class represents a simple simulator state history.
 */
public final class History {

  /** program counter history */
  private final Stack<Integer> pcHist;
  /** main memory history */
  private final Stack<HashMap<Integer, Byte>> memHist;
  /** rvi register file history */
  private final Stack<HashMap<String, Integer>> rviHist;
  /** rvf register file history */
  private final Stack<HashMap<String, Integer>> rvfHist;

  /**
   * Creates a new history object.
   */
  public History() {
    this.pcHist = new Stack<Integer>();
    this.memHist = new Stack<HashMap<Integer, Byte>>();
    this.rviHist = new Stack<HashMap<String, Integer>>();
    this.rvfHist = new Stack<HashMap<String, Integer>>();
  }

  /**
   * Pushes memory, RVI and RVF state.
   */
  public void push() {
    this.pcHist.push(Globals.regfile.getProgramCounter());
    this.memHist.push(Globals.memory.getState());
    this.rviHist.push(Globals.regfile.getState());
    this.rvfHist.push(Globals.fregfile.getState());
  }

  /**
   * Restores previous memory, RVI and RVF state if possible.
   *
   * @return true if success, false if history is empty
   */
  public boolean pop() {
    if (this.memHist.size() > 0) {
      Globals.regfile.setProgramCounter(this.pcHist.pop());
      Globals.memory.setState(this.memHist.pop());
      Globals.regfile.setState(this.rviHist.pop());
      Globals.fregfile.setState(this.rvfHist.pop());
      return true;
    }
    return false;
  }

  /**
   * This methods pops all the history saved.
   */
  public void popAll() {
    while (this.pop());
  }

}
