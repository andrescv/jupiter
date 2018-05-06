from termcolor import colored


class Register(object):

    def __init__(self, num, val, ABIName, **kwargs):
        editable = kwargs.get('editable', True)
        alt_name = kwargs.get('alt_name', None)
        description = kwargs.get('description', '-')
        saver = kwargs.get('saver', '-')

        # type checking
        if not isinstance(num, int):
            raise TypeError('register number should be an int')
        if not isinstance(val, int):
            raise TypeError('register value should be an int')
        if not isinstance(ABIName, str):
            raise TypeError('register ABI Name should be a string')
        if not isinstance(editable, bool):
            raise TypeError('editable should be a bool')
        if not (isinstance(alt_name, str) or alt_name is None):
            raise TypeError('register alternative name should be a string')
        if not isinstance(description, str):
            raise TypeError('register description should be a string')
        if not isinstance(saver, str):
            raise TypeError('register saver should be a string')

        self._num = num
        self._val = val & 0xffffffff
        self._ABIName = ABIName
        self._reset_val = val
        self._editable = editable
        self._alt_name = alt_name
        self._description = description
        self._saver = saver

    def getNumber(self):
        return self._num

    def getValue(self):
        return self._val

    def getResetValue(self):
        return self._reset_val

    def getABIName(self):
        return self._ABIName

    def getAlternativeName(self):
        return self._alt_name

    def getDescription(self):
        return self._description

    def getSaver(self):
        return self._saver

    def isEditable(self):
        return self._editable

    def setValue(self, val):
        if self._editable:
            if isinstance(val, int):
                # 32 bits only
                self._val = val & 0xffffffff
            else:
                raise TypeError('val should be an integer')

    def setResetValue(self, reset_val):
        if self._editable:
            if isinstance(reset_val, int):
                # 32 bits only
                self._reset_val = reset_val & 0xffffffff
            else:
                raise TypeError('reset_val should be an integer')

    def resetValue(self):
        self._val = self._reset_val

    def __str__(self):
        fmt = '%s [%s]'
        num = self.getNumber()
        num_str = 'x' + (str(num) if num >= 10 else str(num) + ' ')
        val_str = '0x%08x' % self.getValue()
        return fmt % (colored(num_str, 'green'), colored(val_str, 'red'))
