from .base import SimCode
from ...hardware.utils import sign_extend


class STypeSimCode(SimCode):

    def setResult(self, rs1, rs2, imm):
        pass

    def __call__(self, code):
        super().__call__(code)
        rs1 = SimCode.getField(code, 0b11111, 15)
        rs2 = SimCode.getField(code, 0b11111, 20)
        imm5 = SimCode.getField(code, 0b11111, 7)
        imm7 = SimCode.getField(code, 0b1111111, 25)
        imm = sign_extend(imm7 << 5 | imm5, 12)
        rs1_val = self.rf[rs1].getValue()
        rs2_val = self.rf[rs2].getValue()
        self.setResult(rs1_val, rs2_val, imm)


class Sw(STypeSimCode):

    def setResult(self, rs1, rs2, imm):
        self.memory.storeWord(rs1 + imm, rs2)


class Sh(STypeSimCode):

    def setResult(self, rs1, rs2, imm):
        self.memory.storeHalf(rs1 + imm, rs2)


class Sb(STypeSimCode):

    def setResult(self, rs1, rs2, imm):
        self.memory.storeByte(rs1 + imm, rs2)
