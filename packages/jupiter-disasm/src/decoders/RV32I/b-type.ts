import { BTypeNameMappings } from '@/interfaces/instruction-mappings';

const mappings: BTypeNameMappings = <const>{
  0x0: 'beq',
  0x1: 'bne',
  0x4: 'blt',
  0x5: 'bge',
  0x6: 'bltu',
  0x7: 'bgeu',
};

export default mappings;
