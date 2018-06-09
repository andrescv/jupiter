package vsim.utils;

import java.io.IOException;
import vsim.simulator.State;
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
    int syscode = State.regfile.getRegister("a0");
    // match syscall code
    switch (syscode) {
      case PRINT_INT:
        Syscall.printInt();
        break;
      case PRINT_FLOAT:
        Message.warning("ecall: print float not implemented yet, only (RV32IM)");
        break;
      case PRINT_STRING:
        Syscall.printString();
        break;
      case READ_INT:
        Syscall.readInt();
        break;
      case READ_FLOAT:
        Message.warning("ecall: read float not implemented yet, only (RV32IM)");
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
    int num = State.regfile.getRegister("a1");
    System.out.print(num);
  }

  private static void printString() {
    int buffer = State.regfile.getRegister("a1");
    StringBuffer s = new StringBuffer();
    s.setLength(0);
    char c;
    while ((c = (char)State.memory.loadByteUnsigned(buffer)) != '\0') {
      s.append(c);
      buffer++;
    }
    System.out.print(s);
  }

  private static void readInt() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      State.regfile.setRegister("a0", Integer.parseInt(br.readLine()));
    } catch (IOException e) {
      Message.warning("ecall: could not read integer");
    } catch (NumberFormatException e) {
      Message.warning("ecall: invalid integer input");
    }
  }

  private static void readString() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      String s = br.readLine();
      int buffer = State.regfile.getRegister("a1");
      int length = State.regfile.getRegister("a2");
      for (int i = 0; i < Math.min(length, s.length()); i++) {
        char c = s.charAt(i);
        State.memory.storeByte(buffer++, c);
      }
    } catch (IOException e) {
      Message.warning("ecall: could not read string");
    }
  }

  private static void sbrk() {
    int numBytes = State.regfile.getRegister("a1");
    if (numBytes > 0) {
      int address = State.memory.allocateBytesFromHeap(numBytes);
      State.regfile.setRegister("a0", address);
    } else
      Message.warning("ecall: number of bytes should be > 0");
  }

  private static void exit() {
    System.exit(0);
  }

  private static void printChar() {
    int address = State.regfile.getRegister("a1");
    System.out.print((char)State.memory.loadByte(address));
  }

  private static void readChar() {
    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    try {
      State.regfile.setRegister("a0", br.read());
    } catch (IOException e) {
      Message.warning("ecall: could not read char");
    }
  }

  private static void exit2() {
    int status = State.regfile.getRegister("a1");
    System.exit(status);
  }

}
