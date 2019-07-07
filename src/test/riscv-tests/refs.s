######################
#  References TESTS  #
######################

.globl __start

.data
  addr1: .word success

.rodata
  addr2: .word fail

.text

__start:

test01:
  li a1, 1
  la x1, success
  la x2, fail
  lw x3, addr1
  lw x4, addr2
  bne x1, x3, fail
  bne x2, x4, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
