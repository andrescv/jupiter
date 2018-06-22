package vsim.utils;


public final class ALU {

  // floating point fraction/mantissa mask
  public static final int FRACTION_MASK = 0x007fffff;
  // floating point exponent mask
  public static final int EXPONENT_MASK = 0x7f800000;
  // floating point sign mask
  public static final int SIGN_MASK = 0x80000000;

  public static int mulh(int a, int b) {
    long result = ((long) a) * ((long) b);
    return (int)(result >> Data.WORD_LENGTH_BITS);
  }

  public static int mulhsu(int a, int b) {
    long result = ((long) a) * Integer.toUnsignedLong(b);
    return (int)(result >> Data.WORD_LENGTH_BITS);
  }

  public static int mulhu(int a, int b) {
    long result = Integer.toUnsignedLong(a) * Integer.toUnsignedLong(b);
    return (int)(result >>> Data.WORD_LENGTH_BITS);
  }

  public static int divu(int a, int b) {
    return Integer.divideUnsigned(a, b);
  }

  public static int remu(int a, int b) {
    return Integer.remainderUnsigned(a, b);
  }

  public static boolean geu(int a, int b) {
    int cmp = Integer.compareUnsigned(a, b);
    return (cmp == 0) || (cmp > 0);
  }

  public static boolean ltu(int a, int b) {
    return Integer.compareUnsigned(a, b) < 0;
  }

  public static float sqrt(float a) {
    return (float)Math.sqrt(a);
  }

  public static float fsgnj(float a, float b) {
    int ax = Float.floatToIntBits(a);
    int bx = Float.floatToIntBits(b);
    int cx = (bx & SIGN_MASK) | (ax & (EXPONENT_MASK | FRACTION_MASK));
    return Float.intBitsToFloat(cx);
  }

  public static float fsgnjn(float a, float b) {
    int ax = Float.floatToIntBits(a);
    int bx = Float.floatToIntBits(b);
    int cx = (~bx & SIGN_MASK) | (ax & (EXPONENT_MASK | FRACTION_MASK));
    return Float.intBitsToFloat(cx);
  }

  public static float fsgnjx(float a, float b) {
    int ax = Float.floatToIntBits(a);
    int bx = Float.floatToIntBits(b);
    int cx = ((ax ^ bx) & SIGN_MASK) | (ax & (EXPONENT_MASK | FRACTION_MASK));
    return Float.intBitsToFloat(cx);
  }

  public static float fcvtsw(Integer a) {
    return a.floatValue();
  }

  public static float fcvtswu(Integer a) {
    return ((Long) Integer.toUnsignedLong(a)).floatValue();
  }

  public static int fcvtws(float a) {
    if (Float.isNaN(a))
      return Integer.MAX_VALUE;
    else
      return Math.round(a);
  }

  public static int fcvtwus(float a) {
    if (a < Integer.MIN_VALUE)
      return 0;
    else if (a > Integer.MAX_VALUE || Float.isNaN(a))
      return Integer.MAX_VALUE;
    else
      // TODO: check this
      return Math.round(a);
  }

  private static boolean signalingNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) == 0);
  }

  private static boolean quietNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) != 0);
  }

  public static float fmin(float a, float b) {
    // if at least one input is a signaling NaN, return canonical NaN
    if (signalingNaN(a) || signalingNaN(b))
      return Float.NaN;
    // if both inputs are quiet nans, return canonical NaN
    else if (quietNaN(a) && quietNaN(b))
      return Float.NaN;
    // if one operand is a quiet NaN and the other is not a NaN, return
    // the non-NaN operand
    else if (quietNaN(a) || quietNaN(b)) {
      if (!quietNaN(a))
        return a;
      else
        return b;
    }
    // return the smaller floating point number
    else
      return Math.min(a, b);
  }

  public static float fmax(float a, float b) {
    // if at least one input is a signaling NaN, return canonical NaN
    if (signalingNaN(a) || signalingNaN(b))
      return Float.NaN;
    // if both inputs are quiet nans, return canonical NaN
    else if (quietNaN(a) && quietNaN(b))
      return Float.NaN;
    // if one operand is a quiet NaN and the other is not a NaN, return
    // the non-NaN operand
    else if (quietNaN(a) || quietNaN(b)) {
      if (!quietNaN(a))
        return a;
      else
        return b;
    }
    // return the larger floating point number
    else
      return Math.max(a, b);
  }

  public static int fclass(float f) {
    int out = 0;
    int bits = Float.floatToRawIntBits(f);
    // set flags
    boolean infOrNaN = Float.isNaN(f) || Float.isInfinite(f);
    boolean subnormalOrZero = (bits & EXPONENT_MASK) == 0;
    boolean sign = ((bits & SIGN_MASK) >> 31) != 0;
    boolean fracZero = (bits & FRACTION_MASK) == 0;
    boolean isNaN = Float.isNaN(f);
    boolean isSNaN = signalingNaN(f);
    // build 10-bit mask
    if (sign && infOrNaN && fracZero)
      out |= 1 << 0;
    if (sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 1;
    if (sign && subnormalOrZero && !fracZero)
      out |= 1 << 2;
    if (sign && subnormalOrZero && fracZero)
      out |= 1 << 3;
    if (!sign && infOrNaN && fracZero)
      out |= 1 << 7;
    if (!sign && !infOrNaN && !subnormalOrZero)
      out |= 1 << 6;
    if (!sign && subnormalOrZero && !fracZero)
      out |= 1 << 5;
    if (!sign && subnormalOrZero && fracZero)
      out |= 1 << 4;
    if (isNaN &&  isSNaN)
      out |= 1 << 8;
    if (isNaN && !isSNaN)
      out |= 1 << 9;
    return out;
  }

}
