import { describe, expect, it } from 'vitest';
import { mockDeep } from 'vitest-mock-extended';

import { Segment } from '@/enum/segment';

import { Context } from '@/interface/context';
import { Input } from '@/interface/input';
import { Options } from '@/interface/options';

import { E } from './expr';
import { State } from './state';

describe('[Expr] tests', () => {
  const input: Input = {
    name: 'test.s',
    source: 'add a0, a0, a0',
  };

  it('should be defined', () => {
    expect(E).toBeDefined();
    expect(E.int).toBeDefined();
    expect(E.float).toBeDefined();
    expect(E.id).toBeDefined();
    expect(E.unary).toBeDefined();
    expect(E.op).toBeDefined();
    expect(E.hi).toBeDefined();
    expect(E.lo).toBeDefined();
    expect(E.pcrelhi).toBeDefined();
    expect(E.pcrello).toBeDefined();
  });

  it('should create a valid int expression', () => {
    const ctx = mockDeep<Context>({
      INT: () => ({
        getText: () => '1',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const expr = new E.int(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(1);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid int expression (overflow)', () => {
    const ctx = mockDeep<Context>({
      INT: () => ({
        getText: () => '4294967296',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const expr = new E.int(state, ctx);

    expr.validate();

    expect(state.messages).toHaveLength(1);
  });

  it('should create a valid float expression', () => {
    const ctx = mockDeep<Context>({
      FLOAT: () => ({
        getText: () => '13.5',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const expr = new E.float(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(13.5);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeTruthy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid id expression (id is in locals)', () => {
    const ctx = mockDeep<Context>({
      ID: () => ({
        getText: () => 'a',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);

    state.locals.set('a', {
      ctx,
      value: 1,
      segment: Segment.DATA,
    });

    const expr = new E.id(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(1);
    expect(expr.hasIds()).toBeTruthy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid id expression (id is in globals)', () => {
    const ctx = mockDeep<Context>({
      ID: () => ({
        getText: () => 'a',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);

    state.globals.set('a', {
      ctx,
      value: 1,
      input: state.input,
    });

    const expr = new E.id(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(1);
    expect(expr.hasIds()).toBeTruthy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid id expression (id is in defs)', () => {
    const ctx = mockDeep<Context>({
      ID: () => ({
        getText: () => 'a',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);

    state.defs.set('a', {
      ctx,
      expr: new E.float(
        state,
        mockDeep<Context>({
          FLOAT: () => ({
            getText: () => '13.5',
          }),
        })
      ),
    });

    const expr = new E.id(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(13.5);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeTruthy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid id expression (id not defined)', () => {
    const ctx = mockDeep<Context>({
      ID: () => ({
        getText: () => 'a',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);

    const expr = new E.id(state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval()).toEqual(0);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(1);
  });

  it('should create a valid unary expression (+)', () => {
    const ctx = mockDeep<Context>({
      SIGN: () => ({
        getText: () => '+',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );

    const expr = new E.unary(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(1);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid unary expression (-)', () => {
    const ctx = mockDeep<Context>({
      SIGN: () => ({
        getText: () => '-',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );

    const expr = new E.unary(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(-1);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid op expression (-)', () => {
    const ctx = mockDeep<Context>({
      SIGN: () => ({
        getText: () => '-',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e1 = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );
    const e2 = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );

    const expr = new E.op(e1, e2, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(0);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid op expression (+)', () => {
    const ctx = mockDeep<Context>({
      SIGN: () => ({
        getText: () => '+',
      }),
    });

    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e1 = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );
    const e2 = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '1',
        }),
      })
    );

    const expr = new E.op(e1, e2, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(2);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid hi expression', () => {
    const ctx = mockDeep<Context>();
    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '0xfffff000',
        }),
      })
    );

    const expr = new E.hi(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(0xfffff);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid lo expression', () => {
    const ctx = mockDeep<Context>();
    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '0xfffffabc',
        }),
      })
    );

    const expr = new E.lo(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(0xabc);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid pc-rel hi expression', () => {
    const ctx = mockDeep<Context>();
    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '0xfffff000',
        }),
      })
    );

    const expr = new E.pcrelhi(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(0xfffff);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });

  it('should create a valid pc-rel lo expression', () => {
    const ctx = mockDeep<Context>();
    const options = mockDeep<Options>();
    const state = new State(input, options);
    const e = new E.int(
      state,
      mockDeep<Context>({
        INT: () => ({
          getText: () => '0xfffffabc',
        }),
      })
    );

    const expr = new E.pcrello(e, state, ctx);

    expect(expr).toBeDefined();
    expect(expr.eval(0)).toEqual(0x0);
    expect(expr.hasIds()).toBeFalsy();
    expect(expr.hasFloats()).toBeFalsy();
    expect(expr.getContex()).toBeDefined();

    expr.validate();

    expect(state.messages).toHaveLength(0);
  });
});
