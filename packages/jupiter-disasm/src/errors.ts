export class UnsupportedInstructionError extends Error {
  constructor(input: number) {
    super(`Instruction 0x${input.toString(16)} is not supported`);
    this.name = 'UnsupportedInstructionError';
  }
}

export class UnsupportedExtensionError extends Error {
  constructor(extension: string) {
    super(`Extension "${extension}" is not supported`);
    this.name = 'UnsupportedExtensionError';
  }
}

export class DisabledExtensionError extends Error {
  constructor(extension: string, input: number) {
    super(
      `To decode 0x${input.toString(
        16
      )}, extension "${extension}" must be enabled`
    );
    this.name = 'DisabledExtensionError';
  }
}
