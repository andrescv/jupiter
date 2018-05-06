from unittest import TestCase

from riscv.hardware import Register


class TestReg(TestCase):

    def testEditableRegister(self):
        # create register
        ABIName = 's0'
        alt_name = 'fp'
        val = 0
        number = 8
        reg = Register(ABIName, number, val, alt_name=alt_name)
        # test getters
        self.assertEqual(reg.getABIName(), ABIName)
        self.assertEqual(reg.getNumber(), number)
        self.assertEqual(reg.getValue(), val)
        self.assertEqual(reg.getResetValue(), val)
        self.assertEqual(reg.getAlternativeName(), alt_name)
        self.assertEqual(reg.isEditable(), True)
        # test setters
        reg.setValue(0xcafe)
        self.assertEqual(reg.getValue(), 0xcafe)
        reg.setResetValue(0xdead)
        reg.resetValue()
        self.assertEqual(reg.getValue(), 0xdead)

    def testNonEditableRegister(self):
        ABIName = 'zero'
        val = 0
        number = 0
        reg = Register(ABIName, number, val, editable=False)
        # test setters
        reg.setValue(0xcafe)
        self.assertEqual(reg.getValue(), val)
        reg.setResetValue(0xdead)
        reg.resetValue()
        self.assertEqual(reg.getValue(), val)
