######################
#     mul TESTS      #
######################

.globl __start

.text

__start:

test31:
  li a1, 31
  li x1, 0x00007e00
  li x2, 0xb6db6db7
  mul x30, x1, x2
  li x29, 0x00001200
  bne x30, x29, fail

test32:
  li a1, 32
  li x1, 0x00007fc0
  li x2, 0xb6db6db7
  mul x30, x1, x2
  li x29, 0x00001240
  bne x30, x29, fail

test01:
  li a1, 1
  li x1, 0x00000000
  li x2, 0x00000000
  mul x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0x00000001
  li x2, 0x00000001
  mul x30, x1, x2
  li x29, 0x00000001
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x00000003
  li x2, 0x00000007
  mul x30, x1, x2
  li x29, 0x00000015
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0x00000000
  li x2, 0xffff8000
  mul x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0x80000000
  li x2, 0x00000000
  mul x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test06:
  li a1, 6
  li x1, 0x80000000
  li x2, 0xffff8000
  mul x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test29:
  li a1, 29
  li x1, 0xaaaaaaab
  li x2, 0x0002fe7d
  mul x30, x1, x2
  li x29, 0x0000ff7f
  bne x30, x29, fail

test30:
  li a1, 30
  li x1, 0x0002fe7d
  li x2, 0xaaaaaaab
  mul x30, x1, x2
  li x29, 0x0000ff7f
  bne x30, x29, fail

test33:
  li a1, 33
  li x1, 0xff000000
  li x2, 0xff000000
  mul x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test34:
  li a1, 34
  li x1, 0xffffffff
  li x2, 0xffffffff
  mul x30, x1, x2
  li x29, 0x00000001
  bne x30, x29, fail

test35:
  li a1, 35
  li x1, 0xffffffff
  li x2, 0x00000001
  mul x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test36:
  li a1, 36
  li x1, 0x00000001
  li x2, 0xffffffff
  mul x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test07:
  li a1, 7
  li x1, 0x0000000d
  li x2, 0x0000000b
  mul x1, x1, x2
  li x29, 0x0000008f
  bne x1, x29, fail

test08:
  li a1, 8
  li x1, 0x0000000e
  li x2, 0x0000000b
  mul x2, x1, x2
  li x29, 0x0000009a
  bne x2, x29, fail

test09:
  li a1, 9
  li x1, 0x0000000d
  mul x1, x1, x1
  li x29, 0x000000a9
  bne x1, x29, fail

test10:
  li a1, 10
  li x4, 0
label_test10:
  li x1, 0x0000000d
  li x2, 0x0000000b
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test10
  li x29, 0x0000008f
  bne x6, x29, fail

test11:
  li a1, 11
  li x4, 0
label_test11:
  li x1, 0x0000000e
  li x2, 0x0000000b
  mul x30, x1, x2
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test11
  li x29, 0x0000009a
  bne x6, x29, fail

test12:
  li a1, 12
  li x4, 0
label_test12:
  li x1, 0x0000000f
  li x2, 0x0000000b
  mul x30, x1, x2
  nop
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test12
  li x29, 0x000000a5
  bne x6, x29, fail

test13:
  li a1, 13
  li x4, 0
label_test13:
  li x1, 0x0000000d
  li x2, 0x0000000b
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test13
  li x29, 0x0000008f
  bne x6, x29, fail

test14:
  li a1, 14
  li x4, 0
label_test14:
  li x1, 0x0000000e
  li x2, 0x0000000b
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test14
  li x29, 0x0000009a
  bne x6, x29, fail

test15:
  li a1, 15
  li x4, 0
label_test15:
  li x1, 0x0000000f
  li x2, 0x0000000b
  nop
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test15
  li x29, 0x000000a5
  bne x6, x29, fail

test16:
  li a1, 16
  li x4, 0
label_test16:
  li x1, 0x0000000d
  nop
  li x2, 0x0000000b
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test16
  li x29, 0x0000008f
  bne x6, x29, fail

test17:
  li a1, 17
  li x4, 0
label_test17:
  li x1, 0x0000000e
  nop
  li x2, 0x0000000b
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test17
  li x29, 0x0000009a
  bne x6, x29, fail

test18:
  li a1, 18
  li x4, 0
label_test18:
  li x1, 0x0000000f
  nop
  nop
  li x2, 0x0000000b
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test18
  li x29, 0x000000a5
  bne x6, x29, fail

test19:
  li a1, 19
  li x4, 0
label_test19:
  li x2, 0x0000000b
  li x1, 0x0000000d
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test19
  li x29, 0x0000008f
  bne x6, x29, fail

test20:
  li a1, 20
  li x4, 0
label_test20:
  li x2, 0x0000000b
  nop
  li x1, 0x0000000e
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test20
  li x29, 0x0000009a
  bne x6, x29, fail

test21:
  li a1, 21
  li x4, 0
label_test21:
  li x2, 0x0000000b
  nop
  nop
  li x1, 0x0000000f
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test21
  li x29, 0x000000a5
  bne x6, x29, fail

test22:
  li a1, 22
  li x4, 0
label_test22:
  li x2, 0x0000000b
  li x1, 0x0000000d
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test22
  li x29, 0x0000008f
  bne x6, x29, fail

test23:
  li a1, 23
  li x4, 0
label_test23:
  li x2, 0x0000000b
  nop
  li x1, 0x0000000e
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test23
  li x29, 0x0000009a
  bne x6, x29, fail

test24:
  li a1, 24
  li x4, 0
label_test24:
  li x2, 0x0000000b
  li x1, 0x0000000f
  nop
  nop
  mul x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test24
  li x29, 0x000000a5
  bne x6, x29, fail

test25:
  li a1, 25
  li x1, 0x0000001f
  mul x2, x0, x1
  li x29, 0x00000000
  bne x2, x29, fail

test26:
  li a1, 26
  li x1, 0x00000020
  mul x2, x1, x0
  li x29, 0x00000000
  bne x2, x29, fail

test27:
  li a1, 27
  mul x1, x0, x0
  li x29, 0x00000000
  bne x1, x29, fail

test28:
  li a1, 28
  li x1, 0x00000021
  li x2, 0x00000022
  mul x0, x1, x2
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
