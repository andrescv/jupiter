import { RVDecodeHandler } from './handler';

export class RV32MDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'M';

  protected execute(input: number): string | null {
    return null;
  }
}
