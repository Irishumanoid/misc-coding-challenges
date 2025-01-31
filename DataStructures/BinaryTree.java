import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class BinaryTree {

    private Node root;

    public BinaryTree(Node root) {
        this.root = root;
    }

    private Node addRecursive(Node current, int value) {
        if (current == null) {
            return new Node(value);
        }

        if (value < current.value) {
            current.left = addRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    public void add(int value) {
        addRecursive(root, value);
    }

    private boolean containsNodeRecursive(Node current, int value) {
        if (current == null) {
            return false;
        }

        if (value < current.value) {
            return containsNodeRecursive(current.left, value);
        } else if (value > current.value) {
            return containsNodeRecursive(current.right, value);
        } else {
            return true;
        }
    }

    public boolean containsNode(int value) {
        return containsNodeRecursive(root, value);
    }

    private int findSmallestValue(Node root) {
        return root.left == null ? root.value : findSmallestValue(root.left);
    }

    private Node deleteRecursive(Node current, int value) {
        if (current == null) {
            return null;
        }

        if (value < current.value) {
            current.left = deleteRecursive(current.left, value);
        } else if (value > current.value) {
            current.right = deleteRecursive(current.right, value);
        } else {
            // deletion based on number of child nodes
            if (current.left == null && current.right == null) {
                return null;
            }
            if (current.left == null) {
                return current.right;
            }
            if (current.right == null) {
                return current.left;
            }
            int smallest = findSmallestValue(current.right);
            deleteRecursive(current.right, smallest);
            current.value = smallest;
            return current;
        }
        return current;
    }

    public void delete(int value) {
        deleteRecursive(root, value);
    }

    private void traverseInOrder(Node node) {
        if (node != null) {
            traverseInOrder(node.left);
            System.out.println(node.value);
            traverseInOrder(node.right);
        }
    }

    private void inOrderList(Node node, List<Integer> list) {
        if (node != null) {
            inOrderList(node.left, list);
            list.add(node.value);
            inOrderList(node.right, list);
        }
    }

    public void DFS() {
        traverseInOrder(root);
    }

    public void BFS() {
        if (root == null) {
            return;
        }

        Queue<Node> nodes = new LinkedList<>();
        nodes.add(root);

        while (!nodes.isEmpty()) {
            Node curNode = nodes.remove();
            if (curNode.left != null) {
                nodes.add(curNode.left);
            }
            if (curNode.right != null) {
                nodes.add(curNode.right);
            }
            System.out.println(curNode.value);
        }
    }

    private int getDepth(Node node) {
        if (node == null) {
            return 0;
        }
        if (node.left != null && node.right != null) {
            return Math.max(1 + getDepth(node.left), 1 + getDepth(node.right));
        }
        if (node.left != null) {
            return 1 + getDepth(node.left);
        }
        if (node.right != null) {
            return 1 + getDepth(node.right);
        }
        return 0;
    }

    public int getDepth() {
        return getDepth(root);
    }


    private boolean isBalanced(Node node) {
        if (node == null) {
            return true; 
        }

        int depthDiff = Math.abs(getDepth(node.left) - getDepth(node.right));
        if (depthDiff > 1) {
            return false;
        } else {
            return isBalanced(node.left) && isBalanced(node.right);
        }
    }

    public boolean isBalanced() {
        return isBalanced(root);
    }

    public List<Integer> mergeTrees(BinaryTree first, BinaryTree second) {
        List<Integer> firstList = new ArrayList<>();
        this.inOrderList(first.root, firstList);
        List<Integer> secondList = new ArrayList<>();
        this.inOrderList(second.root, secondList);

        int c1 = 0;
        int c2 = 0;
        List<Integer> mergedList = new ArrayList<>();
        while (c1 != firstList.size()-1 && c2 != secondList.size()-1) {
            int firstEntry = firstList.get(c1);
            int secondEntry = secondList.get(c2);
            if (firstEntry < secondEntry) {
                mergedList.add(firstEntry);
                c1 += 1;
            } else if (firstEntry > secondEntry) {
                mergedList.add(secondEntry);
                c2 += 1;
            } else {
                mergedList.add(firstEntry);
                c1 += 1;
                c2 += 1;
            }
        }
        return mergedList;
    }
    

    public static void main(String[] args) {
        Node node = new Node(0);
        BinaryTree tree = new BinaryTree(node);
        tree.add(2);
        tree.add(-1);
        tree.add(5);
        tree.add(7);
        tree.add(8);
        BinaryTree newTree = new BinaryTree(new Node(5));
        newTree.add(-10);
        newTree.add(9);
        newTree.add(10);
        newTree.add(8);
        System.out.println("depth is: " + tree.getDepth());
        List<Integer> out = tree.mergeTrees(tree, newTree);
        for (int i = 0; i < out.size(); i++) {
            System.out.println(out.get(i));
        }
        System.out.println("tree is balanced: " + newTree.isBalanced());
    }
}