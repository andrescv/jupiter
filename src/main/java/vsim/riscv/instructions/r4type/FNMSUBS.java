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

package vsim.riscv.instructions.r4type;

import vsim.State;
import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;


/** RISC-V fnmsub.s (Floating-point Fused Negative Multiply-Subtract, Single-Precision) instruction. */
public final class FNMSUBS extends Instruction {

  /** Creates a new fnmsub.s instruction. */
  public FNMSUBS() {
    super(Format.R4, "fmnsub.s", 0b1001011, 0b111, 0b0000000);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    float rs3 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS3));
    state.fregfile().setRegister(code.get(InstructionField.RD), -((rs1 * rs2) - rs3));
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    int rs3 = code.get(InstructionField.RS3);
    return String.format("fnmsub.s f%d, f%d, f%d, f%d", rd, rs1, rs2, rs3);
  }

}
