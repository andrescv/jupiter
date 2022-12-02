/**
 * Assembler options.
 */
export interface Options {
  /**
   * Bare machine mode (no pseudos).
   */
  readonly bare: boolean;

  /**
   * Start label.
   */
  readonly start: string;

  /**
   * RV extensions.
   */
  readonly extensions: ('Zifencei' | 'Zicsr' | 'M' | 'A' | 'F' | 'D')[];
}
