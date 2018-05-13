from ...base import RTypeSimCode
from ....hardware.utils import sign_extend32


class Add(RTypeSimCode):

    '''
    ADD (add rd, rs1, rs2)

    performs addition. Arithmetic overflow is ignored and the result
    is simply the low XLEN bits of the result.
    '''

    def getResult(self, rs1, rs2):
        return sign_extend32(rs1) + sign_extend32(rs2)


class Sub(RTypeSimCode):

    '''
    SUB (sub rd, rs1, rs2)

    performs substraction. Arithmetic overflow is ignored and the result
    is simply the low XLEN bits of the result.
    '''

    def getResult(self, rs1, rs2):
        return sign_extend32(rs1) - sign_extend32(rs2)


class Sll(RTypeSimCode):

    '''
    SLL (sll rd, rs1, rs2)

    performs logical left shift on the value in register rs1 by the shift
    amount held in the lower 5 bits of register rs2.
    '''

    def getResult(self, rs1, rs2):
        return rs1 << (rs2 & 0b11111)


class Slt(RTypeSimCode):

    '''
    SLT (slt rd, rs1, rs2)

    performs signed compare, writing 1 to rd if rs1 < rs2, 0 otherwise.
    '''

    def getResult(self, rs1, rs2):
        return 1 if sign_extend32(rs1) < sign_extend32(rs2) else 0


class Sltu(RTypeSimCode):

    '''
    SLTU (sltu rd, rs1, rs2)

    performs unsigned compare, writing 1 to rd if rs1 < rs2, 0 otherwise.
    '''

    def getResult(self, rs1, rs2):
        return 1 if rs1 < rs2 else 0


class Xor(RTypeSimCode):

    '''
    XOR (xor rd, rs1, rs2)

    performs bitwise logical XOR.
    '''

    def getResult(self, rs1, rs2):
        return rs1 ^ rs2


class Srl(RTypeSimCode):

    '''
    SRL (srl rd, rs1, rs2)

    performs logical right shift on the value in register rs1 by the shift
    amount held in the lower 5 bits of register rs2.
    '''

    def getResult(self, rs1, rs2):
        return rs1 >> (rs2 & 0b11111)


class Sra(RTypeSimCode):

    '''
    SRA (sra rd, rs1, rs2)

    performs arithmetic right shift on the value in register rs1 by the shift
    amount held in the lower 5 bits of register rs2.
    '''

    def getResult(self, rs1, rs2):
        return sign_extend32(rs1) >> (rs2 & 0b11111)


class Or(RTypeSimCode):

    '''
    OR (or rd, rs1, rs2)

    performs bitwise logical OR.
    '''

    def getResult(self, rs1, rs2):
        return rs1 | rs2


class And(RTypeSimCode):

    '''
    AND (and rd, rs1, rs2)

    performs bitwise logical AND.
    '''

    def getResult(self, rs1, rs2):
        return rs1 & rs2
