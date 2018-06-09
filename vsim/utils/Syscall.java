package vsim.utils;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public final class Syscall {

  public static final int PRINT_INT = 1;
  public static final int PRINT_FLOAT = 2;
  public static final int PRINT_STRING = 4;
  public static final int READ_INT = 5;
  public static final int READ_FLOAT = 6;
  public static final int READ_STRING = 8;
  public static final int SBRK = 9;
  public static final int EXIT = 10;
  public static final int PRINT_CHAR = 11;
  public static final int READ_CHAR = 12;
  public static final int OPEN = 13;
  public static final int READ = 14;
  public static final int WRITE = 15;
  public static final int CLOSE = 16;
  public static final int EXIT2 = 17;

  public static void printInt(int num) {
    System.out.print(num);
  }

  public static void printString(String str) {
    System.out.print(str);
  }

  public static int readInt() {
    try {
      return Integer.parseInt(readString());
    } catch (Exception e) {
      return 0;
    }
  }

  public static String readString() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      return br.readLine();
    } catch (IOException e) {
      return "";
    }
  }

  public static void exit() {
    System.exit(0);
  }

  public static void printChar(char c) {
    System.out.print(c);
  }

  public static char readChar() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      return (char)br.read();
    } catch (IOException e) {
      return '\0';
    }
  }

  public static void exit2(int code) {
    System.exit(code);
  }

}
