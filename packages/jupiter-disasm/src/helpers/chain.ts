import { Handler } from '@/interfaces/handler';

export function chain<T extends Handler>(handlers: T[], base?: T): T {
  const [firstHandler] = handlers;
  handlers.reduce((prev, curr) => {
    prev?.setNext(curr);
    return curr;
  }, base);

  return base || firstHandler;
}
