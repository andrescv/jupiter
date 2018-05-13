from ..... import Globals
from ...base import BTypeSimCode
from ....hardware.utils import sign_extend


class Beq(BTypeSimCode):

    '''
    BEQ (beq rs1, rs2, imm)

    beq takes the branch if registers rs1 and rs2 are equal.
    '''

    def comparison(self, rs1, rs2):
        return sign_extend(rs1, 32) == sign_extend(rs2, 32)


class Bne(BTypeSimCode):

    '''
    BNE (bne rs1, rs2, imm)

    bne takes the branch if registers rs1 and rs2 are unequal.
    '''

    def comparison(self, rs1, rs2):
        return sign_extend(rs1, 32) != sign_extend(rs2, 32)


class Bge(BTypeSimCode):

    '''
    BGE (bge rs1, rs2, imm)

    bge takes the branch if register rs1 is greater or equal to register rs2.
    '''

    def comparison(self, rs1, rs2):
        return sign_extend(rs1, 32) >= sign_extend(rs2, 32)


class Bgeu(BTypeSimCode):

    '''
    BGEU (bgeu rs1, rs2, imm)

    bge takes the branch if register rs1 is greater or equal
    to register rs2 unsigned comparison.
    '''

    def comparison(self, rs1, rs2):
        return rs1 >= rs2


class Blt(BTypeSimCode):

    '''
    BLT (blt rs1, rs2, imm)

    blt takes the branch if register rs1 is less than register rs2.
    '''

    def setResult(self, rs1, rs2, imm):
        return sign_extend(rs1, 32) < sign_extend(rs2, 32)


class Bltu(BTypeSimCode):

    '''
    BLTU (bltu rs1, rs2, imm)

    bltu takes the branch if register rs1 is less than register rs2
    unsigned comparison.
    '''

    def setResult(self, rs1, rs2, imm):
        return rs1 < rs2
