import { areNumeric } from '@/helpers/numeric';

import { Fields, MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

import getRTypeName from './r-type';

export class RV32IDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'RV32I';

  protected execute(input: MachineCode): string | null {
    const opcode = input.get(Fields.OPCODE);

    switch (opcode) {
      case 0x33:
        return this.decodeRType(input);
      case 0x03:
      case 0x13:
      case 0x67:
      case 0x73:
        // I-Type
        return null;
      case 0x23:
        // S-Type
        return null;
      case 0x63:
        // B-Type
        return null;
      case 0x17:
      case 0x37:
        // U-Type
        return null;
      case 0x6f:
        // J-Type
        return null;
      case 0x0f:
        // FENCE
        return null;
    }

    return null;
  }

  private decodeRType(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    const funct7 = input.get(Fields.FUNCT7);

    const name = getRTypeName(funct3, funct7);
    const rd = input.get(Fields.RD);
    const rs1 = input.get(Fields.RS1);
    const rs2 = input.get(Fields.RS2);

    const missingFields = !(name && areNumeric(rd, rs1, rs2));
    if (missingFields) return null;

    const rdName = this.getRegisterName(rd);
    const rs1Name = this.getRegisterName(rs1);
    const rs2Name = this.getRegisterName(rs2);

    return `${name} ${rdName} ${rs1Name} ${rs2Name}`;
  }
}
