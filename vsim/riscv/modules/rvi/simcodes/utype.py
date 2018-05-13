from ..... import Globals
from ...base import UTypeSimCode


class Lui(UTypeSimCode):

    '''
    LUI (lui rd, imm)

    is used to build 32-bit constants and uses the U-type format. LUI
    places the U-immediate value in the top 20 bits of the destination
    register rd, filling in the lowest 12 bits with zeros.
    '''

    def getResult(self, imm):
        return (imm << 20) & 0xfffff000


class Auipc(UTypeSimCode):

    '''
    AUIPC (auipc rd, imm)

    is used to build pc-relative addresses and uses the U-type format.
    AUIPC forms a 32-bit offset from the 20-bit U-immediate, filling in the
    lowest 12 bits with zeros, adds this offset to the pc, then places
    the result in register rd.
    '''

    def getResult(self, imm):
        return ((imm << 20) & 0xfffff000) + Globals.regfile['pc'].getValue()
