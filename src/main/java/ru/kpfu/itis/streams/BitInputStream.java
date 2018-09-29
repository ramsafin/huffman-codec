package ru.kpfu.itis.streams;

import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class BitInputStream {

    private InputStream input;

    private int nextBits;

    private int numBitsRemaining;

    private boolean isEndOfStream;


    public BitInputStream(InputStream in) {

        Objects.requireNonNull(in);

        input = in;
        numBitsRemaining = 0;
        isEndOfStream = false;
    }


    public int read() throws IOException {

        if (isEndOfStream) {
            return -1;
        }
        if (numBitsRemaining == 0) {

            nextBits = input.read();

            if (nextBits == -1) {

                isEndOfStream = true;
                return -1;
            }
            numBitsRemaining = 8;
        }
        numBitsRemaining--;

        return (nextBits >>> numBitsRemaining) & 1;
    }


    public int readNoEof() throws IOException {

        int result = read();

        if (result != -1) {
            return result;
        } else {
            throw new EOFException("End of file reached!");
        }
    }

}
