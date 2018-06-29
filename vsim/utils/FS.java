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
import vsim.Settings;
import java.util.HashMap;
import java.nio.file.Files;
import java.util.ArrayList;
import java.nio.file.StandardOpenOption;


/**
 * The class FS implements a basic file system.
 */
public final class FS {

  private FS() { /* NOTHING */ }

  /** stdin file descriptor */
  public static final int STDIN = 0;
  /** stdout file descriptor */
  public static final int STDOUT = 1;
  /** stderr file descriptor */
  public static final int STDERR = 2;
  /** current file descriptor */
  private static int FD = 3;

  /** read only open flag */
  public static final int O_RDONLY = 0b0000001;
  /** write only open flag */
  public static final int O_WRONLY = 0b0000010;
  /** read and write open flag */
  public static final int O_RDWR   = 0b0000100;
  /** append open flag */
  public static final int O_APPEND = 0b0001000;
  /** truncate open flag */
  public static final int O_TRUNC  = 0b0010000;
  /** create open flag */
  public static final int O_CREAT  = 0b0100000;
  /** excl open flag */
  public static final int O_EXCL   = 0b1000000;

  /** current open files */
  private static final HashMap<Integer, OpenFile> open = new HashMap<Integer, OpenFile>();
  /** max allowed open files */
  public static final int MAX_FILES = 40;

  /**
   * This method parse the given flags mask and maps every
   * O_flag with their respective java nio StandardOpenOption.
   *
   * @param flags flags mask
   * @return an array of standard open options
   */
  private static ArrayList<StandardOpenOption> parseFlags(int flags) {
    ArrayList<StandardOpenOption> flgs = new ArrayList<StandardOpenOption>();
    if (((flags & O_RDONLY) != 0) || ((flags & O_RDWR) != 0))
      flgs.add(StandardOpenOption.READ);
    if (((flags & O_WRONLY) != 0) || ((flags & O_RDWR) != 0))
      flgs.add(StandardOpenOption.WRITE);
    if ((flags & O_APPEND) != 0)
      flgs.add(StandardOpenOption.APPEND);
    if ((flags & O_TRUNC) != 0)
      flgs.add(StandardOpenOption.TRUNCATE_EXISTING);
    if ((flags & O_CREAT) != 0)
      flgs.add(StandardOpenOption.CREATE);
    if ((flags & O_EXCL) != 0)
      flgs.add(StandardOpenOption.CREATE_NEW);
    flgs.trimToSize();
    return flgs;
  }

  /**
   * This method simulates the open syscall from C.
   *
   * @param pathname file pathname
   * @param flags open flags
   * @return the file descriptor for the new file
   */
  public static int open(String pathname, int flags) {
    if (FS.open.size() < FS.MAX_FILES) {
      // parse and validate flags
      ArrayList<StandardOpenOption> flgs = FS.parseFlags(flags);
      if (flgs.size() == 0) {
        if (!Settings.QUIET)
          Message.warning("file system:  invalid open flags");
        return -1;
      } else if (flgs.contains(StandardOpenOption.APPEND) && flgs.contains(StandardOpenOption.TRUNCATE_EXISTING)) {
        if (!Settings.QUIET)
          Message.warning("file system: O_APPEND + O_TRUNC not allowed");
        return -1;
      } else if (flgs.contains(StandardOpenOption.READ) &&
                 !flgs.contains(StandardOpenOption.WRITE) && flgs.size() > 1) {
        if (!Settings.QUIET)
          Message.warning("file system: O_READ + other open flag different from O_WRITE or O_RDWR not allowed");
        return -1;
      } else {
        FS.open.put(FS.FD, new OpenFile(pathname, flgs));
        return FS.FD++;
      }
    }
    if (!Settings.QUIET)
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
   */
  public static int read(int fd, int buffer, int nbytes) {
    // only available from stdin
    if (fd == FS.STDIN) {
      int rbytes = 0;
      for (int i = 0; i < nbytes; i++) {
        try {
          int c = IO.stdin.read();
          if (c == -1)
            break;
          Globals.memory.storeByte(buffer++, c);
          rbytes++;
        } catch (Exception e) {
          break;
        }
      }
      return rbytes;
    }
    // stdout
    else if (fd == FS.STDOUT) {
      if (!Settings.QUIET)
        Message.warning("file system: reading from stdout not allowed");
      return -1;
    }
    // stderr
    else if (fd == FS.STDERR) {
      if (!Settings.QUIET)
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
   */
  public static int write(int fd, int buffer, int nbytes) {
    // stdin
    if (fd == FS.STDIN) {
      if (!Settings.QUIET)
        Message.warning("file system: writing to stdin not allowed");
      return -1;
    }
    // stdout or stderr
    else if (fd == FS.STDOUT || fd == FS.STDERR) {
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
      if (wbytes > 0) {
        if (fd == FS.STDOUT)
          IO.stdout.print(s.toString());
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
      if (!Settings.QUIET)
        Message.warning("file system: can not close stdin/stdout/stderr");
      return -1;
    }
    // normal file
    else if (FS.open.containsKey(fd)) {
      FS.open.remove(fd);
      System.gc();
      return 0;
    }
    return -1;
  }

}
