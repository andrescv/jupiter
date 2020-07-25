######################
#  Relocation TESTS  #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  lui x1, %hi(test01)
  addi x1, x1, %lo(test01)
  la x2, test01
  bne x1, x2, fail
  auipc x1, %pcrel_hi(test01)
  addi x1, x1, %pcrel_lo(test01)
  bne x1, x2, fail

success:
  li a7, 10
  ecall

fail:
  li a7, 17
  ecall
