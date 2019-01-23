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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.TreeMap;


/**
 * The class ArgumentParser represents a useful command line argument parser.
 */
public final class ArgumentParser {

  /** stores the usage string */
  private String usage;
  /** stores the max length of all options */
  private int maxLength;
  /** stores the flags that were set */
  private HashMap<String, Integer> flags;
  /** stores the values that were set */
  private HashMap<Integer, String> values;
  /** stores aliases of values that were set */
  private HashMap<String, String> aliases;
  /** stores the valid options for this parser */
  private TreeMap<String, String> options;
  /** stores the valid options that requires a value */
  private HashSet<String> optsval;
  /** stores all the targers (aka files) */
  private ArrayList<File> targets;
  /** stores all the parser errors */
  private ArrayList<String> errors;

  /**
   * Constructor that initializes a newly ArgumentParser object with a usage message.
   *
   * @param usage the usage message
   */
  public ArgumentParser(String usage) {
    this.maxLength = -1;
    this.usage = usage;
    this.flags = new HashMap<String, Integer>();
    this.values = new HashMap<Integer, String>();
    this.aliases = new HashMap<String, String>();
    this.options = new TreeMap<String, String>();
    this.optsval = new HashSet<String>();
    this.targets = new ArrayList<File>();
    this.errors = new ArrayList<String>();
  }

  /**
   * This method adds a new option to the valid option list.
   *
   * @param option the option that starts with -
   * @param help help information attached to this option
   * @param alias alias for value if this option requires a value, otherwise null
   */
  public void add(String option, String help, String alias) {
    // only include options that starts with '-'
    if (option != null && option.startsWith("-")) {
      this.options.put(option, help);
      // this option requires a value
      if (alias != null) {
        this.optsval.add(option);
        this.aliases.put(option, alias);
        this.maxLength = Math.max(this.maxLength, option.length() + alias.length() + 2);
      } else
        this.maxLength = Math.max(this.maxLength, option.length() + 1);
    }
  }

  /**
   * This method adds a new option that does not requires a value to the valid option list.
   *
   * @param option the option that starts with -
   * @param help help information attached to this option
   */
  public void add(String option, String help) {
    this.add(option, help, null);
  }

  /**
   * This method parses all command line arguments, verifies errors (if any) and stores all useful information.
   *
   * @param args command line arguments
   */
  public void parse(String[] args) {
    // clear old contents
    this.flags.clear();
    this.values.clear();
    this.targets.clear();
    this.errors.clear();
    // examine all arguments
    String lastFlag = null;
    int lastFlagPos = -1;
    for (int i = 0; i < args.length; i++) {
      if (args[i].startsWith("-")) {
        // localize last flag
        lastFlagPos = i;
        lastFlag = args[i];
        // valid flag ?
        if (!this.options.containsKey(args[i]))
          this.errors.add("unknown option: " + args[i]);
        this.flags.put(args[i], i);
      } else
        // store every value
        this.values.put(i, args[i]);
    }
    // verify values
    for (String flag : this.flags.keySet()) {
      int position = this.flags.get(flag);
      // no value is present if needed?
      String value = this.values.get(position + 1);
      if (value == null && this.optsval.contains(flag))
        this.errors.add("argument '" + flag + "' requires a value");
      // examine for unexpected values
      if (position != lastFlagPos && value != null) {
        String invalid = "";
        if (!this.optsval.contains(flag))
          invalid += value + " ";
        for (int i = position + 2; this.values.get(i) != null; i++)
          invalid += this.values.get(i) + " ";
        if (!("".equals(invalid)))
          this.errors.add("unexpected value(s): " + invalid.trim());
      }
    }
    // set targets
    if (lastFlag != null) {
      int offset = lastFlagPos;
      if (this.optsval.contains(lastFlag))
        offset += 2;
      else
        offset += 1;
      // add targets starting at this offset
      for (int i = offset; this.values.get(i) != null; i++) {
        File f = new File(this.values.get(i));
        if (!this.targets.contains(f))
          this.targets.add(f);
        else
          Message.warning("duplicated files: " + f + " (ignoring)");
      }
    } else {
      // targets = all values
      for (int pos : this.values.keySet()) {
        File f = new File(this.values.get(pos));
        if (!this.targets.contains(f))
          this.targets.add(f);
        else
          Message.warning("duplicated files: " + f + " (ignoring)");
      }
    }
  }

  /**
   * This method pretty prints the argument parser usage.
   */
  public void print() {
    String newline = System.getProperty("line.separator");
    String out = "usage: " + this.usage + newline + newline;
    out += "available options:" + newline;
    for (String option : this.options.keySet()) {
      out += "  " + option;
      int length = option.length() + 1;
      if (this.aliases.get(option) != null) {
        String alias = this.aliases.get(option);
        out += " " + alias;
        length += alias.length() + 1;
      }
      for (int i = 0; i < (this.maxLength - length + 1); i++)
        out += " ";
      out += this.options.get(option) + newline;
    }
    IO.stdout.println(out.trim());
  }

  /**
   * This method verifies if a flag was set.
   *
   * @param flag the flag to check
   * @return true if flag was set, false if not
   */
  public boolean hasFlag(String flag) {
    return this.flags.containsKey(flag);
  }

  /**
   * This method returns the value attached to a flag (if any)
   *
   * @param flag the flag to check
   * @return the value attached to this flag, or null if the flag is not preset
   */
  public String value(String flag) {
    if (this.options.containsKey(flag) && this.optsval.contains(flag))
      return this.values.get(this.flags.get(flag) + 1);
    return null;
  }

  /**
   * This method is useful to verify if the argument parser has errors.
   *
   * @return true if the parser has errors, false otherwise
   */
  public boolean hasErrors() {
    return this.errors.size() > 0;
  }

  /**
   * This method returns the list of errors (if any).
   *
   * @return list of errors
   */
  public ArrayList<String> getErrors() {
    this.errors.trimToSize();
    return this.errors;
  }

  /**
   * This method returns all the targets (aka files).
   *
   * @return array of targets
   */
  public ArrayList<File> targets() {
    return this.targets;
  }

}
