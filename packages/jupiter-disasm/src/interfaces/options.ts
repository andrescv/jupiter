export interface Options {
  selectedExtensions: ReadonlyArray<RVExtension>;
  useABINames: boolean;
}

export type RVExtension = 'Zifencei' | 'Zicsr' | 'M' | 'A' | 'F' | 'D';
