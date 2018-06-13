package vsim;

import vsim.utils.Cmd;
import vsim.utils.Message;
import java.io.IOException;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public final class VSim {

  public static void main(String[] args) {
    ArrayList<String> files = Cmd.parse(args);
    if (files.size() > 0) {
      // TODO: assemble files
    } else {
      Cmd.title();
      BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
      while (true) {
        Cmd.prompt();
        try {
          String line = br.readLine();
          if (line == null) {System.out.println(); continue; }
          line = line.trim();
          if (line.equals("quit")) break;
          if (line.equals("exit")) break;
          // TODO: interpret line
        } catch (IOException e) {
          Message.panic("could not read input");
        }
      }
    }
  }

}
