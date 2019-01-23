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

package vsim.utils;

import java.util.HashMap;
import vsim.Globals;
import vsim.Settings;
import vsim.riscv.exceptions.SimulationException;


/**
 * The class FS implements a basic file system.
 */
public final class FS {

  /** stdin file descriptor */
  public static final int STDIN = 0;
  /** stdout file descriptor */
  public static final int STDOUT = 1;
  /** stderr file descriptor */
  public static final int STDERR = 2;

  /** read only open flag */
  public static final int O_RDONLY = 0b0000001;
  /** write only open flag */
  public static final int O_WRONLY = 0b0000010;
  /** read and write open flag */
  public static final int O_RDWR = 0b0000100;
  /** append open flag */
  public static final int O_APPEND = 0b0001000;
  /** truncate open flag */
  public static final int O_TRUNC = 0b0010000;
  /** create open flag */
  public static final int O_CREAT = 0b0100000;
  /** excl open flag */
  public static final int O_EXCL = 0b1000000;
  /** flags mask */
  public static final int O_MASK = 0b1111111;

  /** current open files */
  private static final HashMap<Integer, OpenFile> open = new HashMap<Integer, OpenFile>();
  /** max allowed open files */
  public static final int MAX_FILES = 32;

  /**
   * Gets the next available file descriptor.
   *
   * @return next available file descriptor, or -1 if open files = max no. of open files
   */
  private static int getNextFD() {
    // start at 3 because 0,1,2 are reserved FDs
    for (int i = 3; i < MAX_FILES + 3; i++) {
      if (!FS.open.containsKey(i))
        return i;
    }
    return -1;
  }

  /**
   * This method simulates the open syscall from C.
   *
   * @param pathname file pathname
   * @param flags open flags
   * @return the file descriptor for the new file
   */
  public static int open(String pathname, int flags) {
    int fd = FS.getNextFD();
    if (fd != -1) {
      try {
        FS.open.put(fd, new OpenFile(pathname, flags));
      } catch (SimulationException e) {
        Message.warning(e.getMessage());
        return -1;
      }
      return fd;
    }
    Message.warning("file system: maximum number of open files exceeded");
    return -1;
  }

  /**
   * This method simulates the read syscall from C.
   *
   * @param fd file descriptor of where to read the input
   * @param buffer pointer where the read content will stored
   * @param nbytes number of bytes to read before truncating the data
   * @return the number of bytes that were read, -1 if error
   * @throws SimulationException if an exception occurs while reading open file
   */
  public static int read(int fd, int buffer, int nbytes) throws SimulationException {
    // only available from stdin
    int buff = buffer;
    if (fd == FS.STDIN) {
      int rbytes = 0;
      for (int i = 0; i < nbytes; i++) {
        try {
          int c = IO.readChar();
          if (c == -1)
            c = 0;
          Globals.memory.storeByte(buff++, c);
          rbytes++;
        } catch (Exception e) {
          if (rbytes > 0)
            return rbytes;
          return -1;
        }
      }
      return rbytes;
    }
    // stdout
    else if (fd == FS.STDOUT) {
      Message.warning("file system: reading from stdout not allowed");
      return -1;
    }
    // stderr
    else if (fd == FS.STDERR) {
      Message.warning("file system: reading from stderr not allowed");
      return -1;
    }
    // normal file
    else if (FS.open.containsKey(fd))
      return FS.open.get(fd).read(buffer, nbytes);
    // error
    return -1;
  }

  /**
   * This method simulates the write syscall from C.
   *
   * @param fd file descriptor of where to write the output
   * @param buffer pointer to a buffer of at least nbytes bytes
   * @param nbytes the number of bytes to write
   * @return the number of bytes that were written, -1 if error
   * @throws SimulationException if an exception occurs while writing to open file
   */
  public static int write(int fd, int buffer, int nbytes) throws SimulationException {
    // stdin
    if (fd == FS.STDIN) {
      Message.warning("file system: writing to stdin not allowed");
      return -1;
    }
    // stdout or stderr
    else if (fd == FS.STDOUT || fd == FS.STDERR) {
      StringBuffer s = new StringBuffer(0);
      int wbytes = 0;
      for (int i = 0; i < nbytes; i++) {
        char c = (char) Globals.memory.loadByteUnsigned(buffer++);
        s.append(c);
        wbytes++;
      }
      if (fd == FS.STDOUT) {
        if (Settings.GUI)
          IO.guistdout.postRunMessage(s.toString());
        else
          IO.stdout.print(s.toString());
      } else {
        if (Settings.GUI)
          IO.guistderr.postRunMessage(s.toString());
        else
          IO.stderr.print(s.toString());
      }
      return wbytes;
    }
    // normal file
    else if (FS.open.containsKey(fd))
      return FS.open.get(fd).write(buffer, nbytes);
    return -1;
  }

  /**
   * This method simulates the close syscall from C.
   *
   * @param fd file descriptor to be closed
   * @return 0 if success, -1 otherwise
   */
  public static int close(int fd) {
    // stdin, stdout or stderr not allowed
    if (fd == FS.STDIN || fd == FS.STDOUT || fd == FS.STDERR) {
      Message.warning("file system: can not close stdin/stdout/stderr");
      return -1;
    }
    // normal file
    else if (FS.open.containsKey(fd)) {
      FS.open.remove(fd);
      return 0;
    }
    return -1;
  }

}
