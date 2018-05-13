from .riscv.hardware import Memory
from .riscv.hardware import RegisterFile


class Globals:

    # riscv main memory
    memory = Memory()

    # register file
    regfile = RegisterFile()
