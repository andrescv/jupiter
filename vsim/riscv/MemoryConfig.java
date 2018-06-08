package vsim.riscv;


public final class MemoryConfig {

  /*
      Memory "Layout" Configuration

                                                 base
                      +-------------------+
                      |     reserved      | <- 0x80000000
      SP 0x7ffffffc-> +-------------------+ <- 0x7ffffffc
                      |      stack        |
                      |                   |
                      *********************
                      |                   |
                      |       heap        |
                      +-------------------+ <- 0x10008000
                      |    data segment   |
      GP 0x10000000-> +-------------------+ <- 0x10000000
                      |    text segment   |
      PC 0x00000000-> +-------------------+ <- 0x00000000

                      <------XLENGTH------>

  */

  // memory address where the stack segment starts
  public static final int STACK_SEGMENT  = 0x7ffffffc;

  // memory address where the heap segment starts
  public static final int HEAP_SEGMENT   = 0x10008000;

  // memory address where the data segment starts
  public static final int DATA_SEGMENT   = 0x10000000;

  // memory addres where the text segment starts
  public static final int TEXT_SEGMENT   = 0x00000000;

}
