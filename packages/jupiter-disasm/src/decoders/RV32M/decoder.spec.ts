import { describe, expect, it } from 'vitest';

import { RV32MDecodeHandler } from './decoder';

describe('decoding', () => {
  const decoder = new RV32MDecodeHandler({ useABINames: false });
  const decode = createDecodeFn(decoder);

  it('should decode R-type instructions correctly', () => {
    expect(decode(0x02208033)).toBe('mul x0 x1 x2');
    expect(decode(0x036591b3)).toBe('mulh x3 x11 x22');
    expect(decode(0x03752fb3)).toBe('mulhsu x31 x10 x23');
    expect(decode(0x03573eb3)).toBe('mulhu x29 x14 x21');
    expect(decode(0x02b7cbb3)).toBe('div x23 x15 x11');
    expect(decode(0x02b7da33)).toBe('divu x20 x15 x11');
    expect(decode(0x02d7e9b3)).toBe('rem x19 x15 x13');
    expect(decode(0x0232fbb3)).toBe('remu x23 x5 x3');
  });
});

const createDecodeFn =
  (decoder: RV32MDecodeHandler) => (instruction: number) => {
    const result = decoder.decode(instruction);

    if (result.handledBy) {
      return result.data;
    }

    return null;
  };
