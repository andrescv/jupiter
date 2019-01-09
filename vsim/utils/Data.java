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

/**
 * The class Data contains useful methods, constants and masks for data manipulation in VSim.
 */
public final class Data {

  /** byte int mask (0xff) */
  public static final int BYTE_MASK = 0xff;
  /** half int mask (0xffff) */
  public static final int HALF_MASK = 0xffff;
  /** word int mask (0xffffffff) */
  public static final int WORD_MASK = 0xffffffff;

  /** byte length in bytes (1) */
  public static final int BYTE_LENGTH = 1;
  /** half length in bytes (2) */
  public static final int HALF_LENGTH = 2;
  /** word length in bytes (4) */
  public static final int WORD_LENGTH = 4;

  /** byte length in bits (8) */
  public static final int BYTE_LENGTH_BITS = 8;
  /** half length in bits (16) */
  public static final int HALF_LENGTH_BITS = 16;
  /** word length in bits (32) */
  public static final int WORD_LENGTH_BITS = 32;

  /** byte min value (-128) */
  public static final int BYTE_MIN_VALUE = -128;
  /** byte max value (127) */
  public static final int BYTE_MAX_VALUE = 127;
  /** half min value (-32768) */
  public static final int HALF_MIN_VALUE = -32768;
  /** half max value (32767) */
  public static final int HALF_MAX_VALUE = 32767;

  /** floating point fraction/mantissa integer mask (0x007fffff) */
  public static final int FRACTION_MASK = 0x007fffff;
  /** floating point exponent integer mask (0x7f800000) */
  public static final int EXPONENT_MASK = 0x7f800000;
  /** floating point sign integer mask (0x80000000) */
  public static final int SIGN_MASK = 0x80000000;

  /** program counter high integer mask (0xfffff000) */
  public static final int PC_HI_MASK = 0xfffff000;
  /** prorgam counter lo integer mask (0x00000fff) */
  public static final int PC_LO_MASK = 0x00000fff;

  /**
   * This method sign-extends a value of N bits (N &lt; 32) to 32 bits.
   *
   * @param value the value to sign-extend
   * @param bits the number of bits that this number represents
   * @see vsim.utils.Data#signExtendByte
   * @see vsim.utils.Data#signExtendHalf
   * @return the sign-extended value
   */
  public static int signExtend(int value, int bits) {
    int shift = WORD_LENGTH_BITS - bits;
    return value << shift >> shift;
  }

  /**
   * This method sign-extends a byte to 32 bits.
   *
   * @param value the byte to sign-extend
   * @see vsim.utils.Data#signExtend
   * @see vsim.utils.Data#signExtendHalf
   * @return the sign-extended byte
   */
  public static int signExtendByte(int value) {
    return signExtend(value, BYTE_LENGTH_BITS);
  }

  /**
   * This method sign-extends a half to 32 bits.
   *
   * @param value the half to sign-extend
   * @see vsim.utils.Data#signExtend
   * @see vsim.utils.Data#signExtendByte
   * @return the sign-extended half
   */
  public static int signExtendHalf(int value) {
    return signExtend(value, HALF_LENGTH_BITS);
  }

  /**
   * This method verifies if an address is aligned to a word boundary.
   *
   * @param address the address to verify
   * @see vsim.utils.Data#alignToWordBoundary
   * @return true if address is word aligned, false if not
   */
  public static boolean isWordAligned(int address) {
    return Integer.remainderUnsigned(address, WORD_LENGTH) == 0;
  }

  /**
   * This method aligns an address to a word boundary if necessary.
   *
   * @param address the address to word align
   * @see vsim.utils.Data#isWordAligned
   * @return the word aligned address
   */
  public static int alignToWordBoundary(int address) {
    if (!Data.isWordAligned(address)) {
      int offset = WORD_LENGTH - Integer.remainderUnsigned(address, WORD_LENGTH);
      return address + offset;
    }
    return address;
  }

  /**
   * This method verifies if a value is in a given range [low, high].
   *
   * @param value the value to verify
   * @param low the low value of the range (included)
   * @param high the high value of the range (included)
   * @return true if the value is in the range, false if not
   */
  public static boolean inRange(int value, int low, int high) {
    return !(value < low || value > high);
  }

  /**
   *
   * This method verifies if a value is a valid half value, i.e is in the range [{@link Data#HALF_MIN_VALUE},
   * {@link Data#HALF_MAX_VALUE}]
   *
   * @param value the value to verify
   * @see vsim.utils.Data#inRange
   * @return true if the value is a valid half value, false if not
   */
  public static boolean validHalf(int value) {
    if (((value & ~HALF_MASK) != 0) && !Data.inRange(value, HALF_MIN_VALUE, HALF_MAX_VALUE))
      return false;
    return true;
  }

  /**
   * This method verifies if a value is a valid byte value, i.e is in the range [{@link Data#BYTE_MIN_VALUE},
   * {@link Data#BYTE_MAX_VALUE}].
   *
   * @param value the value to verify
   * @see vsim.utils.Data#inRange
   * @return true if the value is a valid byte value, false if not
   */
  public static boolean validByte(int value) {
    if (((value & ~BYTE_MASK) != 0) && !Data.inRange(value, BYTE_MIN_VALUE, BYTE_MAX_VALUE))
      return false;
    return true;
  }

  /**
   * This method verifies if a float value is a signaling NaN.
   *
   * @param f float value to verify
   * @return true if the float value is a signaling NaN, false if not
   */
  public static boolean signalingNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) == 0);
  }

  /**
   * This method verifies if a float value is a quiet NaN.
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
  public static int parseInt(String number) {
    if (number.matches("0[xX][0-9a-fA-F]+"))
      return Integer.parseUnsignedInt(number.substring(2), 16);
    else if (number.matches("[0-9a-fA-F]+"))
      return Integer.parseUnsignedInt(number, 16);
    else if (number.matches("0[bB][01]+"))
      return Integer.parseUnsignedInt(number.substring(2), 2);
    else
      return Integer.parseInt(number);
  }

  /**
   * Parses a float value from a string constant.
   *
   * @param number string number in hex, bin or float
   * @return parsed float number
   */
  public static float parseFloat(String number) {
    if (number.matches("0[xX][0-9a-fA-F]+"))
      return Float.intBitsToFloat(Integer.parseUnsignedInt(number.substring(2), 16));
    else if (number.matches("[0-9a-fA-F]+"))
      return Float.intBitsToFloat(Integer.parseUnsignedInt(number, 16));
    else if (number.matches("0[bB][01]+"))
      return Float.intBitsToFloat(Integer.parseUnsignedInt(number.substring(2), 2));
    else
      return Float.parseFloat(number);
  }

}
