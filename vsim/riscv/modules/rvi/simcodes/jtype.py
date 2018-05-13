from ..... import Globals
from ...base import JTypeSimCode


class Jal(JTypeSimCode):

    '''
    JAL (jal rd, imm)

    the jump and link (JAL) instruction uses the J-type format,
    where the J-immediate encodes a signed offset in multiples of 2 bytes.
    The offset is sign-extended and added to the pc to form the jump target
    address. Jumps can therefore target a ±1 MiB range. JAL stores the address
    of the instruction following the jump (pc+4) into register rd.
    The standard software calling convention uses x1 as the return address
    register and x5 as an alternate link register.
    '''

    def getResult(self, imm):
        pc = Globals.regfile.getProgramCounter().getValue()
        Globals.regfile.setProgramCounter(pc + imm)
        return pc + 4
