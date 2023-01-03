export interface Handler {
  setNext(handler: Handler): void;
}

export type HandlerResult<T> =
  | { handledBy: string; data: T }
  | { handledBy: null };
