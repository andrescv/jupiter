def sign_extend(value, bits):
    sign_bit = 1 << (bits - 1)
    return (value & (sign_bit - 1)) - (value & sign_bit)


def sign_extend32(value):
    value = value & 0xffffffff
    return sign_extend(value, 32)


def sign_extend16(value):
    value = value & 0xffff
    return sign_extend(value, 16)


def sign_extend8(value):
    value = value & 0xff
    return sign_extend(value, 8)
