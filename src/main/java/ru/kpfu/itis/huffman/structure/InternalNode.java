package ru.kpfu.itis.huffman.structure;

import java.util.Objects;

public final class InternalNode extends Node {

    private final Node leftChild;
    private final Node rightChild;


    public InternalNode(Node leftChild, Node rightChild) {

        Objects.requireNonNull(leftChild);
        Objects.requireNonNull(rightChild);

        this.leftChild = leftChild;
        this.rightChild = rightChild;
    }


    public Node getLeftChild() {
        return leftChild;
    }

    public Node getRightChild() {
        return rightChild;
    }

}