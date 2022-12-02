/**
 * RV segment.
 */
export enum Segment {
  /**
   * Text (.text) segment.
   */
  TEXT = '.text',

  /**
   * Data (.data) segment.
   */
  DATA = '.data',

  /**
   * Read-only data (.rodata) segment.
   */
  RODATA = '.rodata',

  /**
   * BSS (.bss) segment.
   */
  BSS = '.bss',
}
