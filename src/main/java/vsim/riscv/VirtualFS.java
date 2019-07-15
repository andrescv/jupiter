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

package vsim.riscv;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;

import vsim.Logger;
import vsim.State;
import vsim.exc.SimulationException;
import vsim.utils.FS;
import vsim.utils.IO;


/** Virtual file system for open, read, write, lseek ecalls. */
public final class VirtualFS {

  /** max allowed open files */
  public static final int MAX_FILES = 32;

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

  /** lseek set flag */
  public static final int SEEK_SET = 0;
  /** lseek cur flag */
  public static final int SEEK_CUR = 1;
  /** lseek end flag */
  public static final int SEEK_END = 2;

  /** current open files */
  private static final HashMap<Integer, OpenFile> open = new HashMap<Integer, OpenFile>();

  /**
   * Gets the next available file descriptor.
   *
   * @return next available file descriptor, or -1 if open files = max no. of open files
   */
  private static int getNextFD() {
    // start at 3 because 0,1,2 are reserved FDs
    for (int i = 3; i < MAX_FILES + 3; i++) {
      if (!open.containsKey(i))
        return i;
    }
    return -1;
  }

  /**
   * Simulates the open syscall from C.
   *
   * @param buffer file name buffer
   * @param oflags open flags
   * @return the file descriptor for the new file or -1 if an error occurs
   */
  public static int open(int buffer, int oflags, State state) throws SimulationException {
    int fd = getNextFD();
    if (fd != -1) {
      try {
        StringBuilder filename = new StringBuilder(0);
        char c;
        while ((c = (char) state.memory().loadByteUnsigned(buffer)) != '\0') {
          filename.append(c);
          buffer++;
        }
        open.put(fd, new OpenFile(filename.toString(), oflags));
        return fd;
      } catch (IOException e) {
        Logger.warning(e.getMessage());
        return -1;
      }
    }
    Logger.warning("(ecall) maximum number of open files exceeded");
    return -1;
  }

  /**
   * Simulates the lseek syscall from C.
   *
   * @param fd the file descriptor of the pointer that is going to be moved.
   * @param offset the offset of the pointer (measured in bytes).
   * @param whence the method in which the offset is to be interpreted (relative, absolute, etc.)
   * @return the offset of the pointer (in bytes) from the beginning of the file or -1 if an error occurs.
   */
  public static int lseek(int fd, int offset, int whence) {
    switch (fd) {
      case STDIN:
        Logger.warning("(ecall) could not lseek stdin");
        return -1;
      case STDOUT:
        return -1;
      case STDERR:
        return -1;
      default:
        if (open.containsKey(fd)) {
          return open.get(fd).lseek(offset, whence);
        } else {
          Logger.warning("(ecall) unk file descriptor '" + fd + "'");
          return -1;
        }
    }
  }

  /**
   * Simulates the read syscall from C.
   *
   * @param fd file descriptor of where to read the input
   * @param buffer pointer where the read content will stored
   * @param nbytes number of bytes to read before truncating the data
   * @param state program state
   * @return the number of bytes that were read or -1 if an error occurs
   * @throws SimulationException if an I/O error occurs
   */
  public static int read(int fd, int buffer, int nbytes, State state) throws SimulationException {
    // only available from stdin
    switch (fd) {
      case STDIN:
        String data = IO.readString(nbytes);
        for (int i = 0; i < data.length(); i++) {
          state.memory().storeByte(buffer++, (int) data.charAt(i));
        }
        return data.length();
      case STDOUT:
        Logger.warning("(ecall) reading from stdout not allowed");
        return -1;
      case STDERR:
        Logger.warning("(ecall) reading from stderr not allowed");
        return -1;
      default:
        if (open.containsKey(fd)) {
          return open.get(fd).read(buffer, nbytes, state);
        } else {
          Logger.warning("(ecall) unk file descriptor '" + fd + "'");
          return -1;
        }
    }
  }

  /**
   * Simulates the write syscall from C.
   *
   * @param fd file descriptor of where to write the output
   * @param buffer pointer to a buffer of at least nbytes bytes
   * @param nbytes the number of bytes to write
   * @param state program state
   * @return the number of bytes that were written or -1 if an error occurs
   * @throws SimulationException if an I/O error occurs
   */
  public static int write(int fd, int buffer, int nbytes, State state) throws SimulationException {
    switch (fd) {
      case STDIN:
        Logger.warning("(ecall) writing to stdin not allowed");
        return -1;
      case STDOUT:
      case STDERR:
        StringBuilder s = new StringBuilder(0);
        int wbytes = 0;
        for (int i = 0; i < nbytes; i++) {
          int b = state.memory().loadByteUnsigned(buffer++);
          if (b == 0) break;
          s.append((char) b);
          wbytes++;
        }
        if (fd == STDOUT) {
          IO.stdout().print(s.toString());
        } else {
          IO.stderr().print(s.toString());
        }
        return wbytes;
      default:
        if (open.containsKey(fd)) {
          return open.get(fd).write(buffer, nbytes, state);
        } else {
          Logger.warning("(ecall) unk file descriptor '" + fd + "'");
          return -1;
        }
    }
  }

  /**
   * Simulates the close syscall from C.
   *
   * @param fd file descriptor to be closed
   * @return 0 if success, -1 otherwise
   */
  public static int close(int fd) {
    switch (fd) {
      case STDIN:
        Logger.warning("(ecall) can not close stdin");
        return -1;
      case STDOUT:
        Logger.warning("(ecall) can not close stdout");
        return -1;
      case STDERR:
        Logger.warning("(ecall) can not close stderr");
        return -1;
      default:
        if (open.containsKey(fd)) {
          open.remove(fd);
          return 0;
        } else {
          Logger.warning("(ecall) unk file descriptor '" + fd + "'");
        }
    }
    return -1;
  }

