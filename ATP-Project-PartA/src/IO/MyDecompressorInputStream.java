package IO;

import java.io.IOException;
import java.io.InputStream;

/**
 * This class represents an input stream that decompresses a maze byte array
 * The first 12 bytes represent the mazes properties - size, start position and end position.
 * Each one of the rest of the bytes - represents 8 cells of the maze. The way to decompress
 * each byte is to turn it to a binary number - of 8 bits.
 */
public class MyDecompressorInputStream extends InputStream {
    private InputStream in;

    /**
     * constructs a decompressor object
     * @param in The inner input stream
     */
    public MyDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * Reads one byte from the inner input stream
     * @return The byte which was read
     */
    @Override
    public int read() {
        try {
            return in.read();
        } catch (IOException e) {

        }
        return -1;
    }

    /**
     * Recieves a byte which represents a decimal number, and turns it into a binary number of 8 bit
     * @param b The byte to convert to a binary number
     * @return A byte array of length 8, which is the binary representation of b
     */
    private static byte[] numToBinary(byte b) {
        int number = b & 0xFF;
        byte[] bin=new byte[8];
        for( int i=7;i>=0;i--) {
            bin[i]=(byte)(number%2);
            number=number/2;
        }
        return bin;
    }

    /**
     * Recieves a byte array, and reads into it the decompressed data from the inner stream
     * @param b The byte array to read into
     * @return 1 if was successful - otherwise 0
     */
    public int read(byte[] b) {
        try {
            int i;
            //Readd the 12 first bytes which represent the maze's size, start and end positions
            for (i = 0; i < 12; i++) {
                b[i] = (byte) read();
            }
            //For each byte from the inner stream - decompress it by converting it to binary
            //and add to the b array
            for (; i < b.length; ) {
                byte[] bin = numToBinary((byte) in.read());
                for (int j = 0; j < 8 && i < b.length; i++, j++)
                    b[i] = bin[j];
            }
            return 1;
        }
        catch (Exception e) {}
        return 0;
    }

}
