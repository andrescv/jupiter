######################
#        fcmp        #
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
 feq.s a0, f0, f1
  bne a0, a3, fail
  .data
test01_data:
  .float -1.36
  .float -1.36
  .float 0.0
  .word 0x1
  .text

test02:
  li a1, 2
  la a0, test02_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 fle.s a0, f0, f1
  bne a0, a3, fail
  .data
test02_data:
  .float -1.36
  .float -1.36
  .float 0.0
  .word 0x1
  .text

test03:
  li a1, 3
  la a0, test03_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 flt.s a0, f0, f1
  bne a0, a3, fail
  .data
test03_data:
  .float -1.36
  .float -1.36
  .float 0.0
  .word 0x0
  .text

test04:
  li a1, 4
  la a0, test04_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 feq.s a0, f0, f1
  bne a0, a3, fail
  .data
test04_data:
  .float -1.37
  .float -1.36
  .float 0.0
  .word 0x0
  .text

test05:
  li a1, 5
  la a0, test05_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 fle.s a0, f0, f1
  bne a0, a3, fail
  .data
test05_data:
  .float -1.37
  .float -1.36
  .float 0.0
  .word 0x1
  .text

test06:
  li a1, 6
  la a0, test06_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 flt.s a0, f0, f1
  bne a0, a3, fail
  .data
test06_data:
  .float -1.37
  .float -1.36
  .float 0.0
  .word 0x1
  .text

test07:
  li a1, 7
  la a0, test07_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 feq.s a0, f0, f1
  bne a0, a3, fail
  .data
test07_data:
  .float 0x7fc00000
  .float 0
  .float 0.0
  .word 0x0
  .text

test08:
  li a1, 8
  la a0, test08_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 feq.s a0, f0, f1
  bne a0, a3, fail
  .data
test08_data:
  .float 0x7fc00000
  .float 0x7fc00000
  .float 0.0
  .word 0x0
  .text

test09:
  li a1, 9
  la a0, test09_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 feq.s a0, f0, f1
  bne a0, a3, fail
  .data
test09_data:
  .float 0x7f800001
  .float 0
  .float 0.0
  .word 0x0
  .text

test10:
  li a1, 10
  la a0, test10_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 flt.s a0, f0, f1
  bne a0, a3, fail
  .data
test10_data:
  .float 0x7fc00000
  .float 0
  .float 0.0
  .word 0x0
  .text

test11:
  li a1, 11
  la a0, test11_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 flt.s a0, f0, f1
  bne a0, a3, fail
  .data
test11_data:
  .float 0x7fc00000
  .float 0x7fc00000
  .float 0.0
  .word 0x0
  .text

test12:
  li a1, 12
  la a0, test12_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 flt.s a0, f0, f1
  bne a0, a3, fail
  .data
test12_data:
  .float 0x7f800001
  .float 0
  .float 0.0
  .word 0x0
  .text

test13:
  li a1, 13
  la a0, test13_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 fle.s a0, f0, f1
  bne a0, a3, fail
  .data
test13_data:
  .float 0x7fc00000
  .float 0
  .float 0.0
  .word 0x0
  .text

test14:
  li a1, 14
  la a0, test14_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 fle.s a0, f0, f1
  bne a0, a3, fail
  .data
test14_data:
  .float 0x7fc00000
  .float 0x7fc00000
  .float 0.0
  .word 0x0
  .text

test15:
  li a1, 15
  la a0, test15_data
  flw f0, 0(a0)
  flw f1, 4(a0)
  flw f2, 8(a0)
  lw a3, 12(a0)
 fle.s a0, f0, f1
  bne a0, a3, fail
  .data
test15_data:
  .float 0x7f800001
  .float 0
  .float 0.0
  .word 0x0
  .text

success:
  li a7, 10
  ecall

fail:
  li a7, 17
  ecall
