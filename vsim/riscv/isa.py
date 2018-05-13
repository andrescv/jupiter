from .modules import RVI


class InstructionSet:

    def __init__(self):
        self._modules = [
            RVI()
        ]

    def __len__(self):
        length = 0
        for module in self._modules:
            length += len(module)
        return length

    def __str__(self):
        out = ''
        for module in self._modules:
            out += str(module) + '\n'
        return out.strip()
