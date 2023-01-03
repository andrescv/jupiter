import { RTypeNameMappings } from '@/interfaces/instruction-mappings';

const mappings: RTypeNameMappings = <const>{
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

export default mappings;
