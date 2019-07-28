package btviewer;

public class Node {

    private int content;
    private Node left;
    private Node right;

    public Node(int content) {
        this.content = content;
    }

    public int getContent() {
        return content;
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

}
