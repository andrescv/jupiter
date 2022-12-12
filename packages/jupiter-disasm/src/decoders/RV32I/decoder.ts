import { extendSign } from '@/helpers/numeric';

import { Fields, MachineCode } from '@/interfaces/code';
import { Field } from '@/interfaces/field';

import { RVDecodeHandler } from '../handler';

import getITypeName from './i-type';
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
        return this.decodeIType(opcode, input);
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
    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const rs2 = this.getRegisterName(input.get(Fields.RS2));

    return this.normalFormat(name, rd, rs1, rs2);
  }

  private decodeIType(opcode: number, input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);

    let name: string | null;
    let immField: Field = Fields.IMM_11_0;

    if (opcode === 0x13 && (funct3 === 0x01 || funct3 === 0x05)) {
      const special = input.get(Fields.IMM_11_5);
      name = getITypeName(opcode, funct3, special);
      immField = Fields.SHAMT;
    } else if (opcode === 0x73) {
      const special = input.get(Fields.IMM_11_0);
      name = getITypeName(opcode, funct3, special);
      return name;
    } else {
      name = getITypeName(opcode, funct3);
    }

    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const imm = extendSign(input.get(immField), 12).toString();

    if (opcode === 0x03 || opcode === 0x67) {
      return this.offsetFormat(name, rd, rs1, imm);
    }

    return this.normalFormat(name, rd, rs1, imm);
  }
}
