######################
#        fcvt        #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  la a0, test01_data
  lw a3, 0(a0)
  li a0, 2
  fcvt.s.w f0, a0
  fmv.x.w a0, f0
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
  fmv.x.w a0, f0
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
  fmv.x.w a0, f0
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
  fmv.x.w a0, f0
  bne a0, a3, fail
  .data
  .align 2
test04_data:
  .float 4294967300.000000
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  li a3, 2147483647
  fcvt.w.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test05_data:
  .float 4294967300.000000
  .text

test06:
  li a1, 6
  la a0, test06_data
  flw f0, 0(a0)
  li a3, 2147483647
  fcvt.w.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test06_data:
  .float 0x7fffffff
  .text


test07:
  li a1, 7
  la a0, test07_data
  flw f0, 0(a0)
  li a3, 2147483647
  fcvt.wu.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test07_data:
  .float 4294967300.000000
  .text

test08:
  li a1, 8
  la a0, test08_data
  flw f0, 0(a0)
  li a3, 2147483647
  fcvt.wu.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test08_data:
  .float 0x7fffffff
  .text

test09:
  li a1, 9
  la a0, test09_data
  flw f0, 0(a0)
  li a3, 0
  fcvt.wu.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test09_data:
  .float 0xcf000001
  .text

test10:
  li a1, 10
  la a0, test10_data
  flw f0, 0(a0)
  li a3, 2147483647
  fcvt.wu.s a0, f0
  bne a0, a3, fail
  .data
  .align 2
test10_data:
  .float 0x4f000000
  .text

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
