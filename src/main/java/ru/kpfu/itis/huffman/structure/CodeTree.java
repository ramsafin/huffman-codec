package ru.kpfu.itis.huffman.structure;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents a huffman tree of symbols.
 */
public class CodeTree {

    private InternalNode root;

    private List<List<Integer>> codes; // stores each symbol's code or null if symbol is absent

    public CodeTree(InternalNode root, int symbolLimit) { // symbolLimit is 255 (ascii)

        Objects.requireNonNull(root);

        this.root = root;

        codes = new ArrayList<>();

        for (int i = 0; i < symbolLimit; i++) {
            codes.add(null);
        }

        buildCodeList(root, new ArrayList<>()); // start building a code list
    }

    public InternalNode getRoot() {
        return root;
    }

    /**
     * Builds a huffman tree starting from the root node and zero prefix.
     * Prefix - is a code for a particular level of the tree (consists of zeros and ones).
     */
    private void buildCodeList(Node node, List<Integer> prefix) {

        if (node instanceof InternalNode) {
            InternalNode internalNode = (InternalNode) node;

            prefix.add(0); // go to the left
            buildCodeList(internalNode.getLeftChild(), prefix);
            prefix.remove(prefix.size() - 1);

            prefix.add(1); // go to the right
            buildCodeList(internalNode.getRightChild(), prefix);
            prefix.remove(prefix.size() - 1);

        } else if (node instanceof Leaf) {
            Leaf leaf = (Leaf) node;
            if (leaf.getSymbol() >= codes.size()) {
                throw new IllegalArgumentException("Символ за пределами таблицы ASCII");
            }

            if (codes.get(leaf.getSymbol()) != null) {
                throw new IllegalArgumentException("Символ имеет более одного кода");
            }
            //устанавливаем код символа
            codes.set(leaf.getSymbol(), new ArrayList<>(prefix));

        }
    }


    //возвращает код символа
    public List<Integer> getCode(int symbol) {
        if (symbol < 0) {
            throw new IllegalArgumentException("Symbol is not supported");
        } else if (codes.get(symbol) == null) {
            throw new IllegalArgumentException("No code for this symbol");
        }
        return codes.get(symbol);
    }


    public String toString() {
        StringBuilder sb = new StringBuilder();
        toString("", root, sb);
        return sb.toString();
    }


    private static void toString(String prefix, Node node, StringBuilder sb) {
        if (node instanceof InternalNode) {
            InternalNode internalNode = (InternalNode) node;
            toString(prefix + "0", internalNode.getLeftChild(), sb);
            toString(prefix + "1", internalNode.getRightChild(), sb);
        } else if (node instanceof Leaf) {
            sb.append(String.format("Code %s:" + "\t\t\t" + " Symbol %d%n", prefix, ((Leaf) node).getSymbol()));
        }
    }

}
