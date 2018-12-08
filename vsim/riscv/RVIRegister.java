/*
Copyright (C) 2018 Andres Castellanos

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

package vsim.riscv;

import vsim.Settings;


/**
 * The RVIRegister class represents a "hardware" rvi register.
 */
public final class RVIRegister extends Register {

  /**
   * Creates a new a RVI register.
   *
   * @param number register number
   * @param mnemonic register mnemonic
   * @param value register initial value
   * @param editable if the register is editable
   */
  public RVIRegister(int number, String mnemonic, int value, boolean editable) {
    super(number, mnemonic, value, editable);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public void update() {
    // display in hex format
    if (Settings.DISP_RVI_REG == 0)
      this.strValue.set(String.format("0x%08x", this.value));
    // display in unsigned format
    else if (Settings.DISP_RVI_REG == 1)
      this.strValue.set(String.format("%d", Integer.toUnsignedLong(this.value)));
    // display in decimal format
    else
      this.strValue.set(String.format("%d", this.value));
  }

}
