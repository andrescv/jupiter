import unittest
from random import randint

from riscv.hardware import Memory
from riscv.hardware.utils import sign_extend
from riscv.hardware.memory.memconfig import MemoryConfig as config


class TestMemory(unittest.TestCase):

    def setUp(self):
        self.mem = Memory()
        self.data_start = config.DATA_BASE_ADDRESS
        self.data_end = config.DATA_LIMIT_ADDRESS - config.WORD_LENGTH

    def test_init(self):
        self.assertIsInstance(self.mem, Memory)

    def test_getZero(self):
        mem = Memory()
        address = randint(self.data_start, self.data_end)
        address = Memory.alignToWordBoundary(address)
        self.assertEqual(mem.loadWord(address), 0x0)
        self.assertEqual(mem.loadHalf(address), 0x0)
        self.assertEqual(mem.loadByte(address), 0x0)
        self.assertEqual(mem.loadHalfUnsigned(address), 0x0)
        self.assertEqual(mem.loadByteUnsigned(address), 0x0)
        del mem

    def test_allocateBytes(self):
        mem = Memory()
        numBytes = randint(1, 256)
        result = mem.allocateBytesFromHeap(numBytes)
        self.assertEqual(config.HEAP_BASE_ADDRESS, result)
        result += numBytes
        result = Memory.alignToWordBoundary(result)
        self.assertEqual(mem._heap_address, result)
        del mem

    def test_invalidAllocateBytes(self):
        mem = Memory()
        with self.assertRaises(ValueError) as ctxt1:
            mem.allocateBytesFromHeap(-1)
        with self.assertRaises(ValueError) as ctxt2:
            mem.allocateBytesFromHeap(0)
        with self.assertRaises(ValueError) as ctxt3:
            mem.allocateBytesFromHeap(2 ** 32)
        with self.assertRaises(TypeError) as ctxt4:
            mem.allocateBytesFromHeap('2')

        msg1 = str(ctxt1.exception)
        msg2 = str(ctxt2.exception)
        msg3 = str(ctxt3.exception)
        msg4 = str(ctxt4.exception)
        self.assertEqual(msg1, 'numBytes should be > 0')
        self.assertEqual(msg2, 'numBytes should be > 0')
        self.assertEqual(msg3, 'request exceeds available heap storage')
        self.assertEqual(msg4, 'numBytes should be an int')

    def test_loadStoreByte(self):
        address = randint(self.data_start, self.data_end)
        address = Memory.alignToWordBoundary(address)
        self.mem.storeByte(address, 0xca, offset=0)
        self.assertEqual(self.mem.loadByteUnsigned(address, offset=0), 0xca)
        self.mem.storeByte(address, 0xfe, offset=1)
        self.assertEqual(self.mem.loadByteUnsigned(address, offset=1), 0xfe)
        self.mem.storeByte(address, 0xba, offset=2)
        self.assertEqual(self.mem.loadByteUnsigned(address, offset=2), 0xba)
        self.mem.storeByte(address, 0xba, offset=3)
        self.assertEqual(self.mem.loadByteUnsigned(address, offset=3), 0xba)
        val = sign_extend(0xbabafeca, 32)
        self.assertEqual(self.mem.loadWord(address), val)

    def test_loadStoreHalf(self):
        address = randint(self.data_start, self.data_end)
        address = Memory.alignToWordBoundary(address)
        self.mem.storeHalf(address, 0xcafe, offset=0)
        self.assertEqual(self.mem.loadHalfUnsigned(address, offset=0), 0xcafe)
        self.mem.storeHalf(address, 0xffff, offset=1)
        self.assertEqual(self.mem.loadHalfUnsigned(address, offset=1), 0xffff)
        val = sign_extend(0xffffcafe, 32)
        self.assertEqual(self.mem.loadWord(address), val)

    def test_loadStoreWord(self):
        address = randint(self.data_start, self.data_end)
        address = Memory.alignToWordBoundary(address)
        self.mem.storeWord(address, 0xffbbccdd)
        val = sign_extend(0xffbbccdd, 32)
        self.assertEqual(self.mem.loadWord(address), val)

    def test_getsetitem(self):
        address = randint(self.data_start, self.data_end)
        address = Memory.alignToWordBoundary(address)
        self.mem[address] = 0xcafe
        self.assertEqual(self.mem[address], 0xcafe)

    def test_invalidAddress(self):
        with self.assertRaises(ValueError) as ctxt1:
            self.mem[config.RESERVED_LOW_BASE]
        with self.assertRaises(TypeError) as ctxt2:
            self.mem['0x04000000']
        with self.assertRaises(ValueError) as ctxt3:
            self.mem[-10]
        with self.assertRaises(ValueError) as ctxt4:
            self.mem[0x04000000 + 3]

        msg1 = str(ctxt1.exception)
        msg2 = str(ctxt2.exception)
        msg3 = str(ctxt3.exception)
        msg4 = str(ctxt4.exception)
        self.assertEqual(msg1, 'address out of range')
        self.assertEqual(msg2, 'address and offset should be an int')
        self.assertEqual(msg3, 'address and offset should be >= 0')
        self.assertEqual(msg4, 'address is not word aligned')

    def test_inRange(self):
        r = randint(0, 2 ** 16)
        self.assertTrue(Memory.inRange(r, 0, 2 ** 16))

    def test_isWordAligned(self):
        self.assertTrue(Memory.isWordAligned(0x04000000))
        self.assertTrue(not Memory.isWordAligned(0x3))

    def test_wordAlign(self):
        self.assertEqual(Memory.alignToWordBoundary(0x3), 0x4)
