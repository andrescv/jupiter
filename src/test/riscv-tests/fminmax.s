######################
#      fminmax       #
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
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test01_data:
  .float 2.5
  .float 1.0
  .float 0.0
  .float 1.0
  .text

test02:
  li a1, 2
  la a0, test02_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test02_data:
  .float -1235.1
  .float 1.1
  .float 0.0
  .float -1235.1
  .text

test03:
  li a1, 3
  la a0, test03_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test03_data:
  .float 1.1
  .float -1235.1
  .float 0.0
  .float -1235.1
  .text

test04:
  li a1, 4
  la a0, test04_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test04_data:
  .float 0x7fc00000
  .float -1235.1
  .float 0.0
  .float -1235.1
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test05_data:
  .float 3.14159265
  .float 1e-08
  .float 0.0
  .float 1e-08
  .text

test06:
  li a1, 6
  la a0, test06_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test06_data:
  .float -1.0
  .float -2.0
  .float 0.0
  .float -2.0
  .text

test11:
  li a1, 11
  la a0, test11_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test11_data:
  .float 2.5
  .float 1.0
  .float 0.0
  .float 2.5
  .text

test12:
  li a1, 12
  la a0, test12_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test12_data:
  .float -1235.1
  .float 1.1
  .float 0.0
  .float 1.1
  .text

test13:
  li a1, 13
  la a0, test13_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test13_data:
  .float 1.1
  .float -1235.1
  .float 0.0
  .float 1.1
  .text

test14:
  li a1, 14
  la a0, test14_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test14_data:
  .float 0x7fc00000
  .float -1235.1
  .float 0.0
  .float -1235.1
  .text

test15:
  li a1, 15
  la a0, test15_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test15_data:
  .float 3.14159265
  .float 1e-08
  .float 0.0
  .float 3.14159265
  .text

test16:
  li a1, 16
  la a0, test16_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test16_data:
  .float -1.0
  .float -2.0
  .float 0.0
  .float -1.0
  .text

test19:
  li a1, 19
  la a0, test19_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test19_data:
  .float 0x7f800001
  .float 1.0
  .float 0.0
  .float 1.0
  .text

test20:
  li a1, 20
  la a0, test20_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test20_data:
  .float 0x7fc00000
  .float 0x7fc00000
  .float 0.0
  .float 0x7fc00000
  .text

test29:
  li a1, 29
  la a0, test29_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test29_data:
  .float -0.0
  .float 0.0
  .float 0.0
  .float -0.0
  .text

test30:
  li a1, 30
  la a0, test30_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmin.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test30_data:
  .float 0.0
  .float -0.0
  .float 0.0
  .float -0.0
  .text

test31:
  li a1, 31
  la a0, test31_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test31_data:
  .float -0.0
  .float 0.0
  .float 0.0
  .float 0.0
  .text

test32:
  li a1, 32
  la a0, test32_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmax.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test32_data:
  .float 0.0
  .float -0.0
  .float 0.0
  .float 0.0
  .text

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
