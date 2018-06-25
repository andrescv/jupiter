/*
Copyright (C) 2018 Andres Castellanos

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

package vsim.riscv;


/**
 * The class MemorySegments contains useful memory segment constants.
 *
 *     Ref: Chapter 3: RISC-V Assembly Language, The RISC-V Reader
 *
 *                       +------------------+
 *                       |     reserved     |
 *                       +------------------+:- 0x7ffffffc
 *       SP 0xbffffff0 -:|      stack       |
 *                       |                  |
 *                       ********************
 *                       |                  |
 *                       |       heap       |
 *                       +------------------+
 *                       |   static data    |
 *       GP 0x10000000 -:+------------------+:- 0x10000000
 *                       |       text       |
 *       PC 0x00010000 -:+------------------+:- 0x00010000
 *                       |     reserved     |
 *                       +------------------+:- 0x00000000
 *
 *                       |------XLENGTH-----|
 */
public final class MemorySegments {

  private MemorySegments() {/* NOTHING */}

  /** memory address where the stack segment starts */
  public static final int STACK_SEGMENT  = 0x7ffffffc;

  /** memory address where the heap segment starts */
  public static final int HEAP_SEGMENT   = 0x10008000;

  /** memory address where the data segment starts */
  public static final int DATA_SEGMENT   = 0x10000000;

  /** memory addres where the text segment starts */
  public static final int TEXT_SEGMENT   = 0x00010000;

  /** stack pointer memory address */
  public static final int STACK_POINTER  = 0xbffffff0;

}
