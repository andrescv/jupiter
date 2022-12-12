import { MachineCode } from '@/interfaces/code';
import { Field } from '@/interfaces/field';

import { Fields } from '@/rv32/fields';

/**
 * RV code.
 */
export class Code implements MachineCode {
  /**
   * Machine code bits.
   */
  private readonly code = new DataView(new ArrayBuffer(4), 0, 4);

  /**
   * Creates a new machine code.
   *
   * @param code - Initial machine code value.
   */
  constructor(code = 0) {
    this.code.setUint32(0, code);
  }

  /**
   * Sets the value of a field in the machine code.
   *
   * @param field - Field.
   * @param value - Value.
   */
  public set(field: Field, value?: number) {
    const { lo, mask } = field;
    const v = value || 0;
    const code = this.code.getUint32(0);
    const result = (code & ~(mask << lo)) | ((v & mask) << lo);

    this.code.setUint32(0, result);
  }

  /**
   * Gets the value of a field in the machine code.
   *
   * @param field - Field.
   * @returns Value of the field
   */
  public get(field: Field = Fields.ALL) {
    const { lo, mask } = field;
    const code = this.code.getUint32(0);

    return (code >>> lo) & mask;
  }

  /**
   * Gets the string representation of the machine code.
   *
   * @returns String representation of the machine code.
   */
  public toString() {
    const code = this.code.getUint32(0);

    return `0x${code.toString(16).padStart(8, '0')}`;
  }
}
