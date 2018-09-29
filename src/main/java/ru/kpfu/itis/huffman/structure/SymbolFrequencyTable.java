package ru.kpfu.itis.huffman.structure;

import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;


public class SymbolFrequencyTable {

    private int[] frequencies;


    public SymbolFrequencyTable(int[] frequencies) {

        Objects.requireNonNull(frequencies);

        if (frequencies.length < 2) {
            throw new IllegalArgumentException("At least 2 symbols are required!");
        }

        this.frequencies = frequencies.clone();
    }


    public int getSymbolLimit() {
        return frequencies.length;
    }


    public void increment(int symbol) {
        if (symbol < 0 || symbol >= frequencies.length) {
            throw new IllegalArgumentException("Symbols is out of bounds");
        }
        frequencies[symbol]++;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < frequencies.length; i++)
            sb.append(String.format("%d\t%d%n", i, frequencies[i]));
        return sb.toString();
    }


    public CodeTree buildCodeTree() {

        Queue<NodeWithFrequency> q = new PriorityQueue<>();

        // add nodes of symbols
        for (int i = 0; i < frequencies.length; i++) {
            if (frequencies[i] > 0) {
                q.add(new NodeWithFrequency(new Leaf(i), i, frequencies[i]));
            }
        }

        if (q.size() <= 1) {
            throw new IllegalArgumentException("At least 2 symbols are required!");
        }

        // merge two nodes with less frequent symbols
        while (q.size() > 1) {

            NodeWithFrequency nf1 = q.remove();

            NodeWithFrequency nf2 = q.remove();

            q.add(new NodeWithFrequency(
                    new InternalNode(nf1.node, nf2.node),
                    Math.min(nf1.lowestSymbol, nf2.lowestSymbol),
                    nf1.frequency + nf2.frequency));
        }

        return new CodeTree((InternalNode) q.remove().node, frequencies.length);
    }

    private static class NodeWithFrequency implements Comparable<NodeWithFrequency> {

        public final Node node;

        public final int lowestSymbol;

        public final long frequency;


        public NodeWithFrequency(Node node, int lowestSymbol, long freq) {
            this.node = node;
            this.lowestSymbol = lowestSymbol;
            this.frequency = freq;
        }


        public int compareTo(NodeWithFrequency other) {

            // compare by symbol frequency
            if (frequency < other.frequency) {
                return -1;
            } else if (frequency > other.frequency) {
                return 1;
            }
            // compare by symbol value
            else if (lowestSymbol < other.lowestSymbol) {
                return -1;
            } else if (lowestSymbol > other.lowestSymbol) {
                return 1;
            }
            return 0;
        }

    }

}
