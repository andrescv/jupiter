import { Context } from './context';
import { Input } from './input';

/**
 * Global symbol.
 */
export interface GlobalSymbol {
  /**
   * Symbol input.
   */
  readonly input: Input;

  /**
   * Symbol context.
   */
  readonly ctx: Context;

  /**
   * Symbol value.
   */
  readonly value: number;
}
