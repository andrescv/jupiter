import { RVDecodeHandler } from './handler';

export class RV32IDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'RV32I';

  protected execute(input: number): string | null {
    return null;
  }
}
