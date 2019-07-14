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

package vsim.linker;

import java.util.ArrayList;
import java.util.HashMap;

import vsim.Flags;
import vsim.Globals;
import vsim.State;
import vsim.asm.stmts.Statement;
import vsim.exc.NonInstructionException;
import vsim.exc.SimulationException;
import vsim.riscv.instructions.MachineCode;
import vsim.utils.Data;


/** RISC-V linked program. */
public final class LinkedProgram {

  /** text segment */
  private final HashMap<Integer, Statement> text;
  /** static segment */
  private final ArrayList<Byte> data;
  /** program state */
  private final State state;
  /** .rodata start */
  private int rodataStart;
  /** .rodata end */
  private int rodataEnd;
  /** if .rodata segment exists */
  private boolean hasRodata;

  /** Creates a new and empty linked program. */
  protected LinkedProgram() {
    text = new HashMap<>();
    data = new ArrayList<>();
    state = new State();
    hasRodata = false;
  }

  /**
   * Adds a new statement to text segment.
   *
   * @param stmt statement
   */
  protected void add(Statement stmt) {
    text.put(Data.TEXT + text.size() * Data.WORD_LENGTH, stmt);
  }

  /**
   * Adds a new byte to static data segment.
   *
   * @param b byte
   */
  protected void add(byte b) {
    data.add(b);
  }

  /** Aligns static data segment to a word boundary. */
  protected void align() {
    for (int i = 0; i < Data.offsetToWordAlign(data.size()); i++) {
      data.add((byte) 0);
    }
  }

  /** Sets .rodata start address. */
  protected void rodataStart() {
    rodataStart = Data.TEXT + text.size() * Data.WORD_LENGTH;
  }

  /** Sets .rodata end address. */
  protected void rodataEnd() {
    rodataEnd = rodataStart + data.size() - 1;
    hasRodata = data.size() > 0;
  }

  /** Loads program in memory. */
  public void load() {
    int address = Data.TEXT;
    // load code
    for (int i = 0; i < text.size(); i++) {
      state.memory().privStoreWord(address, text.get(address).code().bits());
      address += Data.WORD_LENGTH;
    }
    // load data
    for (Byte b : data) {
      state.memory().privStoreByte(address, b);
      address++;
    }
    // set memory layout
    state.memory().setLayout(rodataStart - 1, rodataStart, rodataEnd, address, hasRodata, text.size() > 0);
  }

  /**
   * Returns program state.
   *
   * @return program state
   */
  public State getState() {
    return state;
  }

  /**
   * Returns all ebreak instructions address.
   *
   * @return all ebreak instructions address
   */
  public ArrayList<Integer> breaks() {
    ArrayList<Integer> breaks = new ArrayList<>();
    for (Integer addr : text.keySet()) {
      Statement stmt = text.get(addr);
      if (stmt.mnemonic().equals("ebreak")) {
        breaks.add(addr);
      }
    }
    breaks.trimToSize();
    return breaks;
  }

  /**
   * Returns all statements of the program.
   *
   * @return all statements of the program
   */
  public ArrayList<Statement> statements() {
    ArrayList<Statement> stmts = new ArrayList<>();
    int pc = Data.TEXT;
    while (true) {
      Statement stmt = text.get(pc);
      if (stmt == null) break;
      stmts.add(stmt);
      pc += Data.WORD_LENGTH;
    }
    stmts.trimToSize();
    return stmts;
  }

  /**
   * Returns static data.
   *
   * @return static data
   */
  public ArrayList<Byte> data() {
    return data;
  }

  /**
   * Returns next program statement.
   *
   * @return next program statement
   * @throws NonInstructionException if no more statements available
   */
  public Statement next() throws SimulationException {
    Statement stmt = text.get(state.xregfile().getProgramCounter());
    if (stmt != null) {
      return stmt;
    } else if (Flags.SELF_MODIFYING) {
      MachineCode code = new MachineCode(state.memory().loadWord(state.xregfile().getProgramCounter()));
      stmt = Globals.iset.decode(code);
      if (stmt != null) {
        return stmt;
      }
    }
    throw new NonInstructionException(state.xregfile().getProgramCounter());
  }

}
