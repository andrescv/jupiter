package vsim.assembler;

import vsim.utils.Colorize;


public final class Sym {

  private Segment segment;
  private int address;

  public Sym(Segment segment, int address) {
    this.segment = segment;
    this.address = address;
  }

  public void setAddress(int address) {
    this.address = address;
  }

  public Segment getSegment() {
    return this.segment;
  }

  public int getAddress() {
    return this.address;
  }

  @Override
  public String toString() {
    return String.format(
      "[%s] @ %s",
      Colorize.green(this.segment.toString().toLowerCase()),
      Colorize.blue(String.format("0x%08x", this.address))
    );
  }

}
