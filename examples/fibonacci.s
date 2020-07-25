#####################
# Fibonnaci Example #
#####################

.globl __start

.rodata
  msg1: .string "Please enter a number: "
  msg2: .string "The "
  msg3: .string " fibonnaci number is: "

.text

__start:
  li t0, 0
  li t1, 1
  # prints msg1
  li a7, 4
  la a0, msg1
  ecall
  # reads an int and moves it to register t3
  li a7, 5
  ecall
  mv t3, a0
  # prints a newline
  li a7, 11
  li a0, '\n'
  ecall
  # prints msg2
  li a7, 4
  la a0, msg2
  ecall
  # prints the int value in t3
  li a7, 1
  mv a0, t3
  ecall
  # fibonnaci program
fib:
  beq t3, zero, finish
  add t2, t1, t0
  mv t0, t1
  mv t1, t2
  addi t3, t3, -1
  j fib
finish:
  # prints msg3
  li a7, 4
  la a0, msg3
  ecall
  # prints the result in t0
  li a7, 1
  mv a0, t0
  ecall
  # ends the program with status code 0
  li a7, 10
  ecall
