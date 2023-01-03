import { Builder } from '@/interfaces/builder';
import { DecodingOptions, RVExtension } from '@/interfaces/options';

import { RVDecodeHandler } from './handler';
import { RV32ADecodeHandler } from './RV32A';
import { RV32DDecodeHandler } from './RV32D';
import { RV32FDecodeHandler } from './RV32F';
import { RV32MDecodeHandler } from './RV32M';
import { RV32ZicsrDecodeHandler } from './RV32Zicsr';
import { RV32ZifenceiDecodeHandler } from './RV32Zifencei';

export * from './handler';
export * from './RV32I';
export * from './RV32M';

export const extensionsDecoders = new Map<
  RVExtension,
  Builder<RVDecodeHandler, [DecodingOptions]>
>();

extensionsDecoders.set('A', (options) => new RV32ADecodeHandler(options));
extensionsDecoders.set('D', (options) => new RV32DDecodeHandler(options));
extensionsDecoders.set('F', (options) => new RV32FDecodeHandler(options));
extensionsDecoders.set('M', (options) => new RV32MDecodeHandler(options));
extensionsDecoders.set(
  'Zicsr',
  (options) => new RV32ZicsrDecodeHandler(options)
);
extensionsDecoders.set(
  'Zifencei',
  (options) => new RV32ZifenceiDecodeHandler(options)
);
