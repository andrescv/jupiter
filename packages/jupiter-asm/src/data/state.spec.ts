import { describe, expect, it } from 'vitest';
import { mockDeep } from 'vitest-mock-extended';

import { Segment } from '@/enum/segment';

import { Input } from '@/interface/input';
import { Options } from '@/interface/options';

import { State } from './state';

describe('[State] tests', () => {
  const input: Input = {
    name: 'test.s',
    source: 'add a0, a0, a0',
  };

  it('should be defined', () => {
    const options = mockDeep<Options>();
    expect(new State(input, options)).toBeDefined();
  });

  it('should check segment value correctly', () => {
    const options = mockDeep<Options>();
    const state = new State(input, options);

    expect(state.inText()).toBeTruthy();
    expect(state.inRodata()).toBeFalsy();
    expect(state.inData()).toBeFalsy();
    expect(state.inBSS()).toBeFalsy();

    state.segment = Segment.RODATA;

    expect(state.inText()).toBeFalsy();
    expect(state.inRodata()).toBeTruthy();
    expect(state.inData()).toBeFalsy();
    expect(state.inBSS()).toBeFalsy();

    state.segment = Segment.DATA;

    expect(state.inText()).toBeFalsy();
    expect(state.inRodata()).toBeFalsy();
    expect(state.inData()).toBeTruthy();
    expect(state.inBSS()).toBeFalsy();

    state.segment = Segment.BSS;

    expect(state.inText()).toBeFalsy();
    expect(state.inRodata()).toBeFalsy();
    expect(state.inData()).toBeFalsy();
    expect(state.inBSS()).toBeTruthy();
  });

  it('should create a globals instance', () => {
    expect(State.createGlobals()).toBeDefined();
  });
});
