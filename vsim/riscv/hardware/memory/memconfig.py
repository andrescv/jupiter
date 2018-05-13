class MemoryConfig:

    '''
        Memory Layout Configuration


                                               base         limit
                    +-------------------+
                    |    reserverd      | <- 0xffff0010 - 0xffffffff
                    +-------------------+
                    | memory mapped I/O | <- 0xffff0000 - 0xffff000c
                    +-------------------+
                    |    kernel data    | <- 0x90000000 - 0xfffefffc
                    +-------------------+
                    |    kernel text    | <- 0x80000000 - 0x8ffffffc
                    +-------------------+
    SP 0x7ffffffc-> |   stack segment   | <- 0x7ffffffc - 0x10010004
                    |                   |
                    ********************
                    |                   | <- 0x10010004 - 0x7ffffffc
                    |   dynamic data    |
                    +-------------------+
    GP 0x10008000-> |    static data    | <- 0x10000000 - 0x10010000
                    +-------------------+
                    |    text segment   | <- 0x04000000 - 0xffffffc
    PC 0x04000000-> +-------------------+
                    |     reserved      | <- 0x00000000 - 0x3fffffc
                    +-------------------+

                    <----WORD_LENGTH---->
    '''

    # word length
    WORD_LENGTH = 4
    # half length
    HALF_LENGTH = 2

    # high reserved memory
    RESERVED_HIGH_LIMIT = 0xffffffff
    RESERVED_HIGH_BASE = 0xffff0010

    # limit address memory mapped I/O
    MMAP_LIMIT_ADDRESS = 0xffff000c
    # base address memory mapped I/O
    MMAP_BASE_ADDRESS = 0xffff0000

    # limit address for kernel data segment
    KDATA_LIMIT_ADDRESS = 0xfffefffc
    # base address for kernel data segment
    KDATA_BASE_ADDRESS = 0x90000000

    # limit address for kernel text segment
    KTEXT_LIMIT_ADDRESS = 0x8ffffffc
    # base address for kernel text segment
    KTEXT_BASE_ADDRESS = 0x80000000

    # limit address for user data segment
    DATA_LIMIT_ADDRESS = 0x7ffffffc
    # base address for user data segment
    DATA_BASE_ADDRESS = 0x10010004

    # limit address for stack
    STACK_LIMIT_ADDRESS = DATA_BASE_ADDRESS
    # base address for stack
    STACK_BASE_ADDRESS = DATA_LIMIT_ADDRESS

    # limit address for static data
    STATIC_LIMIT_ADDRESS = 0x10010000
    # base address for static data
    STATIC_BASE_ADDRESS = 0x10000000

    # limit address for user text segment
    TEXT_LIMIT_ADDRESS = 0xffffffc
    # base address for user text segment
    TEXT_BASE_ADDRESS = 0x04000000

    # reserved memory
    RESERVED_LOW_LIMIT = 0x3fffffc
    RESERVED_LOW_BASE = 0x00000000

    # base address for storing globals (data base + 32KiB)
    GLOBAL_POINTER = DATA_BASE_ADDRESS + 0x8000
    # starting address for stack
    STACK_POINTER = STACK_BASE_ADDRESS
    # base address for heap (static + 64KiB + 4)
    HEAP_BASE_ADDRESS = DATA_BASE_ADDRESS
