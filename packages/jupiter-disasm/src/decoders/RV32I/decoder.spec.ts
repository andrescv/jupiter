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

  it('should decode I-type instructions correctly', () => {
    expect(decode(0x008b0567)).toBe('jalr x10 8(x22)');
    expect(decode(0xff8a02e7)).toBe('jalr x5 -8(x20)');
    expect(decode(0x00028503)).toBe('lb x10 0(x5)');
    expect(decode(0xffc30583)).toBe('lb x11 -4(x6)');
    expect(decode(0x00029503)).toBe('lh x10 0(x5)');
    expect(decode(0xffc31583)).toBe('lh x11 -4(x6)');
    expect(decode(0x8002a503)).toBe('lw x10 -2048(x5)');
    expect(decode(0x7ff44583)).toBe('lbu x11 2047(x8)');
    expect(decode(0x02095503)).toBe('lhu x10 32(x18)');
    expect(decode(0xb5058513)).toBe('addi x10 x11 -1200');
    expect(decode(0x4b05a513)).toBe('slti x10 x11 1200');
    expect(decode(0xed55b413)).toBe('sltiu x8 x11 -299');
    expect(decode(0x19034613)).toBe('xori x12 x6 400');
    expect(decode(0x19036613)).toBe('ori x12 x6 400');
    expect(decode(0x04637b93)).toBe('andi x23 x6 70');
    expect(decode(0x01f59a13)).toBe('slli x20 x11 31');
    expect(decode(0x01455d93)).toBe('srli x27 x10 20');
    expect(decode(0x40f85b13)).toBe('srai x22 x16 15');
    expect(decode(0x00000073)).toBe('ecall');
    expect(decode(0x00100073)).toBe('ebreak');
  });

  it('should decode S-type instructions correctly', () => {
    expect(decode(0x00a58223)).toBe('sb x10 4(x11)');
    expect(decode(0xc0551023)).toBe('sh x5 -1024(x10)');
    expect(decode(0x7e652e23)).toBe('sw x6 2044(x10)');
    expect(decode(0x02652fa3)).toBe('sw x6 63(x10)');
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
