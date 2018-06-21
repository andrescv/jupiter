.globl main

.text

main:
  li x1, 0x0ff00ff0
  li x2, 0xf0f0f0f0
  and x2, x1, x2
  li x29, 0x00f000f0
  bne x2, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall