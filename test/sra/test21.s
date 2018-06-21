.globl main

.text

main:
  li x1, 0x81818181
  li x2, 0xffffffff
  sra x30, x1, x2
  li x29, 0xffffffff
  bne x30, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall