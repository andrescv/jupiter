import { extendSign } from '@/helpers/numeric';

import { Fields, MachineCode } from '@/interfaces/code';

import { RVDecodeHandler } from '../handler';

import bTypeNameMappings from './b-type';
import iTypeNameMappings from './i-type';
import rTypeNameMappings from './r-type';
import sTypeNameMappings from './s-type';
import uTypeNameMappings from './u-type';

export class RV32IDecodeHandler extends RVDecodeHandler {
  protected readonly isaModule = 'RV32I';

  protected init(): void {
    this.mappings = {
      BType: bTypeNameMappings,
      IType: iTypeNameMappings,
      RType: rTypeNameMappings,
      SType: sTypeNameMappings,
      UType: uTypeNameMappings,
    };
  }

  protected execute(input: MachineCode): string | null {
    const opcode = input.get(Fields.OPCODE);

    switch (opcode) {
      case 0x33:
        return this.decodeRType(input);
      case 0x03:
      case 0x13:
      case 0x67:
      case 0x73:
        return this.decodeIType(opcode, input);
      case 0x23:
        return this.decodeSType(input);
      case 0x63:
        return this.decodeBType(input);
      case 0x17:
      case 0x37:
        return this.decodeUType(opcode, input);
      case 0x6f:
        return this.decodeJalInstruction(input);
      case 0x0f:
        return this.decodeFenceInstruction(input);
    }

    return null;
  }

  private decodeJalInstruction(input: MachineCode): string {
    const rd = this.getRegisterName(input.get(Fields.RD));
    const imm10_1 = input.get(Fields.IMM_10_1);
    const imm11 = input.get(Fields.IMM_11J);
    const imm19_12 = input.get(Fields.IMM_19_12);
    const imm20 = input.get(Fields.IMM_20);

    const imm = this.formatImm(
      extendSign(
        (imm20 << 20) | (imm19_12 << 12) | (imm11 << 11) | (imm10_1 << 1),
        21
      )
    );

    return this.normalFormat('jal', rd, imm);
  }

  private decodeFenceInstruction(input: MachineCode): string | null {
    const funct3 = input.get(Fields.FUNCT3);
    if (funct3 !== 0x0) return null;

    const fm = input.get(Fields.FM);
    const pred = input.get(Fields.PRED);
    const succ = input.get(Fields.SUCC);

    switch (fm) {
      case 0x0:
        return this.formatNormalFence(pred, succ);
      case 0x8:
        if (pred == 0x3 && succ == 0x3) {
          return 'fence.tso';
        }
    }

    return null;
  }

  private formatNormalFence(pred: number, succ: number): string {
    return this.normalFormat(
      'fence',
      this.formatFenceCode(pred),
      this.formatFenceCode(succ)
    );
  }

  /**
   * @param code binary code (4-bit)
   * @returns {string} representation of the code (e.g. 0b0011 -> 'rw')
   */
  private formatFenceCode(code: number): string {
    const i = code & 0x8 ? 'i' : '';
    const o = code & 0x4 ? 'o' : '';
    const r = code & 0x2 ? 'r' : '';
    const w = code & 0x1 ? 'w' : '';

    return i + o + r + w;
  }
}
