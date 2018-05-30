package vsim.riscv.hardware;


public final class MemoryConfig {

    /*
        Memory Layout Configuration

                                               base         limit
                    +-------------------+
                    | memory mapped I/O | <- 0xffff0000 - 0xffffffff
                    +-------------------+
    SP 0x7ffffffc-> |   stack segment   | <- 0xfffefffc - 0x10010004
                    |                   |
                    *********************
                    |                   | <- 0x10010004 - 0xfffefffc
    GP 0x10008000-> |   dynamic data    |
                    +-------------------+
                    |    static data    | <- 0x10000000 - 0x10010000
                    +-------------------+
                    |    text segment   | <- 0x00000000 - 0x0ffffffc
    PC 0x00000000-> +-------------------+

                    <----WORD_LENGTH---->

    */

    // memory map IO segment
    public static final int MMAP_BASE_ADDRESS = 0xffff0000;
    public static final int MMAP_LIMIT_ADDRESS = 0xffffffff;

    // stack segment
    public static final int STACK_BASE_ADDRESS = 0xfffefffc;
    public static final int STACK_LIMIT_ADDRESS = 0x10010004;

    // dynamic data segment
    public static final int DATA_BASE_ADDRESS = 0x10010004;
    public static final int DATA_LIMIT_ADDRESS = 0xfffefffc;

    // static segment
    public static final int STATIC_BASE_ADDRESS = 0x10000000;
    public static final int STATIC_LIMIT_ADDRESS = 0x10010000;

    // text segment
    public static final int TEXT_BASE_ADDRESS = 0x00000000;
    public static final int TEXT_LIMIT_ADDRESS = 0x0ffffffc;

    // global pointer (data base + 32KiB)
    public static final int GLOBAL_POINTER = 0x10018004;
    // stack pointer
    public static final int STACK_POINTER = 0xfffefffc;
    // base address for heap
    public static final int HEAP_ADDRESS = 0x10010004;

}