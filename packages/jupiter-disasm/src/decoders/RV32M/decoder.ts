import { Fields, MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

import rTypeNameMappings from './r-type';

export class RV32MDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'M';

  protected init(): void {
    this.mappings = {
      RType: rTypeNameMappings,
    };
  }

  protected execute(input: MachineCode): string | null {
    const opcode = input.get(Fields.OPCODE);

    switch (opcode) {
      case 0x33:
        return this.decodeRType(input);
    }

    return null;
  }

  private decodeRType(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    const funct7 = input.get(Fields.FUNCT7);

    const name = this.getRTypeName(funct3, funct7);
    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const rs2 = this.getRegisterName(input.get(Fields.RS2));

    return this.normalFormat(name, rd, rs1, rs2);
  }
}
