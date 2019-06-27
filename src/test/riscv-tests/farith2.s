######################
#      farith2       #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  la a0, test01_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fdiv.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test01_data:
  .float 3.14159265
  .float 2.71828182
  .float 0.0
  .float 1.1557273520668288
  .text

test02:
  li a1, 2
  la a0, test02_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fdiv.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test02_data:
  .float -1234
  .float 1235.1
  .float 0.0
  .float -0.9991093838555584
  .text

test03:
  li a1, 3
  la a0, test03_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fdiv.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test03_data:
  .float 3.14159265
  .float 1.0
  .float 0.0
  .float 3.14159265
  .text

test04:
  li a1, 4
  la a0, test04_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsqrt.s f3, f0
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test04_data:
  .float 3.14159265
  .float 0.0
  .float 0.0
  .float 1.7724538498928541
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsqrt.s f3, f0
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test05_data:
  .float 10000
  .float 0.0
  .float 0.0
  .float 100
  .text

test07:
  li a1, 7
  la a0, test07_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsqrt.s f3, f0
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test07_data:
  .float 171.0
  .float 0.0
  .float 0.0
  .float 13.076696
  .text

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
