from . import simcodes
from ..itypes import InstructionTypes
from ..description import Description
from ..instruction import Instruction, PseudoInstruction


class RVI:

    def __init__(self):
        # rtype instructions
        self._rtype = {
            'add': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'add',
                    'add rd, rs1, rs2',
                    'set rd = rs1 + rs2, overflow is ignored'
                ),
                simcodes.rtype.Add()
            ),
            'sub': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'sub',
                    'sub rd, rs1, rs2',
                    'set rd = rs1 - rs2, overflow is ignored'
                ),
                simcodes.rtype.Sub()
            ),
            'sll': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'sll',
                    'sll rd, rs1, rs2',
                    'set rd = rs1 << rs2'
                ),
                simcodes.rtype.Sll()
            ),
            'slt': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'slt',
                    'slt rd, rs1, rs2',
                    'set rd = 1 if rs1 < rs2 else 0, signed comparison'
                ),
                simcodes.rtype.Slt()
            ),
            'sltu': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'sltu',
                    'sltu rd, rs1, rs2',
                    'set rd = 1 if rs1 < rs2 else 0, unsigned comparison'
                ),
                simcodes.rtype.Sltu()
            ),
            'xor': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'xor',
                    'xor rd, rs1, rs2',
                    'set rd = rs1 xor rs2'
                ),
                simcodes.rtype.Xor()
            ),
            'srl': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'srl',
                    'srl rd, rs1, rs2',
                    'set rd = rs1 >> rs2'
                ),
                simcodes.rtype.Srl()
            ),
            'sra': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'sra',
                    'sra rd, rs1, rs2',
                    'set rd = rs1 >> rs2 (arithmetic shift)'
                ),
                simcodes.rtype.Sra()
            ),
            'or': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'or',
                    'or rd, rs1, rs2',
                    'set rd = rs1 or rs2'
                ),
                simcodes.rtype.Or()
            ),
            'and': Instruction(
                Description(
                    InstructionTypes.RTYPE,
                    'and',
                    'and rd, rs1, rs2',
                    'set rd = rs1 and rs2'
                ),
                simcodes.rtype.And()
            )
        }
        self._itype = {
            'addi': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'addi',
                    'addi rd, rs1, imm',
                    'set rd = rs1 + imm'
                ),
                simcodes.itype.Addi()
            ),
            'slti': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'slti',
                    'slti rd, rs1, imm',
                    'set rd = 1 if rs1 < imm else 0, signed comparison'
                ),
                simcodes.itype.Slti()
            ),
            'sltiu': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'sltiu',
                    'sltiu rd, rs1, imm',
                    'set rd = 1 if rs1 < imm else 0, unsigned comparison'
                ),
                simcodes.itype.Sltiu()
            ),
            'xori': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'xori',
                    'xori rd, rs1, imm',
                    'set rd = rs1 xor imm',
                ),
                simcodes.itype.Xori()
            ),
            'ori': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'ori',
                    'ori rd, rs1, imm',
                    'set rd = rs1 or imm',
                ),
                simcodes.itype.Ori()
            ),
            'andi': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'andi',
                    'andi rd, rs1, imm',
                    'set rd = rs1 and imm',
                ),
                simcodes.itype.Andi()
            ),
            'slli': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'slli',
                    'slli rd, rs1, imm',
                    'set rd = rs1 << imm'
                ),
                simcodes.itype.Slli()
            ),
            'srli': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'srli',
                    'srli rd, rs1, imm',
                    'set rd = rs1 >> imm'
                ),
                simcodes.itype.Srli()
            ),
            'srai': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'srai',
                    'srai rd, rs1, imm',
                    'set rd = rs1 >> imm (arithmetic shift)'
                ),
                simcodes.itype.Srai()
            ),
            'lw': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'lw',
                    'lw rd, imm(rs1)',
                    'set rd = memory[rs1 + imm] (word)'
                ),
                simcodes.itype.Lw()
            ),
            'lh': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'lh',
                    'lh rd, imm(rs1)',
                    'set rd = memory[rs1 + imm] (half)'
                ),
                simcodes.itype.Lh()
            ),
            'lhu': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'lhu',
                    'lhu rd, imm(rs1)',
                    'set rd = memory[rs1 + imm] unsiged (half)'
                ),
                simcodes.itype.Lhu()
            ),
            'lb': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'lb',
                    'lb rd, imm(rs1)',
                    'set rd = memory[rs1 + imm] (byte)'
                ),
                simcodes.itype.Lb()
            ),
            'lbu': Instruction(
                Description(
                    InstructionTypes.ITYPE,
                    'lbu',
                    'lbu rd, imm(rs1)',
                    'set rd = memory[rs1 + imm] unsiged (byte)'
                ),
                simcodes.itype.Lbu()
            ),
            'jalr': Instruction(
                Description(
                    InstructionTypes.JTYPE,
                    'jalr',
                    'jalr rd, rs1, imm',
                    'set rd= pc + 4 and pc = pc + rs1 + imm'
                ),
                simcodes.itype.Jalr()
            )
        }
        self._stype = {
            'sw': Instruction(
                Description(
                    InstructionTypes.STYPE,
                    'sw',
                    'sw rs2, imm(rs1)',
                    'set memory[rs1 + imm] = rs2 (word)'
                ),
                simcodes.stype.Sw()
            ),
            'sh': Instruction(
                Description(
                    InstructionTypes.STYPE,
                    'sh',
                    'sh rs2, imm(rs1)',
                    'set memory[rs1 + imm] = rs2 (half)'
                ),
                simcodes.stype.Sw()
            ),
            'sb': Instruction(
                Description(
                    InstructionTypes.STYPE,
                    'sb',
                    'sb rs2, imm(rs1)',
                    'set memory[rs1 + imm] = rs2 (byte)'
                ),
                simcodes.stype.Sw()
            )
        }
        self._utype = {
            'lui': Instruction(
                Description(
                    InstructionTypes.UTYPE,
                    'lui',
                    'lui rd, imm',
                    'set rd = (imm << 20) and 0xfffff000'
                ),
                simcodes.utype.Lui()
            ),
            'auipc': Instruction(
                Description(
                    InstructionTypes.UTYPE,
                    'auipc',
                    'auipc rd, imm',
                    'set rd = ((im << 20) and 0xfffff000) or pc'
                ),
                simcodes.utype.Auipc()
            )
        }
        self._btype = {
            'beq': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'beq',
                    'beq rs1, rs2, imm',
                    'set pc = pc + imm if rs1 == rs2, signed comparison'
                ),
                simcodes.btype.Beq()
            ),
            'bne': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'bne',
                    'bne rs1, rs2, imm',
                    'set pc = pc + imm if rs1 != rs2, signed comparison'
                ),
                simcodes.btype.Bne()
            ),
            'bge': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'bge',
                    'bge rs1, rs2, imm',
                    'set pc = pc + imm if rs1 >= rs2, signed comparison'
                ),
                simcodes.btype.Bge()
            ),
            'bgeu': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'bgeu',
                    'bgeu rs1, rs2, imm',
                    'set pc = pc + imm if rs1 >= rs2, unsigned comparison'
                ),
                simcodes.btype.Bgeu()
            ),
            'blt': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'blt',
                    'blt rs1, rs2, imm',
                    'set pc = pc + imm if rs1 < rs2, signed comparison'
                ),
                simcodes.btype.Blt()
            ),
            'bltu': Instruction(
                Description(
                    InstructionTypes.BTYPE,
                    'bltu',
                    'bltu rs1, rs2, imm',
                    'set pc = pc + imm if rs1 < rs2, unsigned comparison'
                ),
                simcodes.btype.Bltu()
            ),
        }
        self._jtype = {
            'jal': Instruction(
                Description(
                    InstructionTypes.JTYPE,
                    'jal',
                    'jal rd, imm',
                    'set rd = pc + 4 and pc = pc + imm'
                ),
                simcodes.jtype.Jal()
            )
        }
        self.types = [
            self._rtype, self._itype, self._stype,
            self._btype, self._utype, self._jtype
        ]

    def __getitem__(self, op):
        for t in self.types:
            if op in t:
                return t[op]

    def __len__(self):
        length = 0
        for t in self.types:
            length += len(t)
        return length

    def __str__(self):
        out = ''
        for t in self.types:
            for i in t.values():
                out += str(i) + '\n'
        return out.strip()
