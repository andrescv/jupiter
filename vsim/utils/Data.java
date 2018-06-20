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

  // min and max values
  public static final int BYTE_MIN_VALUE = -128;
  public static final int BYTE_MAX_VALUE = 127;
  public static final int HALF_MIN_VALUE = -32768;
  public static final int HALF_MAX_VALUE = 32767;

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

  public static boolean isWordAligned(int address) {
    return Integer.remainderUnsigned(address, WORD_LENGTH) == 0;
  }

  public static int alignToWordBoundary(int address) {
    if (Integer.divideUnsigned(address, WORD_LENGTH) != 0) {
      int offset = WORD_LENGTH - Integer.remainderUnsigned(address, WORD_LENGTH);
      return address + offset;
    }
    return address;
  }

  public static boolean inRange(int value, int low, int high) {
    return (value >= low && value <= high);
  }

  public static boolean validHalf(int value) {
    return Data.inRange(value, HALF_MIN_VALUE, HALF_MAX_VALUE);
  }

  public static boolean validByte(int value) {
    return Data.inRange(value, BYTE_MIN_VALUE, BYTE_MAX_VALUE);
  }

}
