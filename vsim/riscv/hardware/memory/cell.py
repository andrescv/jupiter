from termcolor import colored

from .. import utils


class MemoryCell:

    BYTE_LENGTH_BITS = 8
    HALF_LENGTH_BITS = 16

    def __init__(self):
        self._data = 0x0

    def loadByte(self, offset=0):
        mask = 0xff << (offset * self.BYTE_LENGTH_BITS)
        byte = (self._data & mask) >> (offset * self.BYTE_LENGTH_BITS)
        return utils.sign_extend8(byte)

    def loadByteUnsigned(self, offset=0):
        mask = 0xff << (offset * self.BYTE_LENGTH_BITS)
        return (self._data & mask) >> (offset * self.BYTE_LENGTH_BITS)

    def loadHalf(self, offset=0):
        mask = 0xffff << (offset * self.HALF_LENGTH_BITS)
        half = (self._data & mask) >> (offset * self.HALF_LENGTH_BITS)
        return utils.sign_extend16(half)

    def loadHalfUnsigned(self, offset=0):
        mask = 0xffff << (offset * self.HALF_LENGTH_BITS)
        return (self._data & mask) >> (offset * self.HALF_LENGTH_BITS)

    def loadWord(self):
        return utils.sign_extend32(self._data)

    def storeByte(self, byte, offset=0):
        mask = 0xff << (offset * self.BYTE_LENGTH_BITS)
        byte = (byte & 0xff) << (offset * self.BYTE_LENGTH_BITS)
        self._data = (self._data & ~mask) | byte

    def storeHalf(self, half, offset=0):
        mask = 0xffff << (offset * self.HALF_LENGTH_BITS)
        half = (half & 0xffff) << (offset * self.HALF_LENGTH_BITS)
        self._data = (self._data & ~mask) | half

    def storeWord(self, word):
        self._data = word & 0xffffffff

    def __str__(self):
        data = '0x%08x' % self._data
        if self._data != 0:
            return colored(data, 'blue')
        else:
            return colored(data, 'yellow')
