import { Options, RVExtension } from '@/interfaces/options';

export const defaultExtensions: ReadonlyArray<RVExtension> = Object.freeze([
  'Zifencei',
  'Zicsr',
  'M',
  'A',
  'F',
  'D',
  'A',
]);

const extendOptions = (options: Partial<Options>): Options => ({
  selectedExtensions: defaultExtensions,
  useABINames: false,
  ...options,
});

export default extendOptions;
