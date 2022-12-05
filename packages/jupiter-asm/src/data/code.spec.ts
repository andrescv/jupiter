import { describe, expect, it } from 'vitest';

import { Bits } from '@/constant/bits';
import { Fields } from '@/constant/fields';

import { Code } from '@/data/code';

describe('[Code] tests', () => {
  it('should be defined', () => {
    expect(new Code()).toBeDefined();
  });

  it('should set and get fields correctly', () => {
    const random = Math.floor(Math.random() * (Math.pow(2, Bits.WORD) - 1));
    const value = random & 0xffffffff;
    const code = new Code(value);

    expect(code.get(Fields.ALL)).toEqual(value);

    const newValue = (value & 0xffffff80) | 0b1010110;
    code.set(Fields.OPCODE, 0b1010110);

    expect(code.get()).toEqual(newValue);
    expect(code.get(Fields.OPCODE)).toEqual(0b1010110);

    code.set(Fields.ALL);

    expect(code.get(Fields.ALL)).toEqual(0x0);
  });

  it('should get the hex string representation correctly', () => {
    const code = new Code(0x12345678);

    expect(code.toString()).toEqual('0x12345678');

    code.set(Fields.OPCODE);
    code.set(Fields.FUNCT3, 0b111);

    expect(code.toString()).toEqual('0x12347600');
  });
});
