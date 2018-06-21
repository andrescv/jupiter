.globl main

.text

main:
  li x1, 0x00ff00ff
  or x2, x1, x0
  li x29, 0x00ff00ff
  bne x2, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall