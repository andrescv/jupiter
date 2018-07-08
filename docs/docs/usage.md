# Basic Usage

Once the simulator has been installed, you can get familiar with the CLI opening a terminal and putting the following `vsim -help`, that will output something like:

```shell
$ vsim -help
 _   __    _____
| | / /___/ __(_)_ _
| |/ /___/\ \/ /  ' \
|___/   /___/_/_/_/_/

RISC-V Assembler & Runtime Simulator

GPL-3.0 License
Copyright (c) 2018 Andres Castellanos
All Rights Reserved
See the file LICENSE for a full copyright notice

usage: vsim [options] <files>

available options:
  -bare         bare machine (no pseudo-ops)
  -debug        start the debugger
  -dump DUMP    dump machine code to a file
  -help         show this help message and exit
  -iset         print available RISC-V instructions and exit
  -nocolor      do not colorize output
  -notitle      do not print title and copyright notice
  -quiet        do not print warnings
  -start START  start program at global label (default: main)
  -usage USAGE  print usage of an instruction and exit
  -version      show the simulator version and exit
```

As you can see there are some options that you can pass to the simulator, for example `-help` was the
one that you used previously. Here is a detailed table of each option:

|    Option    |                                        Description                                       |
|:------------:|:----------------------------------------------------------------------------------------:|
|     -bare    |                 bare machine mode, no pseudo-instructions available (TAL)                |
|    -debug    |                       start the debugger after assembling all files                      |
|  -dump DUMP  |                       dump all the generated machine code to a file                      |
|     -help    |                         show the simulator help message and exit                         |
|     -iset    |               print the available/implemented RISC-V instructions and exit               |
|   -nocolor   |                 do not colorize output (only applicable for Linux users)                 |
|   -notitle   | do not print title and copyright notice (useful if redirecting or piping program output) |
|    -quiet    |                        do not print warnings during the simulation                       |
| -start START |                       start program at global label (default: main)                      |
| -usage USAGE |              print detailed usage and description of an instruction and exit             |
|   -version   |                            show the simulator version and exit                           |

Now we can try an example, the legendary [fibonacci](https://raw.githubusercontent.com/andrescv/VSim/master/examples/fibonacci.s) program. Download and save the file with the name `fibonacci.s` and put this in the terminal `vsim fibonacci.s` that will output something like:

```shell
$ vsim fibonacci.s
 _   __    _____
| | / /___/ __(_)_ _
| |/ /___/\ \/ /  ' \
|___/   /___/_/_/_/_/

RISC-V Assembler & Runtime Simulator

GPL-3.0 License
Copyright (c) 2018 Andres Castellanos
All Rights Reserved
See the file LICENSE for a full copyright notice

Please enter a number:
```

Because this program is using the `READ_INT` environmental call, the program asks you to enter a number, you can enter for example the number 9 and the result will be:


```shell
$ vsim fibonacci.s
 _   __    _____
| | / /___/ __(_)_ _
| |/ /___/\ \/ /  ' \
|___/   /___/_/_/_/_/

RISC-V Assembler & Runtime Simulator

GPL-3.0 License
Copyright (c) 2018 Andres Castellanos
All Rights Reserved
See the file LICENSE for a full copyright notice

Please enter a number: 9

The 9 fibonnaci number is: 34

vsim: exit(0)
```

And that is the basic usage of the V-Sim simulator, you can also assemble and simulate several files at the same time, the only restriction is that you always have to define a global `main` label, even if it is a single file as this tells V-Sim where to start the simulation. Alternatively you can define a global label with another name and pass the `-start <labelname>` option to the simulator e.g.

```asm
#####################
#     example.s     #
#####################

.globl start

.rodata

msg: .asciiz "hello world"

.text

start:
  li a0, 4
  la a1, msg
  ecall
  li a0, 10
  ecall
```

```shell
$ vsim -start start example.s
 _   __    _____
| | / /___/ __(_)_ _
| |/ /___/\ \/ /  ' \
|___/   /___/_/_/_/_/

RISC-V Assembler & Runtime Simulator

GPL-3.0 License
Copyright (c) 2018 Andres Castellanos
All Rights Reserved
See the file LICENSE for a full copyright notice

hello world
vsim: exit(0)
```

**Support Material**

* [The RISC-V Reader](#)
* [RISC-V Green Card](#)
