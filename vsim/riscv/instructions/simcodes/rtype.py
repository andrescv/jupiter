from .base import SimCode
from ...hardware.utils import sign_extend32


class RTypeSimCode(SimCode):

    def getResult(self, rs1, rs2):
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0b11111, 7)
        rs1 = SimCode.getField(code, 0b11111, 15)
        rs2 = SimCode.getField(code, 0b11111, 20)
        rs1_val = self.rf[rs1].getValue()
        rs2_val = self.rf[rs2].getValue()
        result = self.getResult(rs1_val, rs2_val)
        self.rf[rd] = result


class Add(RTypeSimCode):

    def getResult(self, rs1, rs2):
        result = sign_extend32(rs1) + sign_extend32(rs2)
        if (result > (2 ** 31 - 1)) or (result < (-2 ** 31)):
            raise OverflowError('arithmetic overflow')
        return result


class Sub(RTypeSimCode):

    def getResult(self, rs1, rs2):
        result = sign_extend32(rs1) - sign_extend32(rs2)
        if (result > (2 ** 31 - 1)) or (result < (-2 ** 31)):
            raise OverflowError('arithmetic overflow')
        return result


class Sll(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return rs1 << (rs2 & 0b11111)


class Slt(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return 1 if sign_extend32(rs1) < sign_extend32(rs2) else 0


class Sltu(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return 1 if rs1 < rs2 else 0


class Xor(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return rs1 ^ rs2


class Srl(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return rs1 >> (rs2 & 0b11111)


class Sra(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return sign_extend32(rs1) >> (rs2 & 0b11111)


class Or(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return rs1 | rs2


class And(RTypeSimCode):

    def getResult(self, rs1, rs2):
        return rs1 & rs2
