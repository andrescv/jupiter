<p align="center">
  <img src="./docs/theme/img/vsim-logo.png" alt="V-Sim" width="500">
  <br><br>
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

**VSim** is a RISC-V assembler and runtime simulator for educational purposes made in Java. Almost all the 32-bit base integer ISA can be simulated as well as the **M** and **F** extensions with all their respective pseudo-instructions. For a complete list of supported instructions, please visit the [docs](https://github.com/andrescv/V-Sim/wiki) page.

### Dependencies

**V-Sim** requires **Java** SE 8 (or later) JRE installed on your computer.

### Installation

To install or update **V-Sim**, you can use the install script with

**cURL**:

```shell
curl https://git.io/fbpu0 -L -o vsim && chmod +x vsim && . ./vsim && rm vsim
```
or **Wget**:

```shell
wget -O vsim https://git.io/fbpu0 && chmod +x vsim && . ./vsim && rm vsim
```

To verify that **V-Sim** has been installed, do:

```shell
vsim -version
```

which should output 'vsim-v1.0.0' if the installation was successful.

### Contributing

Pull requests and stars are always welcome. For bugs and feature requests, [please create an issue](https://github.com/andrescv/VSim/issues/new).

### Acknowledgments

A big thank you to all the people working on the RISC-V project
