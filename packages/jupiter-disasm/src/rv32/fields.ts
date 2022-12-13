/**
 * RV instruction fields.
 */
export const Fields = <const>{
  /**
   * All instruction bits.
   */
  ALL: {
    lo: 0,
    mask: 0xffffffff,
  },

  /**
   * Instruction opcode field.
   */
  OPCODE: {
    lo: 0,
    mask: 0b1111111,
  },

  /**
   * Instruction funct3 field.
   */
  FUNCT3: {
    lo: 12,
    mask: 0b111,
  },

  /**
   * Instruction funct5 field.
   */
  FUNCT5: {
    lo: 27,
    mask: 0b11111,
  },

  /**
   * Instruction funct7 field.
   */
  FUNCT7: {
    lo: 25,
    mask: 0b1111111,
  },

  /**
   * Instruction rd field.
   */
  RD: {
    lo: 7,
    mask: 0b11111,
  },

  /**
   * Instruction rs1 field.
   */
  RS1: {
    lo: 15,
    mask: 0b11111,
  },

  /**
   * Instruction rs2 field.
   */
  RS2: {
    lo: 20,
    mask: 0b11111,
  },

  /**
   * Instruction rs2 field.
   */
  RS3: {
    lo: 27,
    mask: 0b11111,
  },

  /**
   * Instruction shamt field.
   */
  SHAMT: {
    lo: 20,
    mask: 0b11111,
  },

  /**
   * Instruction imm_11_0 field.
   */
  IMM_11_0: {
    lo: 20,
    mask: 0b111111111111,
  },

  /**
   * Instruction imm_4_0 field.
   */
  IMM_4_0: {
    lo: 7,
    mask: 0b11111,
  },

  /**
   * Instruction imm_11_5 field.
   */
  IMM_11_5: {
    lo: 25,
    mask: 0b1111111,
  },

  /**
   * Instruction imm_11b field.
   */
  IMM_11B: {
    lo: 7,
    mask: 0b1,
  },

  /**
   * Instruction imm_4_1 field.
   */
  IMM_4_1: {
    lo: 8,
    mask: 0b1111,
  },

  /**
   * Instruction imm_10_5 field.
   */
  IMM_10_5: {
    lo: 25,
    mask: 0b111111,
  },

  /**
   * Instruction imm_12 field.
   */
  IMM_12: {
    lo: 31,
    mask: 0b1,
  },

  /**
   * Instruction imm_31_12 field.
   */
  IMM_31_12: {
    lo: 12,
    mask: 0xfffff,
  },

  /**
   * Instruction imm_19_12 field.
   */
  IMM_19_12: {
    lo: 12,
    mask: 0xff,
  },

  /**
   * Instruction imm_11j field.
   */
  IMM_11J: {
    lo: 20,
    mask: 0b1,
  },

  /**
   * Instruction imm_10_1 field.
   */
  IMM_10_1: {
    lo: 21,
    mask: 0b1111111111,
  },

  /**
   * Instruction imm_20 field.
   */
  IMM_20: {
    lo: 31,
    mask: 0b1,
  },

  /**
   * Instruction rm field.
   */
  RM: {
    lo: 12,
    mask: 0b111,
  },

  /**
   * Instruction fmt field.
   */
  FMT: {
    lo: 25,
    mask: 0b111,
  },

  /**
   * Instruction rl field.
   */
  RL: {
    lo: 25,
    mask: 0b1,
  },

  /**
   * Instruction aq field.
   */
  AQ: {
    lo: 26,
    mask: 0b1,
  },
  FM: {
    lo: 28,
    mask: 0b1111,
  },
  PRED: {
    lo: 24,
    mask: 0b1111,
  },
  SUCC: {
    lo: 20,
    mask: 0b1111,
  },
};
