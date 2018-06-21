.globl main

.text

main:
  li x1, 0xf00ff00f
  li x2, 0xf0f0f0f0
  and x30, x1, x2
  li x29, 0xf000f000
  bne x30, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall