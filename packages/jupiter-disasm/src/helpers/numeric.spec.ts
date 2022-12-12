import { describe, expect, it } from 'vitest';

import { areNumeric } from './numeric';

describe('check numeric fields', () => {
  it('should be numbers', () => {
    expect(areNumeric(10, 1.2, 38)).toBe(true);
    expect(areNumeric(10, null, 38)).toBe(false);
    expect(areNumeric(10, '29', 38)).toBe(false);
    expect(areNumeric(10, 28, undefined)).toBe(false);
  });
});
