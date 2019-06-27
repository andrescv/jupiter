######################
#     jalr TESTS     #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li t0, 0
  la t1, target_2
  jalr t0, t1, 0
linkaddr_2:
  j fail
target_2:
  la  t1, linkaddr_2
  bne t0, t1, fail

test02:
  li a1, 2
  li x4, 0
label1_test02:
  la x6, label2_test02
  jalr x19, x6, 0
  bne x0, a1, fail
label2_test02:
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test02

test03:
  li a1, 3
  li x4, 0
label1_test03:
  la x6, label2_test03
  nop
  jalr x19, x6, 0
  bne x0, a1, fail
label2_test03:
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test03

test04:
  li a1, 4
  li x4, 0
label1_test04:
  la x6, label2_test04
  nop
  nop
  jalr x19, x6, 0
  bne x0, a1, fail
label2_test04:
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, label1_test04

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
