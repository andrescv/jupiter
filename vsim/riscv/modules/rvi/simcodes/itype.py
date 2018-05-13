from ..... import Globals
from ...base import ITypeSimCode
from ....hardware.utils import sign_extend


class Addi(ITypeSimCode):

    '''
    ADDI (addi rd, rs1, imm)

    adds the sign-extended 12-bit immediate to register rs1. Arithmetic
    overflow is ignored and the result is simply the low XLEN bits of
    the result. ADDI rd, rs1, 0 is used to implement the MV rd, rs1 assembler
    pseudo-instruction.
    '''

    def getResult(self, rs1, imm):
        return sign_extend(rs1, 32) + sign_extend(imm, 12)


class Slti(ITypeSimCode):

    '''
    SLTI (slti rd, rs1, imm)

    places the value 1 in register rd if register rs1 is less than the sign-
    extended immediate when both are treated as signed numbers, else 0 is
    written to rd.
    '''

    def getResult(self, rs1, imm):
        return 1 if sign_extend(rs1, 32) < sign_extend(imm, 12) else 0


class Sltiu(ITypeSimCode):

    '''
    SLTIU (sltiu rd, rs1, imm)

    similar to SLTI but compares the values as unsigned numbers
    (i.e., the immediate is first sign-extended to XLEN bits then
    treated as an unsigned number).

    Note:

    SLTIU rd, rs1, 1 sets rd to 1 if rs1 equals zero, otherwise sets rd to 0
    (assembler pseudo-op SEQZ rd, rs).
    '''

    def getResult(self, rs1, imm):
        return 1 if rs1 < (sign_extend(imm, 12) & 0xffffffff) else 0


class Xori(ITypeSimCode):

    '''
    XORI (xori rd, rs1, imm)

    is the logical operation that performs bitwise XOR on register rs1
    and the sign-extended 12-bit immediate and places the result in rd.

    Note:

    XORI rd, rs1, -1 performs a bitwise logical inversion of register
    rs1 (assembler pseudo-instruction NOT rd, rs).
    '''

    def getResult(self, rs1, imm):
        return rs1 ^ (sign_extend(imm, 12) & 0xffffffff)


class Ori(ITypeSimCode):

    '''
    ORI (ori rd, rs1, imm)

    is the logical operation that performs bitwise OR on register rs1
    and the sign-extended 12-bit immediate and places the result in rd.
    '''

    def getResult(self, rs1, imm):
        return rs1 | (sign_extend(imm, 12) & 0xffffffff)


class Andi(ITypeSimCode):

    '''
    ANDI (andi rd, rs1, imm)

    is the logical operation that performs bitwise AND on register rs1
    and the sign-extended 12-bit immediate and places the result in rd.
    '''

    def getResult(self, rs1, imm):
        return rs1 & (sign_extend(imm, 12) & 0xffffffff)


class Slli(ITypeSimCode):

    '''
    SLLI (slli rd, rs1, imm)

    is a logical left shift (zeros are shifted into the lower bits)

    Note:

    The operand to be shifted is in rs1, and the shift amount is
    encoded in the lower 5 bits of the I-immediate field.
    '''

    def getResult(self, rs1, imm):
        return rs1 << (imm & 0b11111)


class Srli(ITypeSimCode):

    '''
    SRLI (srli rd, rs1, imm)

    is a logical right shift (zeros are shifted into the upper bits)

    Note:

    The operand to be shifted is in rs1, and the shift amount is
    encoded in the lower 5 bits of the I-immediate field.
    '''

    def getResult(self, rs1, imm):
        return rs1 >> (imm & 0b11111)


class Srai(ITypeSimCode):

    '''
    SRAI (srai rd, rs1, imm)

    is an arithmetic right shift (the original sign bit is copied into
    the vacated upper bits)

    Note:

    The operand to be shifted is in rs1, and the shift amount is
    encoded in the lower 5 bits of the I-immediate field.
    '''

    def getResult(self, rs1, imm):
        return sign_extend(rs1, 32) >> (imm & 0b11111)


class Lw(ITypeSimCode):

    '''
    LW (lw rd, imm(rs1))

    loads a 32-bit value from memory into rd.
    '''

    def getResult(self, rs1, imm):
        loadWord = Globals.memory.loadWord
        return loadWord(sign_extend(rs1, 32) + sign_extend(imm, 12))


class Lh(ITypeSimCode):

    '''
    LH (lh rd, imm(rs1))


    loads a 16-bit value from memory, then sign-extends to 32-bits before
    storing in rd.
    '''

    def getResult(self, rs1, imm):
        loadHalf = Globals.memory.loadHalf
        return loadHalf(sign_extend(rs1, 32) + sign_extend(imm, 12))


class Lhu(ITypeSimCode):

    '''
    LHU (lhu rd, imm(rs1))

    loads a 16-bit value from memory but then zero extends to 32-bits
    before storing in rd.
    '''

    def getResult(self, rs1, imm):
        loadHalfU = Globals.memory.loadHalfUnsigned
        return loadHalfU(sign_extend(rs1, 32) + sign_extend(imm, 12))


class Lb(ITypeSimCode):

    '''
    LB (lb rd, imm(rs1))


    loads a 8-bit value from memory, then sign-extends to 32-bits before
    storing in rd.
    '''

    def getResult(self, rs1, imm):
        loadByte = Globals.memory.loadByte
        return loadByte(sign_extend(rs1, 32) + sign_extend(imm, 12))


class Lbu(ITypeSimCode):

    '''
    LBU (lbu rd, imm(rs1))

    loads a 8-bit value from memory but then zero extends to 32-bits
    before storing in rd.
    '''

    def getResult(self, rs1, imm):
        loadByteU = Globals.memory.loadByteUnsigned
        return loadByteU(sign_extend(rs1, 32) + sign_extend(imm, 12))


class Jalr(ITypeSimCode):

    '''
    JALR (jalr rd, rs1, imm)

    the indirect jump instruction JALR (jump and link register) uses
    the I-type encoding. The target address is obtained by adding the 12-bit
    signed I-immediate to the register rs1, then setting the least-significant
    bit of the result to zero. The address of the instruction following
    the jump (pc+4) is written to register rd. Register x0 can be used as
    the destination if the result is not required.
    '''

    def getResult(self, rs1, imm):
        pc_4 = Globals.regfile.getProgramCounter().getValue() + 4
        offset = sign_extend(rs1, 32) + sign_extend(imm, 12)
        Globals.regfile.setProgramCounter((offset >> 1) << 1)
        return pc_4
