import { Context } from '@/interface/context';
import { Input } from '@/interface/input';

/**
 * Message.
 */
export class Message {
  /**
   * Creates a new base message.
   *
   * @param input   - The message input.
   * @param ctx     - The message context.
   * @param message - The message message.
   */
  constructor(
    readonly input: Input,
    readonly ctx: Context,
    readonly message: string
  ) {}

  /**
   * Gets the string representation of the message.
   *
   * @return The string representation of the message.
   */
  toString(): string {
    const line = this.ctx.start.line;
    const column = this.ctx.start.column;
    const name = this.input.name;
    const message = this.message;
    const start = this.ctx.start.start;
    const stop = this.ctx.stop.stop;
    const lines = this.input.source.split(/\r?\n/g);
    const lineText = lines[line - 1].replace(/;.*?$/, '').trimEnd();
    const leftPadding = ' '.repeat(column);
    const errorLine = '^'.repeat(stop - start + 1);

    return `[ERROR] ${name}:${line}:${column + 1}: ${message}

${lineText}
${leftPadding}${errorLine}`;
  }
}
