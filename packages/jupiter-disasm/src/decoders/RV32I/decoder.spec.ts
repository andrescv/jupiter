import { describe, expect, it } from 'vitest';

import { RV32IDecodeHandler } from './decoder';

describe('decoding', () => {
  const decoder = new RV32IDecodeHandler({ useABINames: false });
  const decode = createDecodeFn(decoder);

  it('should decode R-type instructions correctly', () => {
    expect(decode(0x00b502b3)).toBe('add x5 x10 x11');
    expect(decode(0x40b60333)).toBe('sub x6 x12 x11');
    expect(decode(0x00c51e33)).toBe('sll x28 x10 x12');
    expect(decode(0x00c522b3)).toBe('slt x5 x10 x12');
    expect(decode(0x00b533b3)).toBe('sltu x7 x10 x11');
    expect(decode(0x00b2c533)).toBe('xor x10 x5 x11');
    expect(decode(0x00b55533)).toBe('srl x10 x10 x11');
    expect(decode(0x40b55633)).toBe('sra x12 x10 x11');
    expect(decode(0x00b56633)).toBe('or x12 x10 x11');
    expect(decode(0x00b57633)).toBe('and x12 x10 x11');
  });
});

const createDecodeFn =
  (decoder: RV32IDecodeHandler) => (instruction: number) => {
    const result = decoder.decode(instruction);

    if (result.handledBy) {
      return result.data;
    }

    return null;
  };
