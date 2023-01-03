export function deepFreeze<T extends object>(ob: T): T {
  const keys = Reflect.ownKeys(ob);

  for (const key of keys) {
    // eslint-disable-next-line @typescript-eslint/no-explicit-any
    const value = (<any>ob)[key];

    if ((value && typeof value === 'object') || typeof value === 'function') {
      deepFreeze(value);
    }
  }

  return Object.freeze(ob);
}
