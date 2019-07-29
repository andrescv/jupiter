/*
Copyright (C) 2018-2019 Andres Castellanos

This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.

This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.

You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>
*/

package jupiter.exc;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;


/** Exceptions Tests */
public class ExceptionsTest {

  @Test
  public void testExceptions() {
    NonInstructionException e1 = new NonInstructionException(0x0);
    assertEquals("attempt to execute non-instruction at 0x00000000", e1.getMessage());
    AssemblerException e2 = new AssemblerException("simulation halted");
    assertEquals("simulation halted", e2.getMessage());
    BreakpointException e3 = new BreakpointException(0x0);
    assertEquals("breakpoint exception at: 0x00000000", e3.getMessage());
    HaltException e4 = new HaltException(-1);
    assertEquals("exit(-1)", e4.getMessage());
    InvalidAddressException e5 = new InvalidAddressException(0x0, true);
    assertEquals("attempting to read to an invalid memory address 0x00000000", e5.getMessage());
    InvalidAddressException e6 = new InvalidAddressException(0x0, false);
    assertEquals("attempting to write to an invalid memory address 0x00000000", e6.getMessage());
    JupiterException e7 = new JupiterException("halo");
    assertEquals("halo", e7.getMessage());
    LinkerException e8 = new LinkerException("no global start label");
    assertEquals("no global start label", e8.getMessage());
    RegisterException e9 = new RegisterException("a40");
    assertEquals("invalid register: a40", e9.getMessage());
    assertEquals("a40", e9.getRegister());
    RelocationException e10 = new RelocationException("foo");
    assertEquals("label: 'foo' used but not defined", e10.getMessage());
    assertEquals("foo", e10.getTarget());
    SimulationException e11 = new SimulationException("ecall error");
    assertEquals("ecall error", e11.getMessage());
  }

}
