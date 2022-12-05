import { Segment } from '@/enum/segment';

import { Context } from '@/interface/context';
import { Def } from '@/interface/def';
import { GlobalSymbol } from '@/interface/global-symbol';
import { Input } from '@/interface/input';
import { LocalSymbol } from '@/interface/local-symbol';
import { Options } from '@/interface/options';

import { Message } from './message';

/**
 * Assembler state.
 */
export class State {
  /**
   * Assembler segment.
   */
  public segment: Segment = Segment.TEXT;

  /**
   *  Creates a new assembler state.
   *
   * @param input    - Assembler input.
   * @param opts     - Assembler options.
   * @param messages - Assembler messages.
   * @param globals  - Assembler global symbols.
   * @param locals   - Assembler local symbols.
   * @param defs     - Assembler defs.
   */
  constructor(
    public readonly input: Input,
    public readonly opts: Options,
    public readonly messages: Message[] = [],
    public readonly globals: Map<string, GlobalSymbol> = new Map(),
    public readonly locals: Map<string, LocalSymbol> = new Map(),
    public readonly defs: Map<string, Def> = new Map()
  ) {}

  /**
   * Adds an error message.
   *
   * @param ctx     - Error context.
   * @param message - Error message.
   */
  addError(ctx: Context, message: string) {
    this.messages.push(new Message(this.input, ctx, message));
  }

  /**
   * Verifies if the current segment is the text segment.
   *
   * @returns true if the current segment is the text segment.
   */
  inText() {
    return this.segment === Segment.TEXT;
  }

  /**
   * Verifies if the current segment is the read-only data segment.
   * @returns true if the current segment is the read-only data segment.
   */
  inRodata() {
    return this.segment === Segment.RODATA;
  }

  /**
   * Verifies if the current segment is the data segment.
   *
   * @returns true if the current segment is the data segment.
   */
  inData() {
    return this.segment === Segment.DATA;
  }

  /**
   * Verifies if the current segment is the bss segment.
   *
   * @returns true if the current segment is the bss segment.
   */
  inBSS() {
    return this.segment === Segment.BSS;
  }

  /**
   * Creates a new assembler globals.
   */
  static createGlobals() {
    return new Map<string, GlobalSymbol>();
  }
}
