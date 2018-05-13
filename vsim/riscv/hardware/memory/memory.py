from termcolor import colored

from .cell import MemoryCell
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
            self._heap_address = Memory.alignToWordBoundary(self._heap_address)
            if self._heap_address >= config.DATA_LIMIT_ADDRESS:
                raise ValueError('request exceeds available heap storage')
            return result
        else:
            raise TypeError('numBytes should be an int')

    def loadByte(self, address, offset=0):
        Memory.checkAddress(address, offset=offset)
        if address not in self._mem:
            return 0x0
        else:
            return self._mem[address].loadByte(offset=offset)

    def loadByteUnsigned(self, address, offset=0):
        Memory.checkAddress(address, offset=offset)
        if address not in self._mem:
            return 0x0
        else:
            return self._mem[address].loadByteUnsigned(offset=offset)

    def loadHalf(self, address, offset=0):
        Memory.checkAddress(address, offset=offset)
        if address not in self._mem:
            return 0x0
        else:
            return self._mem[address].loadHalf(offset=offset)

    def loadHalfUnsigned(self, address, offset=0):
        Memory.checkAddress(address, offset=offset)
        if address not in self._mem:
            return 0x0
        else:
            return self._mem[address].loadHalfUnsigned(offset=offset)

    def loadWord(self, address):
        Memory.checkAddress(address)
        if address not in self._mem:
            return 0x0
        else:
            return self._mem[address].loadWord()

    def storeByte(self, address, byte, offset=0):
        if address not in self._mem:
            Memory.checkAddress(address, offset=offset)
            self._mem[address] = MemoryCell()
        self._mem[address].storeByte(byte, offset=offset)

    def storeHalf(self, address, half, offset=0):
        Memory.checkAddress(address, offset=offset)
        if address not in self._mem:
            self._mem[address] = MemoryCell()
        self._mem[address].storeHalf(half, offset=offset)

    def storeWord(self, address, word):
        Memory.checkAddress(address)
        if address not in self._mem:
            self._mem[address] = MemoryCell()
        self._mem[address].storeWord(word)

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
    def alignToWordBoundary(address):
        if not Memory.isWordAligned(address):
            address += (config.WORD_LENGTH - (address % config.WORD_LENGTH))
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
    def checkAddress(address, offset=0):
        if isinstance(address, int) and isinstance(offset, int):
            if address >= 0 and offset >= 0:
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
                if not Memory.isWordAligned(address):
                    raise ValueError('address is not word aligned')
            else:
                raise ValueError('address and offset should be >= 0')
        else:
            raise TypeError('address and offset should be an int')

    def __str__(self):
        return self.memory(config.DATA_BASE_ADDRESS)
