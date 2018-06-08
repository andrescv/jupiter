package vsim.utils;


public final class Data {

  // masks
  public static final int BYTE_MASK = 0xff;
  public static final int HALF_MASK = 0xffff;

  // lengths [bytes]
  public static final int HALF_LENGTH = 2;
  public static final int WORD_LENGTH = 4;

  // lenghts [bits]
  public static final int BYTE_LENGTH_BITS = 8;
  public static final int HALF_LENGTH_BITS = 16;
  public static final int WORD_LENGTH_BITS = 32;

  private static int signExtend(int value, int bits) {
    int shift = WORD_LENGTH_BITS - bits;
    return value << shift >> shift;
  }

  public static int signExtendByte(int value) {
    return signExtend(value, BYTE_LENGTH_BITS);
  }

  public static int signExtendHalf(int value) {
    return signExtend(value, HALF_LENGTH_BITS);
  }

}
