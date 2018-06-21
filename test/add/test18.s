.globl main

.text

main:
  li x1, 0x0000000d
  add x1, x1, x1
  li x29, 0x0000001a
  bne x1, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall