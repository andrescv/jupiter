.globl main

.text

main:
  srl x1, x0, x0
  li x29, 0x00000000
  bne x1, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall