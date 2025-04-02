import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class NodeNext {
    public static record NodeAndDepth(nNode node, int depth){};

    private void checkEqual(List<NodeAndDepth> allNodes, nNode curNode) {
        for (NodeAndDepth val : allNodes) {
            if (val.node.equals(curNode.left())) {
                curNode.left().setNext(val.node.next());
                break;
            }
        }
    }
    /**Assign next value to right node if exists with BFS */
    public void assignNextNodes(nNode root) {
        Queue<NodeAndDepth> nodes = new LinkedList<>();
        List<NodeAndDepth> allNodes = new ArrayList<>();
        nodes.add(new NodeAndDepth(root, 0));
        while (!nodes.isEmpty()) {
            NodeAndDepth head = nodes.remove();
            nNode parent = head.node;
            if (parent.left() != null) {
                NodeAndDepth child = new NodeAndDepth(parent.left(), head.depth + 1);
                nodes.add(child);
                allNodes.add(child);
            }
            if (parent.right() != null) {
                NodeAndDepth child = new NodeAndDepth(parent.right(), head.depth + 1);
                nodes.add(child);
                allNodes.add(child);
            }
        }
        // assign next pointers (iterate through allNodes and assign nth next node to (n+1th) node if same depth) in list
        for (int i = 0; i < allNodes.size() - 1; i++) {
            if (allNodes.get(i).depth() == allNodes.get(i+1).depth()) {
                allNodes.get(i).node.setNext(allNodes.get(i+1).node);
            }
        }

        // iterate through tree from root and assign next pointers
        Queue<nNode> finalNodes = new LinkedList<>();
        finalNodes.add(root);

        while (!finalNodes.isEmpty()) {
            nNode curNode = finalNodes.remove();
            if (curNode.left() != null) {
                checkEqual(allNodes, curNode);
                finalNodes.add(curNode.left());
            }
            if (curNode.right() != null) {
                checkEqual(allNodes, curNode);
                finalNodes.add(curNode.right());
            }
        }
    }

    public static void main(String[] args) {
        NodeNext nodeNext = new NodeNext();
        nNode node = new nNode(10, new nNode(15, null, null), new nNode(20, null, null));
        nodeNext.assignNextNodes(node);
    }
}

class nNode {
    private int val;
    private nNode left;
    private nNode right;
    private nNode next;

    public nNode(int val, nNode left, nNode right) {
        this.val = val;
        this.left = left;
        this.right = right;
        this.next = null;
    }

    public int getVal() {
        return val;
    }

    public nNode left() {
        return left;
    }

    public nNode right() {
        return right;
    }

    public nNode next() {
        return next;
    }

    public void setNext(nNode next) {
        this.next = next;
    }
}
