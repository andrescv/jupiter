import assert from 'assert';

import { deepFreeze } from '@/helpers/freeze';
import { getXRegister } from '@/helpers/get-register';

import { MachineCode } from '@/interfaces/code';
import { HandlerResult } from '@/interfaces/handler';
import { InstructionMappings } from '@/interfaces/instruction-mappings';
import { DecodingOptions, RVExtension } from '@/interfaces/options';

import { Code } from '@/rv32';

export abstract class RVDecodeHandler {
  protected next: RVDecodeHandler | null = null;
  private _mappings: InstructionMappings;

  public constructor(protected options: DecodingOptions) {
    this._mappings = {
      BType: null,
      IType: null,
      RType: null,
      SType: null,
      UType: null,
    };
    this.init();
  }

  protected get mappings(): InstructionMappings {
    return this._mappings;
  }

  protected set mappings(mappings: Partial<InstructionMappings>) {
    this._mappings = deepFreeze({ ...this._mappings, ...mappings });
  }

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

  protected getBTypeName(funct3: number): string | null {
    assert(this.mappings.BType);
    const name = this.mappings.BType[funct3];

    return name || null;
  }

  protected getITypeName(opcode: number, funct3: number, special?: number) {
    assert(this.mappings.IType);
    const opcodeMappings = this.mappings.IType[opcode];
    const funct3Mappings = opcodeMappings?.[funct3];

    if (funct3Mappings) {
      if (typeof funct3Mappings === 'string' && special === undefined) {
        return funct3Mappings;
      }

      if (typeof funct3Mappings === 'object' && special !== undefined) {
        return funct3Mappings[special] || null;
      }
    }

    return null;
  }

  protected getRTypeName(funct3: number, funct7: number): string | null {
    assert(this.mappings.RType);
    const funct3Mappings = this.mappings.RType[funct7];
    if (funct3Mappings) {
      const name = funct3Mappings[funct3];
      return name || null;
    }

    return null;
  }

  protected getSTypeName(funct3: number): string | null {
    assert(this.mappings.SType);
    const name = this.mappings.SType[funct3];

    return name || null;
  }

  protected getUTypeName(opcode: number): string | null {
    assert(this.mappings.UType);
    const name = this.mappings.UType[opcode];

    return name || null;
  }

  /** The RISC-V ISA Module that decodes */
  protected abstract get isaModule(): 'RV32I' | RVExtension;
  protected abstract init(): void;
  protected abstract execute(input: MachineCode): string | null;
}
