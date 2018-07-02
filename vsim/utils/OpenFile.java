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
import java.io.File;
import vsim.Settings;
import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.BufferedReader;
import java.nio.file.StandardOpenOption;


/**
 * The class OpenFile represents an open file from file system.
 */
public final class OpenFile {

  /** pathname attached to this open file */
  private String pathname;
  /** indicates if the open file has read permissions */
  private boolean read;
  /** indicates if the open file has write permissions */
  private boolean write;
  /** indicates if the open file has read and write permissions */
  private boolean rdwr;
  /** indicates if there are errors when opening and creating the file */
  private boolean error;
  /** open flags */
  private StandardOpenOption[] flags;

  /**
   * Unique constructor that initializes a newly OpenFile object.
   *
   * @param pathname pathname attached to this open file
   * @param flags array of open flags
   */
  public OpenFile(String pathname, int flags) {
    this.pathname = pathname;
    boolean create = false;
    boolean create_new = false;
    boolean truncate = false;
    boolean append = false;
    this.error = false;
    // parse flags
    if ((flags & FS.O_RDONLY) != 0)
      this.read = true;
    if ((flags & FS.O_WRONLY) != 0)
      this.write = true;
    if ((flags & FS.O_RDWR) != 0)
      this.rdwr = true;
    if ((flags & FS.O_APPEND) != 0)
      append = true;
    if ((flags & FS.O_TRUNC) != 0)
      truncate = true;
    if ((flags & FS.O_CREAT) != 0)
      create = true;
    if ((flags & FS.O_EXCL) != 0)
      create_new = true;
    if ((flags & FS.O_MASK) == 0) {
      this.error = true;
      if (!Settings.QUIET)
        Message.warning("file system:  invalid open flags");
    }
    // set StandardOpenOption flags
    if ((this.write || this.rdwr) && append)
      this.flags = new StandardOpenOption[]{StandardOpenOption.WRITE, StandardOpenOption.APPEND};
    else if (this.write || this.rdwr)
      this.flags = new StandardOpenOption[]{StandardOpenOption.WRITE};
    else
      // just for flags not be null
      this.flags = new StandardOpenOption[]{StandardOpenOption.APPEND};
    // try to create file (if create flag)
    if (create && create_new) {
      try {
        this.error = !((new File(this.pathname)).createNewFile());
      } catch (Exception e) {
        this.error = true;
      }
    } else if (create) {
      try {
        (new File(this.pathname)).createNewFile();
      } catch (Exception e) {
        this.error = true;
      }
    }
    // truncate the file (if truncate flag and if file exists)
    if (truncate) {
      File f = new File(this.pathname);
      if (f.exists()) {
        try {
          f.delete();
          f.createNewFile();
        } catch (Exception e) {
          this.error = true;
        }
      }
    }
  }

  /**
   * This method returns the pathname attached to the open file.
   *
   * @return the pathname
   */
  public String getPathname() {
    return this.pathname;
  }

  /**
   * This method indicates if there are any errors when opening and creating
   * the file.
   *
   * @return true if there are errors, false otherwise
   */
  public boolean openErrors() {
    return this.error;
  }

  /**
   * This method simulates the read syscall from C, is used in FS class.
   *
   * @param buffer pointer where the read content will stored
   * @param nbytes number of bytes to read before truncating the data
   * @return the number of bytes that were read, -1 if error
   */
  public int read(int buffer, int nbytes) {
    // read permissions ?
    if (this.read || this.rdwr) {
      int rbytes = 0;
      try {
        BufferedReader br = new BufferedReader(new FileReader(this.pathname));
        for (int i = 0; i < nbytes; i++) {
          int b = br.read();
          if (b == -1)
            b = 0;
          Globals.memory.storeByte(buffer++, b);
          rbytes++;
        }
        return rbytes;
      } catch (Exception e) {
        if (rbytes > 0)
          return rbytes;
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
  public int write(int buffer, int nbytes) {
    // build string
    if (this.write && !this.rdwr || this.rdwr && !this.write) {
      StringBuffer s = new StringBuffer(0);
      int wbytes = 0;
      for (int i = 0; i < nbytes; i++) {
        char c = (char)Globals.memory.loadByteUnsigned(buffer++);
        s.append(c);
        wbytes++;
      }
      // try write data to file
      try {
        Files.write(Paths.get(this.pathname), s.toString().getBytes(), this.flags);
        return wbytes;
      } catch (Exception e) {
        return -1;
      }
    }
    return -1;
  }

}
