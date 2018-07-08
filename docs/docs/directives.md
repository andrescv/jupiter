# Supported Directives

V-Sim supports most common assembler directives, including those indicated [here](https://github.com/riscv/riscv-asm-manual/blob/master/riscv-asm.md#pseudo-ops). Some directives like `.word` accepts an `expr` argument list, where `expr` follows this BNF grammar:

```
expr  ::= expr  +  expr # plus
       |  expr  -  expr # minus
       |  expr  *  expr # times
       |  expr  /  expr # divide
       |  expr  %  expr # remainder
       |  expr <<  expr # shift left logical
       |  expr >>> expr # shift right logical
       |  expr >>  expr # shift right arithmetic
       |  expr  &  expr # bitwise and
       |  expr  |  expr # bitwise or
       |  expr  ^  expr # bitwise xor
       |  ( expr )
       |  - expr
       |  + expr
       |  ~ expr        # bitwise not
       |  const

const ::= INTEGER     # e.g 0, 12
       |  CHARACTER   # e.g 'a', '\n'
       |  HEXADECIMAL # e.g 0xa
       |  BINARY      # e.g 0b1001
```

Also `.float` accepts a `fexpr` argument list, where `fexpr` follows this BNF grammar:

```
fexpr ::= fexpr + fexpr # plus
        | fexpr - fexpr # minus
        | fexpr * fexpr # times
        | fexpr / fexpr # divide
        | fexpr % fexpr # remainder
        | ( fexpr )
        | - fexpr
        | + fexpr
        | const

const ::= FLOAT       # e.g 3.1416
       |  HEXADECIMAL # (IEEE-754 hex representation) e.g 0x7f800000 (+inf)
       |  BINARY      # (IEEE-754 bin representation) e.g 0b0        (zero)
       |  INTEGER     # converted to float e.g 2 -> 2.0
```

***

### .p2align

Align next data item to a power of 2 byte boundary.

**Usage**

```asm
.p2align <alignval>
```

**Arguments**

* `alignval`: {0=byte, 1=half, 2=word}

**Aliases**

* `.align`

***

### .balign

Align next data item to a byte boundary.

**Usage**

```asm
.balign <alignval>
```

**Arguments**

* `alignval`: (should be > 0)

**Aliases**

* **none**

***

### .zero

Reserve the specified number of bytes.

**Usage**

```asm
.zero <expr>
```

**Arguments**

* `expr`: (should be > 0)

**Aliases**

* `.space`

***

### .section

Emit section and make current.

**Usage**

```asm
.section <section>
```

**Arguments**

* `section`: {`.text`, `.data`, `.rodata`, `.bss`}

**Aliases**

* **none**

***

### .text

Emit text section and make current.

**Usage**

```asm
.text
```

**Arguments**

* **none**

**Aliases**

* **none**

***

### .data

Emit data section and make current.

**Usage**

```asm
.data
```

**Arguments**

* **none**

**Aliases**

* **none**

***

### .rodata

Emit read-only data section and make current.

**Usage**

```asm
.rodata
```

**Arguments**

* **none**

**Aliases**

* **none**

***

### .bss

Emit bss section and make current.

**Usage**

```asm
.bss
```

**Arguments**

* **none**

**Aliases**

* **none**

***

### .globl

Store the listed symbol(s) to symbol table (scope `GLOBAL`).

**Usage**

```asm
.globl symbol [, symbol]*
```

**Arguments**

* `list`: comma separated symbols

**Aliases**

* `.global`

***

### .string

Store the string and add null terminator.

**Usage**

```asm
.string <string>
```

**Arguments**

* `string`: quoted string

**Aliases**

* `.asciiz`, `.asciz`

***

### .byte

Store the listed value(s) as 8 bit bytes.

**Usage**

```asm
.byte expr [, expr]*
```

**Arguments**

* `list`: 8-bit comma separated words

**Aliases**

* **none**

***

### .half

Store the listed value(s) as 16 bit halfwords.

**Usage**

```asm
.half expr [, expr]*
```

**Arguments**

* `list`: 16-bit comma separated words

**Aliases**

* `.short`, `.2byte`

***

### .word

Store the listed value(s) as 32 bit words.

**Usage**

```asm
.word expr [, expr]*
```

**Arguments**

* `list`: 32-bit comma separated words

**Aliases**

* `.long`, `.4byte`

***

### .float

Store the listed value(s) as 32 bit float values.

**Usage**

```asm
.float fexpr [, fexpr]*
```

**Arguments**

* `list`: 32-bit comma separated float words

**Aliases**

* **none**
