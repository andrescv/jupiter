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
}
