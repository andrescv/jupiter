######################
#     CSR TESTS      #
######################

.globl __start

.text

__start:

test:
  csrrw x1, 0, x2
  csrrs x1, 0, x2
  csrrc x1, 0, x2
  csrrwi x1, 0, 0
  csrrsi x1, 0, 0
  csrrci x1, 0, 0

success:
  li a7, 10
  ecall
