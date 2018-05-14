from ... import Globals
from ..hardware.utils import sign_extend


class SimCode:

    '''
    SimCode

    abstract class for instruction simulation, the subclasses need
    to implement only the __call__ magic method.
    '''

    @staticmethod
    def getField(code, mask, offset=0):
        '''
        getField

        >>> hex(SimCode.getField(0xbacacafe, 0xffff, 16))
        >>> '0xbaca'

        Parameters
        ----------
        code : int
            machine code
        mask : int
            integer mask
        offset : int
            shift offset (default=0)

        Returns
        -------
        int
            masked field
        '''
        return (code & (mask << offset)) >> offset

    def __call__(self, code):
        '''
        method that performs the simulation of instructions given a
        machine code.
        '''
        if not isinstance(code, int):
            raise TypeError('code should be an instance of int')


class RTypeSimCode(SimCode):

    '''
    RTypeSimCode

    abstract class for R-Type instruction simulation, the subclasses need
    to implement only the getResult method.
    '''

    def getResult(self, rs1, rs2):
        '''method used to simulate a R-Type instruction'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0x1f, 7)
        rs1 = SimCode.getField(code, 0x1f, 15)
        rs2 = SimCode.getField(code, 0x1f, 20)
        rs1_val = Globals.regfile[rs1].getValue()
        rs2_val = Globals.regfile[rs2].getValue()
        result = self.getResult(rs1_val, rs2_val)
        Globals.regfile[rd] = result


class ITypeSimCode(SimCode):

    '''
    ITypeSimCode

    abstract class for I-Type instruction simulation, the subclasses need
    to implement only the getResult method.
    '''

    def getResult(self, rs1, imm):
        '''method used to simulate an I-Type instruction'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0x1f, offset=7)
        rs1 = SimCode.getField(code, 0x1f, offset=15)
        imm = SimCode.getField(code, 0xfff, offset=20)
        rs1_val = Globals.regfile[rs1].getValue()
        result = self.getResult(rs1_val, imm)
        Globals.regfile[rd] = result


class STypeSimCode(SimCode):

    '''
    STypeSimCode

    abstract class for S-Type instruction simulation, the subclasses need
    to implement only the setResult method.
    '''

    def setResult(self, rs1, rs2, imm):
        '''method used to simulate a S-Type instruction'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rs1 = SimCode.getField(code, 0x1f, 15)
        rs2 = SimCode.getField(code, 0x1f, 20)
        imm5 = SimCode.getField(code, 0x1f, 7)
        imm7 = SimCode.getField(code, 0x7f, 25)
        imm = sign_extend(imm7 << 5 | imm5, 12)
        rs1_val = Globals.regfile[rs1].getValue()
        rs2_val = Globals.regfile[rs2].getValue()
        self.setResult(rs1_val, rs2_val, imm)


class BTypeSimCode(SimCode):

    '''
    BTypeSimCode

    abstract class for B-Type instruction simulation, the subclasses need
    to implement only the comparison method.
    '''

    def comparison(self, rs1, rs2):
        '''method used to compare rs1 and rs2'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rs1 = SimCode.getField(code, 0x1f, 15)
        rs2 = SimCode.getField(code, 0x1f, 20)
        imm1a = SimCode.getField(code, 0x1, 7)
        imm4 = SimCode.getField(code, 0xf, 8)
        imm6 = SimCode.getField(code, 0x3f, 25)
        imm1b = SimCode.getField(code, 0x1, 31)
        imm = (imm1b << 11 | imm1a << 10 | imm6 << 4 | imm4) << 1
        imm = sign_extend(imm, 13)
        rs1_val = Globals.regfile[rs1].getValue()
        rs2_val = Globals.regfile[rs2].getValue()
        if self.comparison(rs1_val, rs2_val):
            pc = Globals.regfile.getProgramCounter().getValue()
            Globals.regfile.setProgramCounter(pc + imm)


class UTypeSimCode(SimCode):

    '''
    UTypeSimCode

    abstract class for U-Type instruction simulation, the subclasses need
    to implement only the getResult method.
    '''

    def getResult(self, imm):
        '''method used to simulate a U-Type instruction'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0x1f, 7)
        imm = sign_extend(SimCode.getField(code, 0xfffff, 12), 20)
        Globals.regfile[rd] = self.getResult(imm)


class JTypeSimCode(SimCode):

    '''
    JTypeSimCode

    abstract class for J-Type instruction simulation, the subclasses need
    to implement only the setResult method.
    '''

    def getResult(self, imm):
        '''method used to simulate a J-Type instruction'''
        pass

    def __call__(self, code):
        super().__call__(code)
        rd = SimCode.getField(code, 0x1f, 7)
        imm = SimCode.getField(code, 0x1ffffff, 12)
        imm10 = SimCode.getField(imm, 0x3ff, 9)
        imm1b = SimCode.getField(imm, 0x1, 19)
        imm1a = SimCode.getField(imm, 0x1, 8)
        imm8 = SimCode.getField(imm, 0xff, 0)
        imm = (imm1b << 19 | imm8 << 11 | imm1a << 10 | imm10) << 1
        imm = sign_extend(imm, 21)
        Globals.regfile[rd] = self.getResult(imm)
