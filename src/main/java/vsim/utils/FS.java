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

import static java.nio.file.StandardOpenOption.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;


/**  Basic file system operations. */
public final class FS {

  /**
   * Creates a new empty file.
   *
   * @param file filename
   * @return true if success, false if an error occurs while creating the new file
   * @throws IOException if an I/O error occurs
   */
  public static boolean create(File file) throws IOException {
    if (file.isFile()) {
      return file.createNewFile();
    } else {
      return file.mkdirs();
    }
  }

  /**
   * Deletes a file.
   *
   * @param file file to delete
   * @return true if success, false if an error occurs while deleting
   */
  public static boolean delete(File file) {
    try {
      return file.delete();
    } catch (SecurityException e) {
      return false;
    }
  }

  /**
   * Converts a String url to Path.
   *
   * @param url String url
   * @return converted url
   */
  public static Path toPath(String url) {
    return Paths.get(url);
  }

  /**
   * Converts a String url to File.
   *
   * @param url String url
   * @return converted url to File
   */
  public static File toFile(String url) {
    return new File(url);
  }

  /**
   * Writes text to a file.
   *
   * @param file file path
   * @param text text to write
   * @throws IOException if an I/O error occurs
   */
  public static void write(File file, String text) throws IOException {
    create(file);
    StandardOpenOption[] opts = new StandardOpenOption[] { WRITE, TRUNCATE_EXISTING };
    Files.write(file.toPath(), text.getBytes(), opts);
  }

  /**
   * Reads a file.
   *
   * @param file file to read
   * @return file text content
   * @throws IOException if an I/O error occurs
   */
  public static String read(File file) throws IOException {
    FileInputStream fis = new FileInputStream(file);
    byte[] data = new byte[(int) file.length()];
    fis.read(data);
    fis.close();
    return new String(data);
  }

  /**
   * List all assembly files in directory recursively.
   *
   * @param directory directory path
   * @return list of all files inside the given directory
   * @throws IOException if an I/O error occurs
   */
  public static ArrayList<File> ls(File directory) throws IOException {
    ArrayList<File> files = new ArrayList<>();
    if (directory.isDirectory()) {
      HashSet<FileVisitOption> visitOpts = new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS));
      Files.walkFileTree(directory.toPath(), visitOpts, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          File f = file.toFile();
          if (isAssemblyFile(f)) {
            files.add(f);
          }
          return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException e) throws IOException {
          return FileVisitResult.SKIP_SUBTREE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
          return FileVisitResult.CONTINUE;
        }
      });
    }
    files.trimToSize();
    return files;
  }

  /**
   * Reads a specific line from file.
   *
   * @param file file path
   * @param line line number to get
   * @return line String
   * @throws IOException if an I/O error occurs
   */
  public static String getLine(File file, int number) throws IOException {
    Stream<String> lines = Files.lines(file.toPath());
    return lines.skip(number - 1).findFirst().get();
  }

  /**
   * Verifies if the given path is a regular assembly file.
   *
   * @param path path to verify
   * @return true if the given path is a regular file, false if not
   */
  public static boolean isAssemblyFile(File file) {
    String name = file.getName();
    return file.isFile() && !file.isHidden() && (name.endsWith(".s") || name.endsWith(".asm"));
  }

  /**
   * Verifies if two paths are equal.
   *
   * @param a first path to verify
   * @param b second path to verify
   * @return {@code true} if paths are equal, {@code false} if not
   */
  public static boolean equals(File a, File b) {
    return a.getAbsolutePath().equals(b.getAbsolutePath());
  }

  /**
   * Verifies if a path is already in the given list of paths.
   *
   * @param file path to check
   * @param files list of paths
   * @return true if the given path is already in the given list of paths, false if not
   */
  public static boolean contains(File file, ArrayList<File> files) {
    for (File other : files) {
      if (equals(file, other)) {
        return true;
      }
    }
    return false;
  }

}
