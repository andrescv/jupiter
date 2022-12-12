export interface Options {
  selectedExtensions: ReadonlyArray<RVExtension>;
  useABINames: boolean;
}

export type DecodingOptions = Omit<Options, 'selectedExtensions'>;

export type RVExtension = 'Zifencei' | 'Zicsr' | 'M' | 'A' | 'F' | 'D';
