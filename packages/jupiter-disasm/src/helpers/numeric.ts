export function areNumeric(...args: unknown[]): boolean {
  return args.every((arg) => typeof arg === 'number');
}

export function extendSign(value: number, len: number): number {
  const v = value & ((1 << len) - 1);
  const signBit = 1 << (len - 1);
  return (v ^ signBit) - signBit;
}
