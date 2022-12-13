import { MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

export class RV32ZicsrDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'Zicsr';

  protected execute(input: MachineCode): string | null {
    return null;
  }
}
