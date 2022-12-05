import { Expr } from '@/data/expr';

import { Context } from './context';

/**
 * Def (constant).
 */
export interface Def {
  /**
   * Def context.
   */
  readonly ctx: Context;

  /**
   * Def expression.
   */
  readonly expr: Expr;
}
