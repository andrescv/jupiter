<p align="center">
  <a href="https://github.com/andrescv/Jupiter/">
    <img src="./.github/jupiter.png" alt="Jupiter" width="400">
  </a>
</p>

<p align="center">
  <a href="https://github.com/andrescv/Jupiter/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-GPL%20v3-blue.svg" alt="License: GPL v3">
  </a>
  <a href="https://github.com/andrescv/jupiter/actions/workflows/ci.yml">
    <img src="https://github.com/andrescv/jupiter/actions/workflows/ci.yml/badge.svg?branch=next" alt="">
  </a>
  <a href="https://codecov.io/gh/andrescv/jupiter" > 
    <img src="https://codecov.io/gh/andrescv/jupiter/branch/next/graph/badge.svg?token=FpiLNNzYj8"/> 
  </a>
  <a href="https://github.com/andrescv/Jupiter/releases">
    <img src="https://img.shields.io/github/downloads/andrescv/Jupiter/total.svg">
  </a>
</p>

> **Note**: Currently we are working on a new version of Jupiter, to see the previous codebase and releases visit the [java](https://github.com/andrescv/jupiter/tree/java) branch of this repository.

**Jupiter** is an open source and education-oriented RISC-V assembler and runtime simulator. It is capable of simulate all the instructions of the base integer ISA (`I` extension) plus all the other standard extensions (`M`, `A`, `F`, `D`, `Zicsr`, `Zifencei`), **RV32G**. It was developed taking into account that it could be used in different courses such as: _Computer Architecture, Compilers_ and _Assembly Programming_.

### Features

- **User Friendly**: Jupiter was designed focused on education and for all the people that are getting to know the RISC-V architecture. It places for priority the user experience. Jupiter has two modes of operation (_Command Line Interface_ and _Graphical User Interface_) and both of these were developed to be intuitive to use.

- **Modularity**: Jupiter can assemble and simulate several files at once, not everything has to be one file of 1,000 lines of code. Simply indicate by a global label the main starting point of the program. It seeks to have an easy way to create a good structure of projects and laboratories.

- **Feedback**: Jupiter indicates different types of errors to easily debug what went wrong.

- **Cross-platform**: Jupiter is available for **Linux**, **macOS** and **Windows**.

### Other great simulators

- [Venus](https://github.com/ThaumicMekanism/venusbackend): Berkeley's web-based simulator originally developed by [@kvakil](https://github.com/kvakil) and then updated and improved by [@ThaumicMekanism](https://github.com/ThaumicMekanism).

- [RARS](https://github.com/TheThirdOne/rars): RISC-V Assembler and Runtime Simulator (RARS), based on the originally MARS simulator, but refactored for the RISC-V architecture by [@TheThirdOne](https://github.com/TheThirdOne).

- [Ripes](https://github.com/mortbopet/Ripes): A graphical 5-stage RISC-V pipeline simulator & assembly editor developed by [@mortbopet](https://github.com/mortbopet).

- [Spike](https://github.com/riscv-software-src/riscv-isa-sim): The original RISC-V ISA simulator that implements a functional model of one or more RISC-V harts.

### Acknowledgments

A big thank you to all the people working on the RISC-V project.

### Resources

- [Unprivileged RISC-V Spec](https://github.com/riscv/riscv-isa-manual/releases/download/Ratified-IMAFDQC/riscv-spec-20191213.pdf)
- [Privileged RISC-V Spec](https://github.com/riscv/riscv-isa-manual/releases/download/Priv-v1.12/riscv-privileged-20211203.pdf)
- [RISC-V Green Card UC Berkeley](https://www.cl.cam.ac.uk/teaching/1617/ECAD+Arch/files/docs/RISCVGreenCardv8-20151013.pdf)
- [RISC-V Green Card Cambridge](https://www.cl.cam.ac.uk/teaching/1617/ECAD+Arch/files/docs/RISCVGreenCardv8-20151013.pdf)
- [RISC-V Reader](http://riscbook.com/)

### Contributors

Pull requests and stars are always welcome. For bugs and feature requests, [please create an issue](https://github.com/andrescv/Jupiter/issues/new).

<a href="https://github.com/andrescv/jupiter/graphs/contributors">
  <img src="https://contrib.rocks/image?repo=andrescv/jupiter" />
</a>

Made with [contrib.rocks](https://contrib.rocks).
