package vsim.assembler.lexer;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileNotFoundException;


final class Reader {

    protected static final char EOL = 0xffff;

    private int pos;
    private String current;
    private String filename;
    private BufferedReader buffer;

    protected Reader(String filename) {
        try {
            FileReader fr = new FileReader(new File(filename));
            this.buffer = new BufferedReader(fr);
        } catch (FileNotFoundException e) {
            System.err.print("vsim: file not found: ");
            System.err.println(filename);
            System.exit(-1);
        }
        this.filename = filename;
        this.pos = 0;
        this.current = null;
    }

    protected String getFilename() {
        return this.filename;
    }

    protected String getCurrentLine() {
        return this.current;
    }

    protected boolean setNextLine() {
        try {
            this.current = this.buffer.readLine();
            this.pos = 0;
        } catch (IOException e) {
            System.err.print("vsim: file can not be read: ");
            System.err.println(this.filename);
            System.exit(-1);
        } catch (NullPointerException e) {
            this.current = null;
        }
        return this.current != null;
    }

    protected char get() {
        // char at index = pos
        char c = EOL;
        if (this.current != null && this.pos < this.current.length())
            c = Character.toLowerCase(this.current.charAt(this.pos));
        // increment pos
        this.pos++;
        // special EOL (end of line)
        return c;
    }

    protected void backward() {
        this.pos = (--this.pos < 0) ? 0 : this.pos;
    }

    @Override
    public String toString() {
        return "Reader(file: " + this.filename + ")";
    }

}