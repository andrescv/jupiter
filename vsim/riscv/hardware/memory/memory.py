from termcolor import colored

from .cell import MemoryCell
from ..utils import sign_extend
from .memconfig import MemoryConfig as config


class Memory:

    def __init__(self):
        self._mem = {}
        self._heap_address = config.HEAP_BASE_ADDRESS

    def allocateBytesFromHeap(self, numBytes):
        if isinstance(numBytes, int):
            if numBytes <= 0:
                raise ValueError('numBytes should be > 0')
            result = self._heap_address
            self._heap_address += numBytes
            if self._heap_address >= config.DATA_LIMIT_ADDRESS:
                raise ValueError('request exceeds available heap storage')
            return result
        else:
            raise TypeError('numBytes should be an int')

    def createMemoryCell(self, address):
        if Memory.isWordAligned(address):
            if address not in self._mem:
                self._mem[address] = MemoryCell()
        else:
            raise ValueError('address should be word aligned')

    def loadByteValue(self, address):
        Memory.checkAddress(address)
        word_address = address // config.WORD_LENGTH * config.WORD_LENGTH
        if word_address not in self._mem:
            return 0x0
        else:
            offset = address - word_address
            return self._mem[word_address].loadByteUnsigned(offset=offset)

    def loadByte(self, address):
        return sign_extend(self.loadByteValue(address), 8)

    def loadByteUnsigned(self, address):
        return self.loadByteValue(address)

    def loadHalfValue(self, address):
        Memory.checkAddress(address)
        if Memory.isWordAligned(address):
            if address not in self._mem:
                return 0x0
            return self._mem[address].loadHalfUnsigned()
        else:
            byte1 = self.loadByteUnsigned(address)
            byte2 = self.loadByteUnsigned(address + 1)
            return byte2 << 8 | byte1

    def loadHalf(self, address):
        return sign_extend(self.loadHalfValue(address), 16)

    def loadHalfUnsigned(self, address):
        return self.loadHalfValue(address)

    def loadWord(self, address):
        Memory.checkAddress(address)
        if Memory.isWordAligned(address):
            if address not in self._mem:
                return 0x0
            else:
                return self._mem[address].loadWord()
        else:
            # word = half2 << 16 | half1
            half1 = self.loadHalfUnsigned(address)
            half2 = self.loadHalfUnsigned(address + 2)
            return sign_extend(half2 << 16 | half1, 32)

    def storeByte(self, address, byte):
        Memory.checkAddress(address)
        word_address = address // config.WORD_LENGTH * config.WORD_LENGTH
        offset = address - word_address
        self.createMemoryCell(word_address)
        self._mem[word_address].storeByte(byte, offset=offset)

    def storeHalf(self, address, half):
        Memory.checkAddress(address)
        if Memory.isWordAligned(address):
            self.createMemoryCell(address)
            self._mem[address].storeHalf(half)
        else:
            self.storeByte(address, half)
            self.storeByte(address + 1, half >> 8)

    def storeWord(self, address, word):
        Memory.checkAddress(address)
        if Memory.isWordAligned(address):
            self.createMemoryCell(address)
            self._mem[address].storeWord(word)
        else:
            self.storeHalf(address, word)
            self.storeHalf(address + 2, word >> 16)

    def memory(self, fromAddress, rows=12):  # pragma: no cover
        Memory.checkAddress(fromAddress)
        word_length = config.WORD_LENGTH
        mfrom = fromAddress
        mto = fromAddress + word_length * rows * 4
        header = '             Value (+0) Value (+4) Value (+8) Value (+c)\n'
        out = colored(header, 'cyan')
        for i, address in enumerate(range(mfrom, mto, word_length)):
            if i % word_length == 0:
                out += '[0x%08x]' % address
            if address in self._mem:
                out += ' ' + str(self._mem[address])
            else:
                out += ' ' + colored('0x00000000', 'yellow')
            if (i + 1) % word_length == 0:
                out += '\n'
        return out.strip()

    @staticmethod
    def isWordAligned(address):
        return address % config.WORD_LENGTH == 0

    @staticmethod
    def isHalfAligned(address):
        return address % config.HALF_LENGTH == 0

    @staticmethod
    def alignToWordBoundary(address):
        if not Memory.isWordAligned(address):
            address += (config.WORD_LENGTH - (address % config.WORD_LENGTH))
        return address

    @staticmethod
    def alignToHalfBoundary(address):
        if not Memory.isHalfAligned(address):
            address += (config.HALF_LENGTH - (address % config.HALF_LENGTH))
        return address

    @staticmethod
    def inRange(address, base, limit):
        return address >= base and address <= limit

    @staticmethod
    def inUserText(address):
        limits = (config.TEXT_BASE_ADDRESS, config.TEXT_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def inUserData(address):
        limits = (config.DATA_BASE_ADDRESS, config.DATA_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def inStaticData(address):
        limits = (config.STATIC_BASE_ADDRESS, config.STATIC_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def inKernelText(address):
        limits = (config.KTEXT_BASE_ADDRESS, config.KTEXT_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def inKernelData(address):
        limits = (config.KDATA_BASE_ADDRESS, config.KDATA_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def inMemoryMap(address):
        limits = (config.MMAP_BASE_ADDRESS, config.MMAP_LIMIT_ADDRESS)
        return Memory.inRange(address, *limits)

    @staticmethod
    def checkAddress(address):
        if isinstance(address, int):
            if address >= 0:
                flags = [
                    Memory.inUserText(address),
                    Memory.inUserData(address),
                    Memory.inStaticData(address),
                    Memory.inKernelText(address),
                    Memory.inKernelData(address),
                    Memory.inMemoryMap(address)
                ]
                if True not in flags:
                    raise ValueError('address out of range')
            else:
                raise ValueError('address should be >= 0')
        else:
            raise TypeError('address should be an int')

    def __getitem__(self, address):
        return self.loadWord(address)

    def __setitem__(self, address, word):
        self.storeWord(address, word)

    def __str__(self):
        return self.memory(config.DATA_BASE_ADDRESS)
