import { Builder } from '@/interfaces/builder';
import { RVExtension } from '@/interfaces/options';

import { RVDecodeHandler } from './handler';
import { RV32MDecodeHandler } from './RV32M';

export * from './handler';
export * from './RV32I';
export * from './RV32M';

export const extensionsDecoders = new Map<
  RVExtension,
  Builder<RVDecodeHandler>
>();

extensionsDecoders.set('M', () => new RV32MDecodeHandler());
