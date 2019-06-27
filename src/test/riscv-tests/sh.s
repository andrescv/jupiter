######################
#      sh TESTS      #
######################

.globl __start

.data

tdat:
  tdat1:  .half 0xbeef
  tdat2:  .half 0xbeef
  tdat3:  .half 0xbeef
  tdat4:  .half 0xbeef
  tdat5:  .half 0xbeef
  tdat6:  .half 0xbeef
  tdat7:  .half 0xbeef
  tdat8:  .half 0xbeef
  tdat9:  .half 0xbeef
  tdat10: .half 0xbeef

.text

__start:

test01:
  li a1, 1
  la x1, tdat
  li x2, 0x000000aa
  sh x2, 0(x1)
  lh x30, 0(x1)
  li x29, 0x000000aa
  bne x30, x29, fail

test02:
  li a1, 2
  la x1, tdat
  li x2, 0xffffaa00
  sh x2, 2(x1)
  lh x30, 2(x1)
  li x29, 0xffffaa00
  bne x30, x29, fail

test03:
  li a1, 3
  la x1, tdat
  li x2, 0xbeef0aa0
  sh x2, 4(x1)
  lw x30, 4(x1)
  li x29, 0xbeef0aa0
  bne x30, x29, fail

test04:
  li a1, 4
  la x1, tdat
  li x2, 0xffffa00a
  sh x2, 6(x1)
  lh x30, 6(x1)
  li x29, 0xffffa00a
  bne x30, x29, fail

test05:
  li a1, 5
  la x1, tdat8
  li x2, 0x000000aa
  sh x2, -6(x1)
  lh x30, -6(x1)
  li x29, 0x000000aa
  bne x30, x29, fail

test06:
  li a1, 6
  la x1, tdat8
  li x2, 0xffffaa00
  sh x2, -4(x1)
  lh x30, -4(x1)
  li x29, 0xffffaa00
  bne x30, x29, fail

test07:
  li a1, 7
  la x1, tdat8
  li x2, 0x00000aa0
  sh x2, -2(x1)
  lh x30, -2(x1)
  li x29, 0x00000aa0
  bne x30, x29, fail

test08:
  li a1, 8
  la x1, tdat8
  li x2, 0xffffa00a
  sh x2, 0(x1)
  lh x30, 0(x1)
  li x29, 0xffffa00a
  bne x30, x29, fail

test09:
  li a1, 9
  la x1, tdat9
  li x2, 0x12345678
  addi x4, x1, -32
  sh x2, 32(x4)
  lh x5, 0(x1)
  li x29, 0x00005678
  bne x5, x29, fail

test10:
  li a1, 10
  la x1, tdat9
  li x2, 0x00003098
  addi x1, x1, -5
  sh x2, 7(x1)
  la x4, tdat10
  lh x5, 0(x4)
  li x29, 0x00003098
  bne x5, x29, fail

test11:
  li a1, 11
  li x4, 0
label1_test11:
  li x1, 0xffffccdd
  la x2, tdat
  sh x1, 0(x2)
  lh x30, 0(x2)
  li x29, 0xffffccdd
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test11

test12:
  li a1, 12
  li x4, 0
label1_test12:
  li x1, 0xffffbccd
  la x2, tdat
  nop
  sh x1, 2(x2)
  lh x30, 2(x2)
  li x29, 0xffffbccd
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test12

test13:
  li a1, 13
  li x4, 0
label1_test13:
  li x1, 0xffffbbcc
  la x2, tdat
  nop
  nop
  sh x1, 4(x2)
  lh x30, 4(x2)
  li x29, 0xffffbbcc
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test13

test14:
  li a1, 14
  li x4, 0
label1_test14:
  li x1, 0xffffabbc
  nop
  la x2, tdat
  sh x1, 6(x2)
  lh x30, 6(x2)
  li x29, 0xffffabbc
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test14

test15:
  li a1, 15
  li x4, 0
label1_test15:
  li x1, 0xffffaabb
  nop
  la x2, tdat
  nop
  sh x1, 8(x2)
  lh x30, 8(x2)
  li x29, 0xffffaabb
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test15

test16:
  li a1, 16
  li x4, 0
label1_test16:
  li x1, 0xffffdaab
  nop
  nop
  la x2, tdat
  sh x1, 10(x2)
  lh x30, 10(x2)
  li x29, 0xffffdaab
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test16

test17:
  li a1, 17
  li x4, 0
label1_test17:
  la x2, tdat
  li x1, 0x00002233
  sh x1, 0(x2)
  lh x30, 0(x2)
  li x29, 0x00002233
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test17

test18:
  li a1, 18
  li x4, 0
label1_test18:
  la x2, tdat
  li x1, 0x00001223
  nop
  sh x1, 2(x2)
  lh x30, 2(x2)
  li x29, 0x00001223
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test18

test19:
  li a1, 19
  li x4, 0
label1_test19:
  la x2, tdat
  li x1, 0x00001122
  nop
  nop
  sh x1, 4(x2)
  lh x30, 4(x2)
  li x29, 0x00001122
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test19

test20:
  li a1, 20
  li x4, 0
label1_test20:
  la x2, tdat
  nop
  li x1, 0x00000112
  sh x1, 6(x2)
  lh x30, 6(x2)
  li x29, 0x00000112
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test20

test21:
  li a1, 21
  li x4, 0
label1_test21:
  la x2, tdat
  nop
  li x1, 0x00000011
  nop
  sh x1, 8(x2)
  lh x30, 8(x2)
  li x29, 0x00000011
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test21

test22:
  li a1, 22
  li x4, 0
label1_test22:
  la x2, tdat
  nop
  nop
  li x1, 0x00003001
  sh x1, 10(x2)
  lh x30, 10(x2)
  li x29, 0x00003001
  bne x30, x29, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test22

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
