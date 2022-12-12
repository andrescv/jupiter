const uType: Record<number, string | undefined> = <const>{
  0x37: 'lui',
  0x17: 'auipc',
};

function getUTypeName(opcode: number): string | null {
  const name = uType[opcode];

  return name || null;
}

export default getUTypeName;
