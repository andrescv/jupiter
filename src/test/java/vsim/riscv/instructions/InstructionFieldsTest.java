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

package vsim.riscv.instructions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import vsim.Globals;
import vsim.riscv.instructions.MachineCode;


/** Instruction fields tests. */
public class InstructionFieldsTest {

  @Test
  void testFields() {
    /* RType = Opcode, FUNCT3, FUNCT7 */
    // add
    assertEquals(0b0110011, Globals.iset.get("add").getOpCode);
    assertEquals(0b000, Globals.iset.get("add").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("add").getFunct7);
    // sub
    assertEquals(0b0110011, Globals.iset.get("sub").getOpCode);
    assertEquals(0b000, Globals.iset.get("sub").getFunct3);
    assertEquals(0b0100000, Globals.iset.get("sub").getFunct7);
    // sll
    assertEquals(0b0110011, Globals.iset.get("sll").getOpCode);
    assertEquals(0b001, Globals.iset.get("sll").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("sll").getFunct7);
    // slt
    assertEquals(0b0110011, Globals.iset.get("slt").getOpCode);
    assertEquals(0b010, Globals.iset.get("slt").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("slt").getFunct7);
    // sltu
    assertEquals(0b0110011, Globals.iset.get("sltu").getOpCode);
    assertEquals(0b011, Globals.iset.get("sltu").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("sltu").getFunct7);
    // xor
    assertEquals(0b0110011, Globals.iset.get("xor").getOpCode);
    assertEquals(0b100, Globals.iset.get("xor").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("xor").getFunct7);
    // srl
    assertEquals(0b0110011, Globals.iset.get("srl").getOpCode);
    assertEquals(0b101, Globals.iset.get("srl").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("srl").getFunct7);
    // sra
    assertEquals(0b0110011, Globals.iset.get("sra").getOpCode);
    assertEquals(0b101, Globals.iset.get("sra").getFunct3);
    assertEquals(0b0100000, Globals.iset.get("sra").getFunct7);
    // or
    assertEquals(0b0110011, Globals.iset.get("or").getOpCode);
    assertEquals(0b110, Globals.iset.get("or").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("or").getFunct7);
    // and
    assertEquals(0b0110011, Globals.iset.get("and").getOpCode);
    assertEquals(0b111, Globals.iset.get("and").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("and").getFunct7);
    // addw
    assertEquals(0b0111011, Globals.iset.get("addw").getOpCode);
    assertEquals(0b000, Globals.iset.get("addw").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("addw").getFunct7);
    // subw
    assertEquals(0b0110011, Globals.iset.get("subw").getOpCode);
    assertEquals(0b000, Globals.iset.get("subw").getFunct3);
    assertEquals(0b0100000, Globals.iset.get("subw").getFunct7);
    // sllw
    assertEquals(0b0110011, Globals.iset.get("sllw").getOpCode);
    assertEquals(0b001, Globals.iset.get("sllw").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("sllw").getFunct7);
    // srlw
    assertEquals(0b0110011, Globals.iset.get("srlw").getOpCode);
    assertEquals(0b101, Globals.iset.get("srlw").getFunct3);
    assertEquals(0b0000000, Globals.iset.get("srlw").getFunct7);
    // sraw
    assertEquals(0b0110011, Globals.iset.get("sraw").getOpCode);
    assertEquals(0b101, Globals.iset.get("sraw").getFunct3);
    assertEquals(0b0100000, Globals.iset.get("sraw").getFunct7);



    // mul
    assertEquals(0b0110011, Globals.iset.get("mul").getOpCode);
    assertEquals(0b000, Globals.iset.get("mul").getFunct3);
    assertEquals(0b0000001, Globals.iset.get("mul").getFunct7);
    // div
    assertEquals(0b0110011, Globals.iset.get("div").getOpCode);
    assertEquals(0b100, Globals.iset.get("div").getFunct3);
    assertEquals(0b0000001, Globals.iset.get("div").getFunct7);


  }

}
