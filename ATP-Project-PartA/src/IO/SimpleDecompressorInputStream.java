 package IO;

import java.io.IOException;
import java.io.InputStream;

 /**
  * this class gets simple compressed maze and decompress him
  */
 public class SimpleDecompressorInputStream extends InputStream {

    private InputStream in;

    /**
     * constructor
     * @param in input strea,
     */
    public SimpleDecompressorInputStream(InputStream in) {
        this.in = in;
    }

    /**
     * reads bytes into array
     * @param b array of bytes
     * @return
     */
    @Override
    public int read(byte[] b){
        int i;
        //read start indexes
        for (i = 0; i < 12; i++) {
            b[i]=(byte)read();
        }
        int byteCounter=12;
        int nextChar=1;
        int count;
        while ((count=read())!=-1) {
            byteCounter++;
            for(;count>0;count--,i++)
                b[i]=(byte)nextChar;
            nextChar=Math.abs(nextChar-1);
        }
        return byteCounter;
    }

    /**
     * @return next input from input stream
     */
    @Override
    public int read() {
        try {
            return in.read();
        } catch (IOException e) {

        }
        return -1;
    }
}
