######################
#     fence TESTS    #
######################

.globl __start

.text

__start:

test:
  fence

success:
  li a0, 10
  ecall
