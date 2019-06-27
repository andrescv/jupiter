######################
#      or TESTS      #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  li x29, 0xff0fff0f
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0x0ff00ff0
  li x2, 0xf0f0f0f0
  or x30, x1, x2
  li x29, 0xfff0fff0
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x00ff00ff
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  li x29, 0x0fff0fff
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0xf00ff00f
  li x2, 0xf0f0f0f0
  or x30, x1, x2
  li x29, 0xf0fff0ff
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  or x1, x1, x2
  li x29, 0xff0fff0f
  bne x1, x29, fail

test06:
  li a1, 6
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  or x2, x1, x2
  li x29, 0xff0fff0f
  bne x2, x29, fail

test07:
  li a1, 7
  li x1, 0xff00ff00
  or x1, x1, x1
  li x29, 0xff00ff00
  bne x1, x29, fail

test08:
  li a1, 8
  li x4, 0
label_test08:
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test08
  li x29, 0xff0fff0f
  bne x6, x29, fail

test09:
  li a1, 9
  li x4, 0
label_test09:
  li x1, 0x0ff00ff0
  li x2, 0xf0f0f0f0
  or x30, x1, x2
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test09
  li x29, 0xfff0fff0
  bne x6, x29, fail

test10:
  li a1, 10
  li x4, 0
label_test10:
  li x1, 0x00ff00ff
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  nop
  nop
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test10
  li x29, 0x0fff0fff
  bne x6, x29, fail

test11:
  li a1, 11
  li x4, 0
label_test11:
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test11
  li x29, 0xff0fff0f
  bne x6, x29, fail

test12:
  li a1, 12
  li x4, 0
label_test12:
  li x1, 0x0ff00ff0
  li x2, 0xf0f0f0f0
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test12
  li x29, 0xfff0fff0
  bne x6, x29, fail

test13:
  li a1, 13
  li x4, 0
label_test13:
  li x1, 0x00ff00ff
  li x2, 0x0f0f0f0f
  nop
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test13
  li x29, 0x0fff0fff
  bne x6, x29, fail

test14:
  li a1, 14
  li x4, 0
label_test14:
  li x1, 0xff00ff00
  nop
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test14
  li x29, 0xff0fff0f
  bne x6, x29, fail

test15:
  li a1, 15
  li x4, 0
label_test15:
  li x1, 0x0ff00ff0
  nop
  li x2, 0xf0f0f0f0
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test15
  li x29, 0xfff0fff0
  bne x6, x29, fail

test16:
  li a1, 16
  li x4, 0
label_test16:
  li x1, 0x00ff00ff
  nop
  nop
  li x2, 0x0f0f0f0f
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test16
  li x29, 0x0fff0fff
  bne x6, x29, fail

test17:
  li a1, 17
  li x4, 0
label_test17:
  li x2, 0x0f0f0f0f
  li x1, 0xff00ff00
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test17
  li x29, 0xff0fff0f
  bne x6, x29, fail

test18:
  li a1, 18
  li x4, 0
label_test18:
  li x2, 0xf0f0f0f0
  nop
  li x1, 0x0ff00ff0
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test18
  li x29, 0xfff0fff0
  bne x6, x29, fail

test19:
  li a1, 19
  li x4, 0
label_test19:
  li x2, 0x0f0f0f0f
  nop
  nop
  li x1, 0x00ff00ff
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test19
  li x29, 0x0fff0fff
  bne x6, x29, fail

test20:
  li a1, 20
  li x4, 0
label_test20:
  li x2, 0x0f0f0f0f
  li x1, 0xff00ff00
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test20
  li x29, 0xff0fff0f
  bne x6, x29, fail

test21:
  li a1, 21
  li x4, 0
label_test21:
  li x2, 0xf0f0f0f0
  nop
  li x1, 0x0ff00ff0
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test21
  li x29, 0xfff0fff0
  bne x6, x29, fail

test22:
  li a1, 22
  li x4, 0
label_test22:
  li x2, 0x0f0f0f0f
  li x1, 0x00ff00ff
  nop
  nop
  or x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label_test22
  li x29, 0x0fff0fff
  bne x6, x29, fail

test23:
  li a1, 23
  li x1, 0xff00ff00
  or x2, x0, x1
  li x29, 0xff00ff00
  bne x2, x29, fail

test24:
  li a1, 24
  li x1, 0x00ff00ff
  or x2, x1, x0
  li x29, 0x00ff00ff
  bne x2, x29, fail

test25:
  li a1, 25
  or x1, x0, x0
  li x29, 0x00000000
  bne x1, x29, fail

test26:
  li a1, 26
  li x1, 0x11111111
  li x2, 0x22222222
  or x0, x1, x2
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
