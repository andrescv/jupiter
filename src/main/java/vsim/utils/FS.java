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
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.stream.Stream;

import org.apache.commons.io.FileUtils;


/**  Basic file system operations. */
public final class FS {

  /** valid RISC-V assembly files extensions. */
  public static final String[] extensions = {"s", "asm"};

  /**
   * Creates a new empty file.
   *
   * @param file file to create
   * @throws IOException if an I/O error occurs
   */
  public static void createFile(File file) throws IOException {
    FileUtils.touch(file);
  }

  /**
   * Moves a file.
   *
   * @param src the file to be moved
   * @param dest the destination file
   * @throws IOException if an I/O error occurs
   */
  public static void moveFile(File src, File dest) throws IOException {
    FileUtils.moveFile(src, dest);
  }

  /**
   * Deletes a file.
   *
   * @param file file to delete
   * @throws IOException if an I/O error occurs
   */
  public static void deleteFile(File file) throws IOException {
    FileUtils.forceDelete(file);
  }

  /**
   * Creates a new directory and if necessary all the parent directories.
   *
   * @param directory directory to create
   * @throws IOException if an I/O error occurs
   */
  public static void createDirectory(File directory) throws IOException {
    FileUtils.forceMkdir(directory);
  }

  /**
   * Moves a directory.
   *
   * @param src the directory to be moved.
   * @param dest the destination directory
   */
  public static void moveDirectory(File src, File dest) throws IOException {
    FileUtils.moveDirectory(src, dest);
  }

  /**
   * Deletes a directory recursively.
   *
   * @param directory directory to delete.
   * @throws IOException if an I/O error occurs
   */
  public static void deleteDirectory(File directory) throws IOException {
    FileUtils.deleteDirectory(directory);
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
    FileUtils.write(file, text, "utf-8", false);
  }

  /**
   * Reads a file.
   *
   * @param file file to read
   * @return file text content
   * @throws IOException if an I/O error occurs
   */
  public static String read(File file) throws IOException {
    return FileUtils.readFileToString(file, "utf-8");
  }

  /**
   * List all assembly files in directory recursively.
   *
   * @param directory directory path
   * @return list of all files inside the given directory
   */
  public static ArrayList<File> ls(File directory) {
    return new ArrayList<>(FileUtils.listFiles(directory, extensions, true));
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
    return file.isFile() && (name.endsWith(".s") || name.endsWith(".asm"));
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
   * Returns a human-readable version of the file size, where the input represents a specific file to measure.
   *
   * @param file file to measure
   */
  public static String sizeToDisplay(File file) {
    return FileUtils.byteCountToDisplaySize(FileUtils.sizeOfAsBigInteger(file));
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
