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

package jvpiter.utils;

import jvpiter.riscv.instructions.Format;
import jvpiter.riscv.instructions.InstructionField;
import jvpiter.riscv.instructions.MachineCode;


/** Contains useful methods, constants and masks for data manipulation in Jvpiter. */
public final class Data {

  /** platform independent newline */
  public static final String EOL = System.getProperty("line.separator");

  /** stack pointer memory address */
  public static final int STACK_POINTER = 0xbffffff0;
  /** global pointer memory address */
  public static final int GLOBAL_POINTER = 0x10008000;

  /** represents the end address of the high reserved memory */
  public static final int RESERVED_HIGH_START = 0xbffffff1;
  /** represents the end address of the high reserved memory */
  public static final int RESERVED_HIGH_END = 0xffffffff;
  /** represents the start address of the low reserved memory */
  public static final int RESERVED_LOW_START = 0x00000000;
  /** represents the end address of the low reserved memory */
  public static final int RESERVED_LOW_END = 0x0000ffff;
  /** represents the start address of the text segment */
  public static final int TEXT = 0x00010000;

  /** byte int mask */
  public static final int BYTE_MASK = 0xff;
  /** half int mask */
  public static final int HALF_MASK = 0xffff;
  /** word int mask */
  public static final int WORD_MASK = 0xffffffff;

  /** byte length in bytes */
  public static final int BYTE_LENGTH = 1;
  /** half length in bytes */
  public static final int HALF_LENGTH = 2;
  /** word length in bytes */
  public static final int WORD_LENGTH = 4;

  /** byte length in bits */
  public static final int BYTE_LENGTH_BITS = 8;
  /** half length in bits */
  public static final int HALF_LENGTH_BITS = 16;
  /** word length in bits */
  public static final int WORD_LENGTH_BITS = 32;

  /** byte min value */
  public static final int BYTE_MIN_VALUE = -128;
  /** byte max value */
  public static final int BYTE_MAX_VALUE = 127;
  /** half min value */
  public static final int HALF_MIN_VALUE = -32768;
  /** half max value */
  public static final int HALF_MAX_VALUE = 32767;

  /** floating point fraction/mantissa integer mask */
  public static final int FRACTION_MASK = 0x007fffff;
  /** floating point exponent integer mask */
  public static final int EXPONENT_MASK = 0x7f800000;
  /** floating point sign integer mask */
  public static final int SIGN_MASK = 0x80000000;

  /**
   * Sign-extends a value of N bits (N &lt; 32) to 32 bits.
   *
   * @param value the value to sign-extend
   * @param bits the number of bits that this number represents
   * @return the sign-extended value
   */
  public static int signExtend(int value, int bits) {
    int N = Math.max(0, Math.min(bits, 32));
    int shift = WORD_LENGTH_BITS - N;
    return (value << shift) >> shift;
  }

  /**
   * Sign-extends a byte to 32 bits.
   *
   * @param value the byte to sign-extend
   * @return the sign-extended byte
   */
  public static int signExtendByte(int value) {
    return signExtend(value, BYTE_LENGTH_BITS);
  }

  /**
   * Sign-extends a half to 32 bits.
   *
   * @param value the half to sign-extend
   * @return the sign-extended half
   */
  public static int signExtendHalf(int value) {
    return signExtend(value, HALF_LENGTH_BITS);
  }

  /**
   * Verifies if an address is aligned to a word boundary.
   *
   * @param address the address to verify
   * @return true if address is word aligned, false if not
   */
  public static boolean isWordAligned(int address) {
    return Integer.remainderUnsigned(address, WORD_LENGTH) == 0;
  }

  /**
   * Aligns an address to a word boundary if necessary.
   *
   * @param address the address to word align
   * @return the word aligned address
   */
  public static int alignToWordBoundary(int address) {
    if (!isWordAligned(address)) {
      int offset = WORD_LENGTH - Integer.remainderUnsigned(address, WORD_LENGTH);
      return address + offset;
    }
    return address;
  }

  /**
   * Returns the necessary offset to word align an address.
   *
   * @param address the address to check
   * @return offset to word align an address
   */
  public static int offsetToWordAlign(int address) {
    if (!isWordAligned(address)) {
      return WORD_LENGTH - Integer.remainderUnsigned(address, WORD_LENGTH);
    }
    return 0;
  }

