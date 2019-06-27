######################
#       fclass       #
######################

.globl __start

.text

__start:

test01:
  li a1, 1
  li a0, 0xff800000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000001
  bne a0, x29, fail

test02:
  li a1, 2
  li a0, 0xbf800000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000002
  bne a0, x29, fail

test03:
  li a1, 3
  li a0, 0x807fffff
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000004
  bne a0, x29, fail

test04:
  li a1, 4
  li a0, 0x80000000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000008
  bne a0, x29, fail

test05:
  li a1, 5
  li a0, 0x00000000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000010
  bne a0, x29, fail

test06:
  li a1, 6
  li a0, 0x007fffff
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000020
  bne a0, x29, fail

test07:
  li a1, 7
  li a0, 0x3f800000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000040
  bne a0, x29, fail

test08:
  li a1, 8
  li a0, 0x7f800000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000080
  bne a0, x29, fail

test09:
  li a1, 9
  li a0, 0x7f800001
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000100
  bne a0, x29, fail

test10:
  li a1, 10
  li a0, 0x7fc00000
  fmv.w.x fa0, a0
  fclass.s a0, fa0
  li x29, 0x00000200
  bne a0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
