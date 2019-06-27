######################
#    auipc TESTS     #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li x4, 0
  li x5, 1
label1_test01:
  nop
  nop
  nop
  beq x4, x5, label2_test01
  li x4, 1
  tail label1_test01
label2_test01:
  call label3_test01
  j success
  nop
  nop
  nop
label3_test01:
  nop
  nop
  ret

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
