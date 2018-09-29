package ru.kpfu.itis.huffman;

import ru.kpfu.itis.huffman.structure.CodeTree;
import ru.kpfu.itis.streams.BitInputStream;

import java.io.*;
import java.util.Objects;

public class HuffmanDecompress {

    private CodeTree codeTree;


    public HuffmanDecompress(CodeTree codeTree) {

        Objects.requireNonNull(codeTree);

        this.codeTree = codeTree;
    }


    public void decompress(File input, File output) {

        try(InputStream in = new BufferedInputStream(new FileInputStream(input));
            OutputStream out = new BufferedOutputStream(new FileOutputStream(output)) ){

            BitInputStream b_in = new BitInputStream(in);

            decompress(codeTree,b_in,out);

        }catch (IOException e){
            e.printStackTrace();
        }
    }


    private  void decompress(CodeTree code, BitInputStream in, OutputStream out) throws IOException {
        HuffmanDecoder dec = new HuffmanDecoder(in);
        dec.setCodeTree(code);
        while (true) {
            int symbol = dec.read();
            if (symbol == 256){  // End of file symbol
                break;
            }
            out.write(symbol);
        }
    }
}
