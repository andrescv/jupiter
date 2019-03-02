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

package vsim.utils;

/** Instruction Statistics */
public final class Stats {

  /** global total of executed instructions */
  private long total;
  /** ALU executed instructions */
  private long alu;
  /** Jump executed instructions */
  private long jump;
  /** Branch executed instructions */
  private long branch;
  /** Memory executed instructions */
  private long memory;
  /** Other executed instructions */
  private long other;

  /** Creates a new Instruction Statistics instance */
  public Stats() {
    this.reset();
  }

  /** Increments ALU executed instructions */
  public void alu() {
    this.total += 1;
    this.alu += 1;
  }

  /** Increments jump executed instructions */
  public void jump() {
    this.total += 1;
    this.jump += 1;
  }

  /** Increments branch executed instructions */
  public void branch() {
    this.total += 1;
    this.branch += 1;
  }

  /** Increments memory executed instructions */
  public void memory() {
    this.total += 1;
    this.memory += 1;
  }

  /** Increments other executed instructions */
  public void other() {
    this.total += 1;
    this.other += 1;
  }

  public void reset() {
    this.total = 0;
    this.alu = 0;
    this.jump = 0;
    this.branch = 0;
    this.memory = 0;
    this.other = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public String toString() {
    return String.format(
        "Total %d, ALU: %d (%.2f), Jump: %d (%.2f), Branch: %d (%.2f), Memory: %d (%.2f), Other: %d (%.2f)", this.total,
        this.alu, (double) this.alu / this.total, this.jump, (double) this.jump / this.total, this.branch,
        (double) this.branch / this.total, this.memory, (double) this.memory / this.total, this.other,
        (double) this.other / this.total);
  }

}
