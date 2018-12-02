######################
#     frecoding      #
######################

.data

minf: .float 0xff800000
three: .float 3.0

.globl main

.text

main:
  flw f0, minf, a0
  flw f1, three, a0
  fmul.s f1, f1, f0

test01:
  li a1, 1
  feq.s a0, f0, f1
  li x29, 0x00000001
  bne a0, x29, fail

test02:
  li a1, 2
  fle.s a0, f0, f1
  li x29, 0x00000001
  bne a0, x29, fail

test03:
  li a1, 3
  flt.s a0, f0, f1
  li x29, 0x00000000
  bne a0, x29, fail

  fcvt.s.w f0, x0
  li a0, 1
  fcvt.s.w f1, a0
  fmul.s f1, f1, f0

test04:
  li a1, 4
  feq.s a0, f0, f1
  li x29, 0x00000001
  bne a0, x29, fail

test05:
  li a1, 5
  fle.s a0, f0, f1
  li x29, 0x00000001
  bne a0, x29, fail

test06:
  li a1, 6
  flt.s a0, f0, f1
  li x29, 0x00000000
  bne a0, x29, fail

success:
  li a0, 10
  ecall

fail:
  li a0, 17
  ecall
