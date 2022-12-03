import { Segment } from '@/enum/segment';

import { Context } from './context';

/**
 * Local symbol.
 */
export interface LocalSymbol {
  /**
   * Symbol segment.
   */
  readonly segment: Segment;

  /**
   * Symbol context.
   */
  readonly ctx: Context;

  /**
   * Symbol value.
   */
  readonly value: number;
}
