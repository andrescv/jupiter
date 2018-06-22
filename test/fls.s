######################
#        fls         #
######################

.data

tdat:
  .word 0xbf800000
  .word 0x40000000
  .word 0x40400000
  .word 0xc0800000
  .word 0xdeadbeef
  .word 0xcafebabe
  .word 0xabad1dea
  .word 0x1337d00d

.globl main

.text

main:

test01:
  li a1, 1
  la a3, tdat
  flw f1, 4(a3)
  fsw f1, 20(a3)
  lw a0, 20(a3)
  li x29, 0x40000000
  bne a0, x29, fail

test02:
  li a1, 2
  la a3, tdat
  flw f1, 0(a3)
  fsw f1, 24(a3)
  lw a0, 24(a3)
  li x29, 0xbf800000
  bne a0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
