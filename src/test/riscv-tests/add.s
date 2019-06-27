######################
#     add TESTS      #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x1, 0x00000000
  li x2, 0x00000000
  add x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0x00000001
  li x2, 0x00000001
  add x30, x1, x2
  li x29, 0x00000002
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x00000003
  li x2, 0x00000007
  add x30, x1, x2
  li x29, 0x0000000a
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0x00000000
  li x2, 0xffff8000
  add x30, x1, x2
  li x29, 0xffff8000
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0x80000000
  li x2, 0x00000000
  add x30, x1, x2
  li x29, 0x80000000
  bne x30, x29, fail

test06:
  li a1, 6
  li x1, 0x80000000
  li x2, 0xffff8000
  add x30, x1, x2
  li x29, 0x7fff8000
  bne x30, x29, fail

test07:
  li a1, 7
  li x1, 0x00000000
  li x2, 0x00007fff
  add x30, x1, x2
  li x29, 0x00007fff
  bne x30, x29, fail

test08:
  li a1, 8
  li x1, 0x7fffffff
  li x2, 0x00000000
  add x30, x1, x2
  li x29, 0x7fffffff
  bne x30, x29, fail

test09:
  li a1, 9
  li x1, 0x7fffffff
  li x2, 0x00007fff
  add x30, x1, x2
  li x29, 0x80007ffe
  bne x30, x29, fail

test10:
  li a1, 10
  li x1, 0x80000000
  li x2, 0x00007fff
  add x30, x1, x2
  li x29, 0x80007fff
  bne x30, x29, fail

test11:
  li a1, 11
  li x1, 0x7fffffff
  li x2, 0xffff8000
  add x30, x1, x2
  li x29, 0x7fff7fff
  bne x30, x29, fail

test12:
  li a1, 12
  li x1, 0x00000000
  li x2, 0xffffffff
  add x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test13:
  li a1, 13
  li x1, 0xffffffff
  li x2, 0x00000001
  add x30, x1, x2
  li x29, 0x00000000
  bne x30, x29, fail

test14:
  li a1, 14
  li x1, 0xffffffff
  li x2, 0xffffffff
  add x30, x1, x2
  li x29, 0xfffffffe
  bne x30, x29, fail

test15:
  li a1, 15
  li x1, 0x00000001
  li x2, 0x7fffffff
  add x30, x1, x2
  li x29, 0x80000000
  bne x30, x29, fail

test16:
  li a1, 16
  li x1, 0x0000000d
  li x2, 0x0000000b
  add x1, x1, x2
  li x29, 0x00000018
  bne x1, x29, fail

test17:
  li a1, 17
  li x1, 0x0000000e
  li x2, 0x0000000b
  add x2, x1, x2
  li x29, 0x00000019
  bne x2, x29, fail

test18:
  li a1, 18
  li x1, 0x0000000d
  add x1, x1, x1
  li x29, 0x0000001a
  bne x1, x29, fail

test19:
  li a1, 19
  li x4, 0
label_test19:
  li x1, 0x0000000d
  li x2, 0x0000000b
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test19
  li x29, 0x00000018
  bne x6, x29, fail

test20:
  li a1, 20
  li x4, 0
label_test20:
  li x1, 0x0000000e
  li x2, 0x0000000b
  add x30, x1, x2
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test20
  li x29, 0x00000019
  bne x6, x29, fail

test21:
  li a1, 21
  li x4, 0
label_test21:
  li x1, 0x0000000f
  li x2, 0x0000000b
  add x30, x1, x2
  nop
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test21
  li x29, 0x0000001a
  bne x6, x29, fail

test22:
  li a1, 22
  li x4, 0
label_test22:
  li x1, 0x0000000d
  li x2, 0x0000000b
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test22
  li x29, 0x00000018
  bne x6, x29, fail

test23:
  li a1, 23
  li x4, 0
label_test23:
  li x1, 0x0000000e
  li x2, 0x0000000b
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test23
  li x29, 0x00000019
  bne x6, x29, fail

test24:
  li a1, 24
  li x4, 0
label_test24:
  li x1, 0x0000000f
  li x2, 0x0000000b
  nop
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test24
  li x29, 0x0000001a
  bne x6, x29, fail

test25:
  li a1, 25
  li x4, 0
label_test25:
  li x1, 0x0000000d
  nop
  li x2, 0x0000000b
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test25
  li x29, 0x00000018
  bne x6, x29, fail

test26:
  li a1, 26
  li x4, 0
label_test26:
  li x1, 0x0000000e
  nop
  li x2, 0x0000000b
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test26
  li x29, 0x00000019
  bne x6, x29, fail

test27:
  li a1, 27
  li x4, 0
label_test27:
  li x1, 0x0000000f
  nop
  nop
  li x2, 0x0000000b
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test27
  li x29, 0x0000001a
  bne x6, x29, fail

test28:
  li a1, 28
  li x4, 0
label_test28:
  li x2, 0x0000000b
  li x1, 0x0000000d
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test28
  li x29, 0x00000018
  bne x6, x29, fail

test29:
  li a1, 29
  li x4, 0
label_test29:
  li x2, 0x0000000b
  nop
  li x1, 0x0000000e
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test29
  li x29, 0x00000019
  bne x6, x29, fail

test30:
  li a1, 30
  li x4, 0
label_test30:
  li x2, 0x0000000b
  nop
  nop
  li x1, 0x0000000f
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test30
  li x29, 0x0000001a
  bne x6, x29, fail

test31:
  li a1, 31
  li x4, 0
label_test31:
  li x2, 0x0000000b
  li x1, 0x0000000d
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test31
  li x29, 0x00000018
  bne x6, x29, fail

test32:
  li a1, 32
  li x4, 0
label_test32:
  li x2, 0x0000000b
  nop
  li x1, 0x0000000e
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test32
  li x29, 0x00000019
  bne x6, x29, fail

test33:
  li a1, 33
  li x4, 0
label_test33:
  li x2, 0x0000000b
  li x1, 0x0000000f
  nop
  nop
  add x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test33
  li x29, 0x0000001a
  bne x6, x29, fail

test34:
  li a1, 34
  li x1, 0x0000000f
  add x2, x0, x1
  li x29, 0x0000000f
  bne x2, x29, fail

test35:
  li a1, 35
  li x1, 0x00000020
  add x2, x1, x0
  li x29, 0x00000020
  bne x2, x29, fail

test36:
  li a1, 36
  add x1, x0, x0
  li x29, 0x00000000
  bne x1, x29, fail

test37:
  li a1, 37
  li x1, 0x00000010
  li x2, 0x0000001e
  add x0, x1, x2
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
