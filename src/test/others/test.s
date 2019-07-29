.globl __start

__start:
  li a0, 0xbb
  call print
  li a0, 10
  ecall
