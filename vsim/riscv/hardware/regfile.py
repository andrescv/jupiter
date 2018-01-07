from . import Register


class RegisterFile(object):

    # ref: Figure 2.1
    # https://github.com/riscv/riscv-isa-manual/blob/master/release/riscv-spec-v2.2.pdf
    _names = {k: v for v, k in enumerate(['x%i' % i for i in range(32)] + ['pc'])}

    # ref: Table 20.1
    # https://github.com/riscv/riscv-isa-manual/blob/master/release/riscv-spec-v2.2.pdf
    _abi_names = {
        # hard-wired zero
        'zero': 0,
        # return address
        'ra': 1,
        # stack pointer
        'sp': 2,
        # global pointer
        'gp': 3,
        # thread pointer
        'tp': 4,
        # temporary / alternate link register
        't0': 5,
        # temporaries
        't1': 6, 't2': 7,
        # saved register / frame pointer
        's0': 8, 'fp': 8,
        # saved register
        's1': 9,
        # function arguments / return values
        'a0': 10, 'a1': 11,
        # function arguments
        'a2': 12, 'a3': 13, 'a4': 14,
        'a5': 15, 'a6': 16, 'a7': 17,
        # saved registers
        's2': 18, 's3': 19, 's4': 20, 's5': 21, 's6': 22,
        's7': 23, 's8': 24, 's9': 25, 's10': 26, 's11': 27,
        # temporaries
        't3': 28, 't4': 29,
        't5': 30, 't6': 31
    }

    # assembler mnemonics for the x registers
    _mnemonics = dict(_names, **_abi_names)

    # zero + 31 general purpose registers + program counter
    _registers = [Register(i, editable=i != 0) for i in range(33)]

    @classmethod
    def getRegisterByNumber(cls, number):
        if not isinstance(number, int):
            raise TypeError('argument "number" should be a integer')
        for register in cls._registers:
            if register.getNumber() == number:
                return register

    @classmethod
    def getRegisterByMnemonic(cls, mnemonic):
        if not isinstance(mnemonic, str):
            raise TypeError('argument "mnemonic" should be a string')
        mnemonic = mnemonic.strip().lower()
        if mnemonic in cls._mnemonics:
            return cls.getRegisterByNumber(cls._mnemonics[mnemonic])

    @classmethod
    def getRegisterNumber(cls, mnemonic):
        register = cls.getRegisterByMnemonic(mnemonic)
        if register:
            return register.getNumber()

    @classmethod
    def getMnemonics(cls):
        return [mnemonic for mnemonic in cls._mnemonics]

    @classmethod
    def isValidMnemonic(cls, mnemonic):
        return mnemonic.lower() in cls._mnemonics

    @classmethod
    def updateRegister(cls, number, value):
        if not isinstance(number, int):
            raise TypeError('argument "number" should be a integer')
        register = cls.getRegisterByNumber(number)
        if register:
            register.setValue(value)

    @classmethod
    def resetRegisters(cls):
        for register in cls._registers:
            register.reset()

    @classmethod
    def printRegisters(cls):
        s = ''
        for mnemonic in cls._names:
            register = self.getRegisteByMnemonic(mnemonic)
            s += '%s[0x%08X]\n' % (mnemonic.upper().ljust(4), register.getValue())
        print(s.strip())

    def __getitem__(self, register):
        if isinstance(register, int):
            return self.getRegisterByNumber(register)
        if isinstance(register, str):
            return self.getRegisterByMnemonic(register)

    def __setitem__(self, register, value):
        if isinstance(register, int):
            self.getRegisterByNumber(register).setValue(value)
        if isinstance(register, str):
            self.getRegisterByMnemonic(register).setValue(value)

    def __str__(self):
        s = ''
        for mnemonic in self._names:
            register = self.getRegisteByMnemonic(mnemonic)
            s += '%s[0x%08X]\n' % (mnemonic.upper().ljust(4), register.getValue())
        return s.strip()
