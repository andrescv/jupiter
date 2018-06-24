package vsim;

import vsim.utils.Cmd;
import vsim.utils.Message;
import java.util.ArrayList;
import vsim.simulator.Simulator;

public final class VSim {

  public static void main(String[] args) {
    // run this only if some argument(s) is/are passed
    if (args.length > 0) {
      // parse arguments
      ArrayList<String> files = Cmd.parse(args);
      // simulate or debug
      Cmd.title();
      // only if files are provided
      if (files.size() > 0) {
        if (!Settings.DEBUG)
          Simulator.simulate(files);
        else
          Simulator.debug(files);
      } else
        Message.panic("no input files");
    }
  }

}
