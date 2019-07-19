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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

import jvpiter.riscv.instructions.Format;
import jvpiter.riscv.instructions.MachineCode;


/** jvpiter.utils.Data tests. */
public class DataTest {

  @Test
  void testConstantsValues() {
    assertEquals(Data.EOL, System.getProperty("line.separator"));
    assertEquals(Data.STACK_POINTER, 0xbffffff0);
    assertEquals(Data.GLOBAL_POINTER, 0x10008000);
    assertEquals(Data.RESERVED_HIGH_START, 0xbffffff1);
    assertEquals(Data.RESERVED_HIGH_END, 0xffffffff);
    assertEquals(Data.RESERVED_LOW_START, 0x00000000);
    assertEquals(Data.RESERVED_LOW_END, 0x0000ffff);
    assertEquals(Data.TEXT, 0x00010000);
    // test masks
    assertEquals(Data.BYTE_MASK, 0xff);
    assertEquals(Data.HALF_MASK, 0xffff);
    assertEquals(Data.WORD_MASK, 0xffffffff);
    assertEquals(Data.FRACTION_MASK, 0x007fffff);
    assertEquals(Data.EXPONENT_MASK, 0x7f800000);
    assertEquals(Data.SIGN_MASK, 0x80000000);
    // test length in bytes
    assertEquals(Data.BYTE_LENGTH, 1);
    assertEquals(Data.HALF_LENGTH, 2);
    assertEquals(Data.WORD_LENGTH, 4);
    // test length in bits
    assertEquals(Data.BYTE_LENGTH_BITS, 8);
    assertEquals(Data.HALF_LENGTH_BITS, 16);
    assertEquals(Data.WORD_LENGTH_BITS, 32);
    // test min and max values
    assertEquals(Data.BYTE_MIN_VALUE, -128);
    assertEquals(Data.BYTE_MAX_VALUE, 127);
    assertEquals(Data.HALF_MIN_VALUE, -32768);
    assertEquals(Data.HALF_MAX_VALUE, 32767);
  }

  @Test
  void testCreateData() {
    assertEquals(new Data() instanceof Data, true);
  }

  @Test
  void testSignExtension() {
    assertEquals(Data.signExtend(1, 1), -1);
    assertEquals(Data.signExtend(1, 2), 1);
    assertEquals(Data.signExtendByte(0xf), 0xf);
    assertEquals(Data.signExtendByte(0xff), -1);
    assertEquals(Data.signExtendHalf(0xfff), 0xfff);
    assertEquals(Data.signExtendHalf(32767), 32767);
    assertEquals(Data.signExtendHalf(0xffff), -1);
  }

  @Test
  void testIsWordAligned() {
    assertEquals(Data.isWordAligned(0x100), true);
    assertEquals(Data.isWordAligned(0x101), false);
    assertEquals(Data.isWordAligned(Data.WORD_LENGTH * 512), true);
  }

  @Test
  void testAlignToWordBoundary() {
    assertEquals(Data.alignToWordBoundary(0x100), 0x100);
    assertEquals(Data.alignToWordBoundary(0x101), 0x104);
    assertEquals(Data.alignToWordBoundary(0x102), 0x104);
    assertEquals(Data.alignToWordBoundary(0x103), 0x104);
    assertEquals(Data.alignToWordBoundary(0x104), 0x104);
  }

  @Test
  void testOffsetToWordAlign() {
    assertEquals(Data.offsetToWordAlign(0x100), 0);
    assertEquals(Data.offsetToWordAlign(0x101), 3);
    assertEquals(Data.offsetToWordAlign(0x102), 2);
    assertEquals(Data.offsetToWordAlign(0x103), 1);
    assertEquals(Data.offsetToWordAlign(0x104), 0);
  }

