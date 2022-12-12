import { Field } from './field';

export interface MachineCode {
  get(field: Field): number;
  set(field: Field, value?: number): void;
  toString(): string;
}

export * from '@/rv32/fields';
