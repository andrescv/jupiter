# V-Sim

<p align="center">
  <img src="./img/vsim-logo.png" alt="V-Sim" width="500"><br>
  <a href="https://github.com/andrescv/V-Sim/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-GPL%20v3-blue.svg" alt="License: GPL v3">
  </a>
  <a href="https://travis-ci.org/andrescv/V-Sim">
    <img src="https://api.travis-ci.org/andrescv/V-Sim.svg?branch=master" alt="Travis CI">
  </a>
  <a href="https://github.com/andrescv/V-Sim/releases">
    <img src="https://img.shields.io/github/release/andrescv/V-Sim/all.svg" alt="Version">
  </a>
  <a href="https://github.com/andrescv/V-Sim/releases">
    <img src="https://img.shields.io/github/downloads/andrescv/V-Sim/total.svg">
  </a>
</p>

V-Sim is a simple assembler and runtime simulator inspired by _SPIM_ for programming in **RISC-V** assembly language and intended for educational purposes. One of the main goals was to make it functional and easy to use. Almost all the 32-bit base integer instruction set (`RV32I`) can be simulated, as well as the `M` and `F` extensions plus all the their respective pseudo-instructions.

***

## Dependencies

V-Sim requires Java **8** SE (or later) SDK installed on your computer.

<p align="center">
  <a href="http://www.oracle.com/technetwork/java/javase/downloads/index.html">
    <img src="./img/button.png" alt="Download Java">
  </a>
</p>

## Unix Installation

To install or update V-Sim on a _Unix_ system, you can use the (fancy) installation script.

with **cURL**:

```shell
$ curl https://git.io/fbpu0 -L -o vsim && chmod +x vsim && . ./vsim && rm vsim
```
or **Wget**:

```shell
$ wget -O vsim https://git.io/fbpu0 && chmod +x vsim && . ./vsim && rm vsim
```

## Windows Installation

To install or update V-Sim on a _Windows_ PC, you need to follow the steps below.

**1.** Download the lastest stable release from [here](https://github.com/andrescv/V-Sim/releases/download/v1.0.0/v1.0.0.zip).

**2.** Unzip and move all the files to your preferred directory, say `C:\vsim`.

**3.** Add **VSim.jar** to `CLASSPATH`, using System Properties dialog

```text
System Properties > Environment variables > Create or append to CLASSPATH variable
```

**4.** Create a short convenient command for the simulator, using a batch file called `vsim.bat` (save this file in a directory in system `PATH`, e.g _system32_):

```text
@echo off
java vsim.VSim %*
```

## Testing Installation

To verify that V-Sim has been installed, open a terminal/command line and enter this:

```shell
$ vsim -version
```

which should output `v1.0.3` if the installation was successful. Then you can follow the [usage](usage.md) guide.
