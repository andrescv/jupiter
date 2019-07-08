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

package vsim.riscv.instructions.rtype;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;


/** vsim.riscv.instructions.rtype tests. */
public class RTypeTest {

  @Test
  void testRType() {
    assertEquals("fcvt.wu.s x10, f0", Globals.iset.get("fcvt.wu.s").disassemble(new MachineCode(0xC0000553)));
    assertEquals("fcvt.w.s x8, f8", Globals.iset.get("fcvt.w.s").disassemble(new MachineCode(0xC0040453)));
    assertEquals("flt.s x5, f1, f2", Globals.iset.get("flt.s").disassemble(new MachineCode(0xA02092D3)));
    assertEquals("feq.s x5, f1, f2", Globals.iset.get("feq.s").disassemble(new MachineCode(0xA020A2D3)));
    assertEquals("fle.s x5, f1, f2", Globals.iset.get("fle.s").disassemble(new MachineCode(0xA02082D3)));
    assertEquals("fclass.s x5, f0", Globals.iset.get("fclass.s").disassemble(new MachineCode(0xE00012D3)));
    assertEquals("fcvt.s.wu f9, x31", Globals.iset.get("fcvt.s.wu").disassemble(new MachineCode(0xD01F84D3)));
    assertEquals("fcvt.s.w f23, x28", Globals.iset.get("fcvt.s.w").disassemble(new MachineCode(0xD00E0BD3)));
    assertEquals("fmin.s f0, f1, f2", Globals.iset.get("fmin.s").disassemble(new MachineCode(0x28208053)));
    assertEquals("fmax.s f0, f1, f2", Globals.iset.get("fmax.s").disassemble(new MachineCode(0x28209053)));
    assertEquals("fsgnjx.s f0, f1, f2", Globals.iset.get("fsgnjx.s").disassemble(new MachineCode(0x2020A053)));
    assertEquals("fsgnjn.s f0, f1, f2", Globals.iset.get("fsgnjn.s").disassemble(new MachineCode(0x20209053)));
    assertEquals("fsgnj.s f0, f1, f2", Globals.iset.get("fsgnj.s").disassemble(new MachineCode(0x20208053)));
    assertEquals("mulhu x10, x5, x6", Globals.iset.get("mulhu").disassemble(new MachineCode(0x0262B533)));
    assertEquals("mulhsu x17, x6, x7", Globals.iset.get("mulhsu").disassemble(new MachineCode(0x027328B3)));
    assertEquals("mulh x13, x24, x10", Globals.iset.get("mulh").disassemble(new MachineCode(0x02AC16B3)));
    assertEquals("remu x12, x11, x13", Globals.iset.get("remu").disassemble(new MachineCode(0x02D5F633)));
    assertEquals("divu x5, x6, x7", Globals.iset.get("divu").disassemble(new MachineCode(0x027352B3)));
    assertEquals("div x5, x6, x7", Globals.iset.get("div").disassemble(new MachineCode(0x027342B3)));
    assertEquals("rem x5, x24, x17", Globals.iset.get("rem").disassemble(new MachineCode(0x031C62B3)));
    assertEquals("sltu x5, x5, x6", Globals.iset.get("sltu").disassemble(new MachineCode(0x0062B2B3)));
    assertEquals("sra x5, x6, x7", Globals.iset.get("sra").disassemble(new MachineCode(0x407352B3)));
    assertEquals("fsqrt.s f10, f1, f0", Globals.iset.get("fsqrt.s").disassemble(new MachineCode(0x5800f553)));
    assertEquals("slt x10, x12, x13", Globals.iset.get("slt").disassemble(new MachineCode(0x00D62533)));
    assertEquals("fsub.s f0, f1, f2", Globals.iset.get("fsub.s").disassemble(new MachineCode(0x08208053)));
    assertEquals("fmul.s f2, f3, f4", Globals.iset.get("fmul.s").disassemble(new MachineCode(0x10418153)));
    assertEquals("sll x10, x5, x8", Globals.iset.get("sll").disassemble(new MachineCode(0x00829533)));
    assertEquals("fadd.s f5, f6, f7", Globals.iset.get("fadd.s").disassemble(new MachineCode(0x007302D3)));
    assertEquals("xor x10, x11, x13", Globals.iset.get("xor").disassemble(new MachineCode(0x00D5C533)));
    assertEquals("sub x14, x15, x16", Globals.iset.get("sub").disassemble(new MachineCode(0x41078733)));
    assertEquals("or x17, x18, x19", Globals.iset.get("or").disassemble(new MachineCode(0x013968B3)));
    assertEquals("and x20, x21, x22", Globals.iset.get("and").disassemble(new MachineCode(0x016AFA33)));
    assertEquals("mul x23, x24, x25", Globals.iset.get("mul").disassemble(new MachineCode(0x039C0BB3)));
    assertEquals("add x26, x27, x28", Globals.iset.get("add").disassemble(new MachineCode(0x01CD8D33)));
  }

}
