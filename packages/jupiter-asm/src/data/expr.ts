import { Bits } from '@/constant/bits';
import { Mask } from '@/constant/mask';

import { Context } from '@/interface/context';

import { getFloat } from '@/util/get-float';
import { getInt } from '@/util/get-int';

import { State } from './state';

/**
 * Base assembler expression.
 */
export abstract class Expr {
  /**
   * Creates a new assembler expression.
   *
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(public readonly state: State, public readonly ctx: Context) {}

  /**
   * Evaluates the expression.
   *
   * @virtual
   * @param pc - Current program counter.
   */
  abstract eval(pc: number): number;

  /**
   * Checks if the expression is correct.
   */
  validate() {
    // Do nothing.
  }

  /**
   * Gets the expression context.
   *
   * @returns Expression context.
   */
  getContex() {
    return this.ctx;
  }

  /**
   * Checks if the expression contains ids.
   *
   * @returns True if the expression has ids, false if not.
   */
  hasIds() {
    return false;
  }

  /**
   * Checks if the expression contains floats.
   *
   * @returns True if the expression has floats, false if not.
   */
  hasFloats() {
    return false;
  }
}

/**
 * Assembler integer expression.
 */
export class IntExpr extends Expr {
  /**
   * @override
   */
  validate(): void {
    const int = this.ctx.INT().getText();
    const value = getInt(this.ctx.INT());

    if (isNaN(value) || value < -2147483648 || value > 4294967295) {
      this.state.addError(this.ctx, `invalid int: ${int}`);
    }
  }

  /**
   * @override
   */
  eval(): number {
    return getInt(this.ctx.INT());
  }
}

/**
 * Assembler float expression.
 */
export class FloatExpr extends Expr {
  /**
   * @override
   */
  eval(): number {
    return getFloat(this.ctx.FLOAT());
  }

  /**
   * @override
   */
  hasFloats() {
    return true;
  }
}

/**
 * Assembler id expression.
 */
export class IdExpr extends Expr {
  /**
   * @override
   */
  validate(): void {
    const id = this.ctx.ID().getText();
    const global = this.state.globals.has(id);
    const local = this.state.locals.has(id);
    const def = this.state.defs.has(id);

    // should be a def, global or local symbol
    if (!global && !local && !def) {
      this.state.addError(this.ctx, `'${id}' used but not defined`);
    }
  }

  /**
   * @override
   */
  eval(): number {
    const id = this.ctx.ID().getText();
    const global = this.state.globals.get(id);
    const local = this.state.locals.get(id);
    const def = this.state.defs.get(id);

    if (global) {
      return global.value;
    } else if (local) {
      return local.value;
    }

    return def?.expr.eval(0) ?? 0;
  }

  /**
   * @override
   */
  hasIds() {
    const id = this.ctx.ID().getText();
    const def = this.state.defs.get(id);

    return (
      this.state.locals.has(id) ||
      this.state.globals.has(id) ||
      !!def?.expr.hasIds()
    );
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    const id = this.ctx.ID().getText();
    const def = this.state.defs.get(id);

    return !!def && def.expr.hasFloats();
  }
}

/**
 * Assembler unary expression.
 */
export class UnaryExpr extends Expr {
  /**
   * Creates a new assembler unary expression.
   *
   * @param e     - Expression.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(private readonly e: Expr, state: State, ctx: Context) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    const sign = this.ctx.SIGN().getText();
    const weight = sign === '-' ? -1 : 1;

    return this.e.eval(pc) * weight;
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e.hasFloats();
  }
}

/**
 * Assembler op expression.
 */
export class OpExpr extends Expr {
  /**
   * Creates a new assembler op expression.
   *
   * @param e1    - Expression 1.
   * @param e2    - Expression 2.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(
    private readonly e1: Expr,
    private readonly e2: Expr,
    state: State,
    ctx: Context
  ) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e1.validate();
    this.e2.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    const sign = this.ctx.SIGN().getText();
    const weight = sign === '-' ? -1 : 1;

    return this.e1.eval(pc) + weight * this.e2.eval(pc);
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e1.hasIds() || this.e2.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e1.hasFloats() || this.e2.hasFloats();
  }
}

/**
 * Assembler HI expression.
 */
export class HIExpr extends Expr {
  /**
   * Creates a new assembler HI expression.
   *
   * @param e     - Expression.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(private readonly e: Expr, state: State, ctx: Context) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    return (this.e.eval(pc) >>> Bits.LO) & Mask.HI;
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e.hasFloats();
  }
}

/**
 * Assembler LO expression.
 */
export class LOExpr extends Expr {
  /**
   * Creates a new assembler LO expression.
   *
   * @param e     - Expression.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(private readonly e: Expr, state: State, ctx: Context) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    return this.e.eval(pc) & Mask.LO;
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e.hasFloats();
  }
}

/**
 * Assembler PC relative HI expression.
 */
export class PCRelHIExpr extends Expr {
  /**
   * Creates a new assembler PC relative HI expression.
   *
   * @param e     - Expression.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(private readonly e: Expr, state: State, ctx: Context) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    const delta = (this.e.eval(pc) - pc) & Mask.WORD;
    return ((delta >>> Bits.LO) + ((delta >>> (Bits.LO - 1)) & 0x1)) & Mask.HI;
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e.hasFloats();
  }
}

/**
 * Assembler PC relative LO expression.
 */
export class PCRelLOExpr extends Expr {
  /**
   * Creates a new assembler PC relative LO expression.
   *
   * @param e     - Expression.
   * @param state - Assembler state.
   * @param ctx   - Expression context.
   */
  constructor(private readonly e: Expr, state: State, ctx: Context) {
    super(state, ctx);
  }

  /**
   * @override
   */
  validate(): void {
    this.e.validate();
  }

  /**
   * @override
   */
  eval(pc: number) {
    const delta = (this.e.eval(pc) - pc) & Mask.WORD;
    return ((delta >>> Bits.LO) + ((delta >>> (Bits.LO - 1)) & 0x1)) & Mask.HI;
  }

  /**
   * @override
   */
  hasIds(): boolean {
    return this.e.hasIds();
  }

  /**
   * @override
   */
  hasFloats(): boolean {
    return this.e.hasFloats();
  }
}

/**
 * Expressions constructors.
 */
export const E = <const>{
  /**
   * IntExpr constructor.
   */
  int: IntExpr,

  /**
   * FloatExpr constructor.
   */
  float: FloatExpr,

  /**
   * IdExpr constructor.
   */
  id: IdExpr,

  /**
   * UnaryExpr constructor.
   */
  unary: UnaryExpr,

  /**
   * OpExpr constructor.
   */
  op: OpExpr,

  /**
   * HiExpr constructor.
   */
  hi: HIExpr,

  /**
   * LoExpr constructor.
   */
  lo: LOExpr,

  /**
   * PCRelHiExpr constructor.
   */
  pcrelhi: PCRelHIExpr,

  /**
   * PCRelLoExpr constructor.
   */
  pcrello: PCRelLOExpr,
};
