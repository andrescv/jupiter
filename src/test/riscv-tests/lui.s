######################
#     lui TESTS      #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  lui x1, 0x00000
  li x29, 0x00000000
  bne x1, x29, fail

test02:
  li a1, 2
  lui x1, 0xfffff
  srai x1,x1,1
  li x29, 0xfffff800
  bne x1, x29, fail

test03:
  li a1, 3
  lui x1, 0x7ffff
  srai x1,x1,20
  li x29, 0x000007ff
  bne x1, x29, fail

test04:
  li a1, 4
  lui x1, 0x80000
  srai x1,x1,20
  li x29, 0xfffff800
  bne x1, x29, fail

test05:
  li a1, 5
  lui x0, 0x80000
  li x29, 0x00000000
  bne x0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
