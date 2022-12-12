import { describe, expect, it } from 'vitest';

import { areNumeric, extendSign } from './numeric';

describe('check numeric fields', () => {
  it('should be numbers', () => {
    expect(areNumeric(10, 1.2, 38)).toBe(true);
    expect(areNumeric(10, null, 38)).toBe(false);
    expect(areNumeric(10, '29', 38)).toBe(false);
    expect(areNumeric(10, 28, undefined)).toBe(false);
  });

  it('should extend numbers correctly', () => {
    expect(extendSign(0b1010, 4)).toBe(-6);
    expect(extendSign(0b1010, 5)).toBe(10);
    expect(extendSign(0b1110, 5)).toBe(14);
    expect(extendSign(0b1011, 4)).toBe(-5);
    expect(extendSign(0b1011, 3)).toBe(3);
    expect(extendSign(0b1001, 3)).toBe(1);
  });
});
