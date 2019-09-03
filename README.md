<p align="center">
  <a href="https://github.com/andrescv/Jupiter/">
    <img src="./docs/assets/images/jupiter.png" alt="Jupiter" width="400">
  </a>
  <br><br>
  <a href="https://github.com/andrescv/Jupiter/blob/master/LICENSE">
    <img src="https://img.shields.io/badge/License-GPL%20v3-blue.svg" alt="License: GPL v3">
  </a>
  <a href="https://dev.azure.com/andrescv/Jupiter">
    <img src="https://dev.azure.com/andrescv/Jupiter/_apis/build/status/andrescv.Jupiter?branchName=master" alt="Azure Pipelines">
  </a>
  <a href="https://dev.azure.com/andrescv/Jupiter">
    <img src="https://img.shields.io/azure-devops/coverage/andrescv/Jupiter/4.svg" alt="Coverage" />
  </a>
  <a href="https://github.com/andrescv/Jupiter/releases">
    <img src="https://img.shields.io/github/release/andrescv/Jupiter/all.svg" alt="Version">
  </a>
  <a href="https://github.com/andrescv/Jupiter/releases">
    <img src="https://img.shields.io/github/downloads/andrescv/Jupiter/total.svg">
  </a>
  <a href="https://github.com/andrescv/Jupiter/issues">
    <img src="https://img.shields.io/github/issues/andrescv/Jupiter.svg" alt="issues" />
  </a>
</p>

**Jupiter** is an open source and education-oriented RISC-V assembler and runtime simulator. It is written in Java 11 and capable of simulate all the instructions of the base integer ISA (`I` extension) plus the `M` and `F` extensions (**RV32IMF**), including all the pseudo-instructions described in the user-level instruction set manual<sup>1</sup>. It was developed taking into account that it could be used in various courses such as: _Computer Architecture, Compilers and Assembly Programming_.

### Features

* **User Friendly**: Jupiter was designed focused in education and for all the people that are getting to know the RISC-V architecture. It places for priority the user experience. Jupiter has two modes of operation (_Command Line Interface_ and A _Graphical User Interface_) and both of these were developed to be intuitive and easy to use.

* **Modularity**: Jupiter can assemble and simulate several files at once, not everything has to be one file of 1,000 lines of code. Simply one has to indicate by a global label what is the main starting point of the program. This permits modularity and enables the creation of projects and laboratories more easily.

* **Feedback**: People using Jupiter gets feedback on what they are doing wrong :100:. The simulator shows errors of: syntax, when trying to access reserved memory or when trying to write to a read-only memory.

* **Cross-platform**: Jupiter is available for **Linux** (_Ubuntu_), **macOS** and **Windows**.

### Screenshots

<p align="center">
  <img src="./docs/assets/images/gui.png" alt="Jupiter GUI mode"/>
  <strong>GUI Mode</strong>
</p>

<p align="center">
  <img src="./docs/assets/images/cli.png" alt="Jupiter CLI mode" />
  <strong>CLI Mode</strong>
</p>

### Installation
Download the app image for your operating system and unzip the file:

* [Jupiter v3.1 - Linux (Ubuntu)](https://github.com/andrescv/Jupiter/releases/download/v3.1/Jupiter-3.1-linux.zip)
* [Jupiter v3.1 - macOS](https://github.com/andrescv/Jupiter/releases/download/v3.1/Jupiter-3.1-mac.zip)
* [Jupiter v3.1 - Windows](https://github.com/andrescv/Jupiter/releases/download/v3.1/Jupiter-3.1-win.zip)

#### Running Jupiter on Linux or macOS

```shell
./image/bin/jupiter # for GUI mode
./image/bin/jupiter [options] <files> # for CLI mode
```

#### Running Jupiter on Windows
```shell
image\bin\jupiter # for GUI mode
image\bin\jupiter [options] <files> # for CLI mode
```

### Why this name, Jupiter ?

Traditionally other simulators of this type have used planet names, for example the famous educational simulator for the _MIPS_ architecture **MARS**<sup>2</sup> or the Berkeley's web-based simulator **Venus**<sup>3</sup>. The name **Jupiter**<sup>4</sup> was chosen because of this tradional reasons and also because this planet represents in some sort the number **5**, in contrast with RISC-V that is the 5th generation of the ISA originally designed in the 90's<sup>5</sup>.

### Contributing

Pull requests and stars are always welcome. For bugs and feature requests, [please create an issue](https://github.com/andrescv/Jupiter/issues/new).

### Other great simulators

* [Venus](https://github.com/ThaumicMekanism/venusbackend): Berkeley's Web-based simulator originally developed by [@kvakil](https://github.com/kvakil) and then updated and improved by [@ThaumicMekanism](https://github.com/ThaumicMekanism).

* [RARS](https://github.com/TheThirdOne/rars): RISC-V Assembler and Runtime Simulator (RARS), based on the originally MARS simulator, but refactored for the RISC-V architecture by [@TheThirdOne](https://github.com/TheThirdOne).

* [Ripes](https://github.com/mortbopet/Ripes): A graphical 5-stage RISC-V pipeline simulator & assembly editor developed by [@mortbopet](https://github.com/mortbopet).

* [Spike](https://github.com/riscv/riscv-isa-sim): The original RISC-V ISA simulator that implements a functional model of one or more RISC-V harts.

### Acknowledgments

A big thank you to all the people working on the RISC-V project.

### References

1. https://github.com/riscv/riscv-isa-manual
2. http://courses.missouristate.edu/KenVollmar/mars/index.htm
3. https://github.com/kvakil/venus
4. https://en.wikipedia.org/wiki/Jupiter
5. https://en.wikipedia.org/wiki/DLX
