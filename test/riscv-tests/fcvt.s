######################
#        fcvt        #
######################

.globl main

.text

main:

test01:
  li a1, 1
  la a0, test01_data
  lw a3, 0(a0)
  li a0, 2
  fcvt.s.w f0, a0
  fmv.x.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test01_data:
  .float 2.000000
  .text

test02:
  li a1, 2
  la a0, test02_data
  lw a3, 0(a0)
  li a0, -2
  fcvt.s.w f0, a0
  fmv.x.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test02_data:
  .float -2.000000
  .text

test03:
  li a1, 3
  la a0, test03_data
  lw a3, 0(a0)
  li a0, 2
  fcvt.s.wu f0, a0
  fmv.x.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test03_data:
  .float 2.000000
  .text

test04:
  li a1, 4
  la a0, test04_data
  lw a3, 0(a0)
  li a0, -2
  fcvt.s.wu f0, a0
  fmv.x.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test04_data:
  .float 4294967300.000000
  .text

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
