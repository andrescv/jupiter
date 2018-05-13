from os import linesep
from termcolor import colored

from .register import Register
from .rfileconfig import RegistersConfig as config


class RegisterFile(object):

    def __init__(self):
        self._regfile = []
        for number, register in enumerate(config.registers):
            self._regfile.append(Register(
                number,
                0,
                register.get('ABIName'),
                editable=register.get('editable', True),
                alt_name=register.get('alt_name', None),
                description=register.get('description', '-'),
                saver=register.get('saver', '-')
            ))
        self._pc = Register(32, 0, 'pc', description='program counter')

    def getRegisterNumber(self, name):
        if isinstance(name, str):
            name = name.lower()
            for reg in self._regfile:
                if name == reg.getABIName().lower():
                    return reg.getNumber()
                alt_name = reg.getAlternativeName()
                if alt_name is not None and name == alt_name.lower():
                    return reg.getNumber()
        else:
            raise TypeError('name should be a string')

    def getRegister(self, num):
        if isinstance(num, int):
            for reg in self._regfile:
                if reg.getNumber() == num:
                    return reg
            raise ValueError('num should be in a range of [0, 32]')
        else:
            raise TypeError('num should be an int')

    def getProgramCounter(self):
        return self._pc

    def setRegister(self, num, val):
        reg = self.getRegister(num)
        reg.setValue(val)

    def setProgramCounter(self, val):
        self._pc.setValue(val)

    def setStackPointer(self, val):
        self['sp'] = val

    def setGlobalPointer(self, val):
        self['gp'] = val

    def __getitem__(self, index):
        if isinstance(index, str):
            index = self.getRegisterNumber(index)
        return self.getRegister(index)

    def __setitem__(self, index, val):
        if isinstance(index, str):
            index = self.getRegisterNumber(index)
        self.setRegister(index, val)

    def __len__(self):
        return len(self._regfile)

    def __str__(self):
        s = ''
        for reg in self._regfile:
            s += str(reg) + linesep
        pc = 'PC  [' + colored('0x%08x' % self._pc.getValue(), 'yellow') + ']'
        s = s.strip() + linesep * 2 + pc
        return s
