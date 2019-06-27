######################
#     jal TESTS      #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li ra, 0
  jal x4, target_2
linkaddr_2:
  nop
  nop
  j fail
target_2:
  la  x2, linkaddr_2
  bne x2, x4, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
