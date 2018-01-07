class Register(object):

    def __init__(self, number, value=0, editable=True):
        # errors
        if not isinstance(number, int) and number >= 0:
            raise TypeError('argument "number" should be a integer >= 0')
        if not isinstance(value, int):
            raise TypeError('argument "value" should be a integer')
        self._number = int(number)
        self._value = int(value)
        self._reset_value = self._value
        self._editable = editable

    def getNumber(self):
        return self._number

    def getName(self):
        return 'X%i' % self._number

    def getValue(self):
        return self._value

    def getResetValue(self):
        return self._reset_value

    def setValue(self, value):
        if self._editable:
            if not isinstance(value, int):
                raise TypeError('argument "value" should be a integer')
            self._value = value & 0xFFFFFFFF

    def setResetValue(self, reset_value):
        if self._editable:
            if not isinstance(reset_value, int):
                raise TypeError('argument "reset_value" should be a integer')
            self._reset_value = reset_value & 0xFFFFFFFF

    def reset(self):
        self._value = self._reset_value

    def __str__(self):
        return '0x%08X' % self._value
