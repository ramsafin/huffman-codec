package ru.kpfu.itis.streams;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Objects;


public class BitOutputStream {

    private OutputStream output;

    private int currentByte;

    private int numBitsInCurrentByte;


    public BitOutputStream(OutputStream out) {

        Objects.requireNonNull(out);

        output = out;
        currentByte = 0;
        numBitsInCurrentByte = 0;
    }


    public void write(int b) throws IOException {

        if (!(b == 0 || b == 1)) {
            throw new IllegalArgumentException("argument must be 0 or 1");
        }

        currentByte = currentByte << 1 | b;
        numBitsInCurrentByte++;

        if (numBitsInCurrentByte == 8) {

            output.write(currentByte);
            numBitsInCurrentByte = 0;
        }
    }

    public void flush() throws IOException {

        while (numBitsInCurrentByte != 0) {
            write(0);
        }
    }

}
