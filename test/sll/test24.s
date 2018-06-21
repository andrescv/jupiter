.globl main

.text

main:
  li x1, 0x00000003
  sll x1, x1, x1
  li x29, 0x00000018
  bne x1, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall