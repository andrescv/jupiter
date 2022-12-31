import { describe, expect, it } from 'vitest';

import { deepFreeze } from './freeze';

describe('freeze', () => {
  it('should deep freeze an object', () => {
    const obj = {
      a: 1,
      b: {
        c: 2,
      },
    };

    const frozen = deepFreeze(obj);

    expect(Object.isFrozen(frozen)).toBe(true);
    expect(Object.isFrozen(frozen.b)).toBe(true);
  });
});
