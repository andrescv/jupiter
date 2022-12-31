const rType: RTypeMappings = <const>{
  0x01: {
    0x0: 'mul',
    0x1: 'mulh',
    0x2: 'mulhsu',
    0x3: 'mulhu',
    0x4: 'div',
    0x5: 'divu',
    0x6: 'rem',
    0x7: 'remu',
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
