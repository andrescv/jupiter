######################
#     srl TESTS      #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x1, 0x80000000
  li x2, 0x00000000
  srl x30, x1, x2
  li x29, 0x80000000
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0x80000000
  li x2, 0x00000001
  srl x30, x1, x2
  li x29, 0x40000000
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x80000000
  li x2, 0x00000007
  srl x30, x1, x2
  li x29, 0x01000000
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0x80000000
  li x2, 0x0000000e
  srl x30, x1, x2
  li x29, 0x00020000
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0x80000001
  li x2, 0x0000001f
  srl x30, x1, x2
  li x29, 0x00000001
  bne x30, x29, fail

test06:
  li a1, 6
  li x1, 0xffffffff
  li x2, 0x00000000
  srl x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test07:
  li a1, 7
  li x1, 0xffffffff
  li x2, 0x00000001
  srl x30, x1, x2
  li x29, 0x7fffffff
  bne x30, x29, fail

test08:
  li a1, 8
  li x1, 0xffffffff
  li x2, 0x00000007
  srl x30, x1, x2
  li x29, 0x01ffffff
  bne x30, x29, fail

test09:
  li a1, 9
  li x1, 0xffffffff
  li x2, 0x0000000e
  srl x30, x1, x2
  li x29, 0x0003ffff
  bne x30, x29, fail

test10:
  li a1, 10
  li x1, 0xffffffff
  li x2, 0x0000001f
  srl x30, x1, x2
  li x29, 0x00000001
  bne x30, x29, fail

test11:
  li a1, 11
  li x1, 0x21212121
  li x2, 0x00000000
  srl x30, x1, x2
  li x29, 0x21212121
  bne x30, x29, fail

test12:
  li a1, 12
  li x1, 0x21212121
  li x2, 0x00000001
  srl x30, x1, x2
  li x29, 0x10909090
  bne x30, x29, fail

test13:
  li a1, 13
  li x1, 0x21212121
  li x2, 0x00000007
  srl x30, x1, x2
  li x29, 0x00424242
  bne x30, x29, fail

test14:
  li a1, 14
  li x1, 0x21212121
  li x2, 0x0000000e
  srl x30, x1, x2
  li x29, 0x00008484
  bne x30, x29, fail

test15:
  li a1, 15
  li x1, 0x21212121
  li x2, 0x0000001f
  srl x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test16:
  li a1, 16
  li x1, 0x21212121
  li x2, 0xffffffc0
  srl x30, x1, x2
  li x29, 0x21212121
  bne x30, x29, fail

test17:
  li a1, 17
  li x1, 0x21212121
  li x2, 0xffffffc1
  srl x30, x1, x2
  li x29, 0x10909090
  bne x30, x29, fail

test18:
  li a1, 18
  li x1, 0x21212121
  li x2, 0xffffffc7
  srl x30, x1, x2
  li x29, 0x00424242
  bne x30, x29, fail

test19:
  li a1, 19
  li x1, 0x21212121
  li x2, 0xffffffce
  srl x30, x1, x2
  li x29, 0x00008484
  bne x30, x29, fail

test20:
  li a1, 20
  li x1, 0x21212121
  li x2, 0xffffffff
  srl x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test21:
  li a1, 21
  li x1, 0x80000000
  li x2, 0x00000007
  srl x1, x1, x2
  li x29, 0x01000000
  bne x1, x29, fail

test22:
  li a1, 22
  li x1, 0x80000000
  li x2, 0x0000000e
  srl x2, x1, x2
  li x29, 0x00020000
  bne x2, x29, fail

test23:
  li a1, 23
  li x1, 0x00000007
  srl x1, x1, x1
  li x29, 0x00000000
  bne x1, x29, fail

test24:
  li a1, 24
  li x4, 0
label_test24:
  li x1, 0x80000000
  li x2, 0x00000007
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test24
  li x29, 0x01000000
  bne x6, x29, fail

test25:
  li a1, 25
  li x4, 0
label_test25:
  li x1, 0x80000000
  li x2, 0x0000000e
  srl x30, x1, x2
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test25
  li x29, 0x00020000
  bne x6, x29, fail

test26:
  li a1, 26
  li x4, 0
label_test26:
  li x1, 0x80000000
  li x2, 0x0000001f
  srl x30, x1, x2
  nop
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test26
  li x29, 0x00000001
  bne x6, x29, fail

test27:
  li a1, 27
  li x4, 0
label_test27:
  li x1, 0x80000000
  li x2, 0x00000007
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test27
  li x29, 0x01000000
  bne x6, x29, fail

test28:
  li a1, 28
  li x4, 0
label_test28:
  li x1, 0x80000000
  li x2, 0x0000000e
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test28
  li x29, 0x00020000
  bne x6, x29, fail

test29:
  li a1, 29
  li x4, 0
label_test29:
  li x1, 0x80000000
  li x2, 0x0000001f
  nop
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test29
  li x29, 0x00000001
  bne x6, x29, fail

test30:
  li a1, 30
  li x4, 0
label_test30:
  li x1, 0x80000000
  nop
  li x2, 0x00000007
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test30
  li x29, 0x01000000
  bne x6, x29, fail

test31:
  li a1, 31
  li x4, 0
label_test31:
  li x1, 0x80000000
  nop
  li x2, 0x0000000e
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test31
  li x29, 0x00020000
  bne x6, x29, fail

test32:
  li a1, 32
  li x4, 0
label_test32:
  li x1, 0x80000000
  nop
  nop
  li x2, 0x0000001f
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test32
  li x29, 0x00000001
  bne x6, x29, fail

test33:
  li a1, 33
  li x4, 0
label_test33:
  li x2, 0x00000007
  li x1, 0x80000000
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test33
  li x29, 0x01000000
  bne x6, x29, fail

test34:
  li a1, 34
  li x4, 0
label_test34:
  li x2, 0x0000000e
  nop
  li x1, 0x80000000
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test34
  li x29, 0x00020000
  bne x6, x29, fail

test35:
  li a1, 35
  li x4, 0
label_test35:
  li x2, 0x0000001f
  nop
  nop
  li x1, 0x80000000
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test35
  li x29, 0x00000001
  bne x6, x29, fail

test36:
  li a1, 36
  li x4, 0
label_test36:
  li x2, 0x00000007
  li x1, 0x80000000
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test36
  li x29, 0x01000000
  bne x6, x29, fail

test37:
  li a1, 37
  li x4, 0
label_test37:
  li x2, 0x0000000e
  nop
  li x1, 0x80000000
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test37
  li x29, 0x00020000
  bne x6, x29, fail

test38:
  li a1, 38
  li x4, 0
label_test38:
  li x2, 0x0000001f
  li x1, 0x80000000
  nop
  nop
  srl x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test38
  li x29, 0x00000001
  bne x6, x29, fail

test39:
  li a1, 39
  li x1, 0x0000000f
  srl x2, x0, x1
  li x29, 0x00000000
  bne x2, x29, fail

test40:
  li a1, 40
  li x1, 0x00000020
  srl x2, x1, x0
  li x29, 0x00000020
  bne x2, x29, fail

test41:
  li a1, 41
  srl x1, x0, x0
  li x29, 0x00000000
  bne x1, x29, fail

test42:
  li a1, 42
  li x1, 0x00000400
  li x2, 0x00000800
  srl x0, x1, x2
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
