import { describe, expect, it, vi } from 'vitest';

import { RVExtension } from '@/interfaces/options';

import { RVDecodeHandler } from './handler';

describe('[chain of responsibility] tests', () => {
  it('should be handled by first handler', () => {
    const handler1 = fakeHandlerFactory({
      execute: () => 'add x1 x2 x3',
    });

    const handler2 = fakeHandlerFactory({ isaModule: 'A' });

    vi.spyOn(handler1, 'execute');
    vi.spyOn(handler2, 'execute');

    handler1.setNext(handler2);

    const instruction = handler1.decode(0x003100b3);

    expect(instruction).toStrictEqual({
      handledBy: 'RV32I',
      data: 'add x1 x2 x3',
    });
    expect(handler1.execute).toHaveBeenCalledTimes(1);
    expect(handler2.execute).toHaveBeenCalledTimes(0);
  });

  it('should be handled by second handler', () => {
    const handler1 = fakeHandlerFactory({ isaModule: 'F1' });
    const handler2 = fakeHandlerFactory({ execute: () => 'add x1 x2 x3' });
    const handler3 = fakeHandlerFactory({ isaModule: 'A' });

    vi.spyOn(handler1, 'execute');
    vi.spyOn(handler2, 'execute');
    vi.spyOn(handler3, 'execute');

    handler1.setNext(handler2);
    handler2.setNext(handler3);

    const instruction = handler1.decode(0x003100b3);

    expect(instruction).toStrictEqual({
      handledBy: 'RV32I',
      data: 'add x1 x2 x3',
    });
    expect(handler1.execute).toHaveBeenCalledTimes(1);
    expect(handler2.execute).toHaveBeenCalledTimes(1);
    expect(handler3.execute).toHaveBeenCalledTimes(0);
  });

  it('should be handled by last handler', () => {
    const handler1 = fakeHandlerFactory({ isaModule: 'F1' });
    const handler2 = fakeHandlerFactory({ isaModule: 'A' });
    const handler3 = fakeHandlerFactory({ execute: () => 'add x1 x2 x3' });

    vi.spyOn(handler1, 'execute');
    vi.spyOn(handler2, 'execute');
    vi.spyOn(handler3, 'execute');

    handler1.setNext(handler2);
    handler2.setNext(handler3);

    const instruction = handler1.decode(0x003100b3);

    expect(instruction).toStrictEqual({
      handledBy: 'RV32I',
      data: 'add x1 x2 x3',
    });
    expect(handler1.execute).toHaveBeenCalledTimes(1);
    expect(handler2.execute).toHaveBeenCalledTimes(1);
    expect(handler3.execute).toHaveBeenCalledTimes(1);
  });

  it('unsupported instruction', () => {
    const handler1 = fakeHandlerFactory({ isaModule: 'F1' });
    const handler2 = fakeHandlerFactory({ isaModule: 'F2' });

    vi.spyOn(handler1, 'execute');
    vi.spyOn(handler2, 'execute');

    handler1.setNext(handler2);

    const instruction = handler1.decode(0x003100b3);

    expect(instruction).toStrictEqual({
      handledBy: null,
    });

    expect(handler1.execute).toHaveBeenCalledTimes(1);
    expect(handler2.execute).toHaveBeenCalledTimes(1);
  });
});

const fakeHandlerFactory = (instance: Partial<HandlerLike> = {}) =>
  Object.assign(new FakeHandler(), instance);

class FakeHandler extends RVDecodeHandler {
  protected isaModule: 'RV32I' | RVExtension = 'RV32I';

  execute(): string | null {
    return null;
  }
}

type HandlerLike = { isaModule: string; execute: () => string | null };
