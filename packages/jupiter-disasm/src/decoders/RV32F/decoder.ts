import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32FDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'F';

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
