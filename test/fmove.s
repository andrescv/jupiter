######################
#       fmove        #
######################

.globl main

.text

main:

test09:
  li a1, 9
  li a3, 0x12345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnj.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

test10:
  li a1, 10
  li a3, 0x12345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnj.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test11:
  li a1, 11
  li a3, 0x92345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnj.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

test12:
  li a1, 12
  li a3, 0x92345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnj.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test19:
  li a1, 19
  li a3, 0x12345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjn.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test20:
  li a1, 20
  li a3, 0x12345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjn.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

test21:
  li a1, 21
  li a3, 0x92345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjn.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test22:
  li a1, 22
  li a3, 0x92345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjn.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

test29:
  li a1, 29
  li a3, 0x12345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjx.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

test30:
  li a1, 30
  li a3, 0x12345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjx.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test31:
  li a1, 31
  li a3, 0x92345678
  li a2, 0x00000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjx.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x92345678
  bne a0, x29, fail

test32:
  li a1, 32
  li a3, 0x92345678
  li a2, 0x80000000
  fmv.s.x f1, a3
  fmv.s.x f2, a2
  fsgnjx.s f0, f1, f2
  fmv.x.s a0, f0
  li x29, 0x12345678
  bne a0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
