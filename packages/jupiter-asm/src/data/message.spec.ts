import { beforeEach, describe, expect, it } from '@jest/globals';
import { mockDeep, mockReset } from 'jest-mock-extended';

import { Context } from '@/interface/context';
import { Input } from '@/interface/input';

import { Message } from './message';

describe('[Message] tests', () => {
  const input: Input = {
    name: 'test.s',
    source: 'add a0, a1, a2',
  };

  const ctx = mockDeep<Context>({
    start: {
      line: 1,
      column: 0,
      start: 0,
      stop: 13,
    },
    stop: {
      stop: 13,
    },
  });

  beforeEach(() => {
    mockReset(ctx);
  });

  it('should be defined', () => {
    expect(new Message(input, ctx, 'test message')).toBeDefined();
  });

  it('should create an error string', () => {
    const message = new Message(input, ctx, 'test message');

    expect(message.toString()).toBeDefined();
    expect(message.toString()).toEqual(`[ERROR] test.s:1:1: test message

add a0, a1, a2
^^^^^^^^^^^^^^`);
  });
});
