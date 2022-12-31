import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32ZifenceiDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'Zifencei';

  protected init(): void {
    this.mappings = {};
  }

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
