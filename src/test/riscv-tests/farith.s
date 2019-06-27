######################
#       farith       #
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
  fadd.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test01_data:
  .float 2.5
  .float 1.0
  .float 0.0
  .float 3.5
  .text

test02:
  li a1, 2
  la a0, test02_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fadd.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test02_data:
  .float -1235.1
  .float 1.1
  .float 0.0
  .float -1234
  .text

test03:
  li a1, 3
  la a0, test03_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fadd.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test03_data:
  .float 3.14159265
  .float 1e-08
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
  fsub.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test04_data:
  .float 2.5
  .float 1.0
  .float 0.0
  .float 1.5
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsub.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test05_data:
  .float -1235.1
  .float -1.1
  .float 0.0
  .float -1234
  .text

test06:
  li a1, 6
  la a0, test06_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsub.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test06_data:
  .float 3.14159265
  .float 1e-08
  .float 0.0
  .float 3.14159265
  .text

test07:
  li a1, 7
  la a0, test07_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmul.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test07_data:
  .float 2.5
  .float 1.0
  .float 0.0
  .float 2.5
  .text

test08:
  li a1, 8
  la a0, test08_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmul.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test08_data:
  .float -1235.1
  .float -1.1
  .float 0.0
  .float 1358.61
  .text

test09:
  li a1, 9
  la a0, test09_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmul.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test09_data:
  .float 3.14159265
  .float 1e-08
  .float 0.0
  .float 3.14159265e-08
  .text

test10:
  li a1, 10
  la a0, test10_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fsub.s f3, f0, f1
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test10_data:
  .float 0x7f800000
  .float 0x7f800000
  .float 0.0
  .float 0x7fc00000
  .text

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
