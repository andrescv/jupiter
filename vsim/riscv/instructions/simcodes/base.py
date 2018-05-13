from .... import Globals


class SimCode:

    rf = Globals.regfile
    memory = Globals.memory

    @staticmethod
    def getField(code, mask, offset=0):
        return (code & (mask << offset)) >> offset

    def __call__(self, code):
        if not isinstance(code, int):
            raise TypeError('code should be an instance of int')
