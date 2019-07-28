######################
#     addi TESTS     #
######################

.globl __start

.text

__start:

test:
  li a0, 1
  li a2, 0xa
  ecall

success:
  li a0, 10
  ecall
