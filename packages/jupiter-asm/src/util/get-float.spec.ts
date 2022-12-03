import { describe, expect, it } from '@jest/globals';
import TerminalNode from 'antlr4/tree/TerminalNode';
import { mockDeep } from 'jest-mock-extended';

import { getFloat } from './get-float';

describe('[getFloat] tests', () => {
  it('should parse a float value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => '1.0',
    });

    expect(getFloat(node)).toEqual(1.0);
  });

  it('should parse an invalid float value correctly', () => {
    const node = mockDeep<TerminalNode>({
      getText: () => 'ABC',
    });

    expect(getFloat(node)).toEqual(NaN);
  });
});
