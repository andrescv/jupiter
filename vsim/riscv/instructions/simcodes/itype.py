from .base import SimCode
from ...hardware.utils import sign_extend


class ITypeSimCode(SimCode):

    def getResult(self, rs1, imm):
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0b11111, 7)
        rs1 = SimCode.getField(code, 0b11111, 15)
        imm = SimCode.getField(code, 0b111111111111, 20)
        rs1_val = self.rf[rs1].getValue()
        result = self.getResult(rs1_val, imm)
        self.rf[rd] = result


class Addi(ITypeSimCode):

    def getResult(self, rs1, imm):
        result = sign_extend(rs1, 32) + sign_extend(imm, 12)
        if (result > (2 ** 31 - 1)) or (result < (-2 ** 31)):
            raise OverflowError('arithmetic overflow')
        return result


class Slti(ITypeSimCode):

    def getResult(self, rs1, imm):
        return 1 if sign_extend(rs1, 32) < sign_extend(imm, 12) else 0


class Sltiu(ITypeSimCode):

    def getResult(self, rs1, imm):
        return 1 if rs1 < imm else 0


class Xori(ITypeSimCode):

    def getResult(self, rs1, imm):
        return rs1 ^ imm


class Ori(ITypeSimCode):

    def getResult(self, rs1, imm):
        return rs1 | imm


class Andi(ITypeSimCode):

    def getResult(self, rs1, imm):
        return rs1 & imm


class Slli(ITypeSimCode):

    def getResult(self, rs1, imm):
        shamt = imm & 0b11111
        return rs1 << shamt


class Srli(ITypeSimCode):

    def getResult(self, rs1, imm):
        shamt = imm & 0b11111
        return rs1 >> shamt


class Srai(ITypeSimCode):

    def getResult(self, rs1, imm):
        shamt = imm & 0b11111
        return sign_extend(rs1, 32) >> shamt