  @Test
  void testInRange() {
    assertEquals(Data.inRange(0, 0, 0), true);
    assertEquals(Data.inRange(5, -10, 10), true);
    assertEquals(Data.inRange(-10, -10, 10), true);
    assertEquals(Data.inRange(10, -10, 10), true);
    assertEquals(Data.inRange(11, -10, 10), false);
    assertEquals(Data.inRange(-11, -10, 10), false);
    assertEquals(Data.inRange(5, -10, 10), true);
    assertEquals(Data.inRange(30, 10, 100), true);
    assertEquals(Data.inRange(5, 10, 100), false);
    assertEquals(Data.inRange(0x1fffffff, 0x0fffffff, 0xffffffff), true);
    assertEquals(Data.validByte(127), true);
    assertEquals(Data.validByte(0xef), true);
    assertEquals(Data.validByte(-128), true);
    assertEquals(Data.validByte(10), true);
    assertEquals(Data.validByte(-129), false);
    assertEquals(Data.validByte(256), false);
    assertEquals(Data.validHalf(32767), true);
    assertEquals(Data.validHalf(-32768), true);
    assertEquals(Data.validHalf(255), true);
    assertEquals(Data.validHalf(-32769), false);
    assertEquals(Data.validHalf(65536), false);
  }

  @Test
  void testSignalingNaN() {
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0xff800000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7f800000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x00000000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x80000000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x40490ff9)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fa00000)), true);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fbfffff)), true);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7f800001)), true);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0xff800001)), true);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0xffbfffff)), true);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fc00001)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fc00000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fc00002)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0xffc00000)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0x7fffffff)), false);
    assertEquals(Data.signalingNaN(Float.intBitsToFloat(0xffffffff)), false);
  }

  @Test
  void testQuietNaN() {
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0xff800000)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7f800000)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x00000000)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x80000000)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x40490ff9)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fa00000)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fbfffff)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7f800001)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0xff800001)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0xffbfffff)), false);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fc00001)), true);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fc00000)), true);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fc00002)), true);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0xffc00000)), true);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0x7fffffff)), true);
    assertEquals(Data.quietNaN(Float.intBitsToFloat(0xffffffff)), true);
  }

  @Test
  void testAtoi() {
    assertEquals(Data.atoi("10"), 10);
    assertEquals(Data.atoi("          155      "), 155);
    assertEquals(Data.atoi("-8"), -8);
    assertEquals(Data.atoi("      -127             "), -127);
    assertEquals(Data.atoi(" 0xf "), 15);
    assertEquals(Data.atoi("0b11 "), 3);
    assertEquals(Data.atoi(""), 0);
    assertEquals(Data.atoi(null), 0);
    assertThrows(NumberFormatException.class, () -> { Data.atoi("hola"); });
  }

  @Test
  void testAtof() {
    assertEquals(Data.atof("10"), 10.0f);
    assertEquals(Data.atof("           .55 "), 0.55f);
    assertEquals(Data.atof("1.6e-10 "), 1.6e-10f);
    assertEquals(Data.atof("   -.55e10 "), -.55e10f);
    assertEquals(Data.atof(" 0xc14947ae "), -12.58f);
    assertEquals(Data.atof(" 0b01000000010010010000111111111001      "), 3.1416f);
    assertEquals(Data.atof(""), 0.0f);
    assertEquals(Data.atof(null), 0.0f);
    assertThrows(NumberFormatException.class, () -> { Data.atof("adios"); });
  }

  @Test
  void testImm() {
    assertEquals(Data.imm(new MachineCode(0x00a50533), Format.R), 0);
    assertEquals(Data.imm(new MachineCode(0x00400513), Format.I), 4);
    assertEquals(Data.imm(new MachineCode(0xff458593), Format.I), -12);
    assertEquals(Data.imm(new MachineCode(0xfea12e23), Format.S), -4);
    assertEquals(Data.imm(new MachineCode(0x00a12ca3), Format.S), 25);
    assertEquals(Data.imm(new MachineCode(0xfe0000e3), Format.B), -32);
    assertEquals(Data.imm(new MachineCode(0x000e0c63), Format.B), 24);
    assertEquals(Data.imm(new MachineCode(0x0000a017), Format.U), 10);
    assertEquals(Data.imm(new MachineCode(0x10000597), Format.U), 65536);
    assertEquals(Data.imm(new MachineCode(0xfedff06f), Format.J), -20);
    assertEquals(Data.imm(new MachineCode(0x044000ef), Format.J), 68);
  }

}
