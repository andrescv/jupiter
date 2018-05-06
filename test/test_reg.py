import unittest

from riscv.hardware import Register


class TestRegister(unittest.TestCase):

    def setUp(self):
        self.num = 8
        self.val = 0xcafe
        self.ABIName = 's0'
        self.alt_name = 'fp'
        self.description = 'saved register / frame pointer'
        self.saver = 'callee'
        self.register = Register(
            self.num,
            self.val,
            self.ABIName,
            editable=True,
            alt_name=self.alt_name,
            description=self.description,
            saver=self.saver
        )

    def testGetNumber(self):
        self.assertEqual(self.register.getNumber(), self.num)

    def testGetValue(self):
        self.assertEqual(self.register.getValue(), self.val)

    def testGetResetValue(self):
        self.assertEqual(self.register.getResetValue(), self.val)

    def testGetABIName(self):
        self.assertEqual(self.register.getABIName(), self.ABIName)

    def testGetAlternativeName(self):
        self.assertEqual(self.register.getAlternativeName(), self.alt_name)

    def testGetDescription(self):
        self.assertEqual(self.register.getDescription(), self.description)

    def testGetSaver(self):
        self.assertEqual(self.register.getSaver(), self.saver)

    def testIsEditable(self):
        self.assertEqual(self.register.isEditable(), True)

    def testSetValue(self):
        self.register.setValue(0xdead)
        print(self.register.getValue())
        self.assertEqual(self.register.getValue(), 0xdead)
