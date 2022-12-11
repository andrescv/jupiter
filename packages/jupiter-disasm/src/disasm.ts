import { Options, RVExtension } from '@/interfaces/options';

import { extensionsDecoders, RV32IDecodeHandler } from './decoders';
import {
  DisabledExtensionError,
  UnsupportedExtensionError,
  UnsupportedInstructionError,
} from './errors';
import { chain } from './helpers/chain';
import extendOptions, { defaultExtensions } from './helpers/extendOptions';

export function disasm(
  input: number | number[],
  options: Partial<Options> = {}
): string[] {
  const extendedOptions = extendOptions(options);
  const decode = createDecodeFn(extendedOptions.selectedExtensions);

  if (Array.isArray(input)) {
    return input.map(decode);
  }

  return [decode(input)];
}

function createDecodeFn(
  extensions: ReadonlyArray<RVExtension>
): (input: number) => string {
  return (input) => {
    const decoder = createDecoder(extensions);

    const result = decoder.decode(input);

    if (result.handledBy) {
      return result.data;
    }

    throw getDecodeError(input);
  };
}

function getDecodeError(input: number) {
  const { handledBy } = fullDecoder.decode(input);

  if (handledBy) {
    return new DisabledExtensionError(handledBy, input);
  }

  return new UnsupportedInstructionError(input);
}

const createDecoder = (extensions: ReadonlyArray<RVExtension>) =>
  chain(extensions.map(toDecodeHandler), new RV32IDecodeHandler());

function toDecodeHandler(extension: RVExtension) {
  if (extensionsDecoders.has(extension)) {
    const create = extensionsDecoders.get(extension)!;
    return create();
  }

  throw new UnsupportedExtensionError(extension);
}

const fullDecoder = createDecoder(defaultExtensions);
