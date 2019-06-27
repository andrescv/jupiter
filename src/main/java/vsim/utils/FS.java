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

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Stream;

import vsim.Logger;


/**  Basic file system operations. */
public final class FS {

  /**
   * Creates a new empty file.
   *
   * @param file filename
   * @return true if success, false if an error occurs while creating the new file
   */
  public static boolean create(Path file) {
    try {
      Files.createFile(file);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Deletes a file.
   *
   * @param file file to delete
   * @return true if success, false if an error occurs while deleting
   */
  public static boolean delete(Path file) {
    try {
      Files.delete(file);
      return true;
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Writes text to a file.
   *
   * @param file file path
   * @param text text to write
   * @return true if success, false if an error occurs while writing
   * @throws IOException if an I/O error occurs
   */
  public static boolean write(Path file, String text) throws IOException {
    StandardOpenOption[] opts = new StandardOpenOption[] { WRITE, TRUNCATE_EXISTING };
    Files.write(file, text.getBytes(), opts);
    return true;
  }

  /**
   * Reads a file.
   *
   * @param file file to read
   * @return file text content, or null if an error occurs while reading the file
   * @throws IOException if an I/O error occurs
   */
  public static String read(Path file) throws IOException {
    FileInputStream fis = new FileInputStream(file.toFile());
    byte[] data = new byte[(int) file.toFile().length()];
    fis.read(data);
    fis.close();
    return new String(data);
  }

  /**
   * List directory files recursively.
   *
   * @param dir directory
   * @return list of all files inside the given directory
   */
  public static ArrayList<Path> ls(Path dir) {
    ArrayList<Path> files = new ArrayList<>();
    try {
      if (Files.isDirectory(dir)) {
        HashSet<FileVisitOption> visitOpts = new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS));
        Files.walkFileTree(dir, visitOpts, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {
          @Override
          public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            if (attrs.isRegularFile()) {
              files.add(file);
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
    } catch (IOException e) {
      Logger.warning("something occurred while recursively searching the files in directory");
    } finally {
      files.trimToSize();
      return files;
    }
  }

  /**
   * Reads a specific line from file.
   *
   * @param file file path
   * @param line line number to get
   * @return line String or null if an error occurs
   */
  public static String getLine(Path file, int number) {
    try {
      Stream<String> lines = Files.lines(file);
      return lines.skip(number - 1).findFirst().get();
    } catch (IOException e) {
      return null;
    }
  }

  /**
   * Returns true if the given path is a directory, false if not.
   *
   * @param path path to verify
   * @return true if the given path is a directory, false if not
   */
  public static boolean isDirectory(Path path) {
    return Files.isDirectory(path);
  }

  /**
   * Returns true if the given path is a regular file, false if not.
   *
   * @param path path to verify
   * @return true if the given path is a regular file, false if not
   */
  public static boolean isRegularFile(Path file) {
    return Files.isRegularFile(file);
  }

  /**
   * Verifies if two paths are equal.
   *
   * @param a first path to verify
   * @param b second path to verify
   * @return {@code true} if paths are equal, {@code false} if not
   */
  public static boolean equals(Path a, Path b) {
    try {
      return Files.isSameFile(a, b);
    } catch (IOException e) {
      return false;
    }
  }

  /**
   * Verifies if a path is already in the given list of paths.
   *
   * @param path path to check
   * @param paths list of paths
   * @return true if the given path is already in the given list of paths, false if not
   */
  public static boolean contains(Path path, ArrayList<Path> paths) {
    for (Path other : paths) {
      if (equals(path, other)) {
        return true;
      }
    }
    return false;
  }

}
