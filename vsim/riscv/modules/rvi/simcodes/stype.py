from ..... import Globals
from ...base import STypeSimCode


class Sw(STypeSimCode):

    '''
    SW (sw rs2, imm(rs1))

    stores a 32-bit value from register rs2 to memory

    Note:

    for best performance, the effective address should be aligned on a
    four-byte boundary.
    '''

    def setResult(self, rs1, rs2, imm):
        Globals.memory.storeWord(rs1 + imm, rs2)


class Sh(STypeSimCode):

    '''
    SH (sh rs2, imm(rs1))

    stores a 16-bit value from the low bits of register rs2 to memory

    Note:

    for best performance, the effective address should be aligned on a
    two-byte boundary.
    '''

    def setResult(self, rs1, rs2, imm):
        Globals.memory.storeHalf(rs1 + imm, rs2)


class Sb(STypeSimCode):

    '''
    SB (sb rs2, imm(rs1))

    stores a 8-bit value from the low bits of register rs2 to memory
    '''

    def setResult(self, rs1, rs2, imm):
        Globals.memory.storeByte(rs1 + imm, rs2)
