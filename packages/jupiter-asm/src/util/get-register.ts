import TerminalNode from 'antlr4/tree/TerminalNode';

/**
 * X registers.
 */
const XRegisters: Record<string, number | undefined> = {
  /**
   * zero: zero register.
   */
  zero: 0,

  /**
   * ra: return address.
   */
  ra: 1,

  /**
   * sp: stack pointer.
   */
  sp: 2,

  /**
   * gp: global pointer.
   */
  gp: 3,

  /**
   * tp: thread pointer.
   */
  tp: 4,

  /**
   * t0: temporary register.
   */
  t0: 5,

  /**
   * t1: temporary register.
   */
  t1: 6,

  /**
   * t2: temporary register.
   */
  t2: 7,

  /**
   * fp: frame pointer.
   */
  fp: 8,

  /**
   * s0: saved register.
   */
  s0: 8,

  /**
   * s1: saved register.
   */
  s1: 9,

  /**
   * a0: argument register.
   */
  a0: 10,

  /**
   * a1: argument register.
   */
  a1: 11,

  /**
   * a2: argument register.
   */
  a2: 12,

  /**
   * a3: argument register.
   */
  a3: 13,

  /**
   * a4: argument register.
   */
  a4: 14,

  /**
   * a5: argument register.
   */
  a5: 15,

  /**
   * a6: argument register.
   */
  a6: 16,

  /**
   * a7: argument register.
   */
  a7: 17,

  /**
   * s2: saved register.
   */
  s2: 18,

  /**
   * s3: saved register.
   */
  s3: 19,

  /**
   * s4: saved register.
   */
  s4: 20,

  /**
   * s5: saved register.
   */
  s5: 21,

  /**
   * s6: saved register.
   */
  s6: 22,

  /**
   * s7: saved register.
   */
  s7: 23,

  /**
   * s8: saved register.
   */
  s8: 24,

  /**
   * s9: saved register.
   */
  s9: 25,

  /**
   * s10: saved register.
   */
  s10: 26,

  /**
   * s11: saved register.
   */
  s11: 27,

  /**
   * t3: temporary register.
   */
  t3: 28,

  /**
   * t4: temporary register.
   */
  t4: 29,

  /**
   * t5: temporary register.
   */
  t5: 30,

  /**
   * t6: temporary register.
   */
  t6: 31,
};

/**
 * F registers.
 */
const FRegisters: Record<string, number | undefined> = {
  /**
   * ft0: temporary register.
   */
  ft0: 0,

  /**
   * ft1: temporary register.
   */
  ft1: 1,

  /**
   * ft2: temporary register.
   */
  ft2: 2,

  /**
   * ft3: temporary register.
   */
  ft3: 3,

  /**
   * ft4: temporary register.
   */
  ft4: 4,

  /**
   * ft5: temporary register.
   */
  ft5: 5,

  /**
   * ft6: temporary register.
   */
  ft6: 6,

  /**
   * ft7: temporary register.
   */
  ft7: 7,

  /**
   * fs0: saved register.
   */
  fs0: 8,

  /**
   * fs1: saved register.
   */
  fs1: 9,

  /**
   * fa0: argument register.
   */
  fa0: 10,

  /**
   * fa1: argument register.
   */
  fa1: 11,

  /**
   * fa2: argument register.
   */
  fa2: 12,

  /**
   * fa3: argument register.
   */
  fa3: 13,

  /**
   * fa4: argument register.
   */
  fa4: 14,

  /**
   * fa5: argument register.
   */
  fa5: 15,

  /**
   * fa6: argument register.
   */
  fa6: 16,

  /**
   * fa7: argument register.
   */
  fa7: 17,

  /**
   * fs2: saved register.
   */
  fs2: 18,

  /**
   * fs3: saved register.
   */
  fs3: 19,

  /**
   * fs4: saved register.
   */
  fs4: 20,

  /**
   * fs5: saved register.
   */
  fs5: 21,

  /**
   * fs6: saved register.
   */
  fs6: 22,

  /**
   * fs7: saved register.
   */
  fs7: 23,

  /**
   * fs8: saved register.
   */
  fs8: 24,

  /**
   * fs9: saved register.
   */
  fs9: 25,

  /**
   * fs10: saved register.
   */
  fs10: 26,

  /**
   * fs11: saved register.
   */
  fs11: 27,

  /**
   * ft8: temporary register.
   */
  ft8: 28,

  /**
   * ft9: temporary register.
   */
  ft9: 29,

  /**
   * ft10: temporary register.
   */
  ft10: 30,

  /**
   * ft11: temporary register.
   */
  ft11: 31,
};

/**
 * Gets register number from node.
 *
 * @param node - Register node.
 */
export function getRegister(node: TerminalNode) {
  const register = node.getText().toLowerCase();

  if (typeof XRegisters[register] === 'number') {
    return XRegisters[register]!;
  } else if (typeof FRegisters[register] === 'number') {
    return FRegisters[register]!;
  }

  return parseInt(register.slice(1), 10);
}
