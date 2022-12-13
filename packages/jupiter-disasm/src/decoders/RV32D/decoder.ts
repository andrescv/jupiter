import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32DDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'D';

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
