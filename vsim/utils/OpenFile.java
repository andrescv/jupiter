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

package vsim.utils;

import vsim.Globals;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.nio.file.StandardOpenOption;


/**
 * The class OpenFile represents an open file from file system.
 */
final class OpenFile {

  /** pathname attached to this open file */
  private String pathname;
  /** open flags */
  private ArrayList<StandardOpenOption> flags;

  /**
   * Unique constructor that initializes a newly OpenFile object.
   *
   * @param pathname pathname attached to this open file
   * @param flags array of open flags
   */
  protected OpenFile(String pathname, ArrayList<StandardOpenOption> flags) {
    this.pathname = pathname;
    this.flags = flags;
  }

  /**
   * This method simulates the read syscall from C, is used in FS class.
   *
   * @param buffer pointer where the read content will stored
   * @param nbytes number of bytes to read before truncating the data
   * @return the number of bytes that were read, -1 if error
   */
  protected int read(int buffer, int nbytes) {
    // read permissions ?
    if (this.flags.contains(StandardOpenOption.READ)) {
      try {
        byte[] bytes = Files.readAllBytes(Paths.get(this.pathname));
        for (int i = 0; i < Math.min(nbytes, bytes.length); i++)
          Globals.memory.storeByte(buffer++, bytes[i]);
        return Math.min(nbytes, bytes.length);
      } catch (Exception e) {
        return -1;
      }
    }
    return -1;
  }

  /**
   * This method simulates the write syscall from C, is used in FS class.
   *
   * @param buffer pointer to a buffer of at least nbytes bytes
   * @param nbytes the number of bytes to write
   * @return the number of bytes that were written, -1 if error
   */
  protected int write(int buffer, int nbytes) {
    // set flags
    int length = this.flags.size();
    if (this.flags.contains(StandardOpenOption.READ))
      length -= 1;
    StandardOpenOption[] flags = new StandardOpenOption[length];
    int i = 0;
    for (StandardOpenOption option: this.flags) {
      if (option != StandardOpenOption.READ) {
        flags[i] = option;
        i++;
      }
    }
    // build string
    StringBuffer s = new StringBuffer(0);
    char c;
    int wbytes = 0;
    while ((c = (char)Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
      wbytes++;
      if (wbytes == nbytes)
        break;
    }
    wbytes = Math.min(wbytes, nbytes);
    // try write data to file
    try {
      Files.write(Paths.get(this.pathname), s.toString().getBytes(), flags);
      return wbytes;
    } catch (Exception e) { /* DO NOTHING */ }
    return -1;
  }

}
