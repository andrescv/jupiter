######################
#     fence TESTS    #
######################

.globl __start

.text

__start:

test:
  fence

success:
  li a7, 10
  ecall
