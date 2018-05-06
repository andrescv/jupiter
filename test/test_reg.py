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
        self.assertEqual(self.register.getValue(), 0xdead)

    def testInvalidSetValue(self):
        with self.assertRaises(TypeError) as ctxt:
            self.register.setValue('0xcafe')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'val should be an integer')

    def testSetResetValue(self):
        self.register.setResetValue(0xffff)
        self.assertEqual(self.register.getResetValue(), 0xffff)
        self.register.resetValue()
        self.assertEqual(self.register.getValue(), 0xffff)

    def testInvalidSetResetValue(self):
        with self.assertRaises(TypeError) as ctxt:
            self.register.setResetValue('0xdead')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'reset_val should be an integer')

    def testNonEditableRegister(self):
        reg = Register(0, 0, 'zero', editable=False)
        reg.setValue(0xffff)
        self.assertEqual(reg.getValue(), 0)
        reg.setResetValue(0xcafe)
        self.assertEqual(reg.getResetValue(), 0)

    def testInvalidCreationNum(self):
        with self.assertRaises(TypeError) as ctxt:
            Register('0', 0, 'zero')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register number should be an int')

    def testInvalidCreationVal(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, '0', 'zero')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register value should be an int')

    def testInvalidCreationABIName(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, 0, 0)
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register ABI Name should be a string')

    def testInvalidCreationEditable(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, 0, 'zero', editable='false')
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'editable should be a bool')

    def testInvalidCreationAlternative(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, 0, 'zero', alt_name=0)
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register alternative name should be a string')

    def testInvalidCreationDescription(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, 0, 'zero', description=0)
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register description should be a string')

    def testInvalidCreationSaver(self):
        with self.assertRaises(TypeError) as ctxt:
            Register(0, 0, 'zero', saver=0)
        msg = str(ctxt.exception)
        self.assertEqual(msg, 'register saver should be a string')
