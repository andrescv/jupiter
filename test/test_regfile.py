import unittest

from riscv.hardware import Register
from riscv.hardware import RegisterFile


class TestRegisterFile(unittest.TestCase):

    def setUp(self):
        self.rf = RegisterFile()

    def testLen(self):
        self.assertEqual(len(self.rf), 32)

    def testGetRegisterNumber(self):
        self.assertEqual(self.rf.getRegisterNumber('zero'), 0)

    def testGetRegisterNumberPC(self):
        self.assertEqual(self.rf.getRegisterNumber('pc'), 32)

    def testGetRegisterNumberUnknownName(self):
        self.assertEqual(self.rf.getRegisterNumber('cafe'), None)

    def testInvalidGetRegisterNumber(self):
        with self.assertRaises(TypeError) as ctxt:
            self.rf.getRegisterNumber(12)
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'name should be a string')

    def testGetRegister(self):
        zero = self.rf.getRegister(0)
        self.assertIsInstance(zero, Register)

    def testGetRegisterPC(self):
        pc = self.rf.getRegister(32)
        self.assertIsInstance(pc, Register)

    def testInvalidGetRegisterUnknownNum(self):
        with self.assertRaises(ValueError) as ctxt1:
            self.rf.getRegister(33)
        with self.assertRaises(ValueError) as ctxt2:
            self.rf.getRegister(-2)
        msg1 = str(ctxt1.exception)
        self.assertEqual(msg1, 'num should be in a range of [0, 32]')
        msg2 = str(ctxt2.exception)
        self.assertEqual(msg2, 'num should be in a range of [0, 32]')

    def testInvalidGetRegisterTypeError(self):
        with self.assertRaises(TypeError) as ctxt:
            self.rf.getRegister('0')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'num should be an int')

    def testSetRegister(self):
        num = self.rf.getRegisterNumber('t0')
        self.rf.setRegister(num, 0xcafe)
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xcafe)

    def testSetProgramCounter(self):
        num = self.rf.getRegisterNumber('pc')
        self.rf.setProgramCounter(0xdead)
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xdead)

    def testSetStackPointer(self):
        num = self.rf.getRegisterNumber('sp')
        self.rf.setStackPointer(0xbaca)
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xbaca)

    def testSetGlobalPointer(self):
        num = self.rf.getRegisterNumber('gp')
        self.rf.setGlobalPointer(0xfefe)
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xfefe)

    def testGetItem(self):
        a = self.rf['zero']
        b = self.rf[0]
        self.assertEqual(a, b)

    def testSetItem(self):
        num = self.rf.getRegisterNumber('t0')
        self.rf['t0'] = 0xfeca
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xfeca)
        self.rf[num] = 0xdaff
        self.assertEqual(self.rf.getRegister(num).getValue(), 0xdaff)
