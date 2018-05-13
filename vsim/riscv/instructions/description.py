from termcolor import colored


class Description:

    def __init__(self, itype, mnemonic, example):
        self._itype = itype
        self._mnemonic = mnemonic
        self._example = example

    def getType(self):
        return self._itype

    def getName(self):
        return self._mnemonic

    def getExample(self):
        return self._example

    def __str__(self):
        itype = colored(self._itype.getType(), 'red')
        name = colored(self._mnemonic, 'green')
        return '(%s) [%s] %s' % (itype, name, self._example)
