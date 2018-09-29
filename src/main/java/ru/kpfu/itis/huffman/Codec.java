package ru.kpfu.itis.huffman;

import ru.kpfu.itis.huffman.structure.CodeTree;
import ru.kpfu.itis.huffman.HuffmanCompress;
import ru.kpfu.itis.huffman.HuffmanDecompress;

import java.io.File;

public class Codec {

    private CodeTree codeTree;

    public CodeTree getCodeTree() {
        return codeTree;
    }

    public void compress(File in, File out) {

        HuffmanCompress huffmanCompress = new HuffmanCompress();
        huffmanCompress.compress(in, out);
        codeTree = huffmanCompress.getCodeTree();
    }

    public void decompress(File in, File out) {

        HuffmanDecompress huffmanDecompress = new HuffmanDecompress(codeTree);
        huffmanDecompress.decompress(in, out);
    }


}
