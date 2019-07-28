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

package jupiter.riscv;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

import jupiter.Globals;
import jupiter.asm.stmts.Statement;
import jupiter.riscv.instructions.MachineCode;


/** jupiter.riscv.InstructionSet tests. */
public class InstructionSetTest {

  void test(int code, String mnemonic) {
    Statement stmt = Globals.iset.decode(new MachineCode(code));
    assertNotNull(stmt);
    assertEquals(stmt.mnemonic(), mnemonic);
  }

  void testNull(int code) {
    assertEquals(null, Globals.iset.decode(new MachineCode(code)));
  }

  @Test
  void testDecode() {
    test(0x0000A537, "lui");
    test(0x00009417, "auipc");
    test(0x008000EF, "jal");
    test(0x002100E7, "jalr");
    test(0x02A50A63, "beq");
    test(0x02A51863, "bne");
    test(0x00A54263, "blt");
    test(0x02A55663, "bge");
    test(0x02A56463, "bltu");
    test(0x02A57263, "bgeu");
    test(0x00010503, "lb");
    test(0x00011503, "lh");
    test(0x00012503, "lw");
    test(0x00014503, "lbu");
    test(0x00015503, "lhu");
    test(0x00A10023, "sb");
    test(0x00A11023, "sh");
    test(0x00A12023, "sw");
    test(0x00A50513, "addi");
    test(0x00A52513, "slti");
    test(0x00A53513, "sltiu");
    test(0x00A54513, "xori");
    test(0x00A56513, "ori");
    test(0x00A57513, "andi");
    test(0x00A51513, "slli");
    test(0x00A55513, "srli");
    test(0x40A55513, "srai");
    test(0x00A50533, "add");
    test(0x40A50533, "sub");
    test(0x00A51533, "sll");
    test(0x00A52533, "slt");
    test(0x00A53533, "sltu");
    test(0x00A54533, "xor");
    test(0x00A55533, "srl");
    test(0x40A55533, "sra");
    test(0x00A56533, "or");
    test(0x00A57533, "and");
    test(0x0000000F, "fence");
    test(0x00000073, "ecall");
    test(0x00100073, "ebreak");
    test(0x02A50533, "mul");
    test(0x02A51533, "mulh");
    test(0x02A52533, "mulhsu");
    test(0x02A53533, "mulhu");
    test(0x02A54533, "div");
    test(0x02A55533, "divu");
    test(0x02A56533, "rem");
    test(0x02A57533, "remu");
    test(0x00012007, "flw");
    test(0x00012027, "fsw");
    test(0x00000043, "fmadd.s");
    test(0x00000047, "fmsub.s");
    test(0x0000004B, "fnmsub.s");
    test(0x0000004F, "fnmadd.s");
    test(0x00A50553, "fadd.s");
    test(0x08A50553, "fsub.s");
    test(0x10A50553, "fmul.s");
    test(0x18A50553, "fdiv.s");
    test(0x58050553, "fsqrt.s");
    test(0x20A50553, "fsgnj.s");
    test(0x20A51553, "fsgnjn.s");
    test(0x20A52553, "fsgnjx.s");
    test(0x28A50553, "fmin.s");
    test(0x28A51553, "fmax.s");
    test(0xC0007553, "fcvt.w.s");
    test(0xC0107553, "fcvt.wu.s");
    test(0xE0000553, "fmv.x.w");
    test(0xA0002553, "feq.s");
    test(0xA0001553, "flt.s");
    test(0xA0000553, "fle.s");
    test(0xE0001553, "fclass.s");
    test(0xD0057053, "fcvt.s.w");
    test(0xD0157053, "fcvt.s.wu");
    test(0xF0050053, "fmv.w.x");
    test(0x00051573, "csrrw");
    test(0x00052573, "csrrs");
    test(0x00053573, "csrrc");
    test(0x00005573, "csrrwi");
    test(0x00006573, "csrrsi");
    test(0x00007573, "csrrci");
  }

}
