package btviewer;

public class Node {

    private int value;
    private Node left;
    private Node right;

    public Node(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public boolean isNotChildren() {
        return left == null & right == null;
    }

    public boolean isOnlyLeftChild() {
        return left != null & right == null;
    }

    public boolean isOnlyRightChild() {
        return left == null & right != null;
    }

    public boolean isBothChild() {
        return left != null & right != null;
    }

}
