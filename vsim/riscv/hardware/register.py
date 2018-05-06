from termcolor import colored


class Register(object):

    def __init__(self, ABIName, num, val, editable=True, alt_name=None):
        assert isinstance(ABIName, str) and len(ABIName) != 0
        assert isinstance(num, int) and num >= 0
        assert isinstance(num, int)
        assert isinstance(editable, bool)
        assert isinstance(alt_name, str) or alt_name is None
        self._ABIName = ABIName
        self._num = num
        self._val = val
        self._reset_val = val
        self._editable = editable
        self._alt_name = alt_name

    def getABIName(self):
        return self._ABIName

    def getNumber(self):
        return self._num

    def getValue(self):
        return self._val

    def getResetValue(self):
        return self._reset_val

    def getAlternativeName(self):
        return self._alt_name

    def isEditable(self):
        return self._editable

    def setValue(self, val):
        if self._editable:
            assert isinstance(val, int)
            self._val = val & 0xffffffff

    def setResetValue(self, reset_val):
        if self._editable:
            assert isinstance(reset_val, int)
            self._reset_val = reset_val & 0xffffffff

    def resetValue(self):
        self._val = self._reset_val

    def __str__(self):
        fmt = '%s [%s]'
        num = self.getNumber()
        num_str = 'x' + (str(num) if num >= 10 else str(num) + ' ')
        val_str = '0x%08x' % self.getValue()
        return fmt % (colored(num_str, 'green'), colored(val_str, 'red'))
