package vsim.riscv;


public final class MemorySegments {

  /*
      Memory Segments Configuration

      Ref: Chapter 3: RISC-V Assembly Language, The RISC-V Reader

                      +------------------+
                      |     reserved     |
                      +------------------+<- 0x7ffffffc
      SP 0xbffffff0 ->|      stack       |
                      |                  |
                      ********************
                      |                  |
                      |       heap       |
                      +------------------+
                      |   static data    |
      GP 0x10000000 ->+------------------+<- 0x10000000
                      |       text       |
      PC 0x00010000 ->+------------------+<- 0x00010000
                      |     reserved     |
                      +------------------+<- 0x00000000

                      <------XLENGTH------>
  */

  // memory address where the stack segment starts
  public static final int STACK_SEGMENT  = 0x7ffffffc;

  // memory address where the heap segment starts
  public static final int HEAP_SEGMENT   = 0x10008000;

  // memory address where the data segment starts
  public static final int DATA_SEGMENT   = 0x10000000;

  // memory addres where the text segment starts
  public static final int TEXT_SEGMENT   = 0x00010000;

}
