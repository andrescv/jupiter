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

package vsim.riscv;

/**
 * The class MemorySegments contains useful memory segment constants
 */
public final class MemorySegments {

  /** memory address where the high reserved memory starts */
  public static final int RESERVED_HIGH_BEGIN = 0xbffffff1;

  /** memory address where the high reserved memory ends */
  public static final int RESERVED_HIGH_END = 0xffffffff;

  /** memory address where the low reserved memory starts */
  public static final int RESERVED_LOW_BEGIN = 0x00000000;

  /** memory address where the low reserved memory ends */
  public static final int RESERVED_LOW_END = 0x0000ffff;

  /** memory address where the static segment starts */
  public static final int STATIC_SEGMENT = 0x10000000;

  /** memory address where the text segment starts */
  public static final int TEXT_SEGMENT_BEGIN = 0x00010000;

  /** memory address where the text segment ends */
  public static final int TEXT_SEGMENT_END = 0x0fffffff;

  /** stack pointer memory address */
  public static final int STACK_POINTER = 0xbffffff0;

  /** global pointer memory address */
  public static final int GLOBAL_POINTER = 0x10008000;

  /** heap segment pointer */
  public static int HEAP_SEGMENT = -1;

  /** memory address where the heap segment starts */
  public static int HEAP_SEGMENT_BEGIN = -1;

  /** memory address where the rodata segment starts */
  public static int RODATA_SEGMENT_BEGIN = -1;

  /** memory address where the rodata segment ends */
  public static int RODATA_SEGMENT_END = -1;

}
