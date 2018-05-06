from os import linesep
from termcolor import colored
from .register import Register
from ..config import RISCVConfig as Config


class RegisterFile(object):

    def __init__(self):
        self._regfile = [
            Register('zero', 0, 0, editable=False),
            Register('ra', 1, 0),
            Register('sp', 2, 0),
            Register('gp', 3, 0),
            Register('tp', 4, 0), Register('t0', 5, 0),
            Register('t1', 6, 0), Register('t2', 7, 0),
            Register('s0', 8, 0, alt_name='fp'),
            Register('s1', 9, 0), Register('a0', 10, 0),
            Register('a1', 11, 0), Register('a2', 12, 0),
            Register('a3', 13, 0), Register('a4', 14, 0),
            Register('a5', 15, 0), Register('a6', 16, 0),
            Register('a7', 17, 0), Register('s2', 18, 0),
            Register('s3', 19, 0), Register('s4', 20, 0),
            Register('s5', 21, 0), Register('s6', 22, 0),
            Register('s7', 23, 0), Register('s8', 24, 0),
            Register('s9', 25, 0), Register('s10', 26, 0),
            Register('s11', 27, 0), Register('t3', 28, 0),
            Register('t4', 29, 0), Register('t5', 30, 0),
            Register('t6', 31, 0),
        ]
        self._pc = Register('pc', 32, 0)

    def getRegisterNumber(self, name):
        name = name.lower()
        for reg in self._regfile:
            if name == reg.getABIName().lower():
                return reg.getNumber()
            alt_name = reg.getAlternativeName()
            if alt_name is not None and name == alt_name.lower():
                return reg.getNumber()
        if name == 'pc':
            return self._pc.getNumber()

    def getRegisterValue(self, num):
        for reg in self._regfile:
            if reg.getNumber() == num:
                return reg.getValue()
        if self._pc.getNumber() == num:
            return self._pc.getValue()

    def updateRegister(self, num, val):
        for reg in self._regfile:
            if reg.getNumber() == num:
                reg.setValue(val)
                return
        if self._pc.getNumber() == num:
            self._pc.setValue(val)

    def incProgramCounter(self):
        val = self._pc.getValue()
        self._pc.setValue(val + Config.INSTRUCTION_LENGTH)

    def setProgramCounter(self, val):
        self._pc.setValue(val)

    def __str__(self):
        s = 'RegFile' + linesep * 2
        for reg in self._regfile:
            s += str(reg) + linesep
        pc = 'PC  [' + colored('0x%08x' % self._pc.getValue(), 'yellow') + ']'
        s = s.strip() + linesep * 2 + pc
        return s
