package IO;

import java.io.IOException;
import java.io.OutputStream;

/**
 * This class represents an output stream that compresses a maze byte array
 * The first 12 bytes represent the mazes properties - size, start position and end position.
 * Each one of the rest of the bytes - represents 8 cells of the maze. If the 8 cells are a
 * binary number - so the byte which represents them is the decimal number
 */
public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    /**
     * recieves the maze's byte array, and a start index - from which the method
     * should read 8 bytes and turns them to a decimal number
     * @param b The maze's byte array
     * @param index The start index
     * @return A decimal number, which represents the compression of 8 bits of the array
     */
    private byte binaryToByte(byte[] b,int index) {
        int x=0;
        for (int i=0;i<8 && index+i<b.length ;i++)
            x+=b[index+i]*Math.pow(2,7-i);
        return (byte)x;
    }

    /**
     * constructs a compressor object
     * @param out The inner output stream
     */
    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    /**
     * Writes one byte to the inner stream
     * @param b The byte to write
     */
    @Override
    public void write(int b) {
        try {
            out.write(b);
        } catch (IOException e) {

        }
    }

    /**
     * Writes a byte array to the inner stream, using compression
     * @param b The byte array to write
     */
    public void write(byte[] b) {
        try {
            int i;
            //write the first 12 bytes of the byte array - they are the maze's size and indexes of
            //start and end - no need to compress
            for (i = 0; i < 12; i++) {
                write(b[i]);
            }

            //for each 8 positions - compress them to one byte by converting to decimal - and write to the inner
            //stream
            for (; i < b.length; i += 8) {
                write(binaryToByte(b, i));
            }
        }
        catch (Exception e) {}
    }

}
