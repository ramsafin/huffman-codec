package ru.kpfu.itis.huffman;

import ru.kpfu.itis.huffman.structure.CodeTree;
import ru.kpfu.itis.huffman.structure.InternalNode;
import ru.kpfu.itis.huffman.structure.Leaf;
import ru.kpfu.itis.huffman.structure.Node;
import ru.kpfu.itis.streams.BitInputStream;

import java.io.IOException;
import java.util.Objects;


public class HuffmanDecoder {

    private BitInputStream input;

    private CodeTree codeTree;

    public HuffmanDecoder(BitInputStream in) {
        Objects.requireNonNull(in, "Stream is null");
        input = in;
    }

    public void setCodeTree(CodeTree codeTree) {

        this.codeTree = codeTree;
    }

    /**
     * Reads a symbols located on the root of the codeTree.
     */
    public int read() throws IOException {

        Objects.requireNonNull(codeTree, "Code tree is null");

        InternalNode currentNode = codeTree.getRoot();

        // got down the tree until the leafs
        while (true) {

            int temp = input.readNoEof();

            Node nextNode;

            if (temp == 0) {
                nextNode = currentNode.getLeftChild();
            } else {
                nextNode = currentNode.getRightChild();
            }

            if (nextNode instanceof Leaf) {
                return ((Leaf) nextNode).getSymbol();

            } else if (nextNode instanceof InternalNode) {
                currentNode = (InternalNode) nextNode;
            }
        }
    }

}
