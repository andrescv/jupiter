### add

Suma el registro $\texttt{x[rs2]}$ al registro $\texttt{x[rs1]}$ y escribe el resultado en $\texttt{x[rd]}$. Overflow artimético ignorado.

* _Add_
* **Operación**: $\texttt{x[rd] = x[rs1] + x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
add rd, rs1, rs2
```

***

### addi

Suma el inmediato sign-extended al registro $\texttt{x[rs1]}$ y escribe el resultado en $\texttt{x[rd]}$. Overflow
aritmético ignorado.

* _Add Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] + sext(immediate)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
addi rd, rs1, immediate
```

***

### and

Calcula el AND a nivel de bits de los registros $\texttt{x[rs1]}$ y $\texttt{x[rs2]}$ y escribe el resultado en $\texttt{x[rd]}$.

* _AND_
* **Operación**: $\texttt{x[rd] = x[rs1] \& x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
and rd, rs1, rs2
```

***

### andi

Calcula el AND a nivel de bits del inmediato sign-extended y el registro $\texttt{x[rs1]}$ y escribe el
resultado en $\texttt{x[rd]}$.

* _AND Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] \& sext(immediate)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
andi rd, rs1, immediate
```

***

### auipc

Suma el inmediato sign-extended de 20 bits, corrido a la izquierda por 12 bits, al $\texttt{pc}$, y escribe
el resultado en $\texttt{x[rd]}$.

* _AND Immediate_
* **Operación**: $\texttt{x[rd] = pc + sext(immediate[31:12] << 12)}$
* **Tipo**: $\texttt{U}$

**Uso**

```asm
auipc rd, immediate
```

***

### beq

Si el registro $\texttt{x[rs1]}$ es igual al registro $\texttt{x[rs2]}$, asignar al $\texttt{pc}$ su valor actual más el $\texttt{offset}$ sign-extended.

* _Branch if Equal_
* **Operación**: $\texttt{if (rs1 == rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
beq rs1, rs2, offset
```

***

### bge

Si el registro $\texttt{x[rs1]}$ es por lo menos $\texttt{x[rs2]}$, tratando los valores como números de complemento a dos, asignar al $\texttt{pc}$ su valor actual más el offset sign-extended.

* _Branch if Greater Than or Equal_
* **Operación**: $\texttt{if (rs1 ≥s rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
bge rs1, rs2, offset
```

***

### bgeu

Si el registro $\texttt{x[rs1]}$ es por lo menos $\texttt{x[rs2]}$, tratando los valores como números sin signo, asignar al $\texttt{pc}$ su valor actual más el offset sign-extended.

* _Branch if Greater Than or Equal, Unsigned_
* **Operación**: $\texttt{if (rs1}$ $<_u$ $\texttt{rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
bgeu rs1, rs2, offset
```

***

### blt

Si el registro $\texttt{x[rs1]}$ es menor que $\texttt{x[rs2]}$, tratando los valores como números de complemento a dos, asignar al $\texttt{pc}$ su valor actual más el offset sign-extended

* _Branch if Less Than_
* **Operación**: $\texttt{if (rs1 <s rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
blt rs1, rs2, offset
```

***

### bltu

Si el registro $\texttt{x[rs1]}$ es menor que $\texttt{x[rs2]}$, tratando los valores como números sin signo, asignar
al $\texttt{pc}$ su valor actual más el offset sign-extended.

* _Branch if Less Than, Unsigned_
* **Operación**: $\texttt{if (rs1}$ $<_u$ $\texttt{rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
bltu rs1, rs2, offset
```

***

### bne

Si el registro $\texttt{x[rs1]}$ no es igual al registro $\texttt{x[rs2]}$, asignar al $\texttt{pc}$ su valor actual más el offset sign-extended.

* _Branch if Not Equal_
* **Operación**: $\texttt{if (rs1}$ $\neq$ $\texttt{rs2) pc += sext(offset)}$
* **Tipo**: $\texttt{B}$

**Uso**

```asm
bne rs1, rs2, offset
```

***

### csrrc

Setea en $\texttt{t}$ el valor del control and status register $\texttt{csr}$. Escribe el $\texttt{AND}$ a nivel de bits de $\texttt{t}$ y el complemento a uno de $\texttt{x[rs1]}$ en el $\texttt{csr}$, luego escribe $\texttt{t}$ en el $\texttt{x[rd]}$.

* _Control and Status Register Read and Clear_
* **Operación**: $\texttt{t = CSRs[csr]}$;  $\texttt{CSRs[csr] = t}$ &∼$\texttt{x[rs1]}$;  $\texttt{x[rd] = t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrc rd, csr, rs1
```

***

### csrrci

Setea en $\texttt{t}$ el valor del control and status register $\texttt{csr}$. Escribe el $\texttt{AND}$ a nivel de bits de $\texttt{t}$ y el complemento a uno del inmediato de cinco bits zero-extended $\texttt{zimm}$ en el $\texttt{csr}$, luego escribe $\texttt{t}$ en el $\texttt{x[rd]}$ (Los bits del 5 en adelante en el csr no son modificados).

* _Control and Status Register Read and Clear Immediate_
* **Operación**: $\texttt{t = CSRs[csr]}$;  $\texttt{CSRs[csr] = t}$ &∼$\texttt{zimm}$;  $\texttt{x[rd] = t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrci rd, csr, zimm[4:0]
```

***

### csrrs

Setea en $\texttt{t}$ el valor del control and status register $\texttt{csr}$. Escribe el $\texttt{OR}$ a nivel de bits de $\texttt{t}$ y $\texttt{x[rs1]}$ en el $\texttt{csr}$, luego escribe $\texttt{t}$ en el $\texttt{x[rd]}$.

* _Control and Status Register Read and Set_
* **Operación**: $\texttt{t = CSRs[csr]}$;  $\texttt{CSRs[csr] = t}$ | $\texttt{x[rs1]}$;  $\texttt{x[rd] = t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrs rd, csr, rs1
```

***

### csrrsi

Setea en $\texttt{t}$ el valor del control and status register $\texttt{csr}$. Escribe el $\texttt{OR}$ a nivel de bits de $\texttt{t}$ y y el inmediato de cinco bits zero-extended $\texttt{zimm}$ en el $\texttt{csr}$, luego escribe $\texttt{t}$ en el $\texttt{x[rd]}$ (Los bits del 5 en adelante en el csr no son modificados).

* _Control and Status Register Read and Set Immediate_
* **Operación**: $\texttt{t = CSRs[csr]}$;  $\texttt{CSRs[csr] = t}$ | $\texttt{zimm}$;  $\texttt{x[rd] = t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrsi rd, csr, zimm[4:0]
```

***

### csrrw

Setea en $\texttt{t}$ el valor del control and status register $\texttt{csr}$. Copia el valor de $\texttt{x[rs1]}$ en el $\texttt{csr}$, luego escribe el valor de $\texttt{t}$ en el $\texttt{x[rd]}$.

* _Control and Status Register Read and Write_
* **Operación**: $\texttt{t = CSRs[csr]}$;  $\texttt{CSRs[csr] = x[rs1]}$;  $\texttt{x[rd] = t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrw rd, csr, rs1
```

***

### csrrwi

Copia el valor del control and status register $\texttt{csr}$ en $\texttt{x[rd]}$, luego escribe el inmediato de cinco bits zero-extended $\texttt{zimm}$ en el $\texttt{csr}$.

* _Control and Status Register Read and Write Immediate_
* **Operación**: $\texttt{x[rd] = CSRs[csr]}$;  $\texttt{ CSRs[csr] = zimm}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
csrrwi rd, csr, zimm[4:0]
```

***

### div

Divide $\texttt{x[rs1]}$ entre $\texttt{x[rs2]}$, redondeando hacia cero, tratando los valores como números de complemento a dos, y escribe el cociente en $\texttt{x[rd]}$.

* _Divide_
* **Operación**: $\texttt{x[rd] = x[rs1]}$ $÷_s$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
div rd, rs1, rs2
```

***

### divu

Divide $\texttt{x[rs1]}$ entre $\texttt{x[rs2]}$, redondeando hacia cero, tratando los valores como números sin signo, y escribe el cociente en $\texttt{x[rd]}$.

* _Divide, Unsigned_
* **Operación**: $\texttt{x[rd] = x[rs1]}$ $÷_u$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
divu rd, rs1, rs2
```

***

### ebreak

Hace una petición del debugger levantando una excepción de $\texttt{Breakpoint}$.

* _Environment Breakpoint_
* **Operación**: $\texttt{RaiseException(Breakpoint)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
ebreak
```

***

### ecall

Hace una petición del ambiente de ejecución levantando una excepción de $\texttt{Environment Call}$.

* _Environment Call_
* **Operación**: $\texttt{RaiseException(EnvironmentCall)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
ecall
```

***

### fadd.s

Suma los números de punto flotante de precisión simple de los regristros $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$ y escribe la suma redondeada de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Add, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1] + f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fadd.s rd, rs1, rs2
```

***

### fclass.s

Escribe en $\texttt{x[rd]}$ una máscara que indica la clase del número de punto flotante de precisión simple en $\texttt{f[rs1]}$. Exactamente un bit en $\texttt{x[rd]}$ es puesto en $\texttt{1}$, de acuerdo a la siguiente tabla:

| Bit en $\texttt{x[rd]}$ | Significado |
| --- | --- |
| 0 | $\texttt{f[rs1]}$ es $\texttt{-}$$\infty$ |
| 1 | $\texttt{f[rs1]}$ es un número negativo normal |
| 2 | $\texttt{f[rs1]}$ es un número negativo subnormal |
| 3 | $\texttt{f[rs1]}$ es $\texttt{-}0$ |
| 4 | $\texttt{f[rs1]}$ es $\texttt{+}0$ |
| 5 | $\texttt{f[rs1]}$ es un número positivo subnormal |
| 6 | $\texttt{f[rs1]}$ es un número positivo normal |
| 7 | $\texttt{f[rs1]}$ es $\texttt{+}$$\infty$ |
| 8 | $\texttt{f[rs1]}$ es un $\texttt{NaN}$ señalizado |
| 8 | $\texttt{f[rs1]}$ es un $\texttt{NaN}$ simple |

* _Floating-point Classify, Single-Precision_
* **Operación**: $\texttt{x[rd] = }$ $classify_s$ $\texttt{(f[rs1])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fclass.s rd, rs1, rs2
```

***

### fcvt.s.w

Convierte el entero de 32 bits de complemento a dos en $\texttt{x[rs1]}$ en un número de punto flotante de precisión simple y lo escribe en $\texttt{f[rd]}$.

* _Floating-point Convert to Single from Word_
* **Operación**: $\texttt{f[rd] = f32}$ $_{s32}$ $\texttt{(x[rs1])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fcvt.s.w rd, rs1, rs2
```

***

### fcvt.s.wu

Convierte el entero de 32 bits sin signo en $\texttt{x[rs1]}$ en un número de punto flotante de precisión simple y lo escribe en $\texttt{f[rd]}$.

* _Floating-point Convert to Single from Unsigned Word_
* **Operación**: $\texttt{f[rd] = f32}$ $_{u32}$ $\texttt{(x[rs1])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fcvt.s.wu rd, rs1, rs2
```

***

### fcvt.w.s

Convierte el número de punto flotante de precisión simple en el registro $\texttt{f[rs1]}$ en un entero de 32 bits de complemento a dos y escribe el resultado sign-extended en $\texttt{x[rd]}$.
* _Floating-point Convert to Word from Single_
* **Operación**: $\texttt{x[rd] = sext(s32}$ $_{f32}$ $\texttt{(f[rs1]))}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fcvt.w.s rd, rs1, rs2
```

***

### fcvt.wu.s

Convierte el número de punto flotante de precisión simple en el registro $\texttt{f[rs1]}$ en un entero de 32 bits sin signo y escribe el resultado sign-extended en $\texttt{x[rd]}$.

* _Floating-point Convert to Unsigned Word from Single_
* **Operación**: $\texttt{x[rd] = sext(u32}$ $_{f32}$ $\texttt{(f[rs1]))}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fcvt.wu.s rd, rs1, rs2
```

***

### fdiv.s

Divide el número de punto flotante de precisión simple en el registro $\texttt{f[rs1]}$ entre $\texttt{f[rs2]}$ y escribe el cociente redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Divide, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1]}$ $÷$ $\texttt{f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fdiv.s rd, rs1, rs2
```

***

### fence

Vuelve los accesos previos a memoria e I/O en el conjunto $\texttt{predecessor}$ observables a otros threads y dispositivos antes de que los accesos subsiguientes a memoria e I/O en el conjunto $\texttt{successor}$ sean observables. Los bits 3, 2, 1 y 0 en estos conjuntos corresponden a $\texttt{device input, device output, memory reads}$ y $\texttt{memory writes}$, respectivamente.
La instrucción $\texttt{fence r, rw}$ por ejemplo, ordena lecturas más antiguas con lecturas y escrituras más recientes, y se codifica con $\texttt{pred = 0010}$ y $\texttt{suc = 0011}$. Si los argumentos son omitidos, se asume un $\texttt{fence iorw, iorw}$ completo.

* _Fence Memory and I/O_
* **Operación**: $\texttt{Fence(pred, succ)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
fence pred, succ
```

***

### feq.s

Escribe $\texttt{1}$ en $\texttt{x[rd]}$ si el número de punto flotante de precisión simple en $\texttt{f[rs1]}$ es igual al número en $\texttt{f[rs2]}$, y 0 si no.

* _Floating-point Equals, Single-Precision_
* **Operación**: $\texttt{x[rd] = f[rs1] == f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
feq.s rd, rs1, rs2
```

***

### fle.s

Escribe $\texttt{1}$ en $\texttt{x[rd]}$ si el número de punto flotante de precisión simple en $\texttt{f[rs1]}$ es menor o igual que el número en $\texttt{f[rs2]}$, y 0 si no.

* _Floating-point Less Than or Equal, Single-Precision_
* **Operación**: $\texttt{x[rd] = f[rs1] ≤ f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fle.s rd, rs1, rs2
```

***

### flt.s

Escribe $\texttt{1}$ en $\texttt{x[rd]}$ si el número de punto flotante de precisión simple en $\texttt{f[rs1]}$ es menor que el número en $\texttt{f[rs2]}$, y 0 si no.

* _Floating-point Less Than, Single-Precision_
* **Operación**: $\texttt{x[rd] = f[rs1] < f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
flt.s rd, rs1, rs2
```

***

### flw

Carga un número de punto flotante de precisión simple de la dirección me memoria $\texttt{x[rs1] + sign-extend(offset)}$ y lo escribe en $\texttt{f[rd]}$.
Formas comprimidas: **$\texttt{c.flwsp}$** $\texttt{ rd, offset}$; **$\texttt{c.flw}$** $\texttt{ rd, offset(rs1)}$.

* _Floating-point Load Word_
* **Operación**: $\texttt{f[rd] = M[x[rs1]] + sext(offset)][31:0]}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
flw rd, offset(rs1)
```

***

### fmadd.s

Multiplica los números de punto flotante de precisión simple en $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$, suma el producto sin redondear al número de punto flotante de precisión simple en $\texttt{f[rs3]}$, y escribe el resultado redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Fused Multiply-Add, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1]×f[rs2]+f[rs3]}$
* **Tipo**: $\texttt{R4}$

**Uso**

```asm
fmadd.s rd, rs1, rs2, rs3
```

***

### fmax.s

Copia el mayor de los números de punto flotante de precisión simple de los registros $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$ a $\texttt{f[rd]}$.

* _Floating-point Maximum, Single-Precision_
* **Operación**: $\texttt{f[rd] = max(f[rs1], f[rs2])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fmax.s rd, rs1, rs2
```

***

### fmin.s

Copia el menor de los números de punto flotante de precisión simple de los registros $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$ a $\texttt{f[rd]}$.

* _Floating-point Minimum, Single-Precision_
* **Operación**: $\texttt{f[rd] = min(f[rs1], f[rs2])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fmin.s rd, rs1, rs2
```

***

### fmsub.s

Multiplica los números de punto flotante de precisión simple en $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$, resta el número de punto flotante de precisión simple en $\texttt{f[rs3]}$ del producto sin redondear, y escribe el resultado redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Fused Multiply-Subtract, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1]×f[rs2]-f[rs3]}$
* **Tipo**: $\texttt{R4}$

**Uso**

```asm
fmsub.s rd, rs1, rs2
```

***

### fmul.s

Multiplica los números de punto flotante de precisión simple en los registros $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$ y escribe el producto redondeado de precisión simple en  $\texttt{f[rd]}$.

* _Floating-point Multiply, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1] × f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fmul.s rd, rs1, rs2
```

***

### fmv.w.x

Copia el número de punto flotante de precisión simple en el registro $\texttt{x[rs1]}$ a $\texttt{f[rd]}$.

* _Floating-point Move Word from Integer_
* **Operación**: $\texttt{f[rd] = x[rs1][31:0]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fmv.w.x rd, rs1, rs2
```

***

### fmv.x.w

Copia el número de punto flotante de precisión simple en el registro $\texttt{f[rs1]}$ a $\texttt{x[rd]}$], extendiendo en signo el resultado para $\texttt{RV64F}$.

* _Floating-point Move Word to Integer_
* **Operación**: $\texttt{x[rd] = sext(f[rs1][31:0])}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fmv.x.w rd, rs1, rs2
```

***

### fnmadd.s

Multiplica los números de punto flotante de precisión simple en $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$, niega el resultado, resta el número de punto flotante de precisión simple en $\texttt{f[rs3]}$ del producto sin redondear, y escribe el resultado redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Fused Negative Multiply-Add, Single-Precision_
* **Operación**: $\texttt{f[rd] = -f[rs1]×f[rs2]-f[rs3]}$
* **Tipo**: $\texttt{R4}$

**Uso**

```asm
fnmadd.s rd, rs1, rs2, rs3
```

***

### fnmsub.s

Multiplica los números de punto flotante de precisión simple en $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$, niega el resultado, suma el producto sin redondear al número de punto flotante de precisión simple en $\texttt{f[rs3]}$, y escribe el resultado redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Fused Negative Multiply-Subtract, Single-Precision_
* **Operación**: $\texttt{f[rd] = -f[rs1]×f[rs2]+f[rs3]}$
* **Tipo**: $\texttt{R4}$

**Uso**

```asm
fnmsub.s rd, rs1, rs2, rs3
```

***

### fsgnj.s

Construye un nuevo número de punto flotante de precisión simple del exponente y significando de $\texttt{f[rs1]}$ tomando el signo de $\texttt{f[rs2]}$], y lo escribe en $\texttt{f[rd]}$.

* _Floating-point Sign Inject, Single-Precision_
* **Operación**: $\texttt{f[rd] = }${$\texttt{f[rs2][31], f[rs1][30:0]}$}
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fsgnj.s rd, rs1, rs2
```

***

### fsgnjn.s

Construye un nuevo número de punto flotante de precisión simple del exponente y significando de $\texttt{f[rs1]}$ tomando el signo opuesto de $\texttt{f[rs2]}$], y lo escribe en $\texttt{f[rd]}$.

* _Floating-point Sign Inject-Negate, Single-Precision_
* **Operación**:
$\texttt{f[rd] = }${$\texttt{∼f[rs2][31], f[rs1][30:0]}$}
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fsgnjn.s rd, rs1, rs2
```

***

### fsgnjx.s

Construye un nuevo número de punto flotante de precisión simple del exponente y significando de $\texttt{f[rs1]}$ tomando el signo del XOR de los signos de $\texttt{f[rs1]}$ y $\texttt{f[rs2]}$], y lo escribe en $\texttt{f[rd]}$.

* _Floating-point Sign Inject-XOR, Single-Precision_
* **Operación**:
$\texttt{f[rd] = }${$\texttt{f[rs1][31] ˆ f[rs2][31], f[rs1][30:0]}$}
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fsgnjx.s rd, rs1, rs2
```

***

### fsqrt.s

Calcula la raíz cuadrada del número de punto flotante de precisión doble en el registro $\texttt{f[rs1]}$ y escribe el resultado redondeado de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Square Root, Single-Precision_
* **Operación**: $\texttt{f[rd] = }$ $\sqrt{\texttt{f[rs1]}}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fsqrt.s rd, rs1, rs2
```

***

### fsub.s

Resta el número de punto flotante de precisión simple en el registro $\texttt{f[rs2]}$ de $\texttt{f[rs1]}$ y escribe la diferencia de precisión simple en $\texttt{f[rd]}$.

* _Floating-point Subtract, Single-Precision_
* **Operación**: $\texttt{f[rd] = f[rs1] - f[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
fsub.s rd, rs1, rs2
```

***

### fsw

Almacena el número de punto flotante de precisión simple del registro $\texttt{f[rs2]}$ a memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$.
Formas comprimidas: **$\texttt{c.fswsp}$** $\texttt{ rs2, offset}$; **$\texttt{c.fsw}$** $\texttt{ rs2, offset(rs1)}$.

* _Floating-point Store Word_
* **Operación**: $\texttt{M[x[rs1] + sext(offset)] = f[rs2][31:0]}$
* **Tipo**: $\texttt{S}$

**Uso**

```asm
fsw rs2, offset(rs1)
```

***

### jal

Escribe la dirección de la siguiente instrucción $\texttt{(pc+4)}$ en $\texttt{x[rd]}$, luego asigna al $\texttt{pc}$ su valor actual más el $\texttt{offset}$ extendido en signo. Si $\texttt{rd}$ es omitido, se asume $\texttt{x1}$.
Formas comprimidas: **$\texttt{c.j}$** $\texttt{ offset}$; **$\texttt{c.jal}$** $\texttt{ offset}$.

* _Jump and Link_
* **Operación**: $\texttt{x[rd] = pc+4}$; $\texttt{pc += sext(offset)}$
* **Tipo**: $\texttt{J}$

**Uso**

```asm
jal rd, offset
```

***

### jalr

Escribe $\texttt{x[rs1] + sign-extend(offset)}$ al $\texttt{pc}$, enmascarando a cero el bit menos significativo de la dirección calculada, luego escribe el valor previo del $\texttt{pc+4}$ en $\texttt{x[rd]}$. Si $\texttt{rd}$ es omitido, se asume $\texttt{x1}$.
Formas comprimidas: **$\texttt{c.jr}$** $\texttt{ rs1}$; **$\texttt{c.jalr}$** $\texttt{ rs1}$.

* _Jump and Link Register_
* **Operación**: $\texttt{t =pc+4}$; $\texttt{pc=(x[rs1]+sext(offset))}$ & $\texttt{∼1}$; $\texttt{x[rd]=t}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
jalr rd, offset(rs1)
```

***

### lb

Carga un byte de memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$ y lo escribe en $\texttt{x[rd]}$, extendiendo en signo el resultado.

* _Load Byte_
* **Operación**: $\texttt{x[rd] = sext(M[x[rs1] + sext(offset)][7:0])}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lb rd, offset(rs1)
```

***

### lbu

Carga un byte de memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$ y lo escribe en $\texttt{x[rd]}$, extendiendo con cero el resultado.

* _Load Byte, Unsigned_
* **Operación**: $\texttt{x[rd] = M[x[rs1] + sext(offset)][7:0]}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lbu rd, offset(rs1)
```

***

### lh

Carga dos bytes de memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$ y los escribe en $\texttt{x[rd]}$, extendiendo en signo el resultado.

* _Load Halfword_
* **Operación**: $\texttt{x[rd] = sext(M[x[rs1] + sext(offset)][15:0])}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lh rd, offset(rs1)
```

***

### lhu

Carga dos bytes de memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$ y los escribe en $\texttt{x[rd]}$, extendiendo con cero el resultado.

* _Load Halfword, Unsigned_
* **Operación**: $\texttt{x[rd] = M[x[rs1] + sext(offset)][15:0]}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lhu rd, offset(rs1)
```

***

### lw

Carga cuatro bytes de memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$ y los escribe en $\texttt{x[rd]}$. Para $\texttt{RV64I}$, el resultado es extendido en signo.
Formas comprimidas: **$\texttt{c.lwsp}$** $\texttt{ rd, offset}$; **$\texttt{c.lw}$** $\texttt{  rd, offset(rs1)}$

* _Load Word_
* **Operación**: $\texttt{x[rd] = sext(M[x[rs1] + sext(offset)][31:0])}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lw rd, offset(rs1)
```

***

### lui

Escribe el $\texttt{inmediato}$ de 20 bits extendido en signo, corrido a la izquierda por 12 bits, en $\texttt{x[rd]}$, volviendo cero los 12 bits más bajos.
Formas comprimidas: **$\texttt{c.lui}$** $\texttt{ rd, imm}$

* _Load Upper Immediate_
* **Operación**: $\texttt{x[rd] = sext(immediate[31:12] << 12)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
lui rd, immediate
```

***

### mul

Multiplica $\texttt{x[rs1]}$ por $\texttt{x[rs2]}$ y escribe el producto en $\texttt{f[rd]}$. Overflow aritmético ignorado.

* _Multiply_
* **Operación**: $\texttt{x[rd] = x[rs1] × x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
mul rd, rs1, rs2
```

***

### mulh

Multiplica $\texttt{x[rs1]}$ por $\texttt{x[rs2]}$, tratando los valores como números de complemento a dos, y escribe la mitad alta del producto a $\texttt{f[rd]}$.

* _Multiply High_
* **Operación**: $\texttt{x[rd] = (x[rs1]}$ $_s × _s$ $\texttt{x[rs2]) >>}$ $_s$ $\texttt{ XLEN}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
mulh rd, rs1, rs2
```

***

### mulhsu

Multiplica $\texttt{x[rs1]}$ por $\texttt{x[rs2]}$, tratando a $\texttt{x[rs1]}$ como un número de complemento a dos, y a $\texttt{x[rs2]}$ como un número sin signo, y escribe la mitad alta del producto a $\texttt{f[rd]}$.

* _Multiply High Signed-Unsigned_
* **Operación**: $\texttt{x[rd] = (x[rs1]}$ $_s × _u$ $\texttt{x[rs2]) >>}$ $_s$ $\texttt{ XLEN}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
mulhsu rd, rs1, rs2
```

***

### mulhu

Multiplica $\texttt{x[rs1]}$ por $\texttt{x[rs2]}$, tratando los valores como números sin signo, y escribe la mitad alta del producto a $\texttt{f[rd]}$.

* _Multiply High Unsigned_
* **Operación**: $\texttt{x[rd] = (x[rs1]}$ $_u × _u$ $\texttt{x[rs2]) >>}$ $_u$ $\texttt{ XLEN}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
mulhu rd, rs1, rs2
```

***

### or

Calcula el $\texttt{OR}$ inclusivo a nivel de bits de los registros $\texttt{x[rs1]}$ y $\texttt{x[rs2]}$ y escribe el resultado en $\texttt{x[rd]}$.
Formas comprimidas: **$\texttt{c.o}$** $\texttt{ rd, rs2}$

* _OR_
* **Operación**: $\texttt{x[rd] = x[rs1] | x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
or rd, rs1, rs2
```

***

### ori

Calcula el $\texttt{OR}$ inclusivo a nivel de bits del $\texttt{inmediato sign-extended}$ y el registro $\texttt{x[rs1]}$ y escribe el resultado en $\texttt{x[rd]}$.

* _OR Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] | sext(immediate)}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
ori rd, rs1, immediate
```

***

### rem

Divide $\texttt{x[rs1]}$ entre $\texttt{x[rs2]}$, redondeando hacia cero, tratando los valores como números de complemento a dos, y escribe el residuo en $\texttt{f[rd]}$.

* _Remainder_
* **Operación**: $\texttt{x[rd] = x[rs1] \%}$$_s$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
rem rd, rs1, rs2
```

***

### remu

Divide $\texttt{x[rs1]}$ entre $\texttt{x[rs2]}$, redondeando hacia cero, tratando los valores como números sin signo, y escribe el residuo en $\texttt{f[rd]}$.

* _Remainder, Unsigned_
* **Operación**: $\texttt{x[rd] = x[rs1] \%}$$_u$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
remu rd, rs1, rs2
```

***

### sb

Almacena el byte menos significativo del registro $\texttt{x[rs2]}$ a memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$.

* _Store Byte_
* **Operación**: $\texttt{M[x[rs1] + sext(offset)] = x[rs2][7:0]}$
* **Tipo**: $\texttt{S}$

**Uso**

```asm
sb rs2, offset(rs1)
```

***

### sh

Almacena los dos bytes menos significativos del registro $\texttt{x[rs2]}$  a memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$.

* _Store Halfword_
* **Operación**: $\texttt{M[x[rs1] + sext(offset)] = x[rs2][15:0]}$
* **Tipo**: $\texttt{S}$

**Uso**

```asm
sh rs2, offset(rs1)
```

***

### sll

Corre el registro $\texttt{x[rs1]}$ a la izquierda por $\texttt{x[rs2]}$ posiciones de bits. Los bits liberados son reemplazados por ceros, y el resultado es escrito en $\texttt{x[rd]}$. Los cinco bits menos significativos de $\texttt{x[rs2]}$  (o seis bits para $\texttt{RV64I}$) forman la cantidad a correr; los bits altos son ignorados.

* _Shift Left Logical_
* **Operación**: $\texttt{x[rd] = x[rs1] << x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
sll rd, rs1, rs2
```

***

### slli

Corre el registro $\texttt{x[rs1]}$ a la izquierda por $\texttt{shamt}$ posiciones de bits. Los bits liberados son reemplazados por ceros, y el resultado es escrito en $\texttt{x[rd]}$. Para $\texttt{RV32I}$, la instrucción solo es legal cuando $\texttt{shamt[5]=0}$.
Forma comprimida: **$\texttt{c.slli}$** $\texttt{ rd, shamt}$

* _Shift Left Logical Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] << shamt}$.
* **Tipo**: $\texttt{I}$

**Uso**

```asm
slli rd, rs1, shamt
```

***

### slt

Compara $\texttt{x[rs1]}$ con $\texttt{x[rs2]}$ como números de complemento a dos, y escribe $\texttt{1}$ en $\texttt{x[rd]}$ si $\texttt{x[rs1]}$ es menor, ó 0 si no.

* _Set if Less Than_
* **Operación**: $\texttt{x[rd] = x[rs1] <}$ $_s$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
slt rd, rs1, rs2
```

***

### slti

Compara $\texttt{x[rs1]}$ con el $\texttt{inmediato sign-extended}$ como números de complemento a dos, y escribe $\texttt{1}$ en $\texttt{x[rd]}$ si $\texttt{x[rs1]}$ es menor, ó 0 si no.

* _Set if Less Than Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] <}$ $_s$ $\texttt{sext(immediate)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
slti rd, rs1, immediate
```

***

### sltiu

Compara $\texttt{x[rs1]}$ con el $\texttt{inmediato sign-extended}$ como números sin signo, y escribe $\texttt{1}$ en $\texttt{x[rd]}$ si $\texttt{x[rs1]}$ es menor, ó 0 si no.

* _Set if Less Than Immediate, Unsigned_
* **Operación**: $\texttt{x[rd] = x[rs1] <}$ $_u$ $\texttt{sext(immediate)}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
sltiu rd, rs1, immediate
```

***

### sltu

Compara $\texttt{x[rs1]}$ con $\texttt{x[rs2]}$ como números sin signo, y escribe $\texttt{1}$ en $\texttt{x[rd]}$ si $\texttt{x[rs1]}$ es menor, ó 0 si no.

* _Set if Less Than, Unsigned_
* **Operación**: $\texttt{x[rd] = x[rs1] <}$ $_u$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
sltu rd, rs1, rs2
```

***

### sra

Corre el registro $\texttt{x[rs1]}$ a la derecha por $\texttt{x[rs2]}$ posiciones de bits. Los bits liberados son reemplazados por copias del bit más significativo de $\texttt{x[rs1]}$, y el resultado es escrito en $\texttt{x[rd]}$. Los cinco bits menos significativos de $\texttt{x[rs2]}$ (o seis bits para $\texttt{RV64I}$) forman la cantidad a correr; los bits altos son ignorados.

* _Shift Right Arithmetic_
* **Operación**: $\texttt{x[rd] = x[rs1] >>}$$_s$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
sra rd, rs1, rs2
```

***

### srai

Corre el registro $\texttt{x[rs1]}$ a la derecha por $\texttt{shamt}$ posiciones de bits. Los bits liberados son reemplazados por copias del bit más significativo de $\texttt{x[rs1]}$,  y el resultado es escrito en $\texttt{x[rd]}$. Para $\texttt{RV32I}$, la instrucción solo es legal cuando $\texttt{shamt[5]=0}$.
Forma comprimida: **$\texttt{c.srai}$** $\texttt{ rd, shamt}$.

* _Shift Right Arithmetic Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] >>}$$_s$ $\texttt{shamt}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
srai rd, rs1, shamt
```

***

### srl

Corre el registro $\texttt{x[rs1]}$ a la derecha por $\texttt{x[rs2]}$ posiciones de bits. Los bits liberados son reemplazados por ceros ceros, y el resultado es escrito en $\texttt{x[rd]}$. Los cinco bits menos significativos de $\texttt{x[rs2]}$ (o seis bits para $\texttt{RV64I}$) forman la cantidad a correr; los bits altos son ignorados.

* _Shift Right Logical_
* **Operación**: $\texttt{x[rd] = x[rs1] >>}$$_u$ $\texttt{x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
srl rd, rs1, rs2
```

***

### srli

Corre el registro $\texttt{x[rs1]}$ a la derecha por $\texttt{shamt}$ posiciones de bits. Los bits liberados son reemplazados por ceros, y el resultado es escrito en $\texttt{x[rd]}$. Para $\texttt{RV32I}$, la instrucción solo es legal cuando $\texttt{shamt[5]=0}$.
Forma comprimida: **$\texttt{c.srli}$** $\texttt{ rd, shamt}$.

* _Shift Right Logical Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] >>}$$_u$ $\texttt{shamt}$
* **Tipo**: $\texttt{I}$

**Uso**

```asm
srli rd, rs1, shamt
```

***

### sub

Resta el registro $\texttt{x[rs2]}$ del registro $\texttt{x[rs1]}$, trunca el resultado a 32 bits, y escribe el resultado de 32 bits, sign-extended, en $\texttt{x[rd]}$. Overflow aritmético ignorado.
Forma comprimida: **$\texttt{c.sub}$** $\texttt{ rd, rs2}$.

* _Subtract_
* **Operación**: $\texttt{x[rd] = x[rs1] - x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
sub rd, rs1, rs2
```

***

### sw

Almacena los cuatro bytes menos significativos del registro $\texttt{x[rs2]}$ a memoria en la dirección $\texttt{x[rs1] + sign-extend(offset)}$.
Formas comprimidas: **$\texttt{c.swsp}$** $\texttt{ p rs2, offset}$; **$\texttt{c.sw}$** $\texttt{  rs2, offset(rs1)}$.

* _Store Word_
* **Operación**: $\texttt{M[x[rs1] + sext(offset)] = x[rs2][31:0]}$
* **Tipo**: $\texttt{S}$

**Uso**

```asm
sw rs2, offset(rs1)
```

***

### xor

Calcula el $\texttt{OR}$ exclusivo a nivel de bits de los registros $\texttt{x[rs1]}$ y $\texttt{x[rs2]}$ y escribe el resultado en $\texttt{x[rd]}$.
Formas comprimidas: **$\texttt{c.xor}$** $\texttt{ rd, rs2}$

* _Exclusive-OR_
* **Operación**: $\texttt{x[rd] = x[rs1] ˆ x[rs2]}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
xor rd, rs1, rs2
```
``

***

### xori

Calcula el $\texttt{OR}$ exclusivo a nivel de bits del $\texttt{inmediato sign-extended}$ y el registro $\texttt{x[rs1]}$ y escribe el resultado en $\texttt{x[rd]}$.

* _Exclusive-OR Immediate_
* **Operación**: $\texttt{x[rd] = x[rs1] ˆ sext(immediate)}$
* **Tipo**: $\texttt{R}$

**Uso**

```asm
xori rd, rs1, immediate
```
