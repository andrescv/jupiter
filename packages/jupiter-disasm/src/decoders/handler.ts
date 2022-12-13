import { getXRegister } from '@/helpers/get-register';

import { MachineCode } from '@/interfaces/code';
import { HandlerResult } from '@/interfaces/handler';
import { DecodingOptions, RVExtension } from '@/interfaces/options';

import { Code } from '@/rv32';

export abstract class RVDecodeHandler {
  protected next: RVDecodeHandler | null = null;

  public constructor(protected options: DecodingOptions) {}

  public decode(input: number): HandlerResult<string> {
    const machineCode = new Code(input);
    const instruction = this.execute(machineCode);

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

  protected getRegisterName(register: number) {
    return getXRegister(register, {
      useABIName: this.options.useABINames,
    });
  }

  protected normalFormat(name: string, ...args: string[]) {
    return `${name} ${args.join(' ')}`.trim();
  }

  protected offsetFormat(
    name: string,
    reg1: string,
    reg2: string,
    offset: string
  ) {
    return `${name} ${reg1} ${offset}(${reg2})`;
  }

  protected formatImm(value: number): string {
    return value.toString();
  }

  /** The RISC-V ISA Module that decodes */
  protected abstract get isaModule(): 'RV32I' | RVExtension;
  protected abstract execute(input: MachineCode): string | null;
}
