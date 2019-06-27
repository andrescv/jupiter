######################
#     srli TESTS     #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x1, 0x80000000
  srli x30, x1, 0
  li x29, 0x80000000
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0x80000000
  srli x30, x1, 1
  li x29, 0x40000000
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x80000000
  srli x30, x1, 7
  li x29, 0x01000000
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0x80000000
  srli x30, x1, 14
  li x29, 0x00020000
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0x80000001
  srli x30, x1, 31
  li x29, 0x00000001
  bne x30, x29, fail

test06:
  li a1, 6
  li x1, 0xffffffff
  srli x30, x1, 0
  li x29, 0xffffffff
  bne x30, x29, fail

test07:
  li a1, 7
  li x1, 0xffffffff
  srli x30, x1, 1
  li x29, 0x7fffffff
  bne x30, x29, fail

test08:
  li a1, 8
  li x1, 0xffffffff
  srli x30, x1, 7
  li x29, 0x01ffffff
  bne x30, x29, fail

test09:
  li a1, 9
  li x1, 0xffffffff
  srli x30, x1, 14
  li x29, 0x0003ffff
  bne x30, x29, fail

test10:
  li a1, 10
  li x1, 0xffffffff
  srli x30, x1, 31
  li x29, 0x00000001
  bne x30, x29, fail

test11:
  li a1, 11
  li x1, 0x21212121
  srli x30, x1, 0
  li x29, 0x21212121
  bne x30, x29, fail

test12:
  li a1, 12
  li x1, 0x21212121
  srli x30, x1, 1
  li x29, 0x10909090
  bne x30, x29, fail

test13:
  li a1, 13
  li x1, 0x21212121
  srli x30, x1, 7
  li x29, 0x00424242
  bne x30, x29, fail

test14:
  li a1, 14
  li x1, 0x21212121
  srli x30, x1, 14
  li x29, 0x00008484
  bne x30, x29, fail

test15:
  li a1, 15
  li x1, 0x21212121
  srli x30, x1, 31
  li x29, 0x00000000
  bne x30, x29, fail

test16:
  li a1, 16
  li x1, 0x80000000
  srli x1, x1, 7
  li x29, 0x01000000
  bne x1, x29, fail

test17:
  li a1, 17
  li x4, 0
label1_test17:
  li x1, 0x80000000
  srli x30, x1, 7
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test17
  li x29, 0x01000000
  bne x6, x29, fail

test18:
  li a1, 18
  li x4, 0
label1_test18:
  li x1, 0x80000000
  srli x30, x1, 14
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test18
  li x29, 0x00020000
  bne x6, x29, fail

test19:
  li a1, 19
  li x4, 0
label1_test19:
  li x1, 0x80000001
  srli x30, x1, 31
  nop
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test19
  li x29, 0x00000001
  bne x6, x29, fail

test20:
  li a1, 20
  li x4, 0
label1_test20:
  li x1, 0x80000000
  srli x30, x1, 7
  addi x4, x4, 1
  li x5, 2
  bne x4, x5 label1_test20
  li x29, 0x01000000
  bne x30, x29, fail

test21:
  li a1, 21
  li x4, 0
label1_test21:
  li x1, 0x80000000
  nop
  srli x30, x1, 14
  addi x4, x4, 1
  li x5, 2
  bne x4, x5 label1_test21
  li x29, 0x00020000
  bne x30, x29, fail

test22:
  li a1, 22
  li x4, 0
label1_test22:
  li x1, 0x80000001
  nop
  nop
  srli x30, x1, 31
  addi x4, x4, 1
  li x5, 2
  bne x4, x5 label1_test22
  li x29, 0x00000001
  bne x30, x29, fail

test23:
  li a1, 23
  srli x1, x0, 4
  li x29, 0x00000000
  bne x1, x29, fail

test24:
  li a1, 24
  li x1, 0x00000021
  srli x0, x1, 10
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
