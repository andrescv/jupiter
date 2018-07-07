source: index.md

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
  <img src="https://img.shields.io/github/downloads/andrescv/V-Sim/total.svg">
</p>

V-Sim is a **RISC-V** assembler and runtime simulator for educational purposes made in Java. Almost all the 32-bit base integer instruction set can be simulated, as well as the **M** and **F** extensions.

***

## Installation

To install or update V-Sim, you can use the installation script.

with **cURL**:

```shell
curl https://git.io/fbpu0 -L -o vsim && chmod +x vsim && . ./vsim && rm vsim
```
or **Wget**:

```shell
wget -O vsim https://git.io/fbpu0 && chmod +x vsim && . ./vsim && rm vsim
```

To verify that V-Sim has been installed, do:

```shell
vsim -version
```

which should output `vsim-v1.0.0` if the installation was successful.