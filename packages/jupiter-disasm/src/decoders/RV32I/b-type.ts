const bType: Record<number, string | undefined> = <const>{
  0x0: 'beq',
  0x1: 'bne',
  0x4: 'blt',
  0x5: 'bge',
  0x6: 'bltu',
  0x7: 'bgeu',
};

function getBTypeName(funct3: number): string | null {
  const name = bType[funct3];

  return name || null;
}

export default getBTypeName;
