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
  /** heap segment history */
  private final Stack<Integer> heapHist;
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
    this.heapHist = new Stack<Integer>();
    this.memHist = new Stack<HashMap<Integer, Byte>>();
    this.rviHist = new Stack<HashMap<String, Integer>>();
    this.rvfHist = new Stack<HashMap<String, Integer>>();
    // clear diffs
    Globals.memory.getDiff();
    Globals.regfile.getDiff();
    Globals.fregfile.getDiff();
  }

  /**
   * Pushes program counter and heap state.
   */
  public void pushPCAndHeap() {
    this.pcHist.push(Globals.regfile.getProgramCounter());
    this.heapHist.push(Globals.memory.getHeapPointer());
  }

  /**
   * Pushes memory, RVI and RVF state.
   */
  public void pushState() {
    // save diffs
    this.memHist.push(Globals.memory.getDiff());
    this.rviHist.push(Globals.regfile.getDiff());
    this.rvfHist.push(Globals.fregfile.getDiff());
  }

  /**
   * Restores previous memory, RVI and RVF state if possible.
   */
  public void pop() {
    // restore program counter
    if (!this.pcHist.isEmpty())
      Globals.regfile.setProgramCounter(this.pcHist.pop());
    // restore heap
    if (!this.heapHist.isEmpty())
      Globals.memory.restoreHeap(this.heapHist.pop());
    // restore memory
    if (!this.memHist.isEmpty())
      Globals.memory.restore(this.memHist.pop());
    // restore rvi register file
    if (!this.rviHist.isEmpty())
      Globals.regfile.restore(this.rviHist.pop());
    // restore rvf register file
    if (!this.rvfHist.isEmpty())
      Globals.fregfile.restore(this.rvfHist.pop());
  }

  /**
   * This methods pops all the history saved.
   */
  public void popAll() {
    while (!this.isEmpty()) this.pop();
  }

  /**
   * Indicates if the history is totally empty.
   *
   * @return true if the history is empty, false otherwise
   */
  public boolean isEmpty() {
    return this.pcHist.isEmpty() && this.heapHist.isEmpty() && this.memHist.isEmpty() &&
           this.rviHist.isEmpty() && this.rvfHist.isEmpty();
  }

}
