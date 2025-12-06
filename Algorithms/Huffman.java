import java.util.*;

/** Huffman encoding algorithm implementation. Given an input array of characters and a String, 
 * returns their binary encodings as a Map of characters to their encodings.
 */
public class Huffman {
    private Map<String, String> encodings;

    public Huffman() {
        this.encodings = new HashMap<>();
    }

    public Map<String, String> getEncodings(String[] inputs, String target) {
        List<Node> tuples = new ArrayList<>();

        for (String input : inputs) { 
            LocTuple<String, Integer> t = new LocTuple<>(input, getFreq(input, target));
            tuples.add(new Node(t));
        }
        tuples.sort(new TupleComparator());

        while (tuples.size() > 1) {
            Node left = tuples.remove(0);
            Node right = tuples.remove(0);

            LocTuple<String, Integer> newNode = new LocTuple<>(
                left.getContents().getFirst() + "," + right.getContents().getFirst(),
                left.getContents().getSecond() + right.getContents().getSecond()
            );

            tuples.add(new Node(newNode, left, right));
            tuples.sort(new TupleComparator());
        }

        Node root = tuples.get(0);
        traverse(root, "");

        return this.encodings;
    }

    private static int getFreq(String input, String target) {
        List<String> chars = Arrays.asList(target.replaceAll("\\s", "").split(""));
        return Collections.frequency(chars, input);
    }

    private void traverse(Node node, String current) {
        if (node.getLeft() == null && node.getRight() == null) {
            String character = node.getContents().getFirst();
            encodings.put(character, current);
            return;
        }
        if (node.getLeft() != null) {
            traverse(node.getLeft(), current + "0");
        }
        if (node.getRight() != null) {
            traverse(node.getRight(), current + "1");
        }
    }

    public static void main(String[] args) {
        Huffman encoder = new Huffman();
        Map<String, String> encodings = encoder.getEncodings(new String[]{"a", "b", "c", "d", "e", "f", "g", "h", "i", "j"}, "eaaaaaaa eeeeep b yerpity");

        System.out.println("Huffman Encodings: " + encodings);
    }
}

class Node {
    private Node left;
    private Node right;
    private LocTuple<String, Integer> contents;

    public Node(LocTuple<String, Integer> contents) {
        this.contents = contents;
        this.left = null;
        this.right = null;
    }

    public Node(LocTuple<String, Integer> contents, Node left, Node right) {
        this.contents = contents;
        this.left = left;
        this.right = right;
    }

    public Node getLeft() {
        return left;
    }

    public Node getRight() {
        return right;
    }

    public LocTuple<String, Integer> getContents() {
        return contents;
    }
}

class Tuple<F, S> {
    private final F first;
    private final S second;

    public Tuple(F first, S second) {
        this.first = first;
        this.second = second;
    }

    public F getFirst() {
        return first;
    }

    public S getSecond() {
        return second;
    }
}

class TupleComparator implements Comparator<Node> {
    @Override
    public int compare(Node t1, Node t2) {
        return Integer.compare(t1.getContents().getSecond(), t2.getContents().getSecond());
    } 
}
