const sType: Record<number, string | undefined> = <const>{
  0x0: 'sb',
  0x1: 'sh',
  0x2: 'sw',
};

function getSTypeName(funct3: number): string | null {
  const name = sType[funct3];

  return name || null;
}

export default getSTypeName;
