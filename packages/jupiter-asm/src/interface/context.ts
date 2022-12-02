import ParserRuleContext from 'antlr4/context/ParserRuleContext';

/**
 * Assembler context.
 */
export interface Context extends ParserRuleContext {
  /**
   * Context keys.
   */
  // eslint-disable-next-line @typescript-eslint/no-explicit-any
  [key: string]: any;
}
