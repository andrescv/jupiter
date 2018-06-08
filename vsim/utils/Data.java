package vsim.utils;


public final class Data {

  // masks
  public static final int  BYTE_MASK = 0xff;
  public static final int  HALF_MASK = 0xffff;
  public static final long WORD_MASK = 0xffffffffL;

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

  public static int mulh(int a, int b) {
    long result = ((long) a) * ((long) b);
    return (int)(result >>> WORD_LENGTH_BITS);
  }

  public static int mulhsu(int a, int b) {
    long result = ((long) a) * Integer.toUnsignedLong(b);
    return (int)(result >>> WORD_LENGTH_BITS);
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

}
