import { describe, expect, it } from 'vitest';

import { Handler } from '@/interfaces/handler';

import { chain } from './chain';

describe('[chain handlers] tests', () => {
  it('should chain handlers correctly (without base)', () => {
    const base = chain([
      handlerFactory('handler1'),
      handlerFactory('handler2'),
      handlerFactory('handler3'),
    ]);

    expect(base.id).toEqual('handler1');

    const handler2 = base.next;
    expect(handler2).toBeTruthy();
    expect(handler2?.id).toEqual('handler2');

    const handler3 = handler2?.next;
    expect(handler3).toBeTruthy();
    expect(handler3?.id).toEqual('handler3');

    expect(handler3?.next).toBeNull();
  });

  it('should chain handlers correctly (with base)', () => {
    const base = handlerFactory('base');
    const chained = chain(
      [
        handlerFactory('handler1'),
        handlerFactory('handler2'),
        handlerFactory('handler3'),
      ],
      base
    );

    expect(chained).toEqual(base);

    const handler1 = base.next;
    expect(handler1).toBeTruthy();
    expect(handler1?.id).toEqual('handler1');

    const handler2 = handler1?.next;
    expect(handler2).toBeTruthy();
    expect(handler2?.id).toEqual('handler2');

    const handler3 = handler2?.next;
    expect(handler3).toBeTruthy();
    expect(handler3?.id).toEqual('handler3');

    expect(handler3?.next).toBeNull();
  });
});

const handlerFactory = (id: string): FakeHandler => ({
  id,
  next: null,
  setNext(handler: FakeHandler) {
    this.next = handler;
  },
});

interface FakeHandler extends Handler {
  id: string;
  next: FakeHandler | null;
}
