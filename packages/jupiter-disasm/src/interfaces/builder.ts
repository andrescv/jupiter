export type Builder<T, P extends Array<unknown> = []> = (...params: P) => T;
