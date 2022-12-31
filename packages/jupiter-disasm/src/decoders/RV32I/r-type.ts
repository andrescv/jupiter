import { RTypeNameMappings } from '@/interfaces/instruction-mappings';

const mappings: RTypeNameMappings = <const>{
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

export default mappings;
