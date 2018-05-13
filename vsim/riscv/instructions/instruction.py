class Instruction:

    def __init__(self, description, simcode):
        self._description = description
        self._simcode = simcode

    def __str__(self):
        return str(self._description)