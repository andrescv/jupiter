import random
import unittest
from vsim.riscv.hardware import Register


class TestRegister(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.number = random.randint(0, 31)
        cls.value = random.randint(0, 2 ** 32 - 1)
        cls.register = Register(cls.number, value=cls.value, editable=True)

    def test_instance(self):
        self.assertEqual(self.register.getNumber(), self.number)
        self.assertEqual(self.register.getValue(), self.value)
        self.assertEqual(self.register.getResetValue(), self.value)

    def test_exceptions(self):
        self.assertRaises(TypeError, Register, 0.0)
        self.assertRaises(TypeError, Register, 0, value=0.0)

    def test_methods(self):
        # register methods
        self.assertEqual(self.register.getName(), 'X%i' % self.number)
        # value
        n = random.randint(0, 2 ** 32 - 1)
        self.register.setValue(n)
        self.assertEqual(self.register.getValue(), n)
        # reset value
        n = random.randint(0, 2 ** 32 - 1)
        self.register.setResetValue(n)
        self.assertEqual(self.register.getResetValue(), n)
        # overflow
        self.register.setValue(2 ** 32)
        self.assertEqual(self.register.getValue(), 0)
        self.register.setResetValue(2 ** 32)
        self.assertEqual(self.register.getResetValue(), 0)

    def test_signed(self):
        for n in range(-10, 0):
            self.register.setValue(n)
            # sign-extend
            v = (self.register.getValue() ^ 0x80000000) - 0x80000000
            self.assertEqual(n, v)

    def test_non_editable(self):
        register = Register(0, value=0, editable=False)
        n = random.randint(1, 2 ** 32 - 1)
        register.setValue(n)
        register.setResetValue(n)
        self.assertEqual(register.getValue(), 0)
        self.assertEqual(register.getResetValue(), 0)
