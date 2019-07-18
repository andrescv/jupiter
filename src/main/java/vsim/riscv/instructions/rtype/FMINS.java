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

import vsim.exc.SimulationException;
import vsim.riscv.instructions.Format;
import vsim.riscv.instructions.Instruction;
import vsim.riscv.instructions.InstructionField;
import vsim.riscv.instructions.MachineCode;
import vsim.sim.State;
import vsim.utils.Data;


/** RISC-V fmin.s (Floating-point Minimum, Single-Precision) instruction. */
public final class FMINS extends Instruction {

  /** Creates a new fmin.s instruction. */
  public FMINS() {
    super(Format.R, "fmin.s", 0b1010011, 0b000, 0b0010100);
  }

  /** {@inheritDoc} */
  @Override
  public void execute(MachineCode code, State state) throws SimulationException {
    float rs1 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS1));
    float rs2 = state.fregfile().getRegisterFloat(code.get(InstructionField.RS2));
    // if both inputs are signaling nans, return canonical NaN
    if (Data.signalingNaN(rs1) && Data.signalingNaN(rs2)) {
      state.fregfile().setRegister(code.get(InstructionField.RD), Float.NaN);
    }
    // if both inputs are quiet nans, return canonical NaN
    else if (Data.quietNaN(rs1) && Data.quietNaN(rs2)) {
      state.fregfile().setRegister(code.get(InstructionField.RD), Float.NaN);
    }
    // if one operand is a quiet NaN and the other is not a NaN, return
    // the non-NaN operand
    else if (Data.quietNaN(rs1) || Data.quietNaN(rs2)) {
      if (!Data.quietNaN(rs1)) {
        state.fregfile().setRegister(code.get(InstructionField.RD), rs1);
      } else {
        state.fregfile().setRegister(code.get(InstructionField.RD), rs2);
      }
    }
    // if one operand is a signaling NaN and the other is not a NaN, return
    // the non-NaN operand
    else if (Data.signalingNaN(rs1) || Data.signalingNaN(rs2)) {
      if (!Data.signalingNaN(rs1)) {
        state.fregfile().setRegister(code.get(InstructionField.RD), rs1);
      } else {
        state.fregfile().setRegister(code.get(InstructionField.RD), rs2);
      }
    }
    // return the smaller floating point number
    else {
      state.fregfile().setRegister(code.get(InstructionField.RD), Math.min(rs1, rs2));
    }
    state.xregfile().incProgramCounter();
  }

  /** {@inheritDoc} */
  @Override
  public String disassemble(MachineCode code) {
    int rd = code.get(InstructionField.RD);
    int rs1 = code.get(InstructionField.RS1);
    int rs2 = code.get(InstructionField.RS2);
    return String.format("fmin.s f%d, f%d, f%d", rd, rs1, rs2);
  }

}
