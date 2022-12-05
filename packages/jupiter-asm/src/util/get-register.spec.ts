import TerminalNode from 'antlr4/tree/TerminalNode';
import { describe, expect, it } from 'vitest';
import { mockDeep } from 'vitest-mock-extended';

import { getRegister } from './get-register';

describe('[getRegister] tests', () => {
  it('should parse an x register value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'x31',
    });

    expect(getRegister(node)).toEqual(31);
  });

  it('should parse an x register value correctly (mnemonic)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'a0',
    });

    expect(getRegister(node)).toEqual(10);
  });

  it('should parse an f register value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'f12',
    });

    expect(getRegister(node)).toEqual(12);
  });

  it('should parse an f register value correctly (mnemonic)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'fa3',
    });

    expect(getRegister(node)).toEqual(13);
  });

  it('should parse an invalid register value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'ABC',
    });

    expect(getRegister(node)).toEqual(NaN);
  });
});
