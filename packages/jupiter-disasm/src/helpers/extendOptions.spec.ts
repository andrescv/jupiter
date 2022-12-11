import { describe, expect, it } from 'vitest';

import extendOptions from './extendOptions';

describe('extend options for disasm function', () => {
  it('without options', () => {
    const options = extendOptions({});

    expect(options.selectedExtensions).toHaveLength(7);
    expect(options.selectedExtensions).toEqual([
      'Zifencei',
      'Zicsr',
      'M',
      'A',
      'F',
      'D',
      'A',
    ]);
    expect(options.useABINames).toBe(false);
  });

  it('override selected options', () => {
    const options = extendOptions({
      selectedExtensions: ['Zifencei'],
    });

    expect(options.selectedExtensions).toHaveLength(1);
    expect(options.selectedExtensions).toEqual(['Zifencei']);
    expect(options.useABINames).toBe(false);

    const options2 = extendOptions({
      selectedExtensions: [],
    });

    expect(options2.selectedExtensions).toHaveLength(0);
  });

  it('set all options', () => {
    const options = extendOptions({
      selectedExtensions: ['A', 'M'],
      useABINames: true,
    });

    expect(options.selectedExtensions).toHaveLength(2);
    expect(options.selectedExtensions).toEqual(['A', 'M']);
    expect(options.useABINames).toBe(true);
  });
});
