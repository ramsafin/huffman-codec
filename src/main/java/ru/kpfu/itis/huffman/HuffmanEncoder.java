package ru.kpfu.itis.huffman;

import ru.kpfu.itis.huffman.structure.CodeTree;
import ru.kpfu.itis.streams.BitOutputStream;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public final class HuffmanEncoder {

    private BitOutputStream output;

    private CodeTree codeTree;

    public HuffmanEncoder(BitOutputStream out) {
        Objects.requireNonNull(out);
        output = out;
    }

    public CodeTree getCodeTree() {
        return codeTree;
    }

    public void setCodeTree(CodeTree codeTree) {
        this.codeTree = codeTree;
    }


    public void write(int symbol) throws IOException {

        Objects.requireNonNull(codeTree);

        List<Integer> bits = codeTree.getCode(symbol);
        for (int b : bits) {
            output.write(b);
        }
    }

}
