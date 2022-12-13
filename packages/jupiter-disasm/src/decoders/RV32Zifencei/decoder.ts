import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32ZifenceiDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'Zifencei';

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
