from termcolor import colored


class InstructionType:

    def __init__(self, itype, iformat):
        assert itype.upper() in ['R', 'I', 'S', 'B', 'U', 'J']
        self._itype = itype
        self._iformat = iformat

    def getType(self):
        return self._itype

    def getFormat(self):
        return self._iformat

    def __str__(self):
        itype = colored(self._itype, 'green')
        iformat = colored(self._iformat, 'red')
        return 'TYPE: %s FORMAT: %s' % (itype, iformat)


class InstructionTypes:

    RTYPE = InstructionType('R', 'ffffff7 ssss2 ssss1 ff3 ddddd ooooooo')
    ITYPE = InstructionType('I', 'iiiiiiiiiiii sssss1 ff3 ddddd ooooooo')
    STYPE = InstructionType('S', 'iiiiiii ssss2 ssss1 ff3 iiiii ooooooo')
    BTYPE = InstructionType('B', 'iiiiiii ssss2 ssss1 ff3 iiiii ooooooo')
    UTYPE = InstructionType('U', 'iiiiiiiiiiiiiiiiiiiiiii ddddd ooooooo')
    JTYPE = InstructionType('J', 'iiiiiiiiiiiiiiiiiiiiiii ddddd ooooooo')