  /**
   * Verifies if a value is in a given range [low, high].
   *
   * @param value the value to verify
   * @param low the low value of the range (included)
   * @param high the high value of the range (included)
   * @return true if the value is in the range, false if not
   */
  public static boolean inRange(int value, int low, int high) {
    long v = Integer.toUnsignedLong(value);
    long l = Integer.toUnsignedLong(low);
    long h = Integer.toUnsignedLong(high);
    return ((v - l) & 0xffffffffL) <= ((h - l) & 0xffffffffL);
  }

  /**
   * Verifies if a value is a valid half value.
   *
   * @param value the value to verify
   * @return true if the value is a valid half value, false if not
   */
  public static boolean validHalf(int value) {
    return ((value & ~HALF_MASK) == 0) || inRange(value, HALF_MIN_VALUE, HALF_MAX_VALUE);
  }

  /**
   * Verifies if a value is a valid byte value.
   *
   * @param value the value to verify
   * @return true if the value is a valid byte value, false if not
   */
  public static boolean validByte(int value) {
    return ((value & ~BYTE_MASK) == 0) || inRange(value, BYTE_MIN_VALUE, BYTE_MAX_VALUE);
  }

  /**
   * Verifies if a float value is a signaling NaN.
   *
   * @param f float value to verify
   * @return true if the float value is a signaling NaN, false if not
   */
  public static boolean signalingNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) == 0);
  }

  /**
   * Verifies if a float value is a quiet NaN.
   *
   * @param f float value to verify
   * @return true if the float value is a quiet NaN, false if not.
   */
  public static boolean quietNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) != 0);
  }

  /**
   * Parses an integer value from a string constant.
   *
   * @param number string number in hex, bin or dec
   * @return parsed int number
   */
  public static int atoi(String number) {
    if (number != null && !number.trim().equals("")) {
      number = number.trim();
      if (number.matches("0[xX][0-9a-fA-F]+"))
        return Integer.parseUnsignedInt(number.substring(2), 16);
      else if (number.matches("0[bB][01]+"))
        return Integer.parseUnsignedInt(number.substring(2), 2);
      else
        return Integer.parseInt(number);
    }
    return 0;
  }

  /**
   * Parses a float value from a string constant.
   *
   * @param number string number in hex, bin or float
   * @return parsed float number
   */
  public static float atof(String number) {
    if (number != null && !number.trim().equals("")) {
      number = number.trim();
      if (number.matches("0[xX][0-9a-fA-F]+"))
        return Float.intBitsToFloat(Integer.parseUnsignedInt(number.substring(2), 16));
      else if (number.matches("0[bB][01]+"))
        return Float.intBitsToFloat(Integer.parseUnsignedInt(number.substring(2), 2));
      else
        return Float.parseFloat(number);
    }
    return 0.0f;
  }

  /**
   * Verifies if the given value is a power of 2.
   *
   * @param value the value to verify
   * @return true if the given value is a power of 2, false if not
   */
  public static boolean isPowerOf2(int value) {
    return value != 0 && ((value & (value - 1)) == 0);
  }

  /**
   * Returns the immediate value from machine code given a instruction format.
   *
   * @param code machine code to extract the immediate value
   * @param format instruction format
   */
  public static int imm(MachineCode code, Format format) {
    switch (format) {
      case I:
        return signExtend(code.get(InstructionField.IMM_11_0), 12);
      case S:
        int simm_11_5 = code.get(InstructionField.IMM_11_5);
        int simm_4_0 = code.get(InstructionField.IMM_4_0);
        int simm = (simm_11_5 << 5) | simm_4_0;
        return Data.signExtend(simm, 12);
      case B:
        int bimm_4_1 = code.get(InstructionField.IMM_4_1);
        int bimm_10_5 = code.get(InstructionField.IMM_10_5);
        int bimm_11 = code.get(InstructionField.IMM_11B);
        int bimm_12 = code.get(InstructionField.IMM_12);
        int bimm = (bimm_12 << 11 | bimm_11 << 10 | bimm_10_5 << 4 | bimm_4_1) << 1;
        return signExtend(bimm, 13);
      case U:
        return code.get(InstructionField.IMM_31_12);
      case J:
        int jimm_10_1 = code.get(InstructionField.IMM_10_1);
        int jimm_11 = code.get(InstructionField.IMM_11J);
        int jimm_19_12 = code.get(InstructionField.IMM_19_12);
        int jimm_20 = code.get(InstructionField.IMM_20);
        int jimm = (jimm_20 << 19 | jimm_19_12 << 11 | jimm_11 << 10 | jimm_10_1) << 1;
        return signExtend(jimm, 21);
      default:
        return 0;
    }
  }

}
