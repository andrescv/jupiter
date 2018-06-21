.globl main

.text

main:
  li x1, 0x7fffffff
  li x2, 0x0000000e
  sra x30, x1, x2
  li x29, 0x0001ffff
  bne x30, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall