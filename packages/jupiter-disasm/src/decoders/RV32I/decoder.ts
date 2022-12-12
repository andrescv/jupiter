import { Fields, MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32IDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'RV32I';

  protected execute(input: MachineCode): string | null {
    const opcode = input.get(Fields.OPCODE);

    switch (opcode) {
      case 0x33:
        // R-Type
        return null;
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
}
