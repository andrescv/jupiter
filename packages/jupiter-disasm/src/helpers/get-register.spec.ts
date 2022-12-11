import { describe, expect, it } from 'vitest';

import { getFRegister, getXRegister } from './get-register';

describe('get register', () => {
  it('x register', () => {
    expect(getXRegister(0)).toBe('x0');
    expect(getXRegister(0, { useABIName: true })).toBe('zero');
    expect(getXRegister(1)).toBe('x1');
    expect(getXRegister(1, { useABIName: true })).toBe('ra');
    expect(getXRegister(7)).toBe('x7');
    expect(getXRegister(7, { useABIName: true })).toBe('t2');
    expect(getXRegister(15)).toBe('x15');
    expect(getXRegister(15, { useABIName: true })).toBe('a5');
    expect(getXRegister(29)).toBe('x29');
    expect(getXRegister(29, { useABIName: true })).toBe('t4');
  });

  it('f register', () => {
    expect(getFRegister(0)).toBe('f0');
    expect(getFRegister(0, { useABIName: true })).toBe('ft0');
    expect(getFRegister(1)).toBe('f1');
    expect(getFRegister(1, { useABIName: true })).toBe('ft1');
    expect(getFRegister(7)).toBe('f7');
    expect(getFRegister(7, { useABIName: true })).toBe('ft7');
    expect(getFRegister(15)).toBe('f15');
    expect(getFRegister(15, { useABIName: true })).toBe('fa5');
    expect(getFRegister(29)).toBe('f29');
    expect(getFRegister(29, { useABIName: true })).toBe('ft9');
  });
});
