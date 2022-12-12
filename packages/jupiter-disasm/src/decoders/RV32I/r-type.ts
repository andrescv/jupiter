const rType: RTypeMappings = <const>{
  0x00: {
    0x0: 'add',
    0x1: 'sll',
    0x2: 'slt',
    0x3: 'sltu',
    0x4: 'xor',
    0x5: 'srl',
    0x6: 'or',
    0x7: 'and',
  },
  0x20: {
    0x0: 'sub',
    0x5: 'sra',
  },
};

type RTypeMappings = Record<
  number,
  Record<number, string | undefined> | undefined
>;

function getRTypeName(funct3: number, funct7: number): string | null {
  const funct3Mappings = rType[funct7];
  if (funct3Mappings) {
    const name = funct3Mappings[funct3];
    return name || null;
  }

  return null;
}

export default getRTypeName;
