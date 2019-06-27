######################
#     bgeu TESTS     #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x1, 0x00000000
  li x2, 0x00000000
  bgeu x1, x2, label2_test01
  bne x0, a1, fail
label1_test01:
  bne x0, a1, label3_test01
label2_test01:
  bgeu x1, x2, label1_test01
  bne x0, a1, fail
label3_test01:

test02:
  li a1, 2
  li x1, 0x00000001
  li x2, 0x00000001
  bgeu x1, x2, label2_test02
  bne x0, a1, fail
label1_test02:
  bne x0, a1, label3_test02
label2_test02:
  bgeu x1, x2, label1_test02
  bne x0, a1, fail
label3_test02:

test03:
  li a1, 3
  li x1, 0xffffffff
  li x2, 0xffffffff
  bgeu x1, x2, label2_test03
  bne x0, a1, fail
label1_test03:
  bne x0, a1, label3_test03
label2_test03:
  bgeu x1, x2, label1_test03
  bne x0, a1, fail
label3_test03:

test04:
  li a1, 4
  li x1, 0x00000001
  li x2, 0x00000000
  bgeu x1, x2, label2_test04
  bne x0, a1, fail
label1_test04:
  bne x0, a1, label3_test04
label2_test04:
  bgeu x1, x2, label1_test04
  bne x0, a1, fail
label3_test04:

test05:
  li a1, 5
  li x1, 0xffffffff
  li x2, 0xfffffffe
  bgeu x1, x2, label2_test05
  bne x0, a1, fail
label1_test05:
  bne x0, a1, label3_test05
label2_test05:
  bgeu x1, x2, label1_test05
  bne x0, a1, fail
label3_test05:

test06:
  li a1, 6
  li x1, 0xffffffff
  li x2, 0x00000000
  bgeu x1, x2, label2_test06
  bne x0, a1, fail
label1_test06:
  bne x0, a1, label3_test06
label2_test06:
  bgeu x1, x2, label1_test06
  bne x0, a1, fail
label3_test06:

test07:
  li a1, 7
  li x1, 0x00000000
  li x2, 0x00000001
  bgeu x1, x2, label1_test07
  bne x0, a1, label2_test07
label1_test07:
  bne x0, a1, fail
label2_test07:
  bgeu x1, x2, label1_test07
label3_test07:

test08:
  li a1, 8
  li x1, 0xfffffffe
  li x2, 0xffffffff
  bgeu x1, x2, label1_test08
  bne x0, a1, label2_test08
label1_test08:
  bne x0, a1, fail
label2_test08:
  bgeu x1, x2, label1_test08
label3_test08:

test09:
  li a1, 9
  li x1, 0x00000000
  li x2, 0xffffffff
  bgeu x1, x2, label1_test09
  bne x0, a1, label2_test09
label1_test09:
  bne x0, a1, fail
label2_test09:
  bgeu x1, x2, label1_test09
label3_test09:

test10:
  li a1, 10
  li x1, 0x7fffffff
  li x2, 0x80000000
  bgeu x1, x2, label1_test10
  bne x0, a1, label2_test10
label1_test10:
  bne x0, a1, fail
label2_test10:
  bgeu x1, x2, label1_test10
label3_test10:

test11:
  li a1, 11
  li x4, 0
label1_test11:
  li x1, 0xefffffff
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test11

test12:
  li a1, 12
  li x4, 0
label1_test12:
  li x1, 0xefffffff
  li x2, 0xf0000000
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test12

test13:
  li a1, 13
  li x4, 0
label1_test13:
  li x1, 0xefffffff
  li x2, 0xf0000000
  nop
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test13

test14:
  li a1, 14
  li x4, 0
label1_test14:
  li x1, 0xefffffff
  nop
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test14

test15:
  li a1, 15
  li x4, 0
label1_test15:
  li x1, 0xefffffff
  nop
  li x2, 0xf0000000
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test15

test16:
  li a1, 16
  li x4, 0
label1_test16:
  li x1, 0xefffffff
  nop
  nop
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test16

test17:
  li a1, 17
  li x4, 0
label1_test17:
  li x1, 0xefffffff
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test17

test18:
  li a1, 18
  li x4, 0
label1_test18:
  li x1, 0xefffffff
  li x2, 0xf0000000
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test18

test19:
  li a1, 19
  li x4, 0
label1_test19:
  li x1, 0xefffffff
  li x2, 0xf0000000
  nop
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test19

test20:
  li a1, 20
  li x4, 0
label1_test20:
  li x1, 0xefffffff
  nop
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test20

test21:
  li a1, 21
  li x4, 0
label1_test21:
  li x1, 0xefffffff
  nop
  li x2, 0xf0000000
  nop
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test21

test22:
  li a1, 22
  li x4, 0
label1_test22:
  li x1, 0xefffffff
  nop
  nop
  li x2, 0xf0000000
  bgeu x1, x2, fail
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test22

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
