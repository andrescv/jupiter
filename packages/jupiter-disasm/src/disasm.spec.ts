import { describe, expect, it } from 'vitest';

import { disasm } from './disasm';

describe('disasm function', () => {
  it('should decode RV32I instructions', () => {
    const instructions = disasm(
      [
        0x40b60333, 0xff8a02e7, 0x8002a503, 0xc0551023, 0x025efa63, 0xf7f8e597,
        0x802000ef, 0x0ff0000f,
      ],
      { selectedExtensions: [] }
    );

    expect(instructions).toStrictEqual([
      'sub x6 x12 x11',
      'jalr x5 -8(x20)',
      'lw x10 -2048(x5)',
      'sh x5 -1024(x10)',
      'bgeu x29 x5 52',
      'auipc x11 1015694',
      'jal x1 -1048574',
      'fence iorw iorw',
    ]);
  });

  it('should use ABI names for registers', () => {
    const instructions = disasm(
      [0x00c28533, 0x01036413, 0x40bf04b3, 0x0326c533],
      { useABINames: true }
    );

    expect(instructions).toStrictEqual([
      'add a0 t0 a2',
      'ori x8 t1 16',
      'sub s1 t5 a1',
      'div a0 a3 s2',
    ]);
  });

  it('should decode single instruction', () => {
    expect(disasm(0x40bf04b3)).toBe('sub x9 x30 x11');
  });

  it('should decode RV32M instructions', () => {
    const instructions = disasm(
      [0x036591b3, 0x02b7cbb3, 0x03576eb3, 0x0220d033],
      { selectedExtensions: ['M'] }
    );

    expect(instructions).toStrictEqual([
      'mulh x3 x11 x22',
      'div x23 x15 x11',
      'rem x29 x14 x21',
      'divu x0 x1 x2',
    ]);
  });

  // TODO: add test for DisabledExtensionError (need at least one extension)
});
