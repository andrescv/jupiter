import assert from 'assert';

import { deepFreeze } from '@/helpers/freeze';
import { getXRegister } from '@/helpers/get-register';
import { extendSign } from '@/helpers/numeric';

import { Fields, MachineCode } from '@/interfaces/code';
import { Field } from '@/interfaces/field';
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

  protected decodeRType(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    const funct7 = input.get(Fields.FUNCT7);

    const name = this.getRTypeName(funct3, funct7);
    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const rs2 = this.getRegisterName(input.get(Fields.RS2));

    return this.normalFormat(name, rd, rs1, rs2);
  }

  protected decodeIType(opcode: number, input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);

    let name: string | null;
    let immField: Field = Fields.IMM_11_0;

    if (opcode === 0x13 && (funct3 === 0x01 || funct3 === 0x05)) {
      const special = input.get(Fields.IMM_11_5);
      name = this.getITypeName(opcode, funct3, special);
      immField = Fields.SHAMT;
    } else if (opcode === 0x73) {
      const special = input.get(Fields.IMM_11_0);
      name = this.getITypeName(opcode, funct3, special);
      return name;
    } else {
      name = this.getITypeName(opcode, funct3);
    }

    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const imm = this.formatImm(extendSign(input.get(immField), 12));

    if (opcode === 0x03 || opcode === 0x67) {
      return this.offsetFormat(name, rd, rs1, imm);
    }

    return this.normalFormat(name, rd, rs1, imm);
  }

  protected decodeSType(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    const name = this.getSTypeName(funct3);

    if (!name) return null;

    const imm11_5 = input.get(Fields.IMM_11_5);
    const imm4_0 = input.get(Fields.IMM_4_0);
    const imm = this.formatImm(extendSign((imm11_5 << 5) | imm4_0, 12));

    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const rs2 = this.getRegisterName(input.get(Fields.RS2));

    return this.offsetFormat(name, rs2, rs1, imm);
  }

  protected decodeBType(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    const name = this.getBTypeName(funct3);

    if (!name) return null;

    const imm4_1 = input.get(Fields.IMM_4_1);
    const imm_10_5 = input.get(Fields.IMM_10_5);
    const imm11 = input.get(Fields.IMM_11B);
    const imm12 = input.get(Fields.IMM_12);

    const imm = this.formatImm(
      extendSign(
        (imm12 << 12) | (imm11 << 11) | (imm_10_5 << 5) | (imm4_1 << 1),
        13
      )
    );

    const rs1 = this.getRegisterName(input.get(Fields.RS1));
    const rs2 = this.getRegisterName(input.get(Fields.RS2));

    return this.normalFormat(name, rs1, rs2, imm);
  }

  protected decodeUType(opcode: number, input: MachineCode): string | null {
    const name = this.getUTypeName(opcode);
    if (!name) return null;

    const rd = this.getRegisterName(input.get(Fields.RD));
    const imm = this.formatImm(input.get(Fields.IMM_31_12));
    return this.normalFormat(name, rd, imm);
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
