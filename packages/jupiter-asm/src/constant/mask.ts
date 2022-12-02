export const Mask = <const>{
  /**
   * Bit mask.
   */
  BIT: 0x1,

  /**
   * Byte mask.
   */
  BYTE: 0xff,

  /**
   * Half mask.
   */
  HALF: 0xffff,

  /**
   * Word mask.
   */
  WORD: 0xffffffff,

  /**
   * Floating point fraction/mantissa mask.
   */
  FP_FRACTION: 0x007fffff,

  /**
   * Floating point exponent mask.
   */
  FP_EXPONENT: 0x7f800000,

  /**
   * Floating point sign mask.
   */
  FP_SIGN: 0x80000000,

  /**
   * LO mask.
   */
  LO: 0xfff,

  /**
   * HI mask.
   */
  HI: 0xfffff,
};