  /** Represents an open file. */
  private static final class OpenFile {

    /** pathname attached to this open file */
    private final File file;
    private boolean read;
    private boolean write;
    private int pointer;

    /**
     * Creates a new open file.
     *
     * @param filename file name
     * @param flags array of open flags
     * @throws IOException if an I/O error occurs
     */
    public OpenFile(String filename, int oflags) throws IOException {
      file = FS.toFile(filename);
      read = false;
      write = false;
      boolean create = false;
      boolean create_new = false;
      boolean truncate = false;
      boolean append = false;
      // invalid open flags ?
      if ((oflags & O_MASK) == 0)
        throw new IOException("(ecall) invalid open flags");
      // parse flags
      if ((oflags & O_RDONLY) != 0) {
        read = true;
      }
      if ((oflags & O_WRONLY) != 0) {
        write = true;
      }
      if ((oflags & O_RDWR) != 0) {
        read = true;
        write = true;
      }
      if ((oflags & O_APPEND) != 0) {
        append = true;
      }
      if ((oflags & O_TRUNC) != 0) {
        truncate = true;
      }
      if ((oflags & O_CREAT) != 0) {
        create = true;
      }
      if ((oflags & O_EXCL) != 0) {
        create_new = true;
      }
      // file does not exists
      if (!create && !file.exists()) {
        throw new IOException("(ecall) file '" + file + "' does not exists");
      }
      // O_CREAT && O_EXCL
      if (create && create_new && file.exists()) {
        throw new IOException("(ecall) file '" + file + "' already exists");
      }
      // create file
      if (create && !file.exists()) {
        try {
          FS.createFile(file);
        } catch (IOException e) {
          throw new IOException("could not create file '" + file + "'");
        }
      }
      // truncate file if necessary
      if (truncate && file.length() > 0) {
        try {
          PrintWriter pw = new PrintWriter(file);
          pw.close();
        } catch (IOException e) {
          throw new IOException("could not truncate file '" + file + "'");
        }
      }
      // set pointer
      if (append) {
        pointer = getSize();
      } else {
        pointer = 0;
      }
    }

    /**
     * Simulates the lseek syscall from C.
     *
     * @param offset the offset of the pointer
     * @return the offset of the pointer from the beginning of the file.
     */
    public int lseek(int offset, int whence) {
      if (file.exists()) {
        switch (whence) {
          case SEEK_SET:
            pointer = Math.max(0, Math.min(getSize(), offset));
            return pointer;
          case SEEK_CUR:
            pointer += offset;
            pointer = Math.max(0, Math.min(getSize(), pointer));
            return pointer;
          case SEEK_END:
            pointer = getSize() + offset;
            pointer = Math.max(0, Math.min(getSize(), pointer));
            return pointer;
          default:
            return -1;
        }
      }
      return -1;
    }

    /**
     * Simulates the read syscall from C.
     *
     * @param buffer pointer where the read content will stored
     * @param nbytes number of bytes to read before truncating the data
     * @param state program state
     * @return the number of bytes that were read, -1 if error
     * @throws SimulationException if an exception occurs while reading open file
     */
    public int read(int buffer, int nbytes, State state) throws SimulationException {
      if (read) {
        try {
          byte[] bytes = FS.readBytes(file);
          int rbytes = 0;
          for (int i = pointer; i < Math.min(bytes.length, nbytes); i++) {
            state.memory().storeByte(buffer++, bytes[i]);
            rbytes++;
          }
          pointer += rbytes;
          return rbytes;
        } catch (IOException e) {
          Logger.warning("(ecall) could not read file '" + file + "'");
          return -1;
        }
      }
      Logger.warning("(ecall) file '" + file + "' not readable");
      return -1;
    }

    /**
     * Simulates the write syscall from C.
     *
     * @param buffer pointer to a buffer of at least nbytes bytes
     * @param nbytes the number of bytes to write
     * @param state program state
     * @return the number of bytes that were written, -1 if error
     * @throws SimulationException if an I/O error occurs
     */
    public int write(int buffer, int nbytes, State state) throws SimulationException {
      if (write) {
        try {
          StringBuilder s = new StringBuilder(0);
          for (int i = 0; i < nbytes; i++) {
            int c = state.memory().loadByteUnsigned(buffer++);
            if (c == 0) break;
            s.append((char) c);
          }
          String data = s.toString();
          byte[] bytes = FS.readBytes(file);
          ArrayList<Byte> all = new ArrayList<>();
          for (int i = 0; i < Math.min(pointer, bytes.length); i++) {
            all.add(bytes[i]);
          }
          for (int i = 0; i < data.length(); i++) {
            all.add((byte) data.charAt(i));
          }
          pointer += data.length();
          for (int i = pointer; i < bytes.length; i++) {
            all.add(bytes[i]);
          }
          byte[] out = new byte[all.size()];
          for (int i = 0; i < out.length; i++) {
            out[i] = all.get(i);
          }
          FS.writeBytes(file, out);
          return data.length();
        } catch (IOException e) {
          Logger.warning("(ecall) could not write file '" + file + "'");
          return -1;
        }
      }
      Logger.warning("(ecall) file '" + file + "' not writable");
      return -1;
    }

    /**
     * Returns file size in bytes.
     *
     * @return file size in bytes
     */
    private int getSize() {
      if (file.exists()) {
        if (file.length() > Integer.MAX_VALUE) {
          return Integer.MAX_VALUE;
        }
        return (int) file.length();
      }
      return 0;
    }

  }

}
