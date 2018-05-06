from os import path
from yaml import load
from os import linesep
from termcolor import colored

from .register import Register


class RegisterFile(object):

    def __init__(self):
        regpath = path.join(path.dirname(__file__), 'config', 'registers.yml')
        with open(regpath, 'r') as f:
            registers = load(f)
            self._regfile = []
            for number, register in enumerate(registers):
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
            if name == 'pc':
                return self._pc.getNumber()
        else:
            raise TypeError('name should be a string')

    def getRegister(self, num):
        if isinstance(num, int):
            for reg in self._regfile:
                if reg.getNumber() == num:
                    return reg
            if self._pc.getNumber() == num:
                return self._pc
        else:
            raise TypeError('num should be an int')

    def setRegister(self, num, val):
        if isinstance(num, int):
            for reg in self._regfile:
                if reg.getNumber() == num:
                    reg.setValue(val)
                    return
            if self._pc.getNumber() == num:
                self._pc.setValue(val)
        else:
            raise TypeError('num should be an int')

    def setProgramCounter(self, val):
        self['pc'] = val

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
