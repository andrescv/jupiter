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

package jvpiter.sim;

import java.util.ArrayList;
import java.util.HashMap;

import jvpiter.Flags;
import jvpiter.riscv.hardware.Cache.CacheBackup;


/** Jvpiter program state history */
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
  /** cache history */
  private final ArrayList<CacheBackup> cacheHist;

  /** Creates a new history. */
  public History() {
    pcHist = new ArrayList<>();
    heapHist = new ArrayList<>();
    memHist = new ArrayList<>();
    rviHist = new ArrayList<>();
    rvfHist = new ArrayList<>();
    cacheHist = new ArrayList<>();
  }

  /**
   * Stores program counter and heap pointer in history.
   *
   * @param state program state
   */
  public void savePCAndHeap(State state) {
    pcHist.add(state.xregfile().getProgramCounter());
    heapHist.add(state.memory().getHeapPointer());
    if (pcHist.size() > Flags.HIST_SIZE) {
      pcHist.remove(0);
      heapHist.remove(0);
      pcHist.trimToSize();
      heapHist.trimToSize();
    }
  }

  /**
   * Stores memory, cache and register files in history.
   *
   * @param state program state
   */
  public void saveMemAndRegs(State state) {
    memHist.add(state.memory().getDiff());
    rviHist.add(state.xregfile().getDiff());
    rvfHist.add(state.fregfile().getDiff());
    cacheHist.add(state.memory().cache().getDiff());
    if (memHist.size() > Flags.HIST_SIZE) {
      memHist.remove(0);
      rviHist.remove(0);
      rvfHist.remove(0);
      cacheHist.remove(0);
      memHist.trimToSize();
      rviHist.trimToSize();
      rvfHist.trimToSize();
      cacheHist.trimToSize();
    }
  }

  /** Restores state one step. */
  public void restore(State state) {
    // restore program counter and memory heap
    if (!pcHist.isEmpty()) {
      state.xregfile().setProgramCounter(pcHist.remove(pcHist.size() - 1));
      state.memory().setHeapPointer(heapHist.remove(heapHist.size() - 1));
    }
    // restore memory, cache, rvi and rvf register files
    if (!memHist.isEmpty()) {
      state.memory().restore(memHist.remove(memHist.size() - 1));
      state.xregfile().restore(rviHist.remove(rviHist.size() - 1));
      state.fregfile().restore(rvfHist.remove(rvfHist.size() - 1));
      state.memory().cache().restore(cacheHist.remove(cacheHist.size() - 1));
    }
  }

  /** Clears history. */
  public void reset() {
    pcHist.clear();
    heapHist.clear();
    memHist.clear();
    rviHist.clear();
    rvfHist.clear();
    cacheHist.clear();
  }

  /**
   * Verifies if history is empty.
   *
   * @return {@code true} if history is empty, false if not
   */
  public boolean empty() {
    return pcHist.isEmpty() && heapHist.isEmpty() && memHist.isEmpty()
      && rviHist.isEmpty() && rvfHist.isEmpty() && cacheHist.isEmpty();
  }

}
