export function areNumeric(...args: unknown[]): boolean {
  return args.every((arg) => typeof arg === 'number');
}
