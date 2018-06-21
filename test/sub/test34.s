.globl main

.text

main:
  li x1, 0xfffffff1
  sub x2, x0, x1
  li x29, 0x0000000f
  bne x2, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall