import { HandlerResult } from '@/interfaces/handler';
import { RVExtension } from '@/interfaces/options';

export abstract class RVDecodeHandler {
  protected next: RVDecodeHandler | null = null;

  public decode(input: number): HandlerResult<string> {
    const instruction = this.execute(input);

    if (!instruction) {
      if (this.next) {
        return this.next.decode(input);
      }

      return { handledBy: null };
    }

    return {
      handledBy: this.isaModule,
      data: instruction,
    };
  }

  public setNext(next: RVDecodeHandler): void {
    this.next = next;
  }

  /** The RISC-V ISA Module that decodes */
  protected abstract get isaModule(): 'RV32I' | RVExtension;
  protected abstract execute(input: number): string | null;
}
