import TerminalNode from 'antlr4/tree/TerminalNode';
import { describe, expect, it } from 'vitest';
import { mockDeep } from 'vitest-mock-extended';

import { getInt } from './get-int';

describe('[getInt] tests', () => {
  it('should parse an int value correctly (decimal)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => '1',
    });

    expect(getInt(node)).toEqual(1);
  });

  it('should parse an int value correctly (hexadecimal)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => '0x10',
    });

    expect(getInt(node)).toEqual(0x10);
  });

  it('should parse an int value correctly (binary)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => '0b10',
    });

    expect(getInt(node)).toEqual(0b10);
  });

  it('should parse an int value correctly (character)', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => "'a'",
    });

    expect(getInt(node)).toEqual('a'.charCodeAt(0));
  });

  it('should parse an invalid int value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'ABC',
    });

    expect(getInt(node)).toEqual(NaN);
  });
});
