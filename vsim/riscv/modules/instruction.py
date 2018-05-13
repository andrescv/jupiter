from .base import SimCode
from .description import Description


class Instruction:

    def __init__(self, description, simcode):
        self._description = description
        self._simcode = simcode

        # type checking
        if not isinstance(self._description, Description):
            raise TypeError('description should be an instance of Description')
        if not isinstance(self._simcode, SimCode):
            raise TypeError('simcode should be an instance of SimCode')

    def getDescription(self):
        return self._description

    def getSimCode(self):
        return self._simcode

    def execute(self, code):
        self._simcode(code)

    def __call__(self, code):
        self.execute(code)

    def __str__(self):
        return str(self._description)


class PseudoInstruction:

    def __init__(self, description, instructions=[]):
        self._description = description
        self._instructions = instructions

        # type checking
        if not isinstance(instructions, list):
            raise TypeError('instructions should be a list')
        for instruction in instructions:
            if not isinstance(instruction, Instruction):
                error = 'instructions should be an instance of Instruction'
                raise TypeError(error)

    def execute(self):
        for instruction in self._instructions:
            instruction()

    def __call__(self):
        self.execute()

    def __str__(self):
        return str(self._description)
