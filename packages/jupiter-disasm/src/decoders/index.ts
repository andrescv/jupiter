import { Builder } from '@/interfaces/builder';
import { DecodingOptions, RVExtension } from '@/interfaces/options';

import { RVDecodeHandler } from './handler';
import { RV32MDecodeHandler } from './RV32M';

export * from './handler';
export * from './RV32I';
export * from './RV32M';

export const extensionsDecoders = new Map<
  RVExtension,
  Builder<RVDecodeHandler, [DecodingOptions]>
>();

extensionsDecoders.set('M', (options) => new RV32MDecodeHandler(options));
