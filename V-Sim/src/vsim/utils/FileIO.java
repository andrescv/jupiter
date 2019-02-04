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

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitOption;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import vsim.Settings;


/** Basic file operations */
public final class FileIO {

  /**
   * This method adds all assembler files in current user directory into an array list.
   *
   * @param files array list where file paths will be added
   * @return true if success, false otherwise
   */
  public static boolean getFilesInDir(ArrayList<File> files) {
    try {
      HashSet<FileVisitOption> visitOpts = new HashSet<FileVisitOption>(Arrays.asList(FileVisitOption.FOLLOW_LINKS));
      Path dir = Paths.get(Settings.DIR.toString());
      Files.walkFileTree(dir, visitOpts, Integer.MAX_VALUE, new SimpleFileVisitor<Path>() {

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
          if (attrs.isRegularFile()) {
            String path = file.toString();
            File f = new File(path);
            if ((path.endsWith(".s") || path.endsWith(".asm")) && !files.contains(f))
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
    } catch (Exception e) {
      Message.error("An error occurred while recursively searching the files in directory (aborting...)");
      if (!Settings.GUI)
        System.exit(1);
      return false;
    }
    return true;
  }

  /**
   * Adds trap handler to file list.
   *
   * @param files array list where trap handler file will be added
   */
  public static void addTrapHandler(ArrayList<File> files) {
    if (files.size() > 0) {
      // add trap handler at the head of the list
      if (Settings.TRAP != null && Settings.TRAP.exists())
        files.add(0, Settings.TRAP);
      else
        Settings.TRAP = null;
    }
  }

}
