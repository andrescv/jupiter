import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32ADecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'A';

  protected init(): void {
    this.mappings = {};
  }

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
