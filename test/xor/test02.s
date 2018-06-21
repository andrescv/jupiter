.globl main

.text

main:
  li x1, 0xff00ff00
  li x2, 0x0f0f0f0f
  xor x30, x1, x2
  li x29, 0xf00ff00f
  bne x30, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall