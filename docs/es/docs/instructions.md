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
