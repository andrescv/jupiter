export type BTypeNameMappings = Record<number, string | undefined>;

export type ITypeNameMappings = Record<
  number,
  | Record<number, string | Record<number, string | undefined> | undefined>
  | undefined
>;

export type RTypeNameMappings = Record<
  number,
  Record<number, string | undefined> | undefined
>;

export type STypeNameMappings = Record<number, string | undefined>;

export type UTypeNameMappings = Record<number, string | undefined>;

export interface InstructionMappings {
  BType: BTypeNameMappings | null;
  IType: ITypeNameMappings | null;
  RType: RTypeNameMappings | null;
  SType: STypeNameMappings | null;
  UType: UTypeNameMappings | null;
}
