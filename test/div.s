######################
#     div TESTS      #
######################

.globl main

.text

main:

test01:
  li a1, 1
  li x1, 0x00000014
  li x2, 0x00000006
  div x30, x1, x2
  li x29, 0x00000003
  bne x30, x29, fail

test02:
  li a1, 2
  li x1, 0xffffffec
  li x2, 0x00000006
  div x30, x1, x2
  li x29, 0xfffffffd
  bne x30, x29, fail

test03:
  li a1, 3
  li x1, 0x00000014
  li x2, 0xfffffffa
  div x30, x1, x2
  li x29, 0xfffffffd
  bne x30, x29, fail

test04:
  li a1, 4
  li x1, 0xffffffec
  li x2, 0xfffffffa
  div x30, x1, x2
  li x29, 0x00000003
  bne x30, x29, fail

test05:
  li a1, 5
  li x1, 0x80000000
  li x2, 0x00000001
  div x30, x1, x2
  li x29, 0x80000000
  bne x30, x29, fail

test06:
  li a1, 6
  li x1, 0x80000000
  li x2, 0xffffffff
  div x30, x1, x2
  li x29, 0x80000000
  bne x30, x29, fail

test07:
  li a1, 7
  li x1, 0x80000000
  li x2, 0x00000000
  div x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test08:
  li a1, 8
  li x1, 0x00000001
  li x2, 0x00000000
  div x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

test09:
  li a1, 9
  li x1, 0x00000000
  li x2, 0x00000000
  div x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
