package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * this class use is to compress a maze.
 * it compressed the integer bytes to an integer value (max - 255) by collecting the
 * number of time the number appear in the maze in a sequence,
 * and than write it as a byte in byte array
 */
public class SimpleCompressorOutputStream extends OutputStream {
    private OutputStream out;
    private int counter;
    private int last;

    /**
     * constructor
     * @param out output stream
     */
    public SimpleCompressorOutputStream(OutputStream out) {
        this.out = out;
        this.counter = 0;
        this.last=1;
    }

    /**
     * get byte and write it if the sequence is changed or if the
     * sequence appear counter is 255 - means full byte use.
     * @param b integer byte
     */
    @Override
    public void write(int b) {
        try {
            if (last == b) {
                counter++;
                if (counter == 255) {
                    out.write((byte) 255);
                    counter = 0;
                }
            } else {
                last = b;
                out.write((byte) counter);
                counter = 1;
            }
        }
        catch (Exception e) {}
    }

    /**
     * write to out into byte array
     * @param b byte array of bytes
     */
    public void write(byte [] b) {
        try {
            for (int i = 0; i < 12; i++) {
                out.write(b[i]);
            }
            for (int i = 12; i < b.length; i++) {
                write(b[i]);
            }
            if (counter != 0)
                out.write(counter);
        }
        catch (Exception e ) {}
    }
}
