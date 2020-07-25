######################
#       ffused       #
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
  fmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test01_data:
  .float 1.0
  .float 2.5
  .float 1.0
  .float 3.5
  .text

test02:
  li a1, 2
  la a0, test02_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test02_data:
  .float -1.0
  .float -1235.1
  .float 1.1
  .float 1236.2
  .text

test03:
  li a1, 3
  la a0, test03_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test03_data:
  .float 2.0
  .float -5.0
  .float -2.0
  .float -12.0
  .text

test04:
  li a1, 4
  la a0, test04_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test04_data:
  .float 1.0
  .float 2.5
  .float 1.0
  .float -3.5
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test05_data:
  .float -1.0
  .float -1235.1
  .float 1.1
  .float -1236.2
  .text

test06:
  li a1, 6
  la a0, test06_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmadd.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test06_data:
  .float 2.0
  .float -5.0
  .float -2.0
  .float 12.0
  .text

test07:
  li a1, 7
  la a0, test07_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test07_data:
  .float 1.0
  .float 2.5
  .float 1.0
  .float 1.5
  .text

test08:
  li a1, 8
  la a0, test08_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test08_data:
  .float -1.0
  .float -1235.1
  .float 1.1
  .float 1234
  .text

test09:
  li a1, 9
  la a0, test09_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test09_data:
  .float 2.0
  .float -5.0
  .float -2.0
  .float -8.0
  .text

test10:
  li a1, 10
  la a0, test10_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test10_data:
  .float 1.0
  .float 2.5
  .float 1.0
  .float -1.5
  .text

test11:
  li a1, 11
  la a0, test11_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test11_data:
  .float -1.0
  .float -1235.1
  .float 1.1
  .float -1234
  .text

test12:
  li a1, 12
  la a0, test12_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
  fnmsub.s f3, f0, f1, f2
  fmv.x.w a0, f3
  bne a0, a3, fail
  .data
test12_data:
  .float 2.0
  .float -5.0
  .float -2.0
  .float 8.0
  .text

success:
  li a7, 10
  ecall

fail:
  li a7, 17
  ecall
