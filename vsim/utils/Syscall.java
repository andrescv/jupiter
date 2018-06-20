package vsim.utils;

import vsim.Globals;
import vsim.Settings;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.InputStreamReader;


public final class Syscall {

  // syscall codes
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

  public static void handler() {
    int syscode = Globals.regfile.getRegister("a0");
    // match syscall code
    switch (syscode) {
      case PRINT_INT:
        Syscall.printInt();
        break;
      case PRINT_FLOAT:
        Syscall.printFloat();
        break;
      case PRINT_STRING:
        Syscall.printString();
        break;
      case READ_INT:
        Syscall.readInt();
        break;
      case READ_FLOAT:
        Syscall.readFloat();
        break;
      case READ_STRING:
        Syscall.readString();
        break;
      case SBRK:
        Syscall.sbrk();
        break;
      case EXIT:
        Syscall.exit();
        break;
      case PRINT_CHAR:
        Syscall.printChar();
        break;
      case READ_CHAR:
        Syscall.readChar();
        break;
      case OPEN:
        Message.warning("ecall: open file not implemented yet");
        break;
      case READ:
        Message.warning("ecall: read file not implemented yet");
        break;
      case WRITE:
        Message.warning("ecall: write to file not implemented yet");
        break;
      case CLOSE:
        Message.warning("ecall: close file not implemented yet");
        break;
      case EXIT2:
        Syscall.exit2();
        break;
      default:
        Message.warning("ecall: invalid syscall code: " + syscode);
    }
  }

  private static void printInt() {
    int num = Globals.regfile.getRegister("a1");
    System.out.print(num);
  }

  private static void printFloat() {
    float num = Globals.fregfile.getRegister("f12");
    System.out.print(num);
  }

  private static void printString() {
    int buffer = Globals.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer();
    s.setLength(0);
    char c;
    while ((c = (char)Globals.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    System.out.print(s);
  }

  private static void readInt() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      Globals.regfile.setRegister("a0", Integer.parseInt(br.readLine()));
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: integer number could not be read");
    } catch (NumberFormatException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: invalid integer number");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  private static void readFloat() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      Globals.fregfile.setRegister("f0", Float.parseFloat(br.readLine()));
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: float number could not be read");
    } catch (NumberFormatException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: invalid float number");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  private static void readString() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String s = br.readLine();
      int buffer = Globals.regfile.getRegister("a1");
      int length = Globals.regfile.getRegister("a2");
      for (int i = 0; i < Math.min(length, s.length()); i++) {
        char c = s.charAt(i);
        Globals.memory.storeByte(buffer++, c);
      }
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: string could not be read");
    } catch (NullPointerException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: no input (null)");
    }
  }

  private static void sbrk() {
    int numBytes = Globals.regfile.getRegister("a1");
    if (numBytes > 0) {
      int address = Globals.memory.allocateBytesFromHeap(numBytes);
      Globals.regfile.setRegister("a0", address);
    } else {
      if (!Settings.QUIET)
        Message.warning("ecall: number of bytes should be > 0");
    }
  }

  private static void exit() {
    System.out.println();
    Message.log("exit(0)");
    System.exit(0);
  }

  private static void printChar() {
    char c = (char)Globals.regfile.getRegister("a1");
    System.out.print(c);
  }

  private static void readChar() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      Globals.regfile.setRegister("a0", br.read());
    } catch (IOException e) {
      if (!Settings.QUIET)
        Message.warning("ecall: char could not be read");
    }
  }

  private static void exit2() {
    int status = Globals.regfile.getRegister("a1");
    System.out.println();
    Message.log("exit(" + status + ")");
    System.exit(status);
  }

}
