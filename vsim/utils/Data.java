package vsim.utils;


public final class Data {

  // masks
  public static final int BYTE_MASK = 0xff;
  public static final int HALF_MASK = 0xffff;
  public static final int WORD_MASK = 0xffffffff;

  // lengths [bytes]
  public static final int BYTE_LENGTH = 1;
  public static final int HALF_LENGTH = 2;
  public static final int WORD_LENGTH = 4;

  // lenghts [bits]
  public static final int BYTE_LENGTH_BITS = 8;
  public static final int HALF_LENGTH_BITS = 16;
  public static final int WORD_LENGTH_BITS = 32;

  public static int signExtend(int value, int bits) {
    int shift = WORD_LENGTH_BITS - bits;
    return value << shift >> shift;
  }

  public static int signExtendByte(int value) {
    return signExtend(value, BYTE_LENGTH_BITS);
  }

  public static int signExtendHalf(int value) {
    return signExtend(value, HALF_LENGTH_BITS);
  }

  public static int alignToWordBoundary(int address) {
    if (Integer.divideUnsigned(address, 4) != 0) {
      int offset = WORD_LENGTH - Integer.remainderUnsigned(address, WORD_LENGTH);
      return address + offset;
    }
    return address;
  }

  public static boolean validHalf(int value) {
    return !((value < -32768) || (value > 32767));
  }

  public static boolean validByte(int value) {
    return !((value < -128) || (value > 127));
  }

  public static int mulh(int a, int b) {
    long result = ((long) a) * ((long) b);
    return (int)(result >> WORD_LENGTH_BITS);
  }

  public static int mulhsu(int a, int b) {
    long result = ((long) a) * Integer.toUnsignedLong(b);
    return (int)(result >> WORD_LENGTH_BITS);
  }

  public static int mulhu(int a, int b) {
    long result = Integer.toUnsignedLong(a) * Integer.toUnsignedLong(b);
    return (int)(result >>> WORD_LENGTH_BITS);
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
    int ax = Float.floatToRawIntBits(a);
    int bx = Float.floatToRawIntBits(b);
    int cx = (bx & 0x80000000) | (ax & 0x7FFFFFFF);
    return Float.intBitsToFloat(cx);
  }

  public static float fsgnjn(float a, float b) {
    int ax = Float.floatToRawIntBits(a);
    int bx = Float.floatToRawIntBits(b);
    int cx = (~bx & 0x80000000) | (ax & 0x7FFFFFFF);
    return Float.intBitsToFloat(cx);
  }

  public static float fsgnjx(float a, float b) {
    int ax = Float.floatToRawIntBits(a);
    int bx = Float.floatToRawIntBits(b);
    int cx = ((ax ^ bx) & 0x80000000) | (ax & 0x7FFFFFFF);
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

  private static boolean signallingNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) == 0);
  }

  private static boolean quietNaN(float f) {
    return Float.isNaN(f) && ((Float.floatToRawIntBits(f) & 0x00400000) != 0);
  }

  public static float fmin(float a, float b) {
    // if at least one input is a signaling NaN, return canonical NaN
    if (signallingNaN(a) || signallingNaN(b))
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
    } else
      return Math.min(a, b);
  }

  public static float fmax(float a, float b) {
    // if at least one input is a signaling NaN, return canonical NaN
    if (signallingNaN(a) || signallingNaN(b))
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
    } else
      return Math.max(a, b);
  }

  public static int fclass(float f) {
    int out = 0;
    int bits = Float.floatToRawIntBits(f);
    boolean infOrNaN = Float.isNaN(f) || Float.isInfinite(f);
    boolean subnormalOrZero = (bits & 0x7F800000) == 0;
    boolean sign = ((bits >>> 31) & 0x1) != 0;
    boolean fracZero = (bits & 0x007FFFFF) == 0;
    boolean isNaN = Float.isNaN(f);
    boolean isSNaN = signallingNaN(f);
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
