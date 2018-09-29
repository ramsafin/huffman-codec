package ru.kpfu.itis.huffman;

import ru.kpfu.itis.huffman.structure.CodeTree;
import ru.kpfu.itis.huffman.structure.SymbolFrequencyTable;
import ru.kpfu.itis.streams.BitOutputStream;

import java.io.*;


public class HuffmanCompress {

    private CodeTree codeTree;

    public CodeTree getCodeTree() {

        return codeTree;
    }

    /**
     * Compresses an input files into an output file.
     *
     * @param input  a file being compressed.
     * @param output resulting compressed file.
     */
    public void compress(File input, File output) {

        try (OutputStream out = new BufferedOutputStream(new FileOutputStream(output));
             InputStream in = new BufferedInputStream(new FileInputStream(input))) {

            BitOutputStream b_out = new BitOutputStream(out);

            SymbolFrequencyTable symbolFrequencyTable = estimateSymbolFrequencies(input);

            symbolFrequencyTable.increment(256); // end of file symbol

            codeTree = symbolFrequencyTable.buildCodeTree();

            compress(codeTree, in, b_out);

            b_out.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static SymbolFrequencyTable estimateSymbolFrequencies(File file) throws IOException {

        try (InputStream input = new BufferedInputStream(new FileInputStream(file))) {

            SymbolFrequencyTable freq = new SymbolFrequencyTable(new int[257]);

            int b;

            while ((b = input.read()) != -1) {

                freq.increment(b);
            }
            return freq;
        }
    }


    private static void compress(CodeTree code, InputStream in, BitOutputStream out) throws IOException {

        HuffmanEncoder enc = new HuffmanEncoder(out);
        enc.setCodeTree(code);
        while (true) {
            int b = in.read();
            if (b == -1) {
                break;
            }
            enc.write(b);
        }
        enc.write(256);  // End of file symbol
    }

}
