import unittest

from riscv.hardware import MemoryCell
from riscv.hardware.utils import sign_extend


class TestMemoryCell(unittest.TestCase):

    def test_initialization(self):
        cell = MemoryCell()
        self.assertEqual(cell.loadWord(), 0x0)

    def test_storeLoadByte(self):
        cell = MemoryCell()
        cell.storeByte(0xfe, offset=0)
        self.assertEqual(cell.loadByteUnsigned(offset=0), 0xfe)
        self.assertEqual(cell.loadByte(offset=0), sign_extend(0xfe, 8))
        cell.storeByte(0xca, offset=1)
        self.assertEqual(cell.loadByteUnsigned(offset=1), 0xca)
        self.assertEqual(cell.loadByte(offset=1), sign_extend(0xca, 8))
        cell.storeByte(0xca, offset=2)
        self.assertEqual(cell.loadByteUnsigned(offset=2), 0xca)
        self.assertEqual(cell.loadByte(offset=2), sign_extend(0xca, 8))
        cell.storeByte(0xba, offset=3)
        self.assertEqual(cell.loadByteUnsigned(offset=3), 0xba)
        self.assertEqual(cell.loadByte(offset=3), sign_extend(0xba, 8))
        self.assertEqual(cell.loadWord(), sign_extend(0xbacacafe, 32))

    def test_storeLoadHalf(self):
        cell = MemoryCell()
        cell.storeHalf(0xcafe, offset=0)
        self.assertEqual(cell.loadHalfUnsigned(offset=0), 0xcafe)
        self.assertEqual(cell.loadHalf(offset=0), sign_extend(0xcafe, 16))
        cell.storeHalf(0xbaca, offset=1)
        self.assertEqual(cell.loadHalfUnsigned(offset=1), 0xbaca)
        self.assertEqual(cell.loadHalf(offset=1), sign_extend(0xbaca, 16))
        self.assertEqual(cell.loadWord(), sign_extend(0xbacacafe, 32))

    def test_storeLoadWord(self):
        cell = MemoryCell()
        cell.storeWord(0xcafecafe)
        self.assertEqual(cell.loadWord(), sign_extend(0xcafecafe, 32))
