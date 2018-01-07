import random
import unittest
from vsim.riscv.hardware import RVRegisterFile


class TestRegfile(unittest.TestCase):

    @classmethod
    def setUpClass(cls):
        cls.rf = RVRegisterFile()

    def test_str(self):
        print(self.rf)
