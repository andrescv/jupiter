/**
 * RV instruction field.
 */
export interface Field {
  /**
   * Field lower bit.
   */
  readonly lo: number;

  /**
   * Field mask.
   */
  readonly mask: number;
}
