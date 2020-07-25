######################
#     addi TESTS     #
######################

.globl __start

.text

__start:

test:
  li a7, 1
  li a0, 0xa
  ecall

success:
  li a7, 10
  ecall
