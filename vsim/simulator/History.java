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

package vsim.simulator;

import java.util.ArrayList;
import java.util.HashMap;
import vsim.Globals;
import vsim.Settings;


/**
 * This class represents a simple simulator state history.
 */
public final class History {

  /** program counter history */
  private final ArrayList<Integer> pcHist;
  /** heap segment history */
  private final ArrayList<Integer> heapHist;
  /** main memory history */
  private final ArrayList<HashMap<Integer, Byte>> memHist;
  /** rvi register file history */
  private final ArrayList<HashMap<String, Integer>> rviHist;
  /** rvf register file history */
  private final ArrayList<HashMap<String, Integer>> rvfHist;

  /**
   * Creates a new history object.
   */
  public History() {
    this.pcHist = new ArrayList<Integer>();
    this.heapHist = new ArrayList<Integer>();
    this.memHist = new ArrayList<HashMap<Integer, Byte>>();
    this.rviHist = new ArrayList<HashMap<String, Integer>>();
    this.rvfHist = new ArrayList<HashMap<String, Integer>>();
    Status.EMPTY.set(true);
  }

  /**
   * Pushes program counter and heap state.
   */
  public void pushPCAndHeap() {
    this.pcHist.add(Globals.regfile.getProgramCounter());
    this.heapHist.add(Globals.memory.getHeapPointer());
    if (this.pcHist.size() > Settings.HIST_SIZE) {
      this.pcHist.remove(0);
      this.heapHist.remove(0);
      this.pcHist.trimToSize();
      this.heapHist.trimToSize();
    }
    Status.EMPTY.set(this.isEmpty());
  }

  /**
   * Pushes memory, RVI and RVF state.
   */
  public void pushState() {
    // save diffs
    this.memHist.add(Globals.memory.getDiff());
    this.rviHist.add(Globals.regfile.getDiff());
    this.rvfHist.add(Globals.fregfile.getDiff());
    if (this.memHist.size() > Settings.HIST_SIZE) {
      this.memHist.remove(0);
      this.rviHist.remove(0);
      this.rvfHist.remove(0);
      this.memHist.trimToSize();
      this.rviHist.trimToSize();
      this.rvfHist.trimToSize();
    }
    Status.EMPTY.set(this.isEmpty());
  }

  /**
   * Restores previous memory, RVI and RVF state if possible.
   */
  public void pop() {
    // restore program counter and memory heap
    if (!this.pcHist.isEmpty()) {
      Globals.regfile.setProgramCounter(this.pcHist.remove(this.pcHist.size() - 1));
      Globals.memory.restoreHeap(this.heapHist.remove(this.heapHist.size() - 1));
    }
    // restore memory, rvi and rvf register files
    if (!this.memHist.isEmpty()) {
      Globals.memory.restore(this.memHist.remove(this.memHist.size() - 1));
      Globals.regfile.restore(this.rviHist.remove(this.rviHist.size() - 1));
      Globals.fregfile.restore(this.rvfHist.remove(this.rvfHist.size() - 1));
    }
    Status.EMPTY.set(this.isEmpty());
  }

  /**
   * This methods clears all the history saved.
   */
  public void reset() {
    // clear history
    this.pcHist.clear();
    this.heapHist.clear();
    this.memHist.clear();
    this.rviHist.clear();
    this.rvfHist.clear();
    Status.EMPTY.set(true);
  }

  /**
   * Indicates if the history is totally empty.
   *
   * @return true if the history is empty, false otherwise
   */
  public boolean isEmpty() {
    return this.pcHist.isEmpty() && this.heapHist.isEmpty() && this.memHist.isEmpty() && this.rviHist.isEmpty()
        && this.rvfHist.isEmpty();
  }

}
