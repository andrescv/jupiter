.globl main

.text

main:
  li x4, 0
l1:
  li x2, 0x0000000e
  nop
  li x1, 0x80000000
  sra x30, x1, x2
  addi x6, x30, 0
  addi x4, x4, 1
  li x5, 2
  bne x4, x5, l1
  li x29, 0xfffe0000
  bne x6, x29, fail

sucess:
  li a0, 10
  ecall

fail:
  li a0, 17
  li a1, 1
  ecall