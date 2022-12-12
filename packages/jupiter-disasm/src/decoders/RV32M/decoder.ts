import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32MDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'M';

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
