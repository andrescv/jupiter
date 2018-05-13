from termcolor import colored

from .itypes import InstructionType


class Description:

    def __init__(self, itype, mnemonic, example, description):
        self._itype = itype
        self._mnemonic = mnemonic
        self._example = example
        self._description = description

        # type checking
        if not isinstance(itype, InstructionType):
            raise TypeError('itype should be an instance of Instruction Type')
        if not isinstance(mnemonic, str):
            raise TypeError('mnemonic should be a string')
        if not isinstance(mnemonic, str):
            raise TypeError('example should be a string')
        if not isinstance(description, str):
            raise TypeError('description should be a string')

    def getType(self):
        return self._itype

    def getName(self):
        return self._mnemonic

    def getExample(self):
        return self._example

    def getDescription(self):
        return self._description

    def __str__(self):
        itype = colored(self._itype.getType(), 'red')
        name = colored(self._mnemonic, 'green')
        return '(%s) [%s] example: %s' % (itype, name, self._example)
