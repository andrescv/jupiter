const iType: ITypeMappings = <const>{
  0x03: {
    0x0: 'lb',
    0x1: 'lh',
    0x2: 'lw',
    0x4: 'lbu',
    0x5: 'lhu',
  },
  0x13: {
    0x0: 'addi',
    0x2: 'slti',
    0x3: 'sltiu',
    0x4: 'xori',
    0x6: 'ori',
    0x7: 'andi',
    0x1: {
      0x00: 'slli',
    },
    0x5: {
      0x00: 'srli',
      0x20: 'srai',
    },
  },
  0x67: {
    0x0: 'jalr',
  },
  0x73: {
    0x0: {
      0x0: 'ecall',
      0x1: 'ebreak',
    },
  },
};

type ITypeMappings = Record<
  number,
  | Record<number, string | Record<number, string | undefined> | undefined>
  | undefined
>;

function getITypeName(opcode: number, funct3: number, special?: number) {
  const opcodeMappings = iType[opcode];
  const funct3Mappings = opcodeMappings?.[funct3];

  if (funct3Mappings) {
    if (typeof funct3Mappings === 'string' && special === undefined) {
      return funct3Mappings;
    }

    if (typeof funct3Mappings === 'object' && special !== undefined) {
      return funct3Mappings[special] || null;
    }
  }

  return null;
}

export default getITypeName;
