import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32FDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'F';

  protected init(): void {
    this.mappings = {};
  }

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
