.globl main

.text

main:
  li x1, 0x00000010
  li x2, 0x0000001e
  sltu x0, x1, x2
  li x29, 0x00000000
  bne x0, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall