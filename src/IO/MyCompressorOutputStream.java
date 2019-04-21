package IO;

import java.io.IOException;
import java.io.OutputStream;

public class MyCompressorOutputStream extends OutputStream {
    private OutputStream out;

    public MyCompressorOutputStream(OutputStream out) {
        this.out = out;
    }

    //TODO implement and document
    @Override
    public void write(int b) throws IOException {

    }
    //TODO implement and document
    @Override
    public void write(byte[] b){

    }
}
